package rob;


import java.util.ArrayList;

import courtChemin.ListCourtChemin;
import exceptions.*;



public class Drone extends Robot{

	private final static int VITESSE_DRONE_DEFAULT = 100;
	private final static int VITESSE_DRONE_MAX = 150;
	private final static int CAPACITE_RESERVOIR_DRONE = 10000;
	private final static int[] EXTINCTION_DRONE = {10000, 30}; //(lites, temps)
	private final static int REMPLISSAGE_DRONE = 1800; //temps en sec



	public Drone(Carte laCarte, Case caseR, int vitesse) throws vitesseMaxException {
		super(laCarte, caseR, CAPACITE_RESERVOIR_DRONE, vitesse, EXTINCTION_DRONE, REMPLISSAGE_DRONE, CAPACITE_RESERVOIR_DRONE);
	}


	//Au cas où la vitesse n'est pas précisé la vitesse est 100 km/h
	public Drone(Carte laCarte, Case caseR) throws vitesseMaxException {
		this(laCarte, caseR, VITESSE_DRONE_DEFAULT);;
	}

	
	public void setVitesse(int speed) throws vitesseMaxException {
		
		if (speed > VITESSE_DRONE_MAX) {
			throw new vitesseMaxException("La vitesse maximale d'un Drone ne peut pas dépasser 150 km/h");
		}
		setVitesseRobot(speed);
	
	}

	
	@Override
	public Case ouSeRemplir() {
		   int nbCol = laCarte.getNbColonnes(), nbLin = laCarte.getNbLignes();
		   int numDep = getCase().getLine()*nbCol+getCase().getColonne();
		   ArrayList<Case> casesEau = laCarte.getCasesEau();
		   double[] distanceEau = new double[casesEau.size()];
		   for(int i=0; i<distanceEau.length; i++) {
			   int numCaseEau =  casesEau.get(i).getLine()*nbCol+casesEau.get(i).getColonne();
			   distanceEau[i] = ListCourtChemin.CalculCourtChemin(numDep, getMatriceAdjacence(), nbLin*nbCol)[0][numCaseEau];
		   }
		   double minimum = distanceEau[0];
		   Case caseCherche = casesEau.get(0);
		   for (int j=0; j<distanceEau.length; j++) {
				if (distanceEau[j]<minimum) {
					minimum = distanceEau[j];
					caseCherche = casesEau.get(j);
				}
		   }
		   return caseCherche;
	   }

	@Override
	public void setPosition(Case nvlCase) throws deplacementImpossibleException {
		 boolean a = (Math.abs(getCase().getLine()- nvlCase.getLine()))==1;
   		 boolean b = Math.abs(getCase().getColonne()- nvlCase.getColonne())==1;
   		 if(!(a^b)) {
 			throw new deplacementImpossibleException("Le robot ne peut pas se déplacer à la colonne voulue");
   		 }
		setCase(nvlCase);
	}

	@Override
	public void remplireReservoir() {
		if (getCase().getNature() == NatureTerrain.EAU) {
			setDispoRes(CAPACITE_RESERVOIR_DRONE);
		}
	}


	@Override
	public double getVitesse(NatureTerrain terrain) {
		// TODO Auto-generated method stub
			return getVitesse();
		}


}

	
	

