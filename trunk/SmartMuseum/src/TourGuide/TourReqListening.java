package TourGuide;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class TourReqListening extends CyclicBehaviour{
	
	private static final long serialVersionUID = -829740294104470033L;
	
	public TourReqListening(Agent a) {
		super(a);
	}
	
	public void action() {
		ACLMessage msg = myAgent.receive( MessageTemplate.MatchPerformative( ACLMessage.REQUEST ) );
		if (msg!=null) {
			System.out.println("tour request");
			myAgent.addBehaviour(new TourNegotiation(myAgent, msg.getSender()));
		}
		block();
	}

}
