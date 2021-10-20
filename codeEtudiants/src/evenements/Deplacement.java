package evenements;
import exceptions.deplacementImpossibleException;
import rob.*;


public class Deplacement extends Evenement {

	private Direction direction;
	
	/**
	 * 
	 * Constructeur d'un évenement de type déplacement simple
	 * @param date la date de l'évenement
	 * @param robDeplace le robot qui va être déplacé
	 * @param direction la direction que prend le robot lors du deplacement
	 * 
	 */
	
	public Deplacement(long date, Robot robDeplace, Direction direction) {
		super(date, robDeplace);
		this.direction = direction;
	}
	
	/**
	 * 
	 * Accesseur à la direction
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
		System.out.println("Deplacement élémentaire du robot "+indRobot+".");
		System.out.println("Robot déplacé de ("+getRobot().getCase().getLine()+","+getRobot().getCase().getColonne()+") vers ("+nvlCase.getLine()+", "+nvlCase.getColonne()+")");
		getRobot().setPosition(nvlCase);
		System.out.println("........................................................");
	}
	
	
}
