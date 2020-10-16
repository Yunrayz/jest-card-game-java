package fr.utt.lo02.cccr.jest.modele.cartes.effets;

import fr.utt.lo02.cccr.jest.controleur.PartieControleur;
import fr.utt.lo02.cccr.jest.modele.cartes.Carte;
import fr.utt.lo02.cccr.jest.modele.cartes.CarteCouleur;
import fr.utt.lo02.cccr.jest.modele.cartes.Effet;
import fr.utt.lo02.cccr.jest.modele.joueurs.Joueur;

/**
 * Classe représentant l'effet plus grande couleur
 */
public class PlusGrandeCouleurEffet implements Effet {
    private CarteCouleur couleur;
    private PartieControleur controleur;

    /**
     * Constructeur
     * @param couleur la couleur de carte sur laquelle l'effet s'applique
     * @param controleur le contrôleur de la partie
     */
    public PlusGrandeCouleurEffet(CarteCouleur couleur, PartieControleur controleur) {
        this.couleur = couleur;
        this.controleur = controleur;
    }

    /**
     * Méthode permettant de déterminer le joueur ayant la plus grande carte d'une certaine couleur (par exemple, qui possède un 4 de pique)
     * @return le joueur possédant la plus grande couleur
     */
    @Override
    public Joueur appliquerEffet() {
        Joueur resultat = null;
        for(Joueur j : this.controleur.getJoueursList()){
            for (Carte c : j.getJest()){
                if (c.getCouleur() == couleur && c.getValeurNum() == 5){
                    resultat = j;
                }
            }
        }
        if (resultat == null){
            for(Joueur j : this.controleur.getJoueursList()){
                for (Carte c : j.getJest()){
                    if (c.getCouleur() == couleur && c.getValeurNum() == 4){
                        resultat = j;
                    }
                }
            }
        }
        return resultat;
    }

    /**
     * Surcharge de la méthode toString permettant d'afficher le nom de l'effet
     * @return Nom de l'effet
     */
    @Override
    public String toString() {
        return "Plus grand " + couleur.getNomLisible();
    }
}
