package Profiler;

import jade.core.AID;
import jade.core.Agent;

public class ProfilerAgent extends Agent{

	private static final long serialVersionUID = 8369289048480998200L;
	private AID tourGuide;
	
	protected void setup(){
		addBehaviour(new SearchTourGuideBehaviour(this));
	}
	
	public void setTourGuide(AID tourGuide) {
		this.tourGuide = tourGuide;
	}
}


