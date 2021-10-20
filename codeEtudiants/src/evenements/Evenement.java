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
	 * Accesseur à la date de l'événement
	 * 
	 */
	
	public long getDate() {
		return date;
	}
	
	
	/**
	 * Accesseur au robot concerné par l'événement
	 * 
	 */
	
	public Robot getRobot() {
		return robot;
	}
	
	/**
	 * Calcule le temps pris par l'événement
	 * 
	 */
	
	public abstract long tempsEvenement(); 
	
	/**
	 * Exécute l'événement
	 * 
	 */
	
	public abstract void execute() throws deplacementImpossibleException, CapaciteDepasseeException;
}
