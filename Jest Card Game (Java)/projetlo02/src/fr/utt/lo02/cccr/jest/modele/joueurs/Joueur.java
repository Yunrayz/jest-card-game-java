package fr.utt.lo02.cccr.jest.modele.joueurs;

import fr.utt.lo02.cccr.jest.Main;
import fr.utt.lo02.cccr.jest.controleur.compteurScore.Visiteur;
import fr.utt.lo02.cccr.jest.modele.cartes.Carte;
import fr.utt.lo02.cccr.jest.modele.cartes.CarteValeur;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe définissant comment est fait un joueur
 */
public class Joueur {
    private final String nom;
    private int score = 0;

    private Carte carteVisible;
    private Carte carteCachee;

    private ArrayList<Carte> jest = new ArrayList<>();
    protected List<Carte> main = new ArrayList<Carte>(2);

    /**
     * Constructeur d'un joueur
     * @param nom le nom du joueur
     */
    public Joueur(String nom) {
        this.nom = nom;
    }

    /**
     * Méthode utilisée lorsqu'un joueur fait sa proposition, permettant de définir la carte visible, et la carte cachée dans les attributs du joueur
     * @param carteVisible la carte visible du joueur
     * @param carteCachee la carte cachée du joueur
     */
    public void proposerCartes(Carte carteVisible, Carte carteCachee){
        this.carteVisible = carteVisible;
        this.carteCachee = carteCachee;
        this.resetMain();
    }

    /**
     * Permet de retirer la carte au joueur après qu'elle ai été piochée par un autre joueur
     * @param carte la carte qui doit être retirée
     */
    public void retirerCartePiochee(Carte carte){
        if(carte.equals(this.carteCachee)){
            this.carteCachee=null;
        } else {
            this.carteVisible=null;
        }
    }

    /**
     * Permet de récupérer la carte restante du joueur lors du dernier tour d'une partie
     */
    public void recupererCarteDernierTour(){
        if (this.getCarteVisible() == null) {
            this.ajouterCarteJest(this.getCarteCachee());
            this.setCarteCachee(null);
        } else {
            this.ajouterCarteJest(this.getCarteVisible());
            this.setCarteVisible(null);
        }
    }

    /**
     * Méthode permettant de récupérer le nombre de carte d'une certaine hauteur que le joueur a dans son Jest
     * @param valeur la hauteur qu'on souhaite observer
     * @return le nombre de cartes de la hauteur
     */
    public int getNbCartesHauteur(CarteValeur valeur){
        int nbCartes = 0;
        for (Carte c : this.getJest()){
            if (c.getValeur() == valeur){
                nbCartes++;
            }
        }
        return nbCartes;
    }

    /**
     * Méthode permettant de retourner la carte la plus élevée dans le Jest du joueur
     * @return la carte la plus élevée
     */
    public Carte plusGrandeCarteJest(){
        Carte resultat = this.getJest().get(0);
        for (Carte carte : this.getJest()){
            if (carte.getValeurNum() > resultat.getValeurNum()){
                resultat = carte;
            } else {
                if (carte.getValeurNum() == resultat.getValeurNum()){
                    if (carte.getCouleurNum() > resultat.getCouleurNum())
                        resultat = carte;
                }
            }
        }
        return resultat;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Carte getCarteVisible() {
        return carteVisible;
    }

    public void setCarteVisible(Carte carteVisible) {
        this.carteVisible = carteVisible;
    }

    public Carte getCarteCachee() {
        return carteCachee;
    }

    public void setCarteCachee(Carte carteCachee) {
        this.carteCachee = carteCachee;
    }

    public ArrayList<Carte> getJest() {
        return jest;
    }

    public void setJest(ArrayList<Carte> jest) {
        this.jest = jest;
    }

    public List<Carte> getMain() {
        return this.main;
    }

    public void resetMain(){
        this.main.clear();
    }

    public void donnerCarte(Carte carte) {
        this.main.add(carte);
    }

    public void accept(Visiteur visiteur){
        visiteur.visit(this);
    }

    public void ajouterCarteJest(Carte carte){
        Main.showDebug();
        this.jest.add(carte);
    }

    public void retirerCarteJest(Carte carte){
        Main.showDebug();
        this.jest.remove(carte);
    }

    /**
     * Méthode permettant d'afficher le nom d'un joueur
     * @return le nom du joueur
     */
    @Override
    public String toString(){
        return this.nom;
    }
}
