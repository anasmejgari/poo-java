package evenements;
import exceptions.CapaciteDepasseeException;
import exceptions.deplacementImpossibleException;
import rob.*;
import courtChemin.ListCourtChemin;
import java.util.ArrayList;
import java.time.*;

public class DeplacementMultiple extends Evenement {

	private Case caseArriv;
	
	/**
	 * 
	 * Constructeur d'un évenement de type déplacement multiple (ensemble de deplacements simples)
	 * @param date la date de l'évenement
	 * @param robDeplace le robot qui va être déplacé
	 * @param caseArriv la case d'arrivée
	 * 
	 */
	
	public DeplacementMultiple(long date, Robot robDeplace, Case caseArriv) {
		super(date, robDeplace);
		this.caseArriv = caseArriv;
	}
	
	
	@Override
	public long tempsEvenement() {
		Robot robDeplace = getRobot();
		double[][] matriceAdjacente = robDeplace.getMatriceAdjacence();
		int nbLin = robDeplace.getCarte().getNbLignes();
		int nbCol = robDeplace.getCarte().getNbColonnes();
		int numCaseDep = (robDeplace.getCase().getLine())*nbCol+(robDeplace.getCase().getColonne());
		int numCaseArr = (caseArriv.getLine())*nbCol+(caseArriv.getColonne());
		return (long)ListCourtChemin.CalculCourtChemin(numCaseDep, matriceAdjacente, nbLin*nbCol)[0][numCaseArr];
	}
	
	
	/**
	 * 
	 * Execution de l'évenement multiple
	 * Cette éxecution consiste à la division de cet évenement à un ensemble de déplacements simples
	 * qui vont être ajouter à la Map des évenements et exécuté après un par un.
	 * 
	 */

	@Override
	public void execute() throws deplacementImpossibleException, CapaciteDepasseeException {
		Robot robDeplace = getRobot();
		double[][] matriceAdjacente = robDeplace.getMatriceAdjacence();
		int nbLin = robDeplace.getCarte().getNbLignes();
		int nbCol = robDeplace.getCarte().getNbColonnes();
		int numCaseDep = (robDeplace.getCase().getLine())*nbCol+(robDeplace.getCase().getColonne());
		int numCaseArr = (caseArriv.getLine())*nbCol+(caseArriv.getColonne());
		// Le plus court chemin entre la case de départ et l'arrivée
		
		ArrayList<Integer> indices = ListCourtChemin.leCourtChemin(numCaseDep, numCaseArr, matriceAdjacente, nbLin*nbCol);
		int ligne_1, ligne_2, colonne_1, colonne_2;
		long dateCour = this.getDate();
		for(int i= 0; i<indices.size()-1; i++) {
			ligne_2 = indices.get(i+1)/nbCol;
			colonne_2 = indices.get(i+1)%nbLin;
			ligne_1 = indices.get(i)/nbCol;
			colonne_1 = indices.get(i)%nbLin;
			// Création et ajout d'un déplacement simple à la Map des évenments
			Deplacement eve = new Deplacement(dateCour, robDeplace, Direction.getDirection(ligne_2-ligne_1, colonne_2-colonne_1));
			robDeplace.getSimulateur().ajouterEvenement(eve);
			// La date du prochain déplacement simple
			dateCour += robDeplace.tempsDeplacement(robDeplace.getCarte().getCase(ligne_1, colonne_1).getNature(), robDeplace.getCarte().getCase(ligne_2, colonne_2).getNature());
		}
		System.out.println();
	}
}
	
	

