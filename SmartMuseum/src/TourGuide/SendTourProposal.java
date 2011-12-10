package TourGuide;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;

public class SendTourProposal extends SimpleBehaviour {

	private static final long serialVersionUID = -8890150364625075207L;
	private int tourSent = 1;

	public SendTourProposal(Agent a) {
		super(a);
	}

	@Override
	public void action() {
		System.out.println("Sending tour proposal...");
	}

	@Override
	public boolean done() {
		return true;
	}
	
	public int OnEnd(){
		return tourSent;
	}

}
