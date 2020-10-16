package fr.utt.lo02.cccr.jest.vue.cli;

import fr.utt.lo02.cccr.jest.Utils;
import fr.utt.lo02.cccr.jest.controleur.MainControleur;
import fr.utt.lo02.cccr.jest.modele.extensions.Extension;
import fr.utt.lo02.cccr.jest.modele.regles.Regles;


/**
 * Classe gérant l'affichage relatif à la création d'une nouvelle partie en mode CLI
 */
public class NouvellePartieCLIView implements Runnable {
    private Thread t;

    /**
     * Constructeur, permettant de créer un nouveau thread
     */
    public NouvellePartieCLIView() {
        this.t = new Thread(this);
        this.t.setName("CLI");
        this.t.start();
    }

    /**
     * Méthode demandant à l'utilisateur le nombre de joueurs humains
     */
    public void demanderNbJoueursHumains(){
        System.out.println("Combien de joueurs humains ? (0-4)");
    }

    /**
     * Méthode demandant à l'utilisateur le nom d'un joueur humain, successivement joueur 1, joueur 2...
     * @param numeroJoueur le numéro du joueur
     */
    public void demanderNomJoueur(int numeroJoueur){
        System.out.println("Nom du joueur " + numeroJoueur);
    }

    /**
     * Méthode permettant de demander le nombre de joueurs virtuels à l'utilisateur
     * @param min le nombre minimum de joueurs virtuels
     * @param max le nombre maximum de joueurs virtuels
     */
    public void demanderNbJoueursVirtuels(int min, int max){
        System.out.println("Combien de joueurs virtuels ? ("+min+"-"+max+")");
    }

    /**
     * Méthode permettant de demander l'extension à utiliser à l'utilisateur
     * @param extensions liste des extensions disponibles dans {@link fr.utt.lo02.cccr.jest.Main}
     */
    public void demanderExtension(Extension[] extensions){
        System.out.println("Avec quelle extension souhaitez-vous jouer ?");
        for (int i = 0; i < extensions.length; i++) {
            System.out.println("\t[" + (i+1) + "] " + extensions[i]);
        }
        System.out.println("Pour ne choisir aucune extension, entrez 0");
    }

    /**
     * Méthode permettant de demander la variante à utiliser à l'utilisateur
     * @param regles liste des variantes disponibles dans {@link fr.utt.lo02.cccr.jest.Main}
     */
    public void demanderVariante(Regles[] regles){
        System.out.println("Avec quelle variante voulez-vous jouer ?");
        for (int i = 0; i < regles.length; i++) {
            System.out.println("\t[" + (i+1) + "] " + regles[i].getNom());
        }
    }

    /**
     * Méthode permettant d'afficher au joueur qu'on créé la partie, en rappelant les paramètres qu'il a saisi
     * @param controleur le contrôleur de la partie
     */
    public void afficherDemarragePartie(MainControleur controleur){
        int nbJoueursVirtuels = controleur.getNbJoueursvirtuels();
        int nbJoueursHumains = controleur.getNbJoueursHumains();
        Regles regleChoisie = controleur.getRegleChoisie();
        Extension extensionChoisie = controleur.getExtensionChoisie();
        System.out.println("Variante choisie : " + regleChoisie.getNom());
        System.out.println("******************************************");
        System.out.println("Création d'une partie avec les paramètres suivants :");
        System.out.println("\t• " + nbJoueursHumains + " joueurs réels");
        System.out.println("\t• " + nbJoueursVirtuels + " joueurs virtuels");
        System.out.println("\t• Variante : " + regleChoisie.getNom());
        if (extensionChoisie == null){
            System.out.println("\t• Extension : Aucune");
        } else {
            System.out.println("\t• Extension : " + regleChoisie.getNom());
        }
        System.out.println("\nCréation de la partie en cours...");
        Utils.attendre(1500);
        System.out.print("\033[H\033[2J");
    }

    /**
     * Méthode permettant d'afficher une erreur au joueur
     * @param erreur texte de l'erreur à afficher
     */
    public void afficherErreur(String erreur){
        System.out.println(erreur);
    }

    @Override
    public void run() {

    }
}
