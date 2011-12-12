package TourGuide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import CommonBehaviours.AgtRegisterService;
import CommonClasses.ProfileObject;
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
		Iterator<Proposal> iter = acceptedProposals.iterator();
		while(iter.hasNext()) {
			Proposal current = iter.next();
			if(current.equals(p)) {
				Set<String> price = p.getPrice();
				Iterator<String> it = price.iterator();
				ProfileObject profileObj = profile.get(profiler);

				if(profileObj != null) {
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
					return true;
				} else {
					return true;
				}
			}
		}
		return false;
	}

	public Map<AID,ProfileObject> getProfile() {
		return profile;
	}

	public ArrayList<String> getTourT1(Set<String> visitedItemIdSet){
		ArrayList<String> tour = new ArrayList<String>();
		tour = itemDb.getRandomTour(visitedItemIdSet); 
		return tour;
	}

	public ArrayList<String> getTourT2(AID profilerAID, Set<String> visitedItemIdSet){
		ArrayList<String> tour = new ArrayList<String>();
		List<String> interests = new ArrayList<String>();
		if(profile.get(profilerAID)!=null && profile.get(profilerAID).hasP2()){
			interests = profile.get(profilerAID).getP2();
		}
		if(interests.size()==0){
			tour = getTourT3(profilerAID, visitedItemIdSet);
		}else{
			tour = itemDb.getTourByInterest(interests, visitedItemIdSet);	
		}
		return tour;
	}

	public ArrayList<String> getTourT3(AID profilerAID, Set<String> visitedItemIdSet){
		ArrayList<String> tour = new ArrayList<String>();
		Map<String, Integer> rankingP3 = new HashMap<String, Integer>();
		if(profile.get(profilerAID)!=null){
			if(profile.get(profilerAID).hasP3_1()){
				rankingP3 = profile.get(profilerAID).getP3_1();
			}
			if(profile.get(profilerAID).hasP3_2()){
				rankingP3.putAll(profile.get(profilerAID).getP3_2());
			}
		}
		tour = itemDb.getTourByRating(rankingP3, visitedItemIdSet);
		return tour;
	}
	
	public void addPayment(AID profiler, ProfileObject payment) {
		ProfileObject profileObj = profile.get(profiler);
		if(profileObj == null) {
			profile.put(profiler, payment);
			return;
		}
		if(payment.hasP1()) {
			profileObj.setP1(payment.getP1());
		}
		if(payment.hasP2()) {
			profileObj.setP2(payment.getP2());
		}
		if(payment.hasP3_1()) {
			profileObj.setP3_1(payment.getP3_1());
		}
		if(payment.hasP3_2()) {
			profileObj.setP3_2(payment.getP3_2());
		}
	}
}
