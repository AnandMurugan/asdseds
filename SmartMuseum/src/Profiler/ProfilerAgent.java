package Profiler;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

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
import jade.util.leap.HashMap;
import jade.util.leap.Map;

public class ProfilerAgent extends Agent{
	private static final long serialVersionUID = 2555041222033394491L;
	
	private AID tourGuide;
	private AID curator;
	private String home;
	private String curatorHome;
	private boolean isHome = true;
	private ArrayList<String> tour;
	private int nrOfTours = 0;
	private int profileNr = 1;
	
	protected void setup(){
		
		getContentManager().registerLanguage(new SLCodec());
		getContentManager().registerOntology(MobilityOntology.getInstance());

		Object[] args = getArguments();
		if (args != null && args.length == 2) {
			home = (String)args[0];
			curatorHome = (String)args[1];
		} else {
			System.out.println("arg error");
			System.exit(1);
		}
		
		addBehaviour(new SearchTourGuide(this));
		addBehaviour(new SearchCurator(this));
		startTourNegotiation();
	}
	
	public void startTourNegotiation() {
		addBehaviour(new TourNegotiation(this));
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
			nrOfTours++;
			if(nrOfTours < 3) {
				startTourNegotiation();
			} else {
				System.out.println("finished touring");
			}
		}
		
	}
	
	public void visitItem(MuseumItem museumItem) {
		Random rand = new Random((new Date()).getTime());
		System.out.println("item visited");
		museumItem.setRating(rand.nextInt(6));
		ProfileManager pm = new ProfileManager();
		ProfileType profile = pm.getProfile("Profile.xml", profileNr);
		profile.getVisitedItems().getVisitedItem().add(museumItem);
		pm.dumpProfile(profile, "Profile.xml");
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


}


