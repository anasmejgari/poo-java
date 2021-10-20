package strategies;
import rob.*;
import evenements.*;
import exceptions.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;

import courtChemin.*;

public class Evolue extends ChefPompier {
	
	public Evolue(DonneesSimulation mesDonnees, Simulateur monSimulateur) {
		super(mesDonnees, monSimulateur);
	}
	
	@Override
	public void setRobotIncendie(Incendie incendie, long date) {
		DonneesSimulation mesDonnees = getDonnees();
		mesDonnees.getCarte().setCasesEau();
		Case caseIncendie = incendie.getCase();
		int nbCol = mesDonnees.getCarte().getNbColonnes();
		int nbLin = mesDonnees.getCarte().getNbLignes();
		int numCaseInc = caseIncendie.getLine()*nbCol+caseIncendie.getColonne();
		ArrayList<Robot> robots = mesDonnees.getRobots();
		double[] distanceRobInc = new double[mesDonnees.getRobots().size()];
		if (incendie.getLitreRestant() > 0) {	
			for (int i=0; i<distanceRobInc.length; i++) {
				if (robots.get(i).estOccupe(date) | robots.get(i).getCapacite()==0) {
					distanceRobInc[i] = Double.POSITIVE_INFINITY;
				} else {
					Case caseRobot = robots.get(i).getCase();
					int numCaseRob =  caseRobot.getLine()*nbCol+caseRobot.getColonne();
					double[][] matriceAdjacente = robots.get(i).getMatriceAdjacence();
					distanceRobInc[i] = ListCourtChemin.CalculCourtChemin(numCaseRob, matriceAdjacente, nbLin*nbCol)[0][numCaseInc];
				}
			}
			double minimum = Double.POSITIVE_INFINITY;
			Robot robotCherche = null;
			for (int j=0; j<distanceRobInc.length; j++) {
				if (distanceRobInc[j]<minimum) {
					minimum = distanceRobInc[j];
					robotCherche = robots.get(j);
				}
			}
			if (robotCherche != null) {
				Evenement eventDeplacement = new DeplacementMultiple(date, robotCherche, caseIncendie);
				getSimulateur().ajouterEvenement(eventDeplacement);
				date += eventDeplacement.tempsEvenement();
				Verser eventDeplacement1 = new Verser(date, robotCherche, incendie, Math.min(robotCherche.getDispo(), incendie.getLitreRestant()));
				getSimulateur().ajouterEvenement(eventDeplacement1);
				date += eventDeplacement1.tempsEvenement();
			}
		}
	}
	
	
	@Override
	public void strategie(long date) {
		for (Robot robot: getDonnees().getRobots()) {
			if (robot.getDispo()== 0 & !robot.estOccupe(date)) {
				Case caseRemplissage = robot.ouSeRemplir();
				Evenement eventDeplacement_2 = new DeplacementMultiple(date, robot, caseRemplissage);
				getSimulateur().ajouterEvenement(eventDeplacement_2);
				Remplir eventRemplissage = new Remplir(date + eventDeplacement_2.tempsEvenement(), robot);
				getSimulateur().ajouterEvenement(eventRemplissage);
			}
		}
		for(Incendie incendie: getDonnees().getIncendies()) {
			setRobotIncendie(incendie, date);
		}
	}
	
	public boolean simuTerminee() {
		DonneesSimulation mesDonnees = getDonnees();
		boolean[] etatIncendies = getSimulateur().getEtatIncendie();
		boolean boolInc = false;
		for (int i=0; i<etatIncendies.length; i++) {
			boolInc = boolInc | etatIncendies[i];
		}
		return !boolInc;
	}
	
	
	
}