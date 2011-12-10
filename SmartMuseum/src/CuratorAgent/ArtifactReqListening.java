package CuratorAgent;

import java.io.IOException;

import CommoClasses.ReadExcel;
import Profiler.profile.MuseumItem;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ArtifactReqListening extends CyclicBehaviour{

	private static final long serialVersionUID = -829740294104470033L;
	private String Id;
	public ArtifactReqListening(Agent a) {
		super(a);
	}

	public void action() {
		final ACLMessage msg = myAgent.receive( MessageTemplate.MatchPerformative( ACLMessage.QUERY_IF ) );
		//		Id="urn:imss:instrument:401037";
		if (msg!=null) {
			System.out.println("Museum item query request.");
			Id = msg.getContent();
			myAgent.addBehaviour(new SimpleBehaviour() {

				private static final long serialVersionUID = 1L;

				@Override
				public boolean done() {
					return true;
				}

				@Override
				public void action() {
					ReadExcel readExcel = new ReadExcel();
					ACLMessage reply = msg.createReply();
					reply.setPerformative(ACLMessage.INFORM);
					MuseumItem item = readExcel.RetrieveItem(Id);
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

