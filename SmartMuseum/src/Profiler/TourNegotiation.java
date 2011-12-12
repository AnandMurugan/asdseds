package Profiler;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import CommonClasses.Proposal;

import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.SimpleBehaviour;

public class TourNegotiation extends FSMBehaviour{

	private static final String STATE_A = "Start_Tour_Negotiation";
	private static final String STATE_B = "Send_Proposal";
	private static final String STATE_C = "End_Negotiation_Success";
	private static final String STATE_D = "End_Negotiation_ERROR";
	
	private ArrayList<Proposal> proposalList;
	private int currentProposal = 0;

	public TourNegotiation(Agent a, int tourNr) {
		super(a);

		loadProposalList(tourNr);

		registerFirstState(new StartNewNegotiation(myAgent), STATE_A);
		registerState(new SendProposal(myAgent, this), STATE_B);
		registerLastState(new EndNegotiation(myAgent, this), STATE_C);
		registerLastState(new SimpleBehaviour(myAgent) {

			@Override
			public boolean done() {
				return true;
			}

			@Override
			public void action() {
				System.out.println("profiler - end negotiation error");
			}
		}, STATE_D);

		registerTransition(STATE_A, STATE_B, 1);
		registerTransition(STATE_A, STATE_D, 2);
		registerTransition(STATE_B, STATE_B, 1);
		registerTransition(STATE_B, STATE_C, 0);
		registerTransition(STATE_B, STATE_D, 2);
	}

	private void loadProposalList(int tourNr) {
		proposalList = new ArrayList<Proposal>();
		
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(((ProfilerAgent)myAgent).getProposalFilePath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine = "";
			//Read File Line By Line
			System.out.println(tourNr);
			while (tourNr >= 0)   {
				if((strLine = br.readLine()) != null) {
					tourNr--;
				} else {
					System.out.println("error proposal file doesn't have 3 lines");
				}
			}
			String[] stringList = strLine.split(" ");
			for(int i = 0; i < stringList.length; i++) {
				int auxTourNr = stringList[i].charAt(2) - '0';
				String auxPrice = stringList[i].substring(4, stringList[i].length()-1); 
				String[] priceItems = auxPrice.split(",");
				
				Set<String> priceItemsSet = new HashSet<String>();
				for(int j = 0; j < priceItems.length; j++) {
					priceItemsSet.add(priceItems[j]);
				}
				Proposal p = new Proposal(auxTourNr, priceItemsSet);
				proposalList.add(p);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public Proposal getCurrentProposal() {
		return proposalList.get(currentProposal);
	}
	
	public void goToNextProposal() {
		currentProposal++;
	}
}
