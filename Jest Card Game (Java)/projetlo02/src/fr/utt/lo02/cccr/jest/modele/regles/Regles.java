package fr.utt.lo02.cccr.jest.modele.regles;

import fr.utt.lo02.cccr.jest.controleur.PartieControleur;
import fr.utt.lo02.cccr.jest.modele.joueurs.Joueur;

/**
 * Interface utilisée par les variantes
 */
public interface Regles {
    /**
     * Retourne le nom d'une variante
     * @return nom de la variante
     */
    String getNom();

    /**
     * Permet d'ajouter le contrôleur de la partie à la variante afin d'accéder aux informations nécessaires plus tard
     * @param partieControleur le contrôleur de la partie
     */
    void ajouterPartieControleur(PartieControleur partieControleur);

    /**
     * Méthode permettant de calculer le score d'un joueur
     * @param j le joueur dont on calcule le score
     */
    void calculerScore(Joueur j);
}
