package evenements;
import exceptions.CapaciteDepasseeException;
import exceptions.deplacementImpossibleException;
import rob.*;
import java.util.ArrayList;

public class Verser extends Evenement {

	private Incendie incendie;
	private int vol;
	
	
	public Verser(long date, Robot robot, Incendie incendie, int vol) {
		super(date, robot);
		this.incendie = incendie;
		this.vol = vol;
	}

	@Override
	public long tempsEvenement() {
		return getRobot().tempsIntervention(Math.min(vol, incendie.getLitreRestant()));
	}

	@Override
	public void execute() throws deplacementImpossibleException, CapaciteDepasseeException {
		Robot robot = getRobot();
		int indRobot = getRobot().getSimulateur().getDonnees().getRobots().indexOf(getRobot());
		int indInc = getRobot().getSimulateur().getDonnees().getIncendies().indexOf(incendie);
		if (incendie.getLitreRestant()>0) {
			System.out.println("Versement (Robot "+indRobot+") pour l'incendie "+indInc+".");
			System.out.println("Avant verser: le robot a "+getRobot().getDispo()+"l  et l'incndie "+incendie.getLitreRestant());
			getRobot().deverserEau(vol, incendie);
			System.out.println("Après verser: le robot a "+getRobot().getDispo()+"l  et l'incndie "+incendie.getLitreRestant());
			System.out.println("........................................................");
		} else {
			System.out.println(getRobot().getIntervalles().get(getRobot().getIntervalles().size()-1)[0]+" "+getRobot().getIntervalles().get(getRobot().getIntervalles().size()-1)[1]);
			System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
			ArrayList<long[]> mesIntervalles = robot.getIntervalles();
			for (int i=0; i<mesIntervalles.size(); i++) {
				if ((mesIntervalles.get(0)[0]==getDate()) && (mesIntervalles.get(0)[1]==getDate()+tempsEvenement())){
					mesIntervalles.remove(i);
				}
			}
		}
	}
	
	
}
