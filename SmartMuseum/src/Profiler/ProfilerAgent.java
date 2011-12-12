package Profiler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import CommonClasses.ProfileObject;
import CommonClasses.Proposal;
import Profiler.profile.MuseumItem;
import Profiler.profile.ProfileManager;
import Profiler.profile.ProfileType;

import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.AID;
import jade.core.Agent;
import jade.core.Location;
import jade.domain.JADEAgentManagement.QueryPlatformLocationsAction;
import jade.domain.mobility.MobilityOntology;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ProfilerAgent extends Agent{
	private static final long serialVersionUID = 2555041222033394491L;
	
	private AID tourGuide;
	private AID curator;
	private String home;
	private String curatorHome;
	private boolean isHome = true;
	private ArrayList<String> tour;
	private int tourNr = 0;
	private String proposalFile;
	private String profileFile;
	
	protected void setup(){
		
		getContentManager().registerLanguage(new SLCodec());
		getContentManager().registerOntology(MobilityOntology.getInstance());

		Object[] args = getArguments();
		if (args != null && args.length == 4) {
			home = (String)args[0];
			curatorHome = (String)args[1];
			proposalFile = (String)args[2];
			profileFile = (String)args[3];
		} else {
			System.out.println("arg error");
			System.exit(1);
		}
		
		addBehaviour(new SearchTourGuide(this));
		addBehaviour(new SearchCurator(this));
		startTourNegotiation();
	}
	
	public void startTourNegotiation() {
		addBehaviour(new TourNegotiation(this, tourNr));
	}
	
	public void setTourGuide(AID tourGuide) {
		this.tourGuide = tourGuide;
	}
	
	public AID getTourGuide() {
		return tourGuide;
	}
	
	public void setCurator(AID curator) {
		this.curator = curator;
	}
	
	public AID getCurator() {
		return curator;
	}
	
	public void visitTour(ArrayList<String> tour) {

		this.tour = tour;
		Map locations = getAvailableLocations();

		System.out.println("moving to curator to visit");
		doWait(5000);
		
		Location dest = (Location)locations.get(curatorHome);
		doMove(dest);
	}
	
	public void goHome() {
		Map locations = getAvailableLocations();
		
		System.out.println("moving back home");
		doWait(5000);
		
		Location dest = (Location)locations.get(home);
		doMove(dest);
	}

	public void afterMove() {
		getContentManager().registerLanguage(new SLCodec());
		getContentManager().registerOntology(MobilityOntology.getInstance());

		if(isHome) {
			isHome = false;
			addBehaviour(new VisitTour(this, tour));
		} else {
			isHome = true;
			tourNr++;
			if(tourNr < 3) {
				startTourNegotiation();
			} else {
				System.out.println("finished touring");
			}
		}
		
	}
	
	public String getProposalFilePath() {
		return proposalFile;
	}
	
	public void visitItem(MuseumItem museumItem) {
		Random rand = new Random((new Date()).getTime());
		System.out.println("item visited");
		museumItem.setRating(rand.nextInt(6));
		ProfileManager pm = new ProfileManager();
		ProfileType profile = pm.getProfile(profileFile);
		profile.getVisitedItems().getVisitedItem().add(museumItem);
		pm.dumpProfile(profile, profileFile);
	}
	
	private Map getAvailableLocations() {
		Map locations = new HashMap();
		// Get available locations with AMS
		sendRequest(new Action(getAMS(), new QueryPlatformLocationsAction()));

		//Receive response from AMS
		MessageTemplate mt = MessageTemplate.and(
				MessageTemplate.MatchSender(getAMS()),
				MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		ACLMessage resp = blockingReceive(mt);
		ContentElement ce;

		try {
			ce = getContentManager().extractContent(resp);
			Result result = (Result) ce;
			jade.util.leap.Iterator it = result.getItems().iterator();
			while (it.hasNext()) {
				Location loc = (Location)it.next();
				locations.put(loc.getName(), loc);
			}
			return locations;
		} catch (UngroundedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CodecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OntologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	void sendRequest(Action action) {
		// ---------------------------------

		ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
		request.setLanguage(new SLCodec().getName());
		request.setOntology(MobilityOntology.getInstance().getName());
		try {
			getContentManager().fillContent(request, action);
			request.addReceiver(action.getActor());
			send(request);
		}
		catch (Exception ex) { ex.printStackTrace(); }
	}

	
	/*
	 * payment functions
	 */
	public ProfileObject getPayment(Proposal p) {
		ProfileObject payment = new ProfileObject();
		Iterator<String> it = p.getPrice().iterator();

		while(it.hasNext()) {
			String profilePart = it.next();

			if(profilePart.equals("P1")) {
				payment.setP1(getP1());
			} else if(profilePart.equals("P2")) {
				payment.setP2(getP2());
			} else if(profilePart.equals("P3_1")) {
				payment.setP3_1(getP3(0, 15));
			} else if(profilePart.equals("P3_2")) {
				payment.setP3_2(getP3(15,30));
			}
		}
		return payment;
	}

	private Map<String, String> getP1() {
		Map<String, String> p1 = new HashMap<String, String>();
		ProfileManager pm = new ProfileManager();
		ProfileType profile = pm.getProfile(profileFile);
		
		p1.put("age", "" + profile.getAge());
		p1.put("education", profile.getEducation());
		p1.put("motivation", profile.getMotivationOfVisit());
		
		return p1;
	}
	
	private List<String> getP2() {
		ProfileManager pm = new ProfileManager();
		ProfileType profile = pm.getProfile(profileFile);
		
		return profile.getInterests().getInterest();
	}
	
	private Map<String, Integer> getP3(int start, int end) {
		ProfileManager pm = new ProfileManager();
		ProfileType profile = pm.getProfile(profileFile);
		
		Map<String, Integer> ratings = new HashMap<String, Integer>();
		List<MuseumItem> l = profile.getVisitedItems().getVisitedItem();
		MuseumItem item;
		for(int i = start; i < end; i++) {
			item = l.get(i);
			int r = (int) item.getRating();
			ratings.put(item.getId(), r);
		}
		return ratings;
	}
	
	public Set<String> getVisitedItems() {
		ProfileManager pm = new ProfileManager();
		ProfileType profile = pm.getProfile(profileFile);
		
		Set<String> visited = new HashSet<String>();
		List<MuseumItem> l = profile.getVisitedItems().getVisitedItem();
		MuseumItem item;
		for(int i = 0; i < l.size(); i++) {
			item = l.get(i);
			visited.add(item.getId());
		}
		return visited;
	}
}


