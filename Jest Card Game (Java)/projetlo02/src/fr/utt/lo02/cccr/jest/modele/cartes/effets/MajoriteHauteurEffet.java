package fr.utt.lo02.cccr.jest.modele.cartes.effets;

import fr.utt.lo02.cccr.jest.controleur.PartieControleur;
import fr.utt.lo02.cccr.jest.modele.cartes.Carte;
import fr.utt.lo02.cccr.jest.modele.cartes.CarteValeur;
import fr.utt.lo02.cccr.jest.modele.cartes.Effet;
import fr.utt.lo02.cccr.jest.modele.joueurs.Joueur;

/**
 * Classe représentant l'effet majorité d'une certaine hauteur
 */
public class MajoriteHauteurEffet implements Effet {
    private CarteValeur valeur;
    private PartieControleur controleur;

    /**
     * Constructeur
     * @param valeur la valeur de la hauteur (4, par exemple)
     * @param controleur le contrôleur de la partie
     */
    public MajoriteHauteurEffet(CarteValeur valeur, PartieControleur controleur) {
        this.valeur = valeur;
        this.controleur = controleur;
    }

    /**
     * Méthode retournant le joueur remplissant la condition
     * @return le joueur remplissant la condition
     */
    @Override
    public Joueur appliquerEffet() {
        Joueur resultat = this.controleur.getJoueursList().get(0);
        for(Joueur j : this.controleur.getJoueursList()){
            if (j.getNbCartesHauteur(valeur) > resultat.getNbCartesHauteur(valeur)){
                resultat=j;
            } else if (j.getNbCartesHauteur(valeur) == resultat.getNbCartesHauteur(valeur)){
                for (Carte c : j.getJest()){
                    for (Carte c2 : resultat.getJest()){
                        if (c.getValeur() == valeur && c2.getValeur() == valeur){
                            if (c.getCouleurNum() > c2.getCouleurNum()){
                                resultat=j;
                            }
                        }
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
        return "Majorité de " + valeur.getNomLisible();
    }
}
