package evenements;
import exceptions.CapaciteDepasseeException;
import exceptions.deplacementImpossibleException;
import rob.*;


public class Remplir extends Evenement {
	
	/**
	 * 
	 * Constructeur d'un �venement de remplissage du r�servoir d'un robot
	 * @param date la date de l'�venement
	 * @param robot le robot dont le r�servoir va �tre rempli
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
		System.out.println("Reservoir apr�s: "+getRobot().getDispo());
		System.out.println("........................................................");

	}
	
	
}
