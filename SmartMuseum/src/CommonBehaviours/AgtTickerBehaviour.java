package CommonBehaviours;

import Profiler.ProfilerAgent;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class AgtTickerBehaviour  extends TickerBehaviour{

	private static final long serialVersionUID = 1468131818439862817L;
	private AID[] serviceProvider;
	private String srvType;
	private RefreshSearchedServicesI refInt;
	public AgtTickerBehaviour(ProfilerAgent a, long period, String srvType) {
		super(a, period);
		this.refInt = (RefreshSearchedServicesI) a;
		this.srvType = srvType;
	}

	protected void onTick() {
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType(srvType);
		dfd.addServices(sd);
		AID[] newServiceProviders;
		try{
			DFAgentDescription[] result = DFService.search(myAgent, dfd);
			newServiceProviders = new AID[result.length];
			for(int i=0; i< result.length; ++i){
				newServiceProviders[i] = result[i].getName();
			}
			if(newServiceProviders!=null && newServiceProviders.length!=0) {
				if(serviceProvider == null ){
					serviceProvider = newServiceProviders;
					refInt.refreshSearchedServices(serviceProvider);
					System.out.println("servicee providers registered.");
				}else {

					if(newServiceProviders.length!=serviceProvider.length){
						System.out.println("change detected in length servicee providers...");
						serviceProvider = newServiceProviders;
						refInt.refreshSearchedServices(serviceProvider);
					} else{

						for(int i=0; i < newServiceProviders.length; i++ ){
							if(!(newServiceProviders[i].equals(serviceProvider[i]))){
								System.out.println("change detected in servicee providers...");
								serviceProvider = newServiceProviders;
								refInt.refreshSearchedServices(serviceProvider);
								break;
							}
						}
					}	
				}
			}
		}catch(FIPAException e){
			System.out.println("AgtTickerBehaviour on tick FIPAException");
			e.printStackTrace();
			return;
		}
	}


}
