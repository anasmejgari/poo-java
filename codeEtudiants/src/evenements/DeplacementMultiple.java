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
	 * Constructeur d'un �venement de type d�placement multiple (ensemble de deplacements simples)
	 * @param date la date de l'�venement
	 * @param robDeplace le robot qui va �tre d�plac�
	 * @param caseArriv la case d'arriv�e
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
	 * Execution de l'�venement multiple
	 * Cette �xecution consiste � la division de cet �venement � un ensemble de d�placements simples
	 * qui vont �tre ajouter � la Map des �venements et ex�cut� apr�s un par un.
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
		// Le plus court chemin entre la case de d�part et l'arriv�e
		
		ArrayList<Integer> indices = ListCourtChemin.leCourtChemin(numCaseDep, numCaseArr, matriceAdjacente, nbLin*nbCol);
		int ligne_1, ligne_2, colonne_1, colonne_2;
		long dateCour = this.getDate();
		for(int i= 0; i<indices.size()-1; i++) {
			ligne_2 = indices.get(i+1)/nbCol;
			colonne_2 = indices.get(i+1)%nbLin;
			ligne_1 = indices.get(i)/nbCol;
			colonne_1 = indices.get(i)%nbLin;
			// Cr�ation et ajout d'un d�placement simple � la Map des �venments
			Deplacement eve = new Deplacement(dateCour, robDeplace, Direction.getDirection(ligne_2-ligne_1, colonne_2-colonne_1));
			robDeplace.getSimulateur().ajouterEvenement(eve);
			// La date du prochain d�placement simple
			dateCour += robDeplace.tempsDeplacement(robDeplace.getCarte().getCase(ligne_1, colonne_1).getNature(), robDeplace.getCarte().getCase(ligne_2, colonne_2).getNature());
		}
		System.out.println();
	}
}
	
	

