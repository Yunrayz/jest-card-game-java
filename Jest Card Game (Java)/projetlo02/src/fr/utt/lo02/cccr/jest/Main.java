package fr.utt.lo02.cccr.jest;

import fr.utt.lo02.cccr.jest.controleur.MainControleur;
import fr.utt.lo02.cccr.jest.modele.extensions.Extension;
import fr.utt.lo02.cccr.jest.modele.extensions.extension6.ExtensionSix;
import fr.utt.lo02.cccr.jest.modele.regles.PasNegatif;
import fr.utt.lo02.cccr.jest.modele.regles.Regles;
import fr.utt.lo02.cccr.jest.modele.regles.ReglesBasiques;
import fr.utt.lo02.cccr.jest.modele.regles.ReglesInverses;

/**
 * Classe principale du programme
 */
public class Main {
    private static final String[] NOMSBOTS = {"Secundo", "Jade", "Pey'J", "Double-H"};
    private static final Regles[] REGLES = {new ReglesBasiques(), new ReglesInverses(), new PasNegatif()};
    private static final Extension[] EXTENSIONS = {new ExtensionSix()};
    public static final boolean DEBUG = false;

    /**
     * Méthode permettant d'afficher la classe et la méthode dans laquelle elle est appelée
     */
    public static void showDebug(){
        String nameOfCurrMethod = new Throwable().getStackTrace()[1].getMethodName();
        String nameOfCurrClass = new Throwable().getStackTrace()[1].getClassName();
        if (Main.DEBUG) {
            System.out.println("=====================================================================================" +
                    Utils.CYAN_BOLD + "\n\t[DEBUG] " + nameOfCurrClass + "." + nameOfCurrMethod + "()" + Utils.RESET);
        }
    }

    /**
     * Méthode principale du programme
     * @param args arguments passés en option, non utilisés ici
     */
    public static void main(String[] args) {
        System.out.println("\n\t+===============+");
        System.out.println("\t|  Jest - LO02  |");
        System.out.println("\t+===============+\n");

        new MainControleur(NOMSBOTS, REGLES, EXTENSIONS).demarrerPartie();
    }
}
