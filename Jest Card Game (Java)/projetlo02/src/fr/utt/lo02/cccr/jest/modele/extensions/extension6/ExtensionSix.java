package fr.utt.lo02.cccr.jest.modele.extensions.extension6;

import fr.utt.lo02.cccr.jest.controleur.PartieControleur;
import fr.utt.lo02.cccr.jest.modele.cartes.Carte;
import fr.utt.lo02.cccr.jest.modele.cartes.CarteCouleur;
import fr.utt.lo02.cccr.jest.modele.cartes.CarteValeur;
import fr.utt.lo02.cccr.jest.modele.cartes.effets.JoueurAvecJokerEffet;
import fr.utt.lo02.cccr.jest.modele.cartes.effets.MajoriteHauteurEffet;
import fr.utt.lo02.cccr.jest.modele.cartes.effets.PlusPetiteCouleurEffet;
import fr.utt.lo02.cccr.jest.modele.extensions.Extension;

import java.util.ArrayList;

/**
 * Classe définissant la création d'une extension
 */
public class ExtensionSix implements Extension {

    public ExtensionSix() {
    }

    /**
     * Méthode permettant la création de cartes "6" dans le cadre d'un extension
     * @param partieControleur le contrôleur de la partie dans laquelle on créé les cartes
     * @return la liste des cartes créées
     */
    public ArrayList<Carte> creerCartes(PartieControleur partieControleur){
        ArrayList<Carte> cartes = new ArrayList<>();
        cartes.add(new Carte(CarteCouleur.PIQUE, CarteValeur.SIX, new MajoriteHauteurEffet(CarteValeur.QUATRE, partieControleur), false, "/fr/utt/lo02/cccr/jest/modele/extensions/extension6/images/sixpique.png"));
        cartes.add(new Carte(CarteCouleur.TREFLE, CarteValeur.SIX, new PlusPetiteCouleurEffet(CarteCouleur.CARREAU, partieControleur), false, "/fr/utt/lo02/cccr/jest/modele/extensions/extension6/images/sixtrefle.png"));
        cartes.add(new Carte(CarteCouleur.CARREAU, CarteValeur.SIX, new MajoriteHauteurEffet(CarteValeur.TROIS, partieControleur), false, "/fr/utt/lo02/cccr/jest/modele/extensions/extension6/images/sixcarreau.png"));
        cartes.add(new Carte(CarteCouleur.COEUR, CarteValeur.SIX, new JoueurAvecJokerEffet(partieControleur), false, "/fr/utt/lo02/cccr/jest/modele/extensions/extension6/images/sixcoeur.png"));
        return cartes;
    }

    /**
     * Méthode retournant le nom de l'extension
     * @return Le nom de l'extension
     */
    @Override
    public String toString() {
        return "Ajout des cartes 6";
    }
}
