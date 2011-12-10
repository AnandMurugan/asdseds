package Profiler;

import java.util.ArrayList;

import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.SimpleBehaviour;

public class VisitTour extends SequentialBehaviour {

	private static final long serialVersionUID = -423068248303321282L;

	private ArrayList<String> tourItems;
	
	public VisitTour(Agent a, ArrayList<String> tourItems) {
		super(a);
		this.tourItems = tourItems;
	}
	
	public void onStart() {
		System.out.println("visiting tour");
		for(int i = 0; i < tourItems.size(); i++) {
			addSubBehaviour(new VisitItem(myAgent, tourItems.get(i)));
		}
		addSubBehaviour(new SimpleBehaviour() {
			
			@Override
			public boolean done() {
				return true;
			}
			
			@Override
			public void action() {
				((ProfilerAgent)myAgent).goHome();
			}
		});
	}
}
