package rob;

//import java.io.FileNotFoundException;
import java.util.ArrayList;

public class DonneesSimulation {
	
	private ArrayList<Incendie> incendies;
	private ArrayList<Robot> robots;
	private Carte laCarte; 
	
	/**
	 * 
	 * Accesseur aux incendies
	 * 
	 */ 
	
	public ArrayList<Incendie> getIncendies() {
	      return incendies;
	}
	
	/**
	 * 
	 * Accesseur aux robots
	 * 
	 */
	
	public ArrayList<Robot> getRobots() {
	      return robots;
	}
	
	/**
	 * 
	 * Accesseur à la carte
	 * 
	 */
	
	public Carte getCarte() {
		 return laCarte;
	 }
	
	/**
	 * 
	 * Mutateur de la carte
	 * @param lignes nombre de lignes de la carte
	 * @param colonnes nombre de colonnes de la carte
	 * @param taille taille des case de la carte
	 * @returns
	 * 
	 */
	
	public void setCarte(int lignes, int colonnes, int taille) {
		laCarte = new Carte(lignes, colonnes, taille); 
	}
	
	/**
	 * 
	 * Mutateur de robots
	 * @param nbrRobots le nombre de robots
	 * 
	 */ 
	
	public void setRobots(int nbrRobots) {
		robots = new ArrayList<Robot>(nbrRobots);
	}
	
	
	/**
	 * 
	 * Mutateur d'incendies
	 * @param nbrIncendie le nombre d'incendies
	 * 
	 */ 
	
	public void setIncendies(int nbrIncendie) {
		incendies = new ArrayList<Incendie>(nbrIncendie); 
	}
	
	/**
	 * 
	 * Méthode pour ajouter un certian robot
	 * @param nvRobot le robot à ajouter
	 * 
	 */ 
	
	public void ajouterRobot(Robot nvRobot) {
	      robots.add(nvRobot);
	}
	
	/**
	 * 
	 * Méthode pour ajouter incendie
	 * @param nvIncendie l'incendie à ajouter
	 * 
	 */ 
	
	public void ajouterIncendie(Incendie nvIncendie) {
	      incendies.add(nvIncendie);
	 }
	 	
}
