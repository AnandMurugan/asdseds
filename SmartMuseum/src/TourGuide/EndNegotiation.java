package TourGuide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import CommonBehaviours.MsgListener;
import Profiler.ProfilerAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class EndNegotiation extends SequentialBehaviour {

	private AID profiler;
	private Set<String> visitedItems;
	
	public EndNegotiation(Agent a, AID profiler) {
		super(a);
		this.profiler = profiler;
	}
	
	public void onStart() {
		System.out.println("tourGuide - ending negotiation");
	
		MessageTemplate mt1 = MessageTemplate.and(MessageTemplate.MatchSender(profiler), MessageTemplate.MatchPerformative(ACLMessage.INFORM)); 
		addSubBehaviour(new MsgListener(myAgent, 3000, mt1) {

			public void handle(ACLMessage m) { 
				if(m != null){
					System.out.println("tourGuide - received payment");
//					try {
//						((TourGuideAgent)myAgent).addPayment(profiler, m.getContentObject());
//					} catch (UnreadableException e) {
//						e.printStackTrace();
//						System.exit(1);
//					}
				}
			}		
		});

		MessageTemplate mt2 = MessageTemplate.and(MessageTemplate.MatchSender(profiler), MessageTemplate.MatchPerformative(ACLMessage.INFORM_IF)); 
		addSubBehaviour(new MsgListener(myAgent, 3000, mt2) {

			public void handle( ACLMessage m) { 
				if(m!=null){
					System.out.println("tourGuide - received visited items");
					try {
						visitedItems = (Set<String>)m.getContentObject();
					} catch (UnreadableException e) {
						e.printStackTrace();
						System.exit(1);
					}
				}
			}		
		});
		
		addSubBehaviour(new SimpleBehaviour() {
			
			@Override
			public boolean done() {
				return true;
			}
			
			@Override
			public void action() {
				ArrayList<String> tour = ((TourGuideAgent)myAgent).getTourT1(visitedItems);
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				try {
					msg.setContentObject(tour);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
				msg.addReceiver(profiler);
				myAgent.send(msg);

			}
		};

	}
}
