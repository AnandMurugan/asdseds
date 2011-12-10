package Profiler;

import CommonBehaviours.MsgListener;
import Profiler.profile.MuseumItem;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class VisitItem extends SequentialBehaviour {

	private static final long serialVersionUID = -3620911038429384820L;

	private String itemId;
	
	public VisitItem(Agent a, String itemId) {
		super(a);
		this.itemId = itemId;
	}
	
	public void onStart() {
		System.out.println("getting item " + itemId);
		ACLMessage msg = new ACLMessage(ACLMessage.QUERY_IF);
		msg.addReceiver(((ProfilerAgent)myAgent).getCurator());
		msg.setContent(itemId);
		myAgent.send(msg);
		System.out.println(((ProfilerAgent)myAgent).getCurator());
		
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM); 
		addSubBehaviour(new MsgListener(myAgent, 3000, mt) {

			private static final long serialVersionUID = 3832509858218821267L;

			public void handle( ACLMessage m) { 
				if(m!=null){
					if(m.getPerformative() == ACLMessage.INFORM){
						try {
							((ProfilerAgent)myAgent).visitItem((MuseumItem)m.getContentObject());
						} catch (UnreadableException e) {
							e.printStackTrace();
							System.exit(1);
						}
					}
				}
			}			
		});
	}
}
