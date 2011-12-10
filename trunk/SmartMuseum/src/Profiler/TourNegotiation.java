package Profiler;

import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.SimpleBehaviour;

public class TourNegotiation extends FSMBehaviour{

	private static final long serialVersionUID = -9896598676906028L;

	private static final String STATE_A = "Start_Tour_Negotiation";
	private static final String STATE_B = "End_Negotiation_Success";
	private static final String STATE_C = "End_Negotiation_Fail";

	public TourNegotiation(Agent a) {
		super(a);

		registerFirstState(new StartNewNegotiation(myAgent), STATE_A);

		registerLastState(new SimpleBehaviour(myAgent) {

			private static final long serialVersionUID = 8762119629160737331L;

			@Override
			public boolean done() {
				return true;
			}

			@Override
			public void action() {
				System.out.println("end negotiation success");
			}
		}, STATE_B);

		registerLastState(new SimpleBehaviour(myAgent) {

			private static final long serialVersionUID = -5941691593426959661L;

			@Override
			public boolean done() {
				return true;
			}

			@Override
			public void action() {
				System.out.println("end negotiation fail");
			}
		}, STATE_C);

		registerTransition(STATE_A, STATE_B, 1);
		registerTransition(STATE_A, STATE_C, 0);
	}

}
