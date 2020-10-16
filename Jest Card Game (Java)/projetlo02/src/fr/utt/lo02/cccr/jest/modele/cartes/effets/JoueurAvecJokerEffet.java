package fr.utt.lo02.cccr.jest.modele.cartes.effets;

import fr.utt.lo02.cccr.jest.Utils;
import fr.utt.lo02.cccr.jest.controleur.PartieControleur;
import fr.utt.lo02.cccr.jest.modele.cartes.Effet;
import fr.utt.lo02.cccr.jest.modele.joueurs.Joueur;

import java.util.ArrayList;

/**
 * Classe représentant l'effet Joueur avec Joker
 */
public class JoueurAvecJokerEffet implements Effet {
    private PartieControleur controleur;

    /**
     * Constructeur
     * @param controleur le contrôleur de la Partie
     */
    public JoueurAvecJokerEffet(PartieControleur controleur) {
        this.controleur = controleur;
    }

    /**
     * Méthode permettant de récupérer le joueur remplissant l'effet
     * @return le joueur possédant le Joker
     */
    @Override
    public Joueur appliquerEffet() {
        return Utils.joueurAvecJoker((ArrayList<Joueur>) this.controleur.getJoueursList());
    }

    /**
     * Surcharge de la méthode toString permettant d'afficher le nom de l'effet
     * @return Nom de l'effet
     */
    @Override
    public String toString() {
        return "Joueur avec Joker";
    }
}
