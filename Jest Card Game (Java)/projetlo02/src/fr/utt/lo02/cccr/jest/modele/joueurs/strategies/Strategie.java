package fr.utt.lo02.cccr.jest.modele.joueurs.strategies;

import fr.utt.lo02.cccr.jest.controleur.PartieControleur;
import fr.utt.lo02.cccr.jest.modele.joueurs.Joueur;
import fr.utt.lo02.cccr.jest.modele.joueurs.JoueurVirtuel;

import java.util.ArrayList;

/**
 * Interface utilisée par les stratégies
 */
public interface Strategie {
    /**
     * Méthode permettant à un joueur virtuel de proposer ses cartes
     * @param joueurVirtuel le joueur virtuel qui propose
     */
    void proposerCartes(JoueurVirtuel joueurVirtuel);

    /**
     * Méthode permettant à un joueur virtuel de piocher une carte chez un autre joueur
     * @param controleur le contrôleur de la partie
     * @param joueursDispos liste de joueurs disponibles, chez qui on peut piocher
     * @param joueur le joueur qui doit piocher
     */
    void piocherCarte(PartieControleur controleur, ArrayList<Joueur> joueursDispos, Joueur joueur);
}
