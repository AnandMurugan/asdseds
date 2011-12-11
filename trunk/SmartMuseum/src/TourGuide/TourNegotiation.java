package TourGuide;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class TourNegotiation extends FSMBehaviour {
	
	private static final long serialVersionUID = -7698043134484210372L;
	private static final String STATE_A = "Send_Tour_OK";
	private static final String STATE_B = "Receiving_Proposal";
	private static final String STATE_C = "End_Negotiation_Success";
	private static final String STATE_D = "End_Negotiation_Error";
	
	public TourNegotiation(Agent a, final AID profiler) {
		super(a);

		//sending ok can start negotiation
		registerFirstState(new SimpleBehaviour() {
			
			@Override
			public boolean done() {
				return true;
			}
			
			@Override
			public void action() {
				System.out.println("tourGuide - Ok new negotiation");
				ACLMessage msg = new ACLMessage(ACLMessage.CONFIRM);
				msg.addReceiver(profiler);
				myAgent.send(msg);		

			}
		}, STATE_A);
		
		registerState(new ReceiveProposal(myAgent, profiler), STATE_B);
		
		registerState(new EndNegotiation(myAgent, profiler), STATE_C);
		
		registerLastState(new SimpleBehaviour() {
			

			public boolean done() {
				return true;
			}
			
			public void action() {
				System.out.println("negotiation failed error");
			}
		}, STATE_D);
		
		registerDefaultTransition(STATE_A, STATE_B);
		registerTransition(STATE_B, STATE_B, 1);
		registerTransition(STATE_B, STATE_C, 0);
		registerTransition(STATE_B, STATE_D, 2);
	}
}
