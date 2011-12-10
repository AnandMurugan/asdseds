package CuratorAgent;

import java.io.IOException;

import CommoClasses.MuseumItem;
import CommoClasses.ReadExcel;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ArtifactReqListening extends CyclicBehaviour{
	
	private static final long serialVersionUID = -829740294104470033L;
	
	public ArtifactReqListening(Agent a) {
		super(a);
	}
	
	public void action() {
		final ACLMessage msg = myAgent.receive( MessageTemplate.MatchPerformative( ACLMessage.REQUEST ) );
		final String Id;
//		if(msg.getContent()!=null){
//			Id = msg.getContent();
//		}
		Id="urn:imss:instrument:401037";
		if (msg!=null) {
			System.out.println("new artifact request");
			myAgent.addBehaviour(new SimpleBehaviour() {
				
				private static final long serialVersionUID = 1L;

				@Override
				public boolean done() {
					return true;
				}
				
				@Override
				public void action() {
					ReadExcel readExcel = new ReadExcel();
					MuseumItem item = readExcel.RetrieveItem(Id);
					ACLMessage reply = msg.createReply();
					reply.setPerformative(ACLMessage.INFORM);
					try {
						reply.setContentObject(item);
					} catch (IOException e) {
						e.printStackTrace();
					}
					myAgent.send(reply);
				}
			});
		}
		block();
	}
}

