package CommonBehaviours;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class MsgListener extends SimpleBehaviour {

	private static final long serialVersionUID = 8918059420973943016L;
	private MessageTemplate template;
	private long    timeOut, 
	wakeupTime;
	private boolean finished;
	private ACLMessage msg;
	
	public MsgListener(Agent agt, int millis, MessageTemplate template) {
		super(agt);
		timeOut = millis;
		this.template = template;
	}

	public MsgListener() {
		System.out.println("!!!!! this constructor ...error");
	}

	public ACLMessage getMessage() { 
		return msg; 
	}

	public void onStart() {
		wakeupTime = (timeOut<0 ? Long.MAX_VALUE:System.currentTimeMillis() + timeOut);
	}

	public boolean done () {
		return finished;
	}


	public void action() {
		if(template == null)
			msg = myAgent.receive();
		else
			msg = myAgent.receive(template);

		if( msg != null) {
			finished = true;
			handle( msg );
			return;
		}
		long dt = wakeupTime - System.currentTimeMillis();
		if ( dt > 0 ) 
			block(dt);
		else {
			finished = true;
			handle( msg );
		}
	}
	
	public void handle( ACLMessage m) { /* can be redefined in sub_class */ }
	
	public void reset() {
		msg = null;
		finished = false;
		super.reset();
	}

	public void reset(int dt) {
		timeOut= dt;
		reset();
	}
}
