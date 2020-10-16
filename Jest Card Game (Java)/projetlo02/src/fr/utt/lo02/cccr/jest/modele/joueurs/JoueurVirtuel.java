package fr.utt.lo02.cccr.jest.modele.joueurs;

import fr.utt.lo02.cccr.jest.controleur.PartieControleur;
import fr.utt.lo02.cccr.jest.modele.joueurs.strategies.Strategie;
import fr.utt.lo02.cccr.jest.modele.joueurs.strategies.StrategieBasse;
import fr.utt.lo02.cccr.jest.modele.joueurs.strategies.StrategieHaute;

import java.util.ArrayList;
import java.util.Random;

/**
 * Classe définissant comment est fait un joueur virtuel
 */
public class JoueurVirtuel extends Joueur {
    private Strategie strategie;

    /**
     * Constructeur d'un joueur virtuel, qui en plus de lui donner un nom, va lui attribuer une stratégie au hasard parmi une liste de stratégies
     * @param nom le nom du joueur
     */
    public JoueurVirtuel(String nom) {
        super(nom);
        Strategie[] listeStrategies = {new StrategieHaute(), new StrategieBasse()};
        Random ran = new Random();
        int i = ran.nextInt(listeStrategies.length);
        this.strategie = listeStrategies[i];
    }

    /**
     * Méthode permettant au joueur de proposer ses cartes tel que défini par la stratégie qu'il utilise
     */
    public void proposerCarte(){
        strategie.proposerCartes(this);
    }

    /**
     * Méthode permettant au joueur virtuel de piocher une carte chez un autre joueur, qu'il soit virtuel ou non, tel que défini dans la stratégie qu'il utilise
     * @param controleur le contrôleur de la partie
     * @param joueursDispos liste de joueurs disponibles, chez qui il peut piocher une carte
     * @param joueur le joueur qui doit piocher
     */
    public void piocherCarte(PartieControleur controleur, ArrayList<Joueur> joueursDispos, Joueur joueur){
        strategie.piocherCarte(controleur, joueursDispos, joueur);
    }
}
