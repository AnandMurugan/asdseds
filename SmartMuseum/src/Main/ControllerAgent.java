package Main;

import CuratorAgent.CuratorAgent;
import Profiler.ProfilerAgent;
import TourGuide.TourGuideAgent;

import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.domain.mobility.MobilityOntology;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

public class ControllerAgent extends Agent {

	private static final long serialVersionUID = 5495318842471561579L;
	private jade.wrapper.AgentContainer profilerHome;
	private jade.wrapper.AgentContainer tourGuideHome;
	private jade.wrapper.AgentContainer curatorHome;

	jade.core.Runtime runtime = jade.core.Runtime.instance();

	protected void setup() {
		// Register language and ontology
		getContentManager().registerLanguage(new SLCodec());
		getContentManager().registerOntology(MobilityOntology.getInstance());

		// Create the container objects
		profilerHome = runtime.createAgentContainer(new ProfileImpl());
		tourGuideHome = runtime.createAgentContainer(new ProfileImpl());
		curatorHome = runtime.createAgentContainer(new ProfileImpl());
		doWait(2000);

		
		try {
			Object[] args = new Object[0];
			AgentController a;
			a = tourGuideHome.createNewAgent("tourGuide", TourGuideAgent.class.getName(), args);
			a.start();
			doWait(1000);
			a = curatorHome.createNewAgent("curator", CuratorAgent.class.getName(), args);
			a.start();
			doWait(1000);
			args = new Object[2];
			args[0] = profilerHome.getContainerName();
			args[1] = curatorHome.getContainerName();
			args[2] = "proposal1.txt";
			args[3] = "profile1.xml";
			a = profilerHome.createNewAgent("profiler", ProfilerAgent.class.getName(), args);
			a.start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (ControllerException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}

}
