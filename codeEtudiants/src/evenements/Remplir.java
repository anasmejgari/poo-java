package evenements;
import exceptions.CapaciteDepasseeException;
import exceptions.deplacementImpossibleException;
import rob.*;


public class Remplir extends Evenement {
	
	/**
	 * 
	 * Constructeur d'un évenement de remplissage du réservoir d'un robot
	 * @param date la date de l'évenement
	 * @param robot le robot dont le réservoir va être rempli
	 * 
	 */
	public Remplir(long date, Robot robot) {
		super(date, robot);
	}
	
	
	@Override
	public long tempsEvenement() {
		return (long)getRobot().getTempsRemplissage();
	}

	@Override
	public void execute() throws deplacementImpossibleException, CapaciteDepasseeException {
		int indRobot = getRobot().getSimulateur().getDonnees().getRobots().indexOf(getRobot());
		System.out.println("Remplissage du Robot "+indRobot);
		System.out.println("Reservoir avant: "+getRobot().getDispo());
		getRobot().remplireReservoir();
		System.out.println("Reservoir après: "+getRobot().getDispo());
		System.out.println("........................................................");

	}
	
	
}
