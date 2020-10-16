package fr.utt.lo02.cccr.jest.controleur;

import fr.utt.lo02.cccr.jest.Utils;
import fr.utt.lo02.cccr.jest.modele.extensions.Extension;
import fr.utt.lo02.cccr.jest.modele.regles.*;
import fr.utt.lo02.cccr.jest.vue.cli.Console;
import fr.utt.lo02.cccr.jest.vue.cli.NouvellePartieCLIView;
import fr.utt.lo02.cccr.jest.vue.gui.NouvellePartieGUIView;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Classe gérant le démarrage d'une nouvelle partie, permettant de régler le nombre de joueurs, leurs noms, la variante ou encore l'extension utilisée
 */
public class MainControleur extends Observable {
    private final String[] nomsBots;
    private final Regles[] regles;
    private final Extension[] extensions;
    private int nbJoueursHumains=-1;
    private int nbJoueursvirtuels=-1;
    private Regles regleChoisie = null;
    private Extension extensionChoisie = null;
    private ArrayList<String> nomsJoueurs = new ArrayList<>();
    private NouvellePartieCLIView cli;
    private NouvellePartieGUIView gui;
    private boolean partieDemarree;
    private Console console = null;

    /**
     * Constructeur, permettant de récupérer les différentes valeurs telles que les noms de bots, variantes... proposées à l'utilisateur durant la configuration de la partie.
     * Cette méthode va également provoquer la création de l'interface graphique, et de l'interface en ligne de commande.
     * @param nomsBots les noms des joueurs virtuels, tels que définis dans la classe {@link fr.utt.lo02.cccr.jest.Main}
     * @param regles les variantes disponibles, telles que définies dans la classe {@link fr.utt.lo02.cccr.jest.Main}
     * @param extensions les extensions utilisables, telles que définies dans la classe {@link fr.utt.lo02.cccr.jest.Main}
     */
    public MainControleur(String[] nomsBots, Regles[] regles, Extension[] extensions) {
        this.nomsBots = nomsBots;
        this.regles = regles;
        this.extensions = extensions;
        this.partieDemarree = false;

        this.cli = new NouvellePartieCLIView();
        this.gui = new NouvellePartieGUIView(this);
    }

    /**
     *
     */
    public void demarrerPartie() {
        this.addObserver(gui);
        cli.demanderNbJoueursHumains();
        this.console = new Console(this);
    }

    /**
     * Méthode permettant de régler le nombre de joueur humain.
     * Une fois le nombre de joueurs humains choisis, va demander soit:
     * <ul>
     *     <li>Si le nombre de joueurs humains est 0, le nombre de joueurs virtuels, en affichant le nombre min. et max. de joueurs</li>
     *     <li>Sinon, le nom du premier joueur humain</li>
     * </ul>
     * @param nbJoueursHumains le nombre de joueurs humains tel que choisi par l'utilisateur en CLI/GUI
     */
    public void setNbJoueursHumains(int nbJoueursHumains) {
        this.nbJoueursHumains = nbJoueursHumains;
        this.setChanged();
        this.notifyObservers(this.nbJoueursHumains);
        if (this.nbJoueursHumains == 0){
            cli.demanderNbJoueursVirtuels(3-this.nbJoueursHumains,4-this.nbJoueursHumains);
        } else {
            cli.demanderNomJoueur(1);
        }
    }

    /**
     * Méthode permettant de régler le nombre de joueur humain.
     * Une fois le nombre de joueurs virtuels choisis, va demander la variante à utiliser
     * @param nbJoueursvirtuels le nombre de joueurs virtuels tel que choisi par l'utilisateur en CLI/GUI
     */
    public void setNbJoueursvirtuels(int nbJoueursvirtuels) {
        if (nbJoueursvirtuels >= 3-this.nbJoueursHumains && nbJoueursvirtuels <= 4-this.nbJoueursHumains){
            this.nbJoueursvirtuels = nbJoueursvirtuels;
            this.setChanged();
            this.notifyObservers();
            cli.demanderVariante(regles);
        } else {
            System.out.println(3-this.nbJoueursHumains);
            System.out.println(4-this.nbJoueursHumains);
            System.out.println(nbJoueursvirtuels);
            cli.afficherErreur("Le nombre de joueurs virtuels entré est incorrect, veuillez réessayer.");
        }
    }

    /**
     * Méthode ajoutant le nom d'un joueur tel que choisi par l'utilisateur, s'il reste des joueurs humains sans nom, on demande à nouveau un nom à l'utilisateur, sinon, on demande le nombre de joueurs virtuels
     * @param nom le nom choisi par l'utilisateur
     */
    public void ajouterNomJoueur(String nom) {
        if (this.nomsJoueurs.contains(nom)){
            cli.afficherErreur("Le nom entré a déjà été pris par un autre joueur, veuillez réessayer.");
            cli.demanderNomJoueur(this.nomsJoueurs.size()+1);
        } else {
            this.nomsJoueurs.add(nom);
            setChanged();
            this.notifyObservers();
            if (this.nomsJoueurs.size() != this.nbJoueursHumains){
                cli.demanderNomJoueur(this.nomsJoueurs.size()+1);
            } else {
                if (this.nbJoueursHumains == 4){
                    this.nbJoueursvirtuels = 0;
                } else {
                    cli.demanderNbJoueursVirtuels(3-this.nbJoueursHumains,4-this.nbJoueursHumains);
                }
            }
        }
    }

    /**
     * Méthode permettant de définir la variante à utiliser pour la partie
     * @param choixRegle la variante choisie par l'utilisateur
     */
    public void setRegleChoisie(int choixRegle) {
        this.regleChoisie = regles[choixRegle];
        this.setChanged();
        this.notifyObservers(this);
        cli.demanderExtension(extensions);
    }

    /**
     * Méthode permettant de définir l'extension à utiliser pour la partie
     * @param choixExtension l'extension choisie par l'utilisateur
     */
    public void setExtensionChoisie(int choixExtension){
        if (choixExtension == -1){
            this.extensionChoisie = null;
        } else {
            this.extensionChoisie = extensions[choixExtension];
        }
        this.partieDemarree = true;
        setChanged();
        notifyObservers();
        cli.afficherDemarragePartie(this);
        Utils.attendre(1500);
        creerPartie(this.nomsJoueurs, this.nbJoueursvirtuels, regleChoisie, extensionChoisie);
    }

    /**
     *  @param nomJoueursReels Tableau contenant les noms de tous les joueurs réels de la partie
     * @param nbJoueursVirtuels Nombre de joueurs virtuels à créer dans la partie
     * @param regles Règles à utiliser pour la partie
     * @param extension L'extension à utiliser dans la partie
     */
    public void creerPartie(ArrayList<String> nomJoueursReels, int nbJoueursVirtuels, Regles regles, Extension extension) {
        this.console.setPartieControleur(new PartieControleur(nomJoueursReels, this.nomsBots, nbJoueursVirtuels, regles, extension));
    }

    /**
     * Méthode permettant de gérer toute entrée utilisateur réalisée durant la configuration de la partie, qui va ensuite déterminer quelles informations ont déjà été renseignées ou non, afin d'effectuer la bonne action
     * @param retour l'entrée réalisée par l'utilisateur
     */
    public void retourConsole(String retour){
        if (!retour.equals("")){
            if (this.nbJoueursHumains == -1) {
                try {
                    int nbJoueursHumains = Integer.parseInt(retour);
                    if(nbJoueursHumains >= 0 && nbJoueursHumains < 5){
                        this.setNbJoueursHumains(nbJoueursHumains);
                    } else {
                        cli.afficherErreur("Nous ne pouvez jouer qu'avec 4 joueurs maximum!");
                        cli.demanderNbJoueursHumains();
                    }
                } catch (Exception e) {
                    cli.afficherErreur("La valeur entrée est incorrecte, essayez à nouveau SVP");
                    cli.demanderNbJoueursHumains();
                }
            } else if (this.nbJoueursHumains != this.nomsJoueurs.size()){
                this.ajouterNomJoueur(retour);
            } else if (this.nbJoueursvirtuels == -1){
                try {
                    int nbJoueursVirtuels = Integer.parseInt(retour);
                    this.setNbJoueursvirtuels(nbJoueursVirtuels);
                } catch (Exception e) {
                    cli.afficherErreur("La valeur entrée est incorrecte, essayez à nouveau SVP");
                    cli.demanderNbJoueursVirtuels(3-this.nbJoueursHumains, 4-this.nbJoueursHumains);
                }
            } else if (this.regleChoisie == null){
                try {
                    int choixRegle = (Integer.parseInt(retour)-1);
                    if(choixRegle >= 0 && choixRegle < regles.length){
                        this.setRegleChoisie(choixRegle);
                    } else {
                        cli.afficherErreur("Aucune variante ne correspond, essayez à nouveau SVP.");
                        cli.demanderVariante(regles);
                    }
                } catch (Exception e) {
                    cli.afficherErreur("La valeur entrée est incorrecte, essayez à nouveau SVP");
                    cli.demanderVariante(regles);
                    throw e;
                }
            } else {
                try {
                    int choixExtension = (Integer.parseInt(retour)-1);
                    if(choixExtension >= -1 && choixExtension < extensions.length){
                        this.setExtensionChoisie(choixExtension);
                    } else {
                        cli.afficherErreur("Aucune extension ne correspond, essayez à nouveau SVP.");
                        cli.demanderExtension(extensions);
                    }
                } catch (Exception e) {
                    cli.afficherErreur("La valeur entrée est incorrecte, essayez à nouveau SVP");
                    cli.demanderExtension(extensions);
                    throw e;
                }
            }
        }
    }

    public Regles[] getRegles() {
        return regles;
    }

    public Extension getExtensionChoisie(){
        return this.extensionChoisie;
    }

    public int getNbJoueursHumains() {
        return nbJoueursHumains;
    }

    public boolean isPartieDemarree() {
        return partieDemarree;
    }

    public int getNbJoueursvirtuels() {
        return nbJoueursvirtuels;
    }

    public ArrayList<String> getNomsJoueurs() {
        return nomsJoueurs;
    }

    public Regles getRegleChoisie() {
        return regleChoisie;
    }

    public Extension[] getExtensions() {
        return extensions;
    }
}
