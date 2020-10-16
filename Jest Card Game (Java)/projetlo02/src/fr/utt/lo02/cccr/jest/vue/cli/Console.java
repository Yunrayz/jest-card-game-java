package fr.utt.lo02.cccr.jest.vue.cli;

import fr.utt.lo02.cccr.jest.controleur.MainControleur;
import fr.utt.lo02.cccr.jest.controleur.PartieControleur;

import java.util.Scanner;

/**
 * Classe gérant la console, utilisée afin de gérer les entrées utilisateur pour la configuration de la partie et durant la partie
 */
public class Console implements Runnable {
    private MainControleur mainControleur;
    private PartieControleur partieControleur = null;
    private Thread t;

    /**
     * Constructeur démarrant un nouveau thread pour la console
     * @param mainControleur le contrôleur de création de partie
     */
    public Console(MainControleur mainControleur){
        this.mainControleur = mainControleur;
        this.t = new Thread(this);
        this.t.start();
    }

    public void setPartieControleur(PartieControleur partieControleur) {
        this.partieControleur = partieControleur;
    }

    /**
     * Méthode permettant de rediriger l'entrée utilisateur vers le bon contrôleur.
     * <ul>
     *     <li>Si partieControleur n'est pas défini, on renvoi dans la méthode retourConsole() de {@link fr.utt.lo02.cccr.jest.controleur.MainControleur}</li>
     *     <li>Si partieControleur est défini, on renvoi dans la méthode retourConsole() de {@link fr.utt.lo02.cccr.jest.controleur.PartieControleur}</li>
     * </ul>
     */
    @Override
    public void run() {
        this.t.setName("Console");
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            String entree = sc.nextLine();
            if (this.partieControleur == null){
                this.mainControleur.retourConsole(entree);
            } else {
                this.partieControleur.retourConsole(entree);
            }
        }
    }
}
