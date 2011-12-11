package TourGuide;

import java.io.IOException;
import java.util.ArrayList;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class SendTour extends SimpleBehaviour {

	private static final long serialVersionUID = -8890150364625075207L;
	private ACLMessage msg;

	public SendTour(Agent a, ACLMessage msg) {
		super(a);
		this.msg = msg;
	}

	@Override
	public void action() {
		System.out.println("Sending tour proposal...");
		ACLMessage reply = msg.createReply();
		reply.setPerformative(ACLMessage.INFORM);
		try {
			reply.setContentObject(getTour());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		myAgent.send(reply);
	}

	@Override
	public boolean done() {
		return true;
	}

	public ArrayList<String> getTour() {
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

}
