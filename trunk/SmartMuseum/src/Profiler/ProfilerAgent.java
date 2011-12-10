package Profiler;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import Profiler.profile.MuseumItem;

import jade.core.AID;
import jade.core.Agent;

public class ProfilerAgent extends Agent{

	private static final long serialVersionUID = 8369289048480998200L;
	private AID tourGuide;
	private AID curator;
	
	
	protected void setup(){
		addBehaviour(new SearchTourGuide(this));
		addBehaviour(new SearchCurator(this));
		startTourNegotiation();
	}
	
	public void startTourNegotiation() {
		addBehaviour(new TourNegotiation(this));
	}
	
	public void setTourGuide(AID tourGuide) {
		this.tourGuide = tourGuide;
	}
	
	public AID getTourGuide() {
		return tourGuide;
	}
	
	public void setCurator(AID curator) {
		this.curator = curator;
	}
	
	public AID getCurator() {
		return curator;
	}
	
	public void visitTour(ArrayList<String> tour) {
		addBehaviour(new VisitTour(this, tour));
	}
	
	public void visitItem(MuseumItem museumItem) {
		Random rand = new Random((new Date()).getTime());
		System.out.println("item visited");
		museumItem.setRating(rand.nextInt(6));
	}
}


