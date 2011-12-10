package CuratorAgent;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ArtifactReqListening extends CyclicBehaviour{
	
	private static final long serialVersionUID = -829740294104470033L;
	
	public ArtifactReqListening(Agent a) {
		super(a);
	}
	
	public void action() {
		ACLMessage msg = myAgent.receive( MessageTemplate.MatchPerformative( ACLMessage.REQUEST ) );
		if (msg!=null) {
			System.out.println("new artifact request");
			ACLMessage reply = msg.createReply();
			reply.setContent("OK");
			myAgent.send(reply);
		}
		block();
	}
}

