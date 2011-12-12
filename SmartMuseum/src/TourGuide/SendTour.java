//package TourGuide;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//import jade.core.Agent;
//import jade.core.behaviours.SimpleBehaviour;
//import jade.lang.acl.ACLMessage;
//
//public class SendTour extends SimpleBehaviour {
//
//	private static final long serialVersionUID = -8890150364625075207L;
//	private ACLMessage msg;
//	private static final int T1=1;
//	private static final int T2=2;
//	private static final int T3=3;
//
//	public SendTour(Agent a, ACLMessage msg) {
//		super(a);
//		this.msg = msg;
//	}
//
//	@Override
//	public void action() {
//		System.out.println("Sending tour proposal...");
//		ACLMessage reply = msg.createReply();
//		reply.setPerformative(ACLMessage.INFORM);
//		try {
//			reply.setContentObject(getTour(T1));
//		} catch (IOException e) {
//			e.printStackTrace();
//			System.exit(1);
//		}
//		myAgent.send(reply);
//	}
//
//	@Override
//	public boolean done() {
//		return true;
//	}
//
//	public ArrayList<String> getTour(int tourType) {
//		switch(tourType){
//		case T1:
//			return ((TourGuideAgent)myAgent).getTourT1();
//		case T2:
//			return ((TourGuideAgent)myAgent).getTourT2();
//		case T3:
//			return ((TourGuideAgent)myAgent).getTourT3();
//		}
//		return null;
//	}
//
//}
