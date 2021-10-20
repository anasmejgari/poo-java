package rob;

public class Incendie {

	private Case caseR;
	private int litreRestant;
	
	
	/**
	 * 
	 * Constructeur d'incendie
	 * @param CASE la case de l'incendie
	 * @param lit sa quantité
	 */
	public Incendie(Case CASE, int lit){
		caseR = CASE;
		litreRestant = lit;
	}
	
	
	/**
	 * Accesseur au nombre de litre restant pour éteinde l'incendie
	 * @return les litres restants
	 */
	public int getLitreRestant() {
		return litreRestant;
	}
	
	
	/**
	 * Accesseur à la case de l'incendie
	 * @return la case de l'incencie
	 */
	public Case getCase() {
		return caseR;
	}
	
	
	/**
	 * Versement d'une quantité d'eau sur l'incendie
	 * @param quantite nombe de litre qu'on veur verser
	 */
	public void eteindre(int quantite) {
		litreRestant = quantite<litreRestant? litreRestant-quantite:0;
	}
	
	
	/**
	 * On 
	 * @param integer
	 */
	public void setIntensite(int intensite) {
		litreRestant = intensite;	
	}
	
	public boolean estAttiser() {
		return  litreRestant>0;
	}
}
