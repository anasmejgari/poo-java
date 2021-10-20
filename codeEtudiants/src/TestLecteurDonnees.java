

import io.LecteurDonnees;
import rob.*;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import exceptions.vitesseMaxException;

public class TestLecteurDonnees {

    public static void main(String[] args) throws vitesseMaxException {
    	
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            LecteurDonnees.lire(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }

}

