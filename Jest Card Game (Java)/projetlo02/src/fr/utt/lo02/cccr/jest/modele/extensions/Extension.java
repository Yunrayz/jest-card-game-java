package fr.utt.lo02.cccr.jest.modele.extensions;

import fr.utt.lo02.cccr.jest.controleur.PartieControleur;
import fr.utt.lo02.cccr.jest.modele.cartes.Carte;

import java.util.ArrayList;

/**
 * Interface permettant l'implémentation d'Extensions dans le jeu
 */
public interface Extension {
    /**
     * Méthode permettant de créer les cartes nécéssaires si une extension est choisie
     * @param partieControleur le contrôleur de la partie dans laquelle on doit générer les cartes
     * @return les cartes de l'extension
     */
    ArrayList<Carte> creerCartes(PartieControleur partieControleur);
}
