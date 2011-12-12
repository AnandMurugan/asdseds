package Profiler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import CommonBehaviours.MsgListener;
import CommonClasses.ProfileObject;
import CommonClasses.Proposal;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class EndNegotiation extends SequentialBehaviour {

	private static final long serialVersionUID = 4562228487637705996L;
	private TourNegotiation tour;

	public EndNegotiation(Agent a, TourNegotiation tour) {
		super(a);
		this.tour = tour;
	}

	public void onStart() {
		System.out.println("profiler - ending negotiation");
		System.out.println("accepted proposal " + tour.getCurrentProposal().getTour() + " " + tour.getCurrentProposal().getPrice());
		
		Proposal proposal = tour.getCurrentProposal();
		ProfileObject payment = ((ProfilerAgent)myAgent).getPayment(proposal);
		ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
		try {
			msg1.setContentObject(payment);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		msg1.addReceiver(((ProfilerAgent)myAgent).getTourGuide());
		myAgent.send(msg1);

		Set<String> visitedItems = ((ProfilerAgent)myAgent).getVisitedItems();
		ACLMessage msg2 = new ACLMessage(ACLMessage.INFORM_IF);
		try {
			msg2.setContentObject((Serializable) visitedItems);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		msg2.addReceiver(((ProfilerAgent)myAgent).getTourGuide());
		myAgent.send(msg2);


		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchSender(((ProfilerAgent)myAgent).getTourGuide()),MessageTemplate.MatchPerformative(ACLMessage.INFORM)); 
		addSubBehaviour(new MsgListener(myAgent, 3000, mt) {

			public void handle( ACLMessage m) { 
				if(m!=null){
					System.out.println("profiler - received tour");
					try {
						((ProfilerAgent)myAgent).visitTour((ArrayList<String>)m.getContentObject());
					} catch (UnreadableException e) {
						e.printStackTrace();
						System.exit(1);
					}
				}
			}		
		});
	}
}
