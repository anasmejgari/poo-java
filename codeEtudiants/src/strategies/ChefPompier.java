package strategies;
import rob.*;
import evenements.*;
import exceptions.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;

import courtChemin.*;

abstract public class ChefPompier {
	
	private DonneesSimulation mesDonnees;
	private Simulateur monSimulateur;
	
	
	public ChefPompier(DonneesSimulation mesDonnees, Simulateur monSimulateur) {
		this.mesDonnees = mesDonnees;
		this.monSimulateur = monSimulateur;
	}
	
	abstract public void setRobotIncendie(Incendie incendie, long date);
	
	abstract public void strategie(long date);
	
	public DonneesSimulation getDonnees() {
		return mesDonnees;
	}
	
	public Simulateur getSimulateur() {
		return monSimulateur;
	}
	
	abstract public boolean simuTerminee();
	
	
}