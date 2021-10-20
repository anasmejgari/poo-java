package rob;

import exceptions.*;

public class RobChenille extends Robot{

		private final static int VITESSE_ROBOT_CHENILLE_DEFAULT = 60;
		private final static int VITESSE_ROBOT_CHENILLE_MAX = 80;
		private final static int CAPACITE_RESERVOIR_ROBOT_CHENILLE = 2000;
		private final static int[] EXTINCTION_ROBOT_CHENILLE = {100, 8}; //(lites, temps)
		private final static int REMPLISSAGE_ROBOT_CHENILLE = 300; //temps en sec



		public RobChenille(Carte laCarte, Case caseR, int vitesse) throws vitesseMaxException {
			super(laCarte, caseR, CAPACITE_RESERVOIR_ROBOT_CHENILLE, vitesse, EXTINCTION_ROBOT_CHENILLE, REMPLISSAGE_ROBOT_CHENILLE, CAPACITE_RESERVOIR_ROBOT_CHENILLE);
		}


		//Au cas où la vitesse n'est pas précisé la vitesse est 60 km/h
		public RobChenille(Carte laCarte, Case caseR) throws vitesseMaxException {
			this(laCarte, caseR, VITESSE_ROBOT_CHENILLE_DEFAULT);
		}

		@Override
		public void setVitesse(int speed) throws vitesseMaxException {
			if (speed > VITESSE_ROBOT_CHENILLE_MAX) {
				throw new vitesseMaxException("La vitesse maximale d'un Drone ne peut pas dépasser 150 km/h");
			}
			setVitesseRobot(speed);
		}

    @Override
		public double getVitesse(NatureTerrain terrain) {
			double returnedValue = this.getVitesse();
			switch(terrain) {
				case EAU:
					 returnedValue =  -1;
					 break;
				case ROCHE:
					 returnedValue =  -1;
					 break;
		        case FORET:
		             returnedValue /= 2.0;
		             break;
				default:
						 break;
			}
			return returnedValue;
		}


		@Override
		public void setPosition(Case nvlCase) throws deplacementImpossibleException {
			boolean a = Math.abs(getCase().getLine()- nvlCase.getLine())==1;
			boolean b = Math.abs(getCase().getColonne()- nvlCase.getColonne())==1;
			if(!(a^b)) {
				throw new deplacementImpossibleException("Le Robot ne peut pas se déplacer à la colonne voulue");
			} if  ((nvlCase.getNature() == NatureTerrain.EAU) | (nvlCase.getNature() == NatureTerrain.ROCHE)) {
				throw new deplacementImpossibleException("Le Robot ne peut pas se déplacer à la colonne voulue");
			}
			setCase(nvlCase);
		}

		@Override
		public void remplireReservoir() {
		      for(Direction direction: Direction.values()) {
						if (laCarte.voisinExiste(getCase(), direction)) {
							if (laCarte.getVoisin(getCase(), direction).getNature() == NatureTerrain.EAU) {
									setDispoRes(CAPACITE_RESERVOIR_ROBOT_CHENILLE);
							}
					}
			   }
		}


}
