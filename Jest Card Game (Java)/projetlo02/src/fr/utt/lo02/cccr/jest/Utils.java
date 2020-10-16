package fr.utt.lo02.cccr.jest;

import fr.utt.lo02.cccr.jest.modele.cartes.Carte;
import fr.utt.lo02.cccr.jest.modele.joueurs.Joueur;

import java.util.ArrayList;

/**
 * Classe regroupant des méthodes ou attributs susceptibles d'être appelés à différents endroits.
 */
public class Utils {
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String CYAN = "\033[0;36m";    // CYAN

    // Bold
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN

    // Bold High Intensity
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED

    /**
     * Méthode permettant de mettre en pause l'exécution du programme pendant un laps de temps
     * @param temps le temps à attendre (en ms)
     */
    public static void attendre(int temps){
        try {
            Thread.sleep(temps);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode permettant de déterminer quel joueur a la carte la plus haute
     * @param joueursAComparer liste des joueurs à comparer
     * @return le joueur ayant la carte la plus haute
     */
    public static Joueur joueurCartePlusHaute(ArrayList<Joueur> joueursAComparer){
        Joueur resultat = joueursAComparer.get(0);
        for (Joueur j : joueursAComparer){
            if (j.plusGrandeCarteJest().getValeurNum() > resultat.plusGrandeCarteJest().getValeurNum()){
                resultat = j;
            } else {
                if (j.plusGrandeCarteJest().getValeurNum() == resultat.plusGrandeCarteJest().getValeurNum()){
                    if (j.plusGrandeCarteJest().getCouleurNum() > resultat.plusGrandeCarteJest().getCouleurNum()){
                        resultat = j;
                    }
                }
            }
        }
        return resultat;
    }

    /**
     * Méthode permettant de déterminer le joueur avec le Joker dans sa main
     * @param joueursAComparer liste des joueurs à comparer
     * @return le joueur ayant la carte la plus haute
     */
    public static Joueur joueurAvecJoker(ArrayList<Joueur> joueursAComparer){
        Joueur resultat = joueursAComparer.get(0);
        for (Joueur j : joueursAComparer){
            for (Carte c : j.getJest()){
                if (c.isJoker()) {
                    resultat = j;
                    break;
                }
            }
        }
        return resultat;
    }
}