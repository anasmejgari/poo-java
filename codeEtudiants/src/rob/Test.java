package rob;

import exceptions.vitesseMaxException;

public class Test {
	 public static void main (String[] args) throws vitesseMaxException {
		 Carte ct = new Carte(5, 5, 100);
		 RobRoue rb = new RobRoue(ct, ct.getCase(0, 0));
		 System.out.println(rb.tempsDeplacement(NatureTerrain.HABITAT, NatureTerrain.HABITAT));
	 }
}
