package rob;

import java.util.ArrayList;
import exceptions.*;

public class Carte {
	private int tailleCases;
	private int nbLignes;
	private int nbColonnes;
	private Case[][] carte;
	private ArrayList<Case> casesEau; // Les cases de Nature EAU
    private ArrayList<Case> voisinsEau; // Les voisins des cases dont la nature est EAU

    
    /**
	 * 
	 * Constructeur d'une carte
	 * @param nbLin nombre de lignes de la carte
	 * @param nbCol nombre de colonnes de la carte
	 * @param taille taille de la case
	 * 
	 */ 
    
	public Carte(int nbLin, int nbCol,int taille) {
		nbLignes = nbLin;
		nbColonnes = nbCol;
		tailleCases = taille;
		carte = new Case[nbLignes][nbColonnes];
	}
	
	/**
	 * 
	 * Constructeur de copie d'une carte
	 * @param autreCarte une carte à copié ses attributs
	 * 
	 */
	
	public Carte(Carte autreCarte) {
		nbLignes = autreCarte.nbLignes;
		nbColonnes = autreCarte.nbColonnes;
		tailleCases = autreCarte.tailleCases;
		carte = new Case[nbLignes][nbColonnes];
		for (int i=0; i<nbLignes; i++) {
			for(int j=0; j<nbColonnes; j++) {
				carte[i][j] = new Case(autreCarte.getCase(i, j));
			}
		}
	}
	
	
	/**
	 * 
	 * Accesseur au nombre de lignes de la carte
	 * @return le nombre de lignes 
	 * 
	 */
	
	public int getNbLignes() {
		return nbLignes;
	}
	
	/**
	 * 
	 * Accesseur au nombre de colonnes de la carte
	 * @return le nombre de colonnes 
	 * 
	 */
	
	public int getNbColonnes() {
		return nbColonnes;
	}

	
	/**
	 * 
	 * Accesseur à le taille des case de la carte
	 * @return le taille des cases
	 * 
	 */

	
	public int getTaille() {
		return tailleCases;
	}
	
	/**
	 * 
	 * Accesseur à la carte
	 * @return la carte
	 * 
	 */
	
	public Case[][] getCarte(){
		return carte ;
	}
	
	/**
	 * 
	 * Accesseur à les cases de l'eau
	 * @return Les cases dont NatureTerrain=EAU
	 * 
	 */
	
	public ArrayList<Case> getCasesEau() {
		return casesEau;
	}
	
	/**
	 * 
	 * Accesseur à les cases voisines de l'eau
	 * @return Les cases voisines de ceux ddont NatureTerrain=EAU
	 * 
	 */
	
	public ArrayList<Case> getVoisinsEau() {
		return voisinsEau;
	}
	
	/**
	 * 
	 * Accesseur à une case.
	 * @param ligne la ligne de la case
	 * @param col la colonne de la case
	 * @return la case voulue
	 * 
	 */

	public Case getCase(int ligne, int col) {
		//Exception
		return carte[ligne][col];
	}
	
	/**
	 * 
	 * Mutateur d'une case.
	 * @param ligne la ligne de la case
	 * @param col la colonne de la case
	 * @param ter la nature du terrain de cette case
	 * 
	 */
	public void setCase(int ligne, int col, NatureTerrain ter) {
		Case caseR = new Case(ligne, col, ter);
		carte[ligne][col] = caseR;
	}
	
	
	/**
	 * 
	 * Méthode qui détermine si une case à un certain voisin.
	 * @param src la case concernée
	 * @param dir une direction
	 * @return un boolean
	 * 
	 */
	
	public boolean voisinExiste(Case src, Direction dir) {
		 boolean bool=true;
		 switch(dir) {
		 case NORD:
			 bool = src.getLine()>=1;
			 break;
		 case SUD:
			 bool = (src.getLine()<= nbLignes-2);
			 break;
		 case EST:
			 bool = src.getColonne()<= nbColonnes-2;
			 break;
		 case OUEST:
			 bool = src.getColonne()>0;
			 break;
		 }
		 return bool;
	 }


	/**
	 * 
	 * Méthode qui renvoie une case voisine dans une certaine direction.
	 * @param src la case concernée
	 * @param dir une direction
	 * @return la case cherchée si elle existe
	 * 
	 */
	
	public Case getVoisin(Case src, Direction dir) {
		 int ligne = src.getLine();
		 int colonne = src.getColonne();
         try {
        	 if (!voisinExiste(src, dir)) {
			 	 throw new caseVoisineNonExistanteException("Case voisine non existante");
        	 }
			 switch(dir) {
				 case NORD:
					 return carte[ligne-1][colonne]	;
				 case SUD:
					 return carte[ligne+1][colonne];
				 case EST:
					 return carte[ligne][colonne+1];
				 default:
					 return carte[ligne][colonne-1];
			 }
         } catch (caseVoisineNonExistanteException e) { 
        	System.out.println("La case ("+src.getClass()+", "+src.getColonne()+") n'a pas de vosin dans la direction "+dir);
        	return null;
        }
	}
	
	
	/**
	 * 
	 * Méthode qui détecte si 2 cases sont voisines.
	 * @param case1 la première case
	 * @param case2 la deuxième case
	 * @return un boolean.
	 * 
	 */
	
	public boolean sontVoisins(Case case1, Case case2) {
		for(Direction direction:Direction.values()) {
			if(voisinExiste(case1, direction)) {
				if(getVoisin(case1, direction)==case2) {
					return true;
				}			
			}
		}
		return false;	
	}

	/**
	 * 
	 * Mutateurs qui cherche les casesEau et voisinsEau.
	 * 
	 */
	public void setCasesEau () {
		casesEau = new ArrayList<Case>();
		voisinsEau = new ArrayList<Case>();
		int nbLin = getNbLignes();
		int nbCol = getNbColonnes();
		Case caseCur;
		for(int i=0; i<nbLin; i++) {
			for(int j=0; j<nbCol; j++) {
				caseCur = getCase(i, j);
				if(caseCur.getNature() == NatureTerrain.EAU) {
					casesEau.add(caseCur);
					for(Direction dir:Direction.values()) {
						if(voisinExiste(caseCur, dir)) {
							voisinsEau.add(getVoisin(caseCur, dir));
						}
					}
				}
			}
		}
	}
	
}
