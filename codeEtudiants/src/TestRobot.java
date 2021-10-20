import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

import evenements.Simulateur;
import exceptions.vitesseMaxException;
import gui.GUISimulator;
import io.LecteurDonnees;
import rob.DonneesSimulation;

public class TestRobot {
	public static void main(String[] args) 
			throws FileNotFoundException, DataFormatException, vitesseMaxException {
		Scanner clavier = new Scanner(System.in);
		int numSImulation ;
		do {
			System.out.println("Tapez 1 pour une stratégie élémentaire et 2 pour une stratégie évoluée: ");
			numSImulation = clavier.nextInt();
		} while (numSImulation != 1 & numSImulation!= 2);
		LecteurDonnees.lire(args[0]);
		DonneesSimulation ds = LecteurDonnees.getDonnees();
        GUISimulator gui = new GUISimulator(1000, 1000, Color.GRAY); 
        Simulateur monSimulateur = new Simulateur(gui, ds, 100, numSImulation);
	}
}