package TourGuide;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class SendTourProposal extends SimpleBehaviour {

	private static final long serialVersionUID = -8890150364625075207L;
	private int tourSent = 1;
	private ACLMessage msg;

	public SendTourProposal(Agent a, ACLMessage msg) {
		super(a);
		this.msg = msg;
	}

	@Override
	public void action() {
		System.out.println("Sending tour proposal...");
		ACLMessage reply = msg.createReply();
		reply.setPerformative(ACLMessage.INFORM);
		reply.setContent("Start negotiation...");
		myAgent.send(reply);
	}

	@Override
	public boolean done() {
		return true;
	}
	
	public int OnEnd(){
		return tourSent;
	}

}
