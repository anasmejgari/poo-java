package rob;

public enum Direction {
	NORD(-1, 0), SUD(1, 0), EST(0, 1), OUEST(-1, 0);
	
	private int diffLignes;
	private int diffCol;
	
	private Direction(int diffLignes, int diffCol) {
		this.diffLignes = diffLignes;
		this.diffCol = diffCol;
	}
	
	
	/**
	 * 
	 * Méthode qui la direction cherchée
	 * @param a différence entre les lignes
	 * @param b différence entre les colonnes
	 * @return direction
	 */
	public static Direction getDirection(int a, int b) {
		switch (a) {
			case 1: 
				return Direction.SUD;
			case -1: 
				return Direction.NORD;
		} switch(b) {
			case 1: 
				return Direction.EST;
			case -1: 
				return Direction.OUEST;
		}
		return null;
		
		
	}
}
