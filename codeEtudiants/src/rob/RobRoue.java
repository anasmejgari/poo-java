package rob;
import exceptions.*;
public class RobRoue extends Robot {

	private final static int VITESSE_ROBOT_ROUE_DEFAULT = 80;
	private final static int CAPACITE_RESERVOIR_ROBOT_ROUE = 5000;
	private final static int[] EXTINCTION_ROBOT_ROUE = {100, 5}; //(lites, temps)
	private final static int REMPLISSAGE_ROBOT_ROUE = 600; //temps en sec


	public RobRoue(Carte laCarte, Case caseR, int vitesse) throws vitesseMaxException {
			super(laCarte, caseR, CAPACITE_RESERVOIR_ROBOT_ROUE, vitesse, EXTINCTION_ROBOT_ROUE, REMPLISSAGE_ROBOT_ROUE, CAPACITE_RESERVOIR_ROBOT_ROUE);
	}

	public RobRoue(Carte laCarte, Case caseR) throws vitesseMaxException {
			this(laCarte, caseR, VITESSE_ROBOT_ROUE_DEFAULT);
	}

	public void setVitesse(int vit) {
			setVitesseRobot(vit);
	}


	@Override
	/*
	 * On retourne -1 si la nature du terrain est inaccessible pour ce robot
	 */
	public double getVitesse(NatureTerrain terrain) {
			int returnedValue = -1;
			switch(terrain) {
				case HABITAT:
					 returnedValue =  getVitesse();
					 break;
				case TERRAIN_LIBRE:
					 returnedValue =  getVitesse();
					 break;
				default:
					 break;
			}
			return returnedValue;
	}

	@Override
	public void remplireReservoir() {
			for(Direction direction: Direction.values()) {
				if (laCarte.voisinExiste(getCase(), direction)) {
					if (laCarte.getVoisin(getCase(), direction).getNature() == NatureTerrain.EAU) {
							setDispoRes(CAPACITE_RESERVOIR_ROBOT_ROUE);
							break;
					}
				}
			}
	 }

	 @Override
 	public void setPosition(Case nvlCase) throws deplacementImpossibleException {
		 
		 boolean a = (Math.abs(getCase().getLine()- nvlCase.getLine()))==1;
   		 boolean b = Math.abs(getCase().getColonne()- nvlCase.getColonne())==1;
   		 if(!(a^b)) {
 			throw new deplacementImpossibleException("Le robot à Roue ne peut pas se déplacer à  la case voulue car elle est non voisine");
   		 }
   	     if ((nvlCase.getNature() != NatureTerrain.HABITAT) & (nvlCase.getNature() != NatureTerrain.TERRAIN_LIBRE)) {
   	    	 throw new deplacementImpossibleException("Le robot à Roue ne peut pas se déplacer à  la colonne voulue de type"+ nvlCase.getNature());
   	     }
 	     setCase(nvlCase);
 	}
}
