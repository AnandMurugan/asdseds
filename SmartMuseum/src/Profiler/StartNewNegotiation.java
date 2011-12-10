package Profiler;

import CommonBehaviours.MsgListener;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class StartNewNegotiation extends SequentialBehaviour {

	private static final long serialVersionUID = 6608925789953580146L;

	private int negotiationStatus = 0;
	
	public StartNewNegotiation(Agent a) {
		super(a);
	}
	
	@Override
	public void onStart() {
		System.out.println("starting new tour negotiation");
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST); 
		msg.addReceiver(((ProfilerAgent)myAgent).getTourGuide());
		myAgent.send(msg);
		
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM); 
		addSubBehaviour(new MsgListener(myAgent, 3000, mt) {
			private static final long serialVersionUID = 8955673657803014129L;

			public void handle( ACLMessage m) { 
				if(m!=null){
					if(m.getPerformative() == ACLMessage.INFORM){
						System.out.println("negotiation started");
						negotiationStatus = 1;
					}
				}
			}			
		});
	}
	
	public int onEnd() {
		return negotiationStatus;
	}
}
