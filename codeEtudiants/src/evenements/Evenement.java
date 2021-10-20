package evenements;
import exceptions.*;
import rob.*;

public abstract class Evenement {
		
	private long date;
	private Robot robot;
	
	public Evenement(long date, Robot robot) {
		this.robot = robot;
		this.date = date;
	}
	
	/**
	 * Accesseur � la date de l'�v�nement
	 * 
	 */
	
	public long getDate() {
		return date;
	}
	
	
	/**
	 * Accesseur au robot concern� par l'�v�nement
	 * 
	 */
	
	public Robot getRobot() {
		return robot;
	}
	
	/**
	 * Calcule le temps pris par l'�v�nement
	 * 
	 */
	
	public abstract long tempsEvenement(); 
	
	/**
	 * Ex�cute l'�v�nement
	 * 
	 */
	
	public abstract void execute() throws deplacementImpossibleException, CapaciteDepasseeException;
}
