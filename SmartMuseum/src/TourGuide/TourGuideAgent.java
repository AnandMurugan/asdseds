package TourGuide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import CommonBehaviours.AgtRegisterService;
import CommonClasses.Proposal;
import jade.core.AID;
import jade.core.Agent;

public class TourGuideAgent extends Agent {

	private static final long serialVersionUID = -2470451374035057773L;
	private String provideServiceType = "tourGuide";
	private String serviceId;
	
	private Map<Integer,Set<String>> acceptedProposals;
	private Map<AID,ProfileObject> Profile = new HashMap<AID,ProfileObject>(); 
	
	protected void setup(){
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
		acceptedProposals = new HashMap<Integer, Set<String>>();
		Set price;
		
		price = new HashSet<String>();
		price.add("P3_1");
		price.add("P3_2");
		acceptedProposals.put(3, price);
		
		price = new HashSet<String>();
		price.add("P3_1");
		acceptedProposals.put(2, price);
		
		price = new HashSet<String>();
		price.add("P3_2");
		acceptedProposals.put(2, price);

		price = new HashSet<String>();
		price.add("P2");
		acceptedProposals.put(2, price);
		
		price = new HashSet<String>();
		price.add("P1");
		acceptedProposals.put(1, price);
	}


	public boolean checkProposal(Proposal p, AID profiler) {
		return false;
	}
	
	public Map<AID,ProfileObject> getProfile() {
		return Profile;
	}

	public ArrayList<String> getTourT1(){
		ArrayList<String> tour = new ArrayList<String>();
		tour.add("urn:imss:instrument:401037");
		tour.add("urn:imss:instrument:414141");
		tour.add("urn:imss:instrument:405030");
		tour.add("urn:imss:instrument:414116");
		tour.add("urn:imss:instrument:404014");
		tour.add("urn:imss:instrument:402009");
		tour.add("urn:imss:instrument:414108");
		tour.add("urn:imss:instrument:414091");
		tour.add("urn:imss:instrument:416001");
		tour.add("urn:imss:instrument:402048");
		tour.add("urn:imss:instrument:401038");
		tour.add("urn:imss:instrument:402033");
		tour.add("urn:imss:instrument:401049");
		tour.add("urn:imss:instrument:402023");
		tour.add("urn:imss:instrument:403062");
		return tour;
	}
	
	public ArrayList<String> getTourT2(){
		ArrayList<String> tour = new ArrayList<String>();
		return tour;
	}
	
	public ArrayList<String> getTourT3(){
		ArrayList<String> tour = new ArrayList<String>();
		return tour;
	}

}
