package fr.utt.lo02.cccr.jest.vue.cli;

import fr.utt.lo02.cccr.jest.Main;
import fr.utt.lo02.cccr.jest.Utils;
import fr.utt.lo02.cccr.jest.controleur.PartieControleur;
import fr.utt.lo02.cccr.jest.modele.cartes.Carte;
import fr.utt.lo02.cccr.jest.modele.joueurs.Joueur;

import java.util.ArrayList;

/**
 * Classe gérant l'affichage au cours d'une partie, en mode CLI
 */
public class DeroulementPartieCLIView implements Runnable {
    private final PartieControleur controller;
    private Thread t;

    /**
     * Constructeur, permettant de créer un nouveau thread
     * @param controleur le contrôleur de la partie
     */
    public DeroulementPartieCLIView(PartieControleur controleur) {
        this.controller = controleur;
        this.t = new Thread(this);
        this.t.setName("CLI - Déroulement partie");
        this.t.start();
    }

    /**
     * Méthode permettant d'afficher les propositions des joueurs
     */
    public void afficherPropositions(){
        System.out.println("Propositions des joueurs :");
        for (Joueur j : controller.getJoueursList()){
            System.out.println(j + " : " + j.getCarteVisible() + ", carte cachée");
        }
    }

    /**
     * Méthode permettant d'afficher qu'il s'agit d'un nouveau tour
     */
    public void afficherNouveauTour(){
        System.out.println("==================[ Tour n°" + controller.getNbTours() + " ]==================");
    }

    /**
     * Méthode permettant d'afficher quel joueur doit prendre une carte
     * @param joueur le joueur qui doit prendre une carte
     */
    public void afficherTourJoueur(Joueur joueur){
        System.out.println("C'est au tour de " + joueur + " de prendre une carte.");
    }

    /**
     * Méthode permettant d'afficher quelle carte a été piochée lorsqu'il s'agit d'une carte visible
     * @param joueurTour le joueur qui a pioché une carte
     * @param joueurChoisi le joueur chez qui on a pioché une carte
     * @param cartePiochee la carte piochée
     */
    public void afficherCartePiochee(Joueur joueurTour, Joueur joueurChoisi, Carte cartePiochee){
        System.out.println("\t> " + Utils.CYAN + joueurTour + Utils.RESET + " a pioché le " + cartePiochee + " au joueur " + joueurChoisi);
    }

    /**
     * Méthode permettant d'afficher quelle carte a été piochée lorsqu'il s'agit d'une carte cachée
     * @param joueurTour le joueur qui a pioché une carte
     * @param joueurChoisi le joueur chez qui on a pioché une carte
     */
    public void afficherCartePiochee(Joueur joueurTour, Joueur joueurChoisi){
        System.out.println("\t> " + Utils.CYAN + joueurTour + Utils.RESET + " a pioché la carte cachée du joueur " + joueurChoisi);
    }

    /**
     * Méthode affichant que c'est à un autre joueur de proposer ses cartes
     * @param j le joueur qui doit proposer
     */
    public void afficherAutreJoueurJoue(Joueur j) {
        Main.showDebug();
        System.out.println("C'est au tour de " + Utils.CYAN + j + Utils.RESET + " de proposer ses cartes.");
    }

    /**
     * Méthode affichant au joueur humain que c'est à lui de proposer ses cartes
     * @param joueur le joueur humain qui doit jouer
     */
    public void afficherProposerCartes(Joueur joueur){
        Carte carte1 = joueur.getMain().get(0);
        Carte carte2 = joueur.getMain().get(1);

        String texte =  Utils.CYAN + joueur + Utils.RESET + ", quelle carte souhaitez-vous proposer face VISIBLE ?" +
                "\n\t[1] " + Utils.GREEN_BOLD + carte1 + Utils.RESET + "." +
                "\n\t[2] " + Utils.GREEN_BOLD + carte2 + Utils.RESET + ".";
        System.out.println(texte);
    }

    /**
     * Méthode affichant au joueur humain qu'il doit piocher une carte chez un autre joueur
     * @param joueursDispos Collection contenant les joueurs chez qui on peut prendre une carte
     * @param joueur le joueur qui joue
     */
    public void afficherPiocherCarte(ArrayList<Joueur> joueursDispos, Joueur joueur){
        System.out.print(Utils.CYAN + joueur + Utils.RESET + ", quelle carte souhaitez-vous prendre ?\nEntrez " + Utils.GREEN + "1 v" + Utils.RESET + " pour prendre la carte visible du premier joueur, " + Utils.RED + "1 c" + Utils.RESET + " pour prendre sa carte cachée" );
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < joueursDispos.size(); i++) {
            sb.append("\n\t[" + (i+1) + "] " + joueursDispos.get(i) + " : " + joueursDispos.get(i).getCarteVisible() + ", Carte cachée.");
        }
        System.out.println(sb.toString());
    }

    /**
     * Méthode affichant les cartes dans le trophée
     */
    public void afficherTrophees(){
        if (controller.getTrophes().size() == 1){
            System.out.println(Utils.CYAN_BOLD +  "\nCarte dans le trophée : " + controller.getTrophes().get(0) + Utils.RESET);
        } else {
            System.out.println(Utils.CYAN_BOLD +  "\nCartes dans le trophée : " + controller.getTrophes().get(0) + ", " + controller.getTrophes().get(1) + Utils.RESET);
        }
    }

    /**
     * Méthode affichant qu'on effectue la distribution des trophées
     */
    public void afficherAttributionTrophees(){
        System.out.println("Attribution des trophées selon la " + controller.getRegles().getNom());
    }

    /**
     * Méthode affichant qu'on effectue le calcul du score
     */
    public void afficherCalculScore(){
        System.out.println("Calcul des scores et établissement du classement selon la " + controller.getRegles().getNom());
    }

    /**
     * Méthode affichant le classement en fin de parti
     */
    public void afficherClassement(){
        System.out.println("Classement :");
        for (int i = 0; i < controller.getClassementJoueurs().length; i++) {
            System.out.println("\t" + Utils.GREEN_BOLD + (i+1) + ". " + controller.getClassementJoueurs()[i] + " : (" + controller.getClassementJoueurs()[i].getJest() + ")" + Utils.RED_BOLD_BRIGHT + " [" + controller.getClassementJoueurs()[i].getScore() + "]" + Utils.RESET);
        }
        System.out.println("Gagnant : " + controller.getClassementJoueurs()[0]);
    }

    /**
     * Méthode permettant d'afficher une erreur dans la CLI
     * @param erreur l'erreur à afficher
     */
    public void afficherErreur(String erreur){
        System.out.println(erreur);
    }

    @Override
    public void run() {
    }
}
