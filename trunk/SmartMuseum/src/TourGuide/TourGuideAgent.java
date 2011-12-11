package TourGuide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import CommonBehaviours.AgtRegisterService;
import CommonClasses.Proposal;
import CommonClasses.ReadExcel;
import jade.core.AID;
import jade.core.Agent;

public class TourGuideAgent extends Agent {

	private static final long serialVersionUID = -2470451374035057773L;
	private String provideServiceType = "tourGuide";
	private String serviceId;
	
	private Set<Proposal> acceptedProposals;
	private Map<AID,ProfileObject> profile = new HashMap<AID,ProfileObject>(); 
	private ReadExcel itemDb;
	protected void setup(){
		itemDb = new ReadExcel();
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			serviceId =  (String) args[0];
		}
		else {
			serviceId = "id1";
		}
		
		loadAcceptedProposals();
		
		addBehaviour(new AgtRegisterService(this, provideServiceType, serviceId));
		addBehaviour(new TourReqListening(this));	
	}
	
	private void loadAcceptedProposals() {
		acceptedProposals = new HashSet<Proposal>();
		Set<String> price;
		Proposal p;
		
		price = new HashSet<String>();
		price.add("P3_1");
		price.add("P3_2");
		p = new Proposal(3, price);
		acceptedProposals.add(p);
		
		price = new HashSet<String>();
		price.add("P3_1");
		p = new Proposal(2, price);
		acceptedProposals.add(p);
		
		price = new HashSet<String>();
		price.add("P3_2");
		p = new Proposal(2, price);
		acceptedProposals.add(p);

		price = new HashSet<String>();
		price.add("P2");
		p = new Proposal(2, price);
		acceptedProposals.add(p);
		
		price = new HashSet<String>();
		price.add("P1");
		p = new Proposal(1, price);
		acceptedProposals.add(p);
	}


	public boolean checkProposal(Proposal p, AID profiler) {
		if(acceptedProposals.contains(p)) {
			Set<String> price = p.getPrice();
			Iterator<String> it = price.iterator();
			ProfileObject profileObj = profile.get(profiler);
			
			while(it.hasNext()) {
				String profilePart = it.next();
				
				if(profilePart.equals("P1")) {
					if(profileObj.hasP1()) {
						return false;
					}
				} else if(profilePart.equals("P2")) {
					if(profileObj.hasP2()) {
						return false;
					}
				} else if(profilePart.equals("P3_1")) {
					if(profileObj.hasP3_1()) {
						return false;
					}
				} else if(profilePart.equals("P3_2")) {
					if(profileObj.hasP3_2()) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public Map<AID,ProfileObject> getProfile() {
		return profile;
	}

	public ArrayList<String> getTourT1(){
		ArrayList<String> tour = new ArrayList<String>();
		tour = itemDb.getRandomTour(); 
		return tour;
	}
	
	public ArrayList<String> getTourT2(){
		ArrayList<String> tour = new ArrayList<String>();
		ArrayList<String> interests = new ArrayList<String>();
		interests.add("astronomy");
		interests.add("art");
		tour = itemDb.getTourT2(interests);
		return tour;
	}
	
	public ArrayList<String> getTourT3(){
		ArrayList<String> tour = new ArrayList<String>();
		return tour;
	}

}
