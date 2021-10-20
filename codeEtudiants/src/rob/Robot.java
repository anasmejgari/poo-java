package rob;
import evenements.Simulateur;

import exceptions.*;
import java.util.*;

import courtChemin.ListCourtChemin;

abstract public class Robot {
	   protected Carte laCarte;
	   private  Case caseR; //la case du Robot  sur la carte
	   private int capacReser; //la capacitee reservoir du Robot
	   private int vitesse;
	   private int[] extinct; //tuple de (extinction,temps)
	   private int tempsRemplissage;//
	   private int dispoRes;
	   protected double[][] matriceAdjacence;
	   private Simulateur monSimulateur;
	   private ArrayList<long[]> tempsOccup;
	   
	   
	   /**
	    * 
	    * Constucteur des robot
	    * @param laCarte la carte
	    * @param caseR la case où se trouve le robot
	    * @param capacReser la capacité du réservoir du robot
	    * @param vitesse la vitesse du robot
	    * @param extinct le débit comment on verse l'eau
	    * @param tempsRemplissage temps necessaire pour remplir le robot
	    * @param capacite 
	    * @throws vitesseMaxException si la vitesse est supérieur à celle maximale
	    */
	   public Robot(Carte laCarte, Case caseR, int capacReser, int vitesse, int[] extinct, int tempsRemplissage, int capacite) throws vitesseMaxException {
	     this.laCarte = laCarte;
	     this.caseR = caseR;
	     this.capacReser = capacReser;
	     setVitesse(vitesse);
	     this.extinct = extinct;
	     this.tempsRemplissage = tempsRemplissage;
	     this.dispoRes = capacite;
	     this.monSimulateur = monSimulateur;
	     setMatriceAdjacence();
	     tempsOccup = new ArrayList<long[]>();
	   }
	  
	   
	   /**
	    * Accesseur à la carte
	    * @return la carte
	    */
	   public Carte getCarte() {
		   return laCarte;
	   }
	   
	   /**
	    * Accesseur à la case du robot dans la cas
	    * @return la case actuelle du robot
	    */
	   
	   public Case getCase(){
	  	 return caseR;
	   }
	   
	   /**
	    * Une méthode si détermine si un robot est occupé en une date donnée
	    * @param date
	    * @return un booléen
	    */
	   
	   public boolean estOccupe(long date) {
		   for(long[] intervalle: tempsOccup) {
			   if((intervalle[0] <= date ) & (date <= intervalle[1])) {
				   return true;
			   }
		   }
		   return false;
	   }
	   
	   /**
	    * Accesseur à l'ensemble des temps où le robot est occupé
	    * @return
	    */
	   
	   public ArrayList<long[]> getIntervalles() {
		   return tempsOccup;
	   }
	   
	   public void addEventDate(long start, long end) {
		   long[] intervalle = {start, end}; 
		   tempsOccup.add(intervalle);
	   }

	   
	   /**
	    * Accesseur à la capacité du réservoir
	    * @return capacité du réservoir
	    */
	   public int getCapacite() {
	     return capacReser;
	   }
	   
	   
	   public void setSimulateur(Simulateur monSimulateur) {
		   this.monSimulateur = monSimulateur;
	   }
	   
	   /**
	    * Accesseur au simulateur
	    * @return le simulateur
	    */
	   public Simulateur getSimulateur() {
		   return monSimulateur;
	   }
	   
	   /**
	    * Accesseur à la vitesse générale du robot
	    * @return la vitesse
	    */
	   
	   public int getVitesse() {
	     return vitesse;
	   }

	   public int[] getExtinction () {
	     return extinct;
	   }

	   
	   /**
	    * Accesseur au temps de remplissage du robot
	    * @return temps de remplissage
	    */
	   public int getTempsRemplissage () {
	     return tempsRemplissage;
	   }
	   
	   public int getDispo() {
		   return dispoRes;
	   }
	   
	   
	   public void resetTempOccupe() {
			tempsOccup = new ArrayList<long[]>();
		}
	   
	   /**
	    * Accesseur à la matrice d'adjacence
	    * @return La matrice d'adjacence
	    */
	   public double[][] getMatriceAdjacence(){
		   return matriceAdjacence;
	   }
	   
	   
	   /**
	    * 
	    * Mutatteur du disponible au reservoire 
	    * @param volume
	    */
	   public void setDispoRes(int volume) {
	          dispoRes = volume;
	   }
	   

	   public void setDispoRes() {
		   setDispoRes(capacReser);
	   }
	   
	   
	   /**
	    * Mutateur de la case
	    * @param nvlCase
	    */
	   public void setCase(Case nvlCase) {
		   caseR = nvlCase;
	   }

	   
	   /**
	    * Mutateur de la case  
	    * @param nvlCase
	    * @throws deplacementImpossibleException si la type de la case cible n'est pas accessible pour le robor 
	    */
	   abstract public void setPosition(Case nvlCase) throws deplacementImpossibleException;
	   
	   
	   /**
	    * Accesseur de la vitesse selon la nature de la case  
	    * @param nvlCase
	    * @throws deplacementImpossibleException si la type de la case cible n'est pas accessible pour le robor 
	    */
	   abstract public double getVitesse(NatureTerrain terrain);

	   public void deverserEau(int vol, Incendie incendie) throws CapaciteDepasseeException {
	     if (vol > dispoRes) {
	       throw new CapaciteDepasseeException("Le réservoir a une capacité de "+capacReser+"seulement.");
	     }
	     incendie.eteindre(vol);
	     dispoRes -= vol;
	   }
	   
	   
	   public void setVitesseRobot(int speed) {
		   vitesse = speed;
	   }
	   
	   
	   /**
	    * Methode pour déterminer le temps du déplacement du robot entre deux types de cases
	    * Elle retourne -1 si le déplacement est impossible
	    * @param type1 la première nature du terrain
	    * @param type2 la deuxième nature du terrain
	    * @return le temps necessaire
	    */
	   public long tempsDeplacement(NatureTerrain type1, NatureTerrain type2) {
		  long temps = -1; 	
		  double vitesseCur = getVitesse(type1);
		  double vitesseFut = getVitesse(type2);
		  if (vitesseFut > 0) {
			  temps = (long) (((1/vitesseCur)+(1/vitesseFut))*100*1.8);
		  }
		  return temps;
	   }
	   
	   
	   /**
	    * 
	    * Cette méthode determine pour un robot la case d'eau la plus proche pour aller se remplir
	    * @return la case d'eau la plus proche
	    */
	   public Case ouSeRemplir() {
		   int nbCol = laCarte.getNbColonnes(), nbLin = laCarte.getNbLignes();
		   int numDep = caseR.getLine()*nbCol+caseR.getColonne();
		   ArrayList<Case> casesEau = laCarte.getVoisinsEau();
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
	   
	   
	   /**
	    * 
	    * Le temps nécessaire pour faire une intervention
	    * @param quantite la quatité d'eau à verser
	    * @return le temps nécessaire
	    */
	   
	   public long tempsIntervention(int quantite) {
		   return quantite*extinct[1]/extinct[0];
	   }
	   
	   /**
	    * Méthode pour remplir le réservoir selon la type de robot.
	    */
	   abstract public void remplireReservoir();

	   /**
	    * Mutatteur des vitesses
	    * @param speed
	    * @throws vitesseMaxException si la vitesse donnée est supérieur à la vitesse maximale
	    */
	   abstract public void setVitesse(int speed) throws vitesseMaxException;
	   //abstract public void setMatriceAdjacence();
	   
	   /**
	    * Une méthode qui petmet de bien créer la matrice d'adjacence
	    */
	   public void setMatriceAdjacence() {
		   int nbColonnes = laCarte.getNbColonnes();
		   int nbLignes = laCarte.getNbLignes();
		   int tailleCase = laCarte.getTaille();
		   int taille = nbColonnes*nbLignes;
		   Case case_i ,case_j;
		   double vitesse_i, vitesse_j;
		   matriceAdjacence = new double[taille][taille];
		   for(int i=0;i<taille;i++) {
			   for(int j =0;j<taille;j++) {
				   if (j==i) {
					   matriceAdjacence[i][i]=0;
				   /* 
				    * La Matrice est symetrique
				    */
				   }else if(i>j) {
					   matriceAdjacence[i][j]= matriceAdjacence[j][i];
				   }else {
					   case_i = laCarte.getCase((int) i/nbColonnes, i%nbColonnes);
					   case_j = laCarte.getCase((int) j/nbColonnes, j%nbColonnes);
					   
					   if(laCarte.sontVoisins(case_i, case_j) && (tempsDeplacement(case_i.getNature(), case_j.getNature())>0) ){
						   /*
						    * Les arcs contiennent le temps necessaire pour un déplacement 
						    */
						   matriceAdjacence[i][j] = tempsDeplacement(case_i.getNature(), case_j.getNature()); 
					   }else {
						   matriceAdjacence[i][j] = Double.POSITIVE_INFINITY;
					   }
					   
				   }
			   }
		   }
		   
	   }
	   
	   

	}
