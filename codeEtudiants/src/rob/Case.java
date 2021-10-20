package rob;

public class Case {
	private int ligne;
	private int colonne;
	private NatureTerrain nature;
	public Case() {
		ligne = 0;
		colonne = 50;
		nature = NatureTerrain.TERRAIN_LIBRE;
	}
	public Case(int lin, int col, NatureTerrain ter) {
		ligne = lin;
		colonne = col;
		nature = ter;
		
	}
	
	public void setLigne(int i) {
		// TODO Auto-generated method stub
		ligne = i;
	}
	public void setColonne(int j) {
		colonne = j;
		
	}
	
	public Case(Case autreCase) {
		ligne = autreCase.ligne;
		colonne = autreCase.colonne;
		nature = autreCase.nature;
	}
	
	public int getLine() {
		return ligne;
	}
	
	public int getColonne() {
		return colonne;
	}
	
	public NatureTerrain getNature() {
		return nature;	
	}
	
	

}
