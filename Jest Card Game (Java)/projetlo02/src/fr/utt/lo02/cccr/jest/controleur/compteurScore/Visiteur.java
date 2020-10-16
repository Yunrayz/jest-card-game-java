package fr.utt.lo02.cccr.jest.controleur.compteurScore;

import fr.utt.lo02.cccr.jest.modele.joueurs.Joueur;

/**
 * Interface pour le visiteur
 */
public interface Visiteur {
    /**
     * Méthode permettant de visiter un joueur
     * @param joueur le joueur visité
     */
    void visit(Joueur joueur);
}
