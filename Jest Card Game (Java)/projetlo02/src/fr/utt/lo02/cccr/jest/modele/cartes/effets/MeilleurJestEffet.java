package fr.utt.lo02.cccr.jest.modele.cartes.effets;

import fr.utt.lo02.cccr.jest.Utils;
import fr.utt.lo02.cccr.jest.controleur.PartieControleur;
import fr.utt.lo02.cccr.jest.modele.cartes.Effet;
import fr.utt.lo02.cccr.jest.modele.joueurs.Joueur;

import java.util.ArrayList;

/**
 * Classe représentant l'effet meilleur jest
 */
public class MeilleurJestEffet implements Effet {
    private PartieControleur controleur;

    /**
     * Constructeur
     * @param controleur le contrôleur de la partie
     */
    public MeilleurJestEffet(PartieControleur controleur) {
        this.controleur = controleur;
    }

    /**
     * Méthode permettant de déterminer le joueur ayant le meilleur score, qu'il ai le Joker ou non
     * @return le joueur
     */
    @Override
    public Joueur appliquerEffet() {
        if (this.controleur.getClassementJoueurs()[0].getScore() == this.controleur.getClassementJoueurs()[1].getScore()){
            ArrayList<Joueur> joueursAComparer = new ArrayList<>();
            joueursAComparer.add(this.controleur.getClassementJoueurs()[0]);
            joueursAComparer.add(this.controleur.getClassementJoueurs()[1]);
            if (this.controleur.getClassementJoueurs()[1].getScore() == this.controleur.getClassementJoueurs()[2].getScore()){
                joueursAComparer.add(this.controleur.getClassementJoueurs()[2]);
                if (this.controleur.getJoueursList().size() == 4){
                    if (this.controleur.getClassementJoueurs()[2].getScore() == this.controleur.getClassementJoueurs()[3].getScore()){
                        joueursAComparer.add(this.controleur.getClassementJoueurs()[3]);
                    }
                }
            }
            return Utils.joueurCartePlusHaute(joueursAComparer);
        } else {
            return this.controleur.getClassementJoueurs()[0];
        }
    }

    /**
     * Surcharge de la méthode toString permettant d'afficher le nom de l'effet
     * @return Nom de l'effet
     */
    @Override
    public String toString() {
        return "Meilleur Jest";
    }
}
