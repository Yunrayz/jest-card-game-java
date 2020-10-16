package fr.utt.lo02.cccr.jest.modele.cartes;

/**
 * Classe définissant comment est constituée une carte
 */
public class Carte {
    private final CarteCouleur couleur;
    private final CarteValeur valeur;
    private final String cheminImage;
    private Effet effet;
    private final boolean estJoker;
    private final String cheminDosCarte = "/fr/utt/lo02/cccr/jest/modele/cartes/images/autre/dos.png";

    /**
     * Constructeur d'une carte
     * @param couleur la couleur de la carte
     * @param valeur la valeur de la carte
     * @param estJoker La carte est-elle un joker ou non
     * @param effet Effet de la carte
     * @param cheminImage Chemin vers l'image
     */
    public Carte(CarteCouleur couleur, CarteValeur valeur, Effet effet, boolean estJoker, String cheminImage) {
        this.couleur = couleur;
        this.valeur = valeur;
        this.effet = effet;
        this.estJoker = estJoker;
        this.cheminImage = cheminImage;
    }

    public CarteCouleur getCouleur() {
        return couleur;
    }

    public CarteValeur getValeur() {
        return valeur;
    }

    public Effet getEffet() {
        return effet;
    }

    public int getValeurNum(){
        return this.valeur.getValeurNum();
    }

    public int getCouleurNum(){
        return this.couleur.getValeurCouleur();
    }

    public boolean isJoker() {
        return estJoker;
    }

    public String getCheminImage() {
        return cheminImage;
    }

    public String getCheminDosCarte() {
        return cheminDosCarte;
    }

    /**
     * Méthode permettant d'afficher le nom de la carte, avec un formattage différent s'il s'agit d'un Joker ou non
     * @return le nom de la carte
     */
    @Override
    public String toString() {
        if (valeur == CarteValeur.JOKER){
            return valeur.getNomLisible() + " (" + effet + ")";
        } else {
            return valeur.getNomLisible()+" de " + couleur.getNomLisible() + " (" + effet + ")";
        }
    }
}
