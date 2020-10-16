package fr.utt.lo02.cccr.jest.controleur.compteurScore;

import fr.utt.lo02.cccr.jest.modele.cartes.*;
import fr.utt.lo02.cccr.jest.modele.joueurs.Joueur;
import fr.utt.lo02.cccr.jest.modele.regles.Regles;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant de compter le score de chaque joueur
 */
public class CompteurScoreControleur implements Visiteur {
    private List<Joueur> joueurs;
    private Regles regles;
    private ArrayList<Carte> trophes;

    /**
     * Constructeur
     * @param joueurs Liste des joueurs
     * @param regles Règles utilisées
     * @param trophes Liste des cartes du trophée
     */
    public CompteurScoreControleur(List<Joueur> joueurs, Regles regles, ArrayList<Carte> trophes) {
        this.joueurs = joueurs;
        this.regles = regles;
        this.trophes = trophes;
    }

    /**
     * Méthode calculant le score en visitant chaque joueur
     */
    public void calculerScore(){
        for (Joueur j : this.joueurs){
            j.accept(this);
        }
    }

    /**
     * Méthode attribuant un trophée à un joueur
     */
    public void attribuerTrophes(){
        for (Carte c : trophes){
            c.getEffet().appliquerEffet().ajouterCarteJest(c);
        }
    }

    /**
     * Méthode permettant de visiter un joueur
     * @param joueur le joueur visité
     */
    @Override
    public void visit(Joueur joueur) {
        regles.calculerScore(joueur);
    }
}