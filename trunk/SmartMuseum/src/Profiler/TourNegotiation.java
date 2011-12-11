package Profiler;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.SimpleBehaviour;

public class TourNegotiation extends FSMBehaviour{

	private static final long serialVersionUID = -9896598676906028L;

	private static final String STATE_A = "Start_Tour_Negotiation";
	private static final String STATE_B = "Send_Proposal";
	private static final String STATE_C = "End_Negotiation_Success";
	private static final String STATE_D = "End_Negotiation_ERROR";
	
	private ArrayList<String> proposalList;
	private int currentProposal = 0;

	public TourNegotiation(Agent a, int tourNr) {
		super(a);

		loadProposalList(tourNr);

		registerFirstState(new StartNewNegotiation(myAgent), STATE_A);
		registerState(new SendProposal(myAgent, this), STATE_B);
		registerLastState(new EndNegotiation(myAgent), STATE_C);
		registerLastState(new SimpleBehaviour(myAgent) {
			private static final long serialVersionUID = -5941691593426959661L;

			@Override
			public boolean done() {
				return true;
			}

			@Override
			public void action() {
				System.out.println("end negotiation error");
			}
		}, STATE_D);

		registerTransition(STATE_A, STATE_B, 1);
		registerTransition(STATE_A, STATE_D, 2);
		registerTransition(STATE_B, STATE_B, 1);
		registerTransition(STATE_B, STATE_C, 0);
		registerTransition(STATE_B, STATE_D, 2);
	}

	private void loadProposalList(int tourNr) {
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(((ProfilerAgent)myAgent).getProposalFilePath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null && tourNr >= 0)   {
				tourNr--;
			}
			String[] stringList = strLine.split(" ");
			for(int i = 0; i < stringList.length; i++) {
				proposalList.add(stringList[i]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public String getCurrentProposal() {
		return proposalList.get(currentProposal);
	}
	
	public void goToNextProposal() {
		currentProposal++;
	}
}
