package TourGuide;

import CommonBehaviours.AgtRegisterService;
import jade.core.Agent;

public class TourGuideAgent extends Agent {

	private static final long serialVersionUID = -2470451374035057773L;
	private String provideServiceType = "tours";
	private String serviceId;
	
	protected void setup(){
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			serviceId =  (String) args[0];
		}
		else {
			serviceId = "id1";
		}
		addBehaviour(new AgtRegisterService(this, provideServiceType, serviceId));
		addBehaviour(new TourReqListening(this));	
	}

}
