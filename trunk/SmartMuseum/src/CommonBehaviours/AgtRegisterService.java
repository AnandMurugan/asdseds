package CommonBehaviours;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class AgtRegisterService extends OneShotBehaviour{

	private static final long serialVersionUID = 5610770460748966215L;
	private String serviceType;
	private String serviceName;
	public AgtRegisterService(Agent a,String serviceType, String serviceName){
		super(a);
		this.serviceType = serviceType;
		this.serviceName = serviceName;
	}

	public void action() {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(myAgent.getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType(serviceType);
		sd.setName(serviceName);
		dfd.addServices(sd);
		try{
			DFService.register(myAgent, dfd);
			
		}catch(FIPAException e){
			System.out.println("AgtRegisterService action FIPAException");
			e.printStackTrace();
			return;
		}
	}
}
