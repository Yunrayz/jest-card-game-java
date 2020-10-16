package fr.utt.lo02.cccr.jest.modele.cartes;

import fr.utt.lo02.cccr.jest.modele.joueurs.Joueur;

/**
 * Interface utilisée par les effets afin qu'ils implémentent tous la méthode appliquerEffet()
 */
public interface Effet {
    /**
     * Méthode permettant d'appliquer l'effet de la carte, afin d'obtenir le joueur remplissant la condition
     * @return Joueur remplissant la condition de l'effet
     */
    Joueur appliquerEffet();
}
