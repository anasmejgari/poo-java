package courtChemin;
import java.util.ArrayList;

import java.util.ArrayList;
import java.util.SortedMap;

import evenements.Evenement;
import rob.*;

public class ListCourtChemin {
	
	/**
	 * 
	 * Fonction qui retourne le cout minimal des chemins entre l'élément d'indice 'indice' et les autres composantes
	 * La base de cette méthode est la méthode de Ford
	 * @param indice indice de la case de départ
	 * @param matriceAdjacence la matrice d'adjacence du graphe
	 * @param n la taille du graphe
	 * 
	 */
	
	public static double[][] CalculCourtChemin(int indice,double[][] matriceAdjacence,int n) {
			double[] tempsMinimale = new double[n],indiceCase = new double[n];
			double tmp;
			for(int i=0; i<n;i++) {
				tempsMinimale[i] = 10000000;
				indiceCase[i] = -1;
			}
			
			tempsMinimale[indice] = 0 ;// le temps de se rendre de la case d'indice i a elle meme est null 
			boolean encours = true; //tant que l'algorithme marche
			while(encours) {
				encours = false;
				for(int i =0 ; i < n; i++) {
					for(int j = 0; j<n; j++) {
						tmp = tempsMinimale[i] + matriceAdjacence[i][j];
						if (tmp < tempsMinimale[j]) {
							tempsMinimale[j] = tmp;
							indiceCase[j] = i;
							encours = true;
						}
					}
				}
			}
			double[][] time_chemin = new double[2][n];
			time_chemin[0] = tempsMinimale;//la premiere ligne des temps minimale
			time_chemin[1] = indiceCase;// c'est l'indice est -1 c 'est que cette case et non accessible 
			return  time_chemin;
		
		}
	
	/**
	 * 
	 * Fonction qui retourne le chemins du coût minimal entre une case et une autre
	 * @param start indice de la case de départ
	 * @param end indice de la case d'arrivée
	 * @param matriceAdjacence la matrice d'adjacence du graphe
	 * @param n la taille du graphe
	 * 
	 */
	
	public static ArrayList<Integer> leCourtChemin(int start, int end, double[][] matriceAdjacence, int n) {
		ArrayList<Integer> retour = new ArrayList<Integer>();
		double[][] resultat = CalculCourtChemin(start, matriceAdjacence, n);
		double[] parcours = resultat[1];
		if (end == start) {
			retour.add(start);
			return retour;
		}
		if (parcours[end] != -1) {
			int precedant = (int) parcours[end];
			retour.add(end);
			while(precedant != start) {
				retour.add(0, precedant);
				precedant = (int) parcours[precedant];
			}
			retour.add(0, start);
		}
		return retour;
	}
	
	
	/**
	 *
	 * Test pour La méthode Implémantée du calcul du plus court chemin
	 * 
	 **/
	
	public static void main(String[] args) {
		
		double inf = Double.POSITIVE_INFINITY;
		double[][] matrice = {{0, 3, inf, 1, inf, inf, inf},
				{inf, 0, 1, 1, inf, inf, inf}, 
				{inf, inf, 0, inf, 2, inf, inf},
				{inf, inf, 1, 0, inf, 3, 1}, 
				{inf, inf, inf, 1, 0, inf, 1},
				{1, inf, inf, inf, inf, 0, inf},
				{inf, inf, inf, inf, inf, 1, 0}};
		double[][] resultat = CalculCourtChemin(0, matrice, 7);
		for (int i=0; i<7; i++) {
			System.out.print(resultat[0][i]+"|");
		}
		System.out.println();
		for (int i=0; i<7; i++) {
			ArrayList<Integer> cv = leCourtChemin(0, i, matrice, 7);
			for (int j=0; j<cv.size(); j++) {
				System.out.print(cv.get(j)+"|");
			}
			System.out.println();

		}
		
	}
		
}
