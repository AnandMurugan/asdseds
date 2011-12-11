package Profiler;

import CommonBehaviours.MsgListener;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;

public class SendProposal extends SequentialBehaviour {

	private static final long serialVersionUID = 907635361591313226L;
	
	private int proposalStatus = 1;//keep proposing
	private TourNegotiation tour;
	
	public SendProposal(Agent a, TourNegotiation tour) {
		super(a);
		this.tour = tour;
	}
	
	public void onStart() {
		String proposal = tour.getCurrentProposal();
		System.out.println("sending proposal " + proposal);
		ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
		msg.addReceiver(((ProfilerAgent)myAgent).getTourGuide());
		myAgent.send(msg);		

		addSubBehaviour(new MsgListener(myAgent, 3000, null) {
			private static final long serialVersionUID = 8955673657803014129L;
			
			public void handle( ACLMessage m) { 
				proposalStatus = 2;
				if(m!=null){
					if(m.getPerformative() == ACLMessage.ACCEPT_PROPOSAL){
						System.out.println("profiler - received ACCEPT_PROPOSAL");
						proposalStatus = 0;
					} else if(m.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
						System.out.println("profiler - received REJECT_PROPOSAL");
						tour.goToNextProposal();
						proposalStatus = 1;
					}
				}
			}
		});
	}
	
	public int onEnd() {
		return proposalStatus;
	}
}
