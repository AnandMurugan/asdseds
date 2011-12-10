package Profiler;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class SearchTourGuideBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = -5821808715478331827L;
	private String srvType = "tourGuide";
	
	public SearchTourGuideBehaviour(Agent a) {
		super(a);
	}
	
	@Override
	public void action() {
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType(srvType);
		dfd.addServices(sd);
		try {
			DFAgentDescription[] result = DFService.search(myAgent, dfd);
			if(result.length > 0) {
				ProfilerAgent pAgent = (ProfilerAgent)myAgent;
				pAgent.setTourGuide(result[0].getName());
			}
		} catch(FIPAException e) {
			System.out.println("SearchTourGuideBehaviour FIPAException");
			e.printStackTrace();
			return;
		}

	}

	@Override
	public boolean done() {
		return true;
	}

}
