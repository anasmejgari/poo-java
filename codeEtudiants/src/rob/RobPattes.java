package rob;
import exceptions.*;

public class RobPattes extends Robot{

		private final static int VITESSE_ROBOT_PATTES_DEFAULT =30;
		private final static int CAPACITE_RESERVOIR_ROBOT_PATTES = Integer.MAX_VALUE;
		private final static int[] EXTINCTION_ROBOT_PATTES = {10, 1}; //(lites, temps)
		private final static int REMPLISSAGE_ROBOT_PATTES = 0; //temps en sec



		public RobPattes(Carte laCarte, Case caseR, int vitesse) throws vitesseMaxException {
			super(laCarte, caseR, CAPACITE_RESERVOIR_ROBOT_PATTES, vitesse, EXTINCTION_ROBOT_PATTES, REMPLISSAGE_ROBOT_PATTES, CAPACITE_RESERVOIR_ROBOT_PATTES);
		}


		//Au cas où la vitesse n'est pas précisé la vitesse est 60 km/h
		public RobPattes(Carte laCarte, Case caseR) throws vitesseMaxException {
			this(laCarte, caseR, VITESSE_ROBOT_PATTES_DEFAULT);
		}

		@Override
		public void setVitesse(int speed) throws vitesseMaxException {
			setVitesseRobot(speed);
		}

    @Override
		public double getVitesse(NatureTerrain terrain) {
			double returnedValue = this.getVitesse();
			switch(terrain) {
				case ROCHE:
					returnedValue =  10;
			     	break;
				case EAU:
					 returnedValue =  -1;
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
			}
			if  ((nvlCase.getNature() == NatureTerrain.EAU)) {
				throw new deplacementImpossibleException("Le Robot ne peut pas se déplacer à la colonne voulue");
			}
			setCase(nvlCase);
		}

		@Override
		public void remplireReservoir() {
		      
		}
		
		@Override
		public void deverserEau(int vol, Incendie incendie)  {
		     incendie.eteindre(vol);
		}


}
