package TourGuide;

import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class TourNegotiation extends FSMBehaviour {
	
	private static final long serialVersionUID = -7698043134484210372L;
	private static final String STATE_A = "Send_TourProposal";
	private static final String STATE_B = "Send_EndNegotiation";
	private ACLMessage msg;

	public TourNegotiation(Agent a, ACLMessage msg) {
		super(a);
		this.msg = msg;
		registerFirstState(new SendTour(myAgent, msg), STATE_A);
		registerLastState(new SimpleBehaviour(myAgent) {
			
			private static final long serialVersionUID = 1L;

			public boolean done() {
				return true;
			}
			
			public void action() {
				System.out.println("negotiation finished");
			}
		}, STATE_B);
		
		registerDefaultTransition(STATE_A, STATE_B);
	}

	

	
}
