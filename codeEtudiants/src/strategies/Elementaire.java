package strategies;
import rob.*;
import evenements.*;
import exceptions.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;

import courtChemin.*;

public class Elementaire extends ChefPompier {
	
	public Elementaire(DonneesSimulation mesDonnees, Simulateur monSimulateur) {
		super(mesDonnees, monSimulateur);
	}
	
	@Override
	public void setRobotIncendie(Incendie incendie, long date) {
		
		Robot robotCherche = null;
		if (incendie.getLitreRestant() > 0) {
			for (Robot robot: getDonnees().getRobots()) {
				if (!robot.estOccupe(date) & (robot.getDispo()>0)) {
					robotCherche = robot;
					break;
				}
			} 
			if (robotCherche != null) {
				Case caseIncendie = incendie.getCase();
				Evenement eventDeplacement = new DeplacementMultiple(date, robotCherche, caseIncendie);
				getSimulateur().ajouterEvenement(eventDeplacement);
				Verser eventDeplacement1 = new Verser(date+eventDeplacement.tempsEvenement(), robotCherche, incendie, robotCherche.getDispo());
				getSimulateur().ajouterEvenement(eventDeplacement1);
			}
		}
	}
	
	public void strategie(long date) {
		for(Incendie incendie: getDonnees().getIncendies()) {
			setRobotIncendie(incendie, date);
		}
	}
	
	
	public boolean simuTerminee() {
		DonneesSimulation mesDonnees = getDonnees();
		boolean[] etatIncendies = getSimulateur().getEtatIncendie();
		boolean[] etatRobots = getSimulateur().getEtatRobot();
		boolean boolInc= false, boolRob = false;
		for (int i=0; i<etatIncendies.length; i++) {
			boolInc = boolInc | etatIncendies[i];
		}
		for (int i=0; i<etatRobots.length; i++) {
			boolRob = boolRob | etatIncendies[i];
		}
		return !(boolInc & boolRob);
	}
}