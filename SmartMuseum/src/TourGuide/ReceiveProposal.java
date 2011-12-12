package TourGuide;

import CommonBehaviours.MsgListener;
import CommonClasses.Proposal;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class ReceiveProposal extends SequentialBehaviour {

	private static final long serialVersionUID = 907635361591313226L;
	
	private int proposalStatus = 1;//keep proposing
	
	public ReceiveProposal(Agent a, final AID profiler) {
		super(a);

		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchSender(profiler),MessageTemplate.MatchPerformative(ACLMessage.PROPOSE)); 
		
		addSubBehaviour(new MsgListener(myAgent, 3000, mt) {
			
			public void handle( ACLMessage m) { 
				proposalStatus = 2;
				if(m!=null){
					System.out.println("tour guide - received proposal");
					try {
						checkProposal((Proposal)m.getContentObject(), profiler);
					} catch (UnreadableException e) {
						e.printStackTrace();
						System.exit(1);
					}
				}
			}
		});

	}
	
	private void checkProposal(Proposal p, AID profiler) {
		if(((TourGuideAgent)myAgent).checkProposal(p, profiler)) {
			System.out.println("tourGuide - accepted proposal");
			ACLMessage msg = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
			msg.addReceiver(profiler);
			myAgent.send(msg);		

			proposalStatus = 0;
		} else {
			System.out.println("tourGuide - rejected proposal");
			ACLMessage msg = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
			msg.addReceiver(profiler);
			myAgent.send(msg);	
			proposalStatus = 1;
		}
	}
	
	public int onEnd() {
		reset();
		return proposalStatus;
	}
}
