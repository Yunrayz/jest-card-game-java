package fr.utt.lo02.cccr.jest.modele.regles;

import fr.utt.lo02.cccr.jest.controleur.PartieControleur;
import fr.utt.lo02.cccr.jest.modele.cartes.Carte;
import fr.utt.lo02.cccr.jest.modele.cartes.CarteCouleur;
import fr.utt.lo02.cccr.jest.modele.cartes.CarteValeur;
import fr.utt.lo02.cccr.jest.modele.cartes.effets.JoueurAvecJokerEffet;
import fr.utt.lo02.cccr.jest.modele.cartes.effets.MajoriteHauteurEffet;
import fr.utt.lo02.cccr.jest.modele.cartes.effets.PlusGrandeCouleurEffet;
import fr.utt.lo02.cccr.jest.modele.joueurs.Joueur;

import java.util.ArrayList;

/**
 * Variante inversant la manière dont sont attribués/retirés les points
 */
public class ReglesInverses implements Regles {
    private String nom = "Variante inversée";
    private PartieControleur PC;

    public ReglesInverses() {}

    /**
     * Méthode vérifiant si le joueur possède le joker
     * @param joueur le joueur dont on vérifie le Jest
     * @return true si le joueur possède le joker, false sinon
     */
    private boolean possedeJoker(Joueur joueur){
        for (Carte carte : joueur.getJest()){
            if (carte.isJoker()){
                return true;
            }
        }
        return false;
    }

    /**
     * Compte le nombre de coeurs dans le Jest d'un joueur
     * @param joueur le joueur dont on compte le nombre de coeurs
     * @return le nombre de coeurs sous forme d'entier
     */
    private int nbCoeurs(Joueur joueur){
        int nbCoeurs = 0;
        for (Carte carte : joueur.getJest()){
            if (carte.getCouleur() == CarteCouleur.COEUR){
                nbCoeurs++;
            }
        }
        return nbCoeurs;
    }

    /**
     * Compte le nombre de paires de cartes noires (Piques, Trèfles) dans le Jest d'un joueur
     * @param j le joueur dont on compte le nombre de paires de cartes noires
     * @return le nombre de paire de cartes noires sous forme d'entier
     */
    private int nbBlackPair(Joueur j){
        ArrayList<Carte> cartesVisitees = new ArrayList<>(j.getJest().size());
        int nbPairs = 0;
        for (Carte c : j.getJest()){
            for (Carte c2 : j.getJest()){
                if (c != c2){
                    switch (c.getCouleur()){
                        case TREFLE:
                            if (c2.getCouleur() == CarteCouleur.PIQUE && c.getValeur() == c2.getValeur() && !cartesVisitees.contains(c2)){
                                nbPairs++;
                                cartesVisitees.add(c2);
                                cartesVisitees.add(c);
                            }
                            break;
                        case PIQUE:
                            if (c2.getCouleur() == CarteCouleur.TREFLE && c.getValeur() == c2.getValeur() && !cartesVisitees.contains(c2)){
                                nbPairs++;
                                cartesVisitees.add(c2);
                                cartesVisitees.add(c);
                            }
                            break;
                        case CARREAU:
                        case COEUR:
                        case AUTRE:
                            break;
                    }
                }
            }

        }
        return nbPairs;
    }

    /**
     * Vérifie si l'as du joueur est la seule carte de cette couleur dans son Jest
     * @param jestJoueur le jest du joueur
     * @param couleur la couleur à vérifier
     * @return true s'il s'agit de la seule carte de cette couleur dans son Jest, false sinon
     */
    private boolean seulAsCouleur(ArrayList<Carte> jestJoueur, CarteCouleur couleur){
        int nbCartesCouleur = 0;
        for (Carte c : jestJoueur){
            if (c.getCouleur() == couleur){
                nbCartesCouleur++;
            }
        }
        if (nbCartesCouleur > 1){
            return false;
        } else {
            return true;
        }
    }


    /**
     * Méthode permettant de transformer un as en cinq selon les règles
     * @param cartesAChanger Liste des cartes du joueur à permuter
     * @param j le joueur dont on permute les cartes
     */
    private void permuterAsCinq(ArrayList<Carte> cartesAChanger, Joueur j){
        for (Carte c : cartesAChanger){
            j.retirerCarteJest(c);
            switch (c.getCouleur()){
                case PIQUE:
                    j.ajouterCarteJest(new Carte(c.getCouleur(), CarteValeur.CINQ, new PlusGrandeCouleurEffet(CarteCouleur.TREFLE, PC), false, "/fr/utt/lo02/cccr/jest/modele/cartes/images/pique/as.png"));
                    break;
                case TREFLE:
                    j.ajouterCarteJest(new Carte(c.getCouleur(), CarteValeur.CINQ, new PlusGrandeCouleurEffet(CarteCouleur.PIQUE, PC), false, "/fr/utt/lo02/cccr/jest/modele/cartes/images/trefle/as.png"));
                    break;
                case COEUR:
                    j.ajouterCarteJest(new Carte(c.getCouleur(), CarteValeur.CINQ, new JoueurAvecJokerEffet(PC), false, "/fr/utt/lo02/cccr/jest/modele/cartes/images/coeur/as.png"));
                    break;
                case CARREAU:
                    j.ajouterCarteJest(new Carte(c.getCouleur(), CarteValeur.CINQ, new MajoriteHauteurEffet(CarteValeur.QUATRE, PC), false, "/fr/utt/lo02/cccr/jest/modele/cartes/images/carreau/as.png"));
                    break;
            }
        }
    }

    /**
     * Méthode calculant le score d'un joueur, notamment en faisant appel aux méthodes nbBlackPair, seulAsCouleur et nbCoeurs
     * @param j le joueur dont on calcule le score
     */
    public void calculerScore(Joueur j){
        ArrayList<Carte> cartesAChanger = new ArrayList<>();
        int scoreJoueur = 0;
        int nbPaires = 0;
        int nbCoeursJoueur = nbCoeurs(j);
        for(Carte c : j.getJest()){
            switch (c.getCouleur()){
                case CARREAU:
                    if (c.getValeur() == CarteValeur.AS){
                        if (seulAsCouleur(j.getJest(), CarteCouleur.CARREAU)){
                            cartesAChanger.add(c);
                            scoreJoueur+=5;
                        }
                    } else {
                        scoreJoueur+=c.getValeurNum();
                    }
                    break;
                case PIQUE:
                    if (c.getValeur() == CarteValeur.AS){
                        if (seulAsCouleur(j.getJest(), CarteCouleur.PIQUE)){
                            cartesAChanger.add(c);
                            scoreJoueur-=5;
                        }
                    } else {
                        scoreJoueur-=c.getValeurNum();
                    }
                    break;
                case TREFLE:
                    if (c.getValeur() == CarteValeur.AS){
                        if (seulAsCouleur(j.getJest(), CarteCouleur.TREFLE)){
                            cartesAChanger.add(c);
                            scoreJoueur-=5;
                        }
                    } else {
                        scoreJoueur-=c.getValeurNum();
                    }
                    break;
                case COEUR:
                    if (possedeJoker(j)){
                        if (nbCoeursJoueur < 4){
                            scoreJoueur+=c.getValeurNum();
                        } else {
                            scoreJoueur-=c.getValeurNum();
                        }
                    }
                    if (c.getValeur() == CarteValeur.AS){
                        if (seulAsCouleur(j.getJest(), CarteCouleur.COEUR)){
                            cartesAChanger.add(c);
                            scoreJoueur-=5;
                        }
                    } else {
                        scoreJoueur-=c.getValeurNum();
                    }
                    break;
                case AUTRE:
                    if (nbCoeursJoueur == 0){
                        scoreJoueur-=4;
                    }
                    break;
            }
        }
        permuterAsCinq(cartesAChanger, j);
        nbPaires = nbBlackPair(j);
        scoreJoueur-=(nbPaires*2);
        j.setScore(scoreJoueur);
    }

    public String getNom() {
        return nom;
    }

    @Override
    public void ajouterPartieControleur(PartieControleur partieControleur) {
        this.PC = partieControleur;
    }
}
