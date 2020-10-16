package fr.utt.lo02.cccr.jest.modele.joueurs.strategies;

import fr.utt.lo02.cccr.jest.Utils;
import fr.utt.lo02.cccr.jest.controleur.PartieControleur;
import fr.utt.lo02.cccr.jest.modele.cartes.Carte;
import fr.utt.lo02.cccr.jest.modele.joueurs.Joueur;
import fr.utt.lo02.cccr.jest.modele.joueurs.JoueurVirtuel;

import java.util.ArrayList;
import java.util.Random;

/**
 * Méthode définissant la stratégie "Basse", dans cette stratégie, le jour virtuel va :
 * <ol>
 *     <li>Proposer sa carte la plus faible comme carte visible</li>
 *     <li>Piocher la carte cachée d'un joueur au hasard</li>
 * </ol>
 */
public class StrategieBasse implements Strategie {

    /**
     * Méthode permettant au joueur virtuel de proposer une carte, ici, sa carte la plus basse sera la carte qu'il proposera comme carte visible
     * @param joueurVirtuel le joueur virtuel
     */
    @Override
    public void proposerCartes(JoueurVirtuel joueurVirtuel) {
        Utils.attendre(1000);
        Carte c1 = joueurVirtuel.getMain().get(0);
        Carte c2 = joueurVirtuel.getMain().get(1);
        if (c1.getValeurNum()<c2.getValeurNum()){
            joueurVirtuel.setCarteVisible(c1);
            joueurVirtuel.setCarteCachee(c2);
        } else {
            joueurVirtuel.setCarteVisible(c2);
            joueurVirtuel.setCarteCachee(c1);
        }
        joueurVirtuel.resetMain();
    }

    /**
     * Méthode permettant au joueur virtuel de piocher une carte. Ici, il va piocher la carte cachée d'un joueur
     * @param controleur le contrôleur de la partie
     * @param joueursDispos liste des joueurs disponibles chez qui on peut piocher une carte
     * @param joueur le joueur virtuel dont c'est le tour de piocher
     */
    @Override
    public void piocherCarte(PartieControleur controleur, ArrayList<Joueur> joueursDispos, Joueur joueur) {
        Utils.attendre(1000);
        Random ran = new Random();
        int x = ran.nextInt(joueursDispos.size());
        Joueur joueurChoisi = joueursDispos.get(x);
        Carte carteChoisie = joueurChoisi.getCarteCachee();
        joueur.ajouterCarteJest(carteChoisie);
        joueurChoisi.retirerCartePiochee(carteChoisie);
        controleur.callbackTraitementPioche(joueur, joueurChoisi);
    }
}
