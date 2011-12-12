package Profiler;

import java.io.IOException;

import CommonBehaviours.MsgListener;
import CommonClasses.Proposal;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;

public class SendProposal extends SequentialBehaviour {

	private static final long serialVersionUID = 907635361591313226L;
	
	private int proposalStatus = 1;//keep proposing
	private TourNegotiation tour;
	private Behaviour b;
	
	public SendProposal(Agent a, TourNegotiation tour) {
		super(a);
		this.tour = tour;
	}
	
	public void onStart() {
		Proposal proposal = tour.getCurrentProposal();
		System.out.println("profiller - sending proposal");
		ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
		try {
			msg.setContentObject(proposal);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		msg.addReceiver(((ProfilerAgent)myAgent).getTourGuide());
		myAgent.send(msg);		

		addSubBehaviour(b = new MsgListener(myAgent, 3000, null) {
			private static final long serialVersionUID = 8955673657803014129L;
			
			public void handle(ACLMessage m) { 
				System.out.println("here in ms");
				proposalStatus = 2;
				if(m!=null){
					if(m.getPerformative() == ACLMessage.ACCEPT_PROPOSAL){
						System.out.println("profiler - received ACCEPT_PROPOSAL");
						proposalStatus = 0;
						return;
					} else if(m.getPerformative() == ACLMessage.REJECT_PROPOSAL) {
						System.out.println("profiler - received REJECT_PROPOSAL");
						tour.goToNextProposal();
						proposalStatus = 1;
						return;
					}
				}
			}
		});
	}
	
	public void setProposalStatus(int p) {
		proposalStatus = p;
	}
	
	public int onEnd() {
		if(proposalStatus == 1) {
			this.removeSubBehaviour(b);
			reset();
			return 1;
		} else if(proposalStatus == 0) {
			return 0;
		} 
		return 2;
	}
}
