package CuratorAgent;

import CommonBehaviours.AgtRegisterService;
import jade.core.Agent;

public class CuratorAgent extends Agent{

	private static final long serialVersionUID = -2044310633077125483L;
	private String provideServiceType = "artifacts";
	private String serviceName;
	
	protected void setup(){
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			serviceName =  (String) args[0];
			System.out.println("got serviceName");
		}
		else {
			System.out.println("No serviceName specified");
			serviceName = "id1";
		}
		addBehaviour(new AgtRegisterService(this,provideServiceType, serviceName));
		addBehaviour(new ArtifactReqListening(this));
	}
}
