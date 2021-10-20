package evenements;
import exceptions.deplacementImpossibleException;
import rob.*;


public class Deplacement extends Evenement {

	private Direction direction;
	
	/**
	 * 
	 * Constructeur d'un �venement de type d�placement simple
	 * @param date la date de l'�venement
	 * @param robDeplace le robot qui va �tre d�plac�
	 * @param direction la direction que prend le robot lors du deplacement
	 * 
	 */
	
	public Deplacement(long date, Robot robDeplace, Direction direction) {
		super(date, robDeplace);
		this.direction = direction;
	}
	
	/**
	 * 
	 * Accesseur � la direction
	 * 
	 */
	
	public Direction getDiection() {
		return direction;
	}

	@Override
	public long tempsEvenement() {
		Case depart = getRobot().getCase();
		Case arrive = getRobot().getCarte().getVoisin(depart, direction);
		return (long)getRobot().tempsDeplacement(depart.getNature(), arrive.getNature());
	}
	
	
	@Override
	public void execute() throws deplacementImpossibleException {
		int indRobot = getRobot().getSimulateur().getDonnees().getRobots().indexOf(getRobot());
		//System.out.println(direction);
		Case nvlCase = getRobot().getCarte().getVoisin(getRobot().getCase(), direction);
		System.out.println("Deplacement �l�mentaire du robot "+indRobot+".");
		System.out.println("Robot d�plac� de ("+getRobot().getCase().getLine()+","+getRobot().getCase().getColonne()+") vers ("+nvlCase.getLine()+", "+nvlCase.getColonne()+")");
		getRobot().setPosition(nvlCase);
		System.out.println("........................................................");
	}
	
	
}
