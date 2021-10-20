package evenements;
import io.*;

import java.awt.Color;
import rob.*;
import strategies.*;
import exceptions.*;
import java.util.*;
import strategies.*;

import exceptions.CapaciteDepasseeException;
import exceptions.deplacementImpossibleException;
import gui.GUISimulator;
import gui.ImageElement;
import gui.Rectangle;
import rob.*;
import gui.Simulable;
import gui.Text; 


public class Simulateur implements Simulable {

		private SortedMap<Long, ArrayList<Evenement>> mapEvents ;
		private long dateSimulation;
		private long lastDate;
		private GUISimulator gui;
		private DonneesSimulation doSim;
		private int x_pas;
		private int y_pas;
		private int ballayage;
		private boolean[] etatIncendies;
		private boolean[] etatRobots;
		private ChefPompier strateg;
		private ArrayList<Integer> incendieIntensite;//Les valeurs initiales d' itensite des incendies
		private ArrayList<int[]> positionInitiale; //Les positions initales des robots
		

		
		public Simulateur(GUISimulator gui, DonneesSimulation doSim, int ballayage, int numSim) {
			this.dateSimulation = 0;
			this.lastDate = 1;
			mapEvents = new TreeMap<Long, ArrayList<Evenement>> ();
			this.gui = gui;
			gui.setSimulable(this);
			this.doSim = doSim;
			this.x_pas = (gui.getPanelWidth()-ballayage)/doSim.getCarte().getNbColonnes();
			this.y_pas = (gui.getPanelWidth()-ballayage)/doSim.getCarte().getNbColonnes();
			this.ballayage = ballayage;
			setEtatIncendies();
			setEtatRobots();
			setValeursInitials();
			draw();
			if (numSim == 1) {
				strateg = new Elementaire(doSim, this);
			} else {
				strateg = new Evolue(doSim, this);
			}
			
		}
		
		
		/**
		 * 
		 * Fonction qui détermines l'état des incendies.
		 * 
		 */
		
		public void setEtatIncendies() {
			if (etatIncendies == null) {
				etatIncendies = new boolean[doSim.getIncendies().size()];
			}
			for (int i=0; i<etatIncendies.length; i++) {
				etatIncendies[i] = (doSim.getIncendies().get(i).getLitreRestant()>0);
				
			}
		}
		
		public void setEtatRobots() {
			if (etatRobots == null) {
				etatRobots = new boolean[doSim.getRobots().size()];
			}
			for (int i=0; i<etatRobots.length; i++) {
				etatRobots[i] = (doSim.getRobots().get(i).getDispo()>0);
			}
		}
		
		/**
		 * 
		 * Fonction qui nous informe si la simulation est terminé
		 *
		 */
		
		public boolean simuTermine() {
			boolean boolInc = false;
			for (int i=0; i<etatIncendies.length; i++) {
				boolInc = boolInc | etatIncendies[i];
			}
			return !boolInc;
		}
		
		/**
		 * 
		 */
		
		public boolean[] getEtatRobot() {
			return etatRobots;
		}
		
		/**
		 * 
		 */
		
		public boolean[] getEtatIncendie() {
			return etatIncendies;
		}
		
		/**
		 * 
		 * Accesseur à la date courante de simulation
		 * 
		 */
		
		public long getDateSimulation () {
			return dateSimulation;
		}
		
		/**
		 * 
		 * Accesseur à nos données de simulation
		 * 
		 */
		
		public DonneesSimulation getDonnees() {
			return doSim;
		}
		
		/**
		 * 
		 * Accesseur à l'ensemble de nos évenement
		 * 
		 */
		
		public SortedMap<Long, ArrayList<Evenement>> getMapEvents(){
			return mapEvents;
		}
		
		/**
		 * 
		 * Fonction qui récupère les positions initales des robots et les intensités initiales des incendies
		 * 
		 */
		
		private void setValeursInitials() {
			ArrayList<Incendie> incendies = doSim.getIncendies();
			ArrayList<Robot> robots = doSim.getRobots();
			incendieIntensite =  new ArrayList<Integer>();
			positionInitiale = new ArrayList<int[]>();
			for(Incendie incendie : incendies) {
				incendieIntensite.add(incendie.getLitreRestant());
			}
			for(Robot robot:robots) {
				int[] tab = new int[2];
				tab[0] = robot.getCase().getColonne() ;
				tab[1] = robot.getCase().getLine();
				positionInitiale.add(tab);
			}
			
		}
		
		
		public void ajouterEvenement(Verser e) {
			long eveDate = e.getDate();
			e.getRobot().addEventDate(eveDate, eveDate+((long)e.tempsEvenement()));
			eveDate += (long)e.tempsEvenement();
			ArrayList<Evenement> tmp;
			if (mapEvents.containsKey(eveDate)) {
				tmp = mapEvents.get(eveDate);
				tmp.add(e);
				
			}
			else {
				tmp = new ArrayList<Evenement>();
				tmp.add(e);
				mapEvents.put(eveDate, tmp);
				if(eveDate>lastDate) {
					lastDate = eveDate;
				}
			}
		}
		
		/**
		 * 
		 * Fonction qui ajoute un évenement donné à notre Map d'évenements
		 * @param e un évenement
		 * 
		 */
		
		public void ajouterEvenement(Evenement e) {
			long eveDate = e.getDate();
			e.getRobot().addEventDate(eveDate, eveDate+((long)e.tempsEvenement()));
			ArrayList<Evenement> tmp;
			if (mapEvents.containsKey(eveDate)) {
				tmp = mapEvents.get(eveDate);
				tmp.add(e);
				
			}
			else {
				tmp = new ArrayList<Evenement>();
				tmp.add(e);
				mapEvents.put(eveDate, tmp);
				if(eveDate>lastDate) {
					lastDate = eveDate;
				}
			}
		}
		

		
		private void incrementeDate () {
			dateSimulation += 1;
		}
		
		private boolean simulationTerminee( ) {			
			return (dateSimulation >lastDate);
		}
		
		/**
		 * 
		 * Fonction qui récupere les évenement de la date suivante (+1)
		 * 
		 */
		
		public void nextEvents() {
			incrementeDate();
			if(mapEvents.containsKey(dateSimulation) && !simulationTerminee()) {
				try {
					ArrayList<Evenement> events = mapEvents.get(dateSimulation) ;
					//AjoutRobot
					for (int i= 0; i<events.size(); i++) {
						if (events.get(i).getRobot().getSimulateur() == null ) {
							events.get(i).getRobot().setSimulateur(this);
							
						}
						events.get(i).execute();
		        	}	
			       
				} catch (deplacementImpossibleException e) {
					System.out.println(e.getMessage());
				} catch (CapaciteDepasseeException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		
		
		/**
		 * 
		 * Fonction pour le dessin de la carte
		 * 
		 */
		
		private void carteDraw() {
			Carte laCarte = doSim.getCarte();
			gui.reset();	// clear the window
			int nbLin = laCarte.getNbLignes();
			int nbCol = laCarte.getNbColonnes();
			// on doit tracer chaqune des cases de notre carte selon sa nature de terrain
			for(int ligne = 0; ligne<nbLin; ligne++) {
				for(int col = 0; col<nbCol; col++ ) {
					Color color =  laCarte.getCase(ligne, col).getNature().getColor();
					gui.addGraphicalElement(new Rectangle(col*x_pas+ballayage, ligne*y_pas+ballayage,Color.WHITE, color, x_pas, y_pas));
					
				}
			}
		}
		
		/**
		 * 
		 * Fonction pour le dessin des incendies
		 * 
		 */
		
		private void incendiesDraw() {
			ArrayList<Incendie> incendies = doSim.getIncendies();
			for(Incendie incendie : incendies) {
				if (incendie.getLitreRestant()>0) {
					int lig = incendie.getCase().getLine(), col = incendie.getCase().getColonne();      
				    ImageElement c = new ImageElement(col*x_pas+ballayage-x_pas/2, lig*y_pas+ballayage-y_pas/2, "1f525.png", x_pas, y_pas, gui);
				    gui.addGraphicalElement(c);
				}
			}
		}
		
		/**
		 * 
		 * Fonction pour le dessin des robots
		 * 
		 */
		
		private void robotsDraw() {
			ArrayList<Robot> robots = doSim.getRobots();
			for(Robot robot : robots) {
				int lig = robot.getCase().getLine(), col = robot.getCase().getColonne();      
			    //ImageElement c = new ImageElement(col*x_pas+100, lig*y_pas+100, "Drone.png", 3, 30, gui);
				ImageElement c = new ImageElement(col*x_pas+ballayage-x_pas/2, lig*y_pas+ballayage-y_pas/2, Robots.getFichier(robot.getClass().toString()), x_pas, y_pas, gui);
			    gui.addGraphicalElement(c);
			}
		}
		
		/**
		 * 
		 * Fonction qui remet les robots et incendies à leurs états initiaux
		 * 
		 */
		
		private void reset() throws deplacementImpossibleException {
			dateSimulation = 0;
		
			ArrayList<Incendie> incendies = doSim.getIncendies();
			ArrayList<Robot> robots = doSim.getRobots();
			Incendie incendie;
			Robot robot;
			
			for(int i=0; i < incendies.size(); i++) {
				incendie = incendies.get(i);
				incendie.setIntensite(incendieIntensite.get(i));
				
			}
			for(int i = 0; i < robots.size();i++) {
				robot = robots.get(i);
				Case nvCase = robot.getCarte().getCase(positionInitiale.get(i)[1], positionInitiale.get(i)[0]);
				robot.setCase(nvCase);;;
				robot.setDispoRes();
				robot. resetTempOccupe();
			}
			setEventStrategie();
			
		}
		
		
		private void setEventStrategie() {
			mapEvents = new TreeMap<Long, ArrayList<Evenement>> ();
			strateg = new Evolue(doSim, this);
			
		}
		
		/**
		 * 
		 * Fonction qui dessine l'ensemble des éléments graphiques
		 * 
		 */
		
		private void draw() {
			gui.reset();
			carteDraw();
			incendiesDraw();
			robotsDraw();
		}
		
		@Override
		public void next() {
				strateg.strategie(dateSimulation+1);
				nextEvents();
				setEtatIncendies();
				setEtatRobots();
				draw();
				if (strateg.simuTerminee()) {
					gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2 , Color.WHITE , "Simulation Terminé"));
				}
		}

		@Override
		public void restart() {
			try {
				reset();
				dateSimulation = 0;
			} catch (deplacementImpossibleException e) {
				e.printStackTrace();
			}
			draw();
			
		}
	
}
