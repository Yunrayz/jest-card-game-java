package fr.utt.lo02.cccr.jest.controleur;

import fr.utt.lo02.cccr.jest.*;
import fr.utt.lo02.cccr.jest.controleur.compteurScore.CompteurScoreControleur;
import fr.utt.lo02.cccr.jest.modele.cartes.*;
import fr.utt.lo02.cccr.jest.modele.cartes.effets.*;
import fr.utt.lo02.cccr.jest.modele.extensions.Extension;
import fr.utt.lo02.cccr.jest.modele.joueurs.*;
import fr.utt.lo02.cccr.jest.modele.regles.*;
import fr.utt.lo02.cccr.jest.vue.gui.*;
import fr.utt.lo02.cccr.jest.vue.cli.*;

import java.util.*;

/**
 * Classe gérant le déroulement d'une partie
 */
public class PartieControleur extends Observable {
    private List<Joueur> joueursList = new ArrayList<>(4);
    private List<Joueur> joueursDejaJoues = new ArrayList<>();
    private List<Joueur> joueursPasDejaJoues = new ArrayList<>();
    private ArrayList<Joueur> joueursDispos = new ArrayList<>();

    private Extension extension;

    private Joueur tourDeProposer = null;
    private Joueur tourDePiocher = null;

    private ArrayList<Carte> deck = new ArrayList<>(17);
    private ArrayList<Carte> deckRound = new ArrayList<>(joueursList.size()*2);
    private ArrayList<Carte> trophes = new ArrayList<>(2);

    private int nbTours = 0;
    private Joueur[] classementJoueurs;

    private DeroulementPartieCLIView cli;
    private DeroulementPartieGUIView gui;
    private Regles regles;
    private CompteurScoreControleur CS;

    private int nbJoueursPropositions = 0;
    private boolean proposition = false;

    private boolean partieTerminee = false;

    /**
     * Constructeur de la classe, permet de créer une partie. Va créer le deck de cartes, si une extension a été choisie, va également créer les cartes de cette extension
     * @param nomJoueurs Tableau contenant les noms des joueurs réels
     * @param nomBots Tableau contenant les noms des joueurs virtuels (bots)
     * @param nbJoueursVirtuels Nombre de joueurs virtuels à créer
     * @param regles Règles à utiliser
     * @param extension l'extension à utiliser (peut être null)
     */
    public PartieControleur(ArrayList<String> nomJoueurs, String [] nomBots, int nbJoueursVirtuels, Regles regles, Extension extension){
        this.regles = regles;
        this.regles.ajouterPartieControleur(this);
        this.extension = extension;

        this.cli = new DeroulementPartieCLIView(this);
        this.gui = new DeroulementPartieGUIView(this);
        this.addObserver(this.gui);

        for (String nomJoueur : nomJoueurs) {
            this.joueursList.add(new JoueurHumain(nomJoueur));
        }

        for (int i = 0; i < nbJoueursVirtuels; i++) {
            this.joueursList.add(new JoueurVirtuel(nomBots[i]));
        }

        this.classementJoueurs = new Joueur[this.joueursList.size()];
        for (int i = 0; i < this.classementJoueurs.length; i++) {
            this.classementJoueurs[i] = this.joueursList.get(i);
        }

        this.CS = new CompteurScoreControleur(this.joueursList, this.regles, this.trophes);

        this.deck.add(new Carte(CarteCouleur.PIQUE, CarteValeur.AS, new PlusGrandeCouleurEffet(CarteCouleur.TREFLE, this), false, "/fr/utt/lo02/cccr/jest/modele/cartes/images/pique/as.png"));
        this.deck.add(new Carte(CarteCouleur.PIQUE, CarteValeur.DEUX, new MajoriteHauteurEffet(CarteValeur.TROIS, this), false, "/fr/utt/lo02/cccr/jest/modele/cartes/images/pique/deux.png"));
        this.deck.add(new Carte(CarteCouleur.PIQUE, CarteValeur.TROIS, new MajoriteHauteurEffet(CarteValeur.DEUX, this), false, "/fr/utt/lo02/cccr/jest/modele/cartes/images/pique/trois.png"));
        this.deck.add(new Carte(CarteCouleur.PIQUE, CarteValeur.QUATRE, new PlusPetiteCouleurEffet(CarteCouleur.TREFLE, this), false, "/fr/utt/lo02/cccr/jest/modele/cartes/images/pique/quatre.png"));

        this.deck.add(new Carte(CarteCouleur.TREFLE, CarteValeur.AS, new PlusGrandeCouleurEffet(CarteCouleur.PIQUE, this), false, "/fr/utt/lo02/cccr/jest/modele/cartes/images/trefle/as.png"));
        this.deck.add(new Carte(CarteCouleur.TREFLE, CarteValeur.DEUX, new PlusPetiteCouleurEffet(CarteCouleur.COEUR, this), false, "/fr/utt/lo02/cccr/jest/modele/cartes/images/trefle/deux.png"));
        this.deck.add(new Carte(CarteCouleur.TREFLE, CarteValeur.TROIS, new PlusGrandeCouleurEffet(CarteCouleur.COEUR, this), false, "/fr/utt/lo02/cccr/jest/modele/cartes/images/trefle/trois.png"));
        this.deck.add(new Carte(CarteCouleur.TREFLE, CarteValeur.QUATRE, new PlusPetiteCouleurEffet(CarteCouleur.PIQUE, this), false, "/fr/utt/lo02/cccr/jest/modele/cartes/images/trefle/quatre.png"));

        this.deck.add(new Carte(CarteCouleur.CARREAU, CarteValeur.AS, new MajoriteHauteurEffet(CarteValeur.QUATRE, this), false, "/fr/utt/lo02/cccr/jest/modele/cartes/images/carreau/as.png"));
        this.deck.add(new Carte(CarteCouleur.CARREAU, CarteValeur.DEUX, new PlusGrandeCouleurEffet(CarteCouleur.CARREAU, this), false, "/fr/utt/lo02/cccr/jest/modele/cartes/images/carreau/deux.png"));
        this.deck.add(new Carte(CarteCouleur.CARREAU, CarteValeur.TROIS, new PlusPetiteCouleurEffet(CarteCouleur.CARREAU, this), false, "/fr/utt/lo02/cccr/jest/modele/cartes/images/carreau/trois.png"));
        this.deck.add(new Carte(CarteCouleur.CARREAU, CarteValeur.QUATRE, new MeilleurJestSansJokerEffet(this), false, "/fr/utt/lo02/cccr/jest/modele/cartes/images/carreau/quatre.png"));

        this.deck.add(new Carte(CarteCouleur.COEUR, CarteValeur.AS, new JoueurAvecJokerEffet(this), false, "/fr/utt/lo02/cccr/jest/modele/cartes/images/coeur/as.png"));
        this.deck.add(new Carte(CarteCouleur.COEUR, CarteValeur.DEUX, new JoueurAvecJokerEffet(this), false, "/fr/utt/lo02/cccr/jest/modele/cartes/images/coeur/deux.png"));
        this.deck.add(new Carte(CarteCouleur.COEUR, CarteValeur.TROIS, new JoueurAvecJokerEffet(this), false, "/fr/utt/lo02/cccr/jest/modele/cartes/images/coeur/trois.png"));
        this.deck.add(new Carte(CarteCouleur.COEUR, CarteValeur.QUATRE, new JoueurAvecJokerEffet(this), false, "/fr/utt/lo02/cccr/jest/modele/cartes/images/coeur/quatre.png"));

        this.deck.add(new Carte(CarteCouleur.AUTRE, CarteValeur.JOKER, new MeilleurJestEffet(this), true, "/fr/utt/lo02/cccr/jest/modele/cartes/images/autre/joker.png"));

        if (this.extension != null){
            this.deck.addAll(extension.creerCartes(this));
        }

        Collections.shuffle(this.deck);

        if ((nbJoueursVirtuels+nomJoueurs.size()) == 3 ){
            for (int i = 0; i<2; i++){
                this.trophes.add(this.deck.get(0));
                this.deck.remove(0);
            }
        } else {
            this.trophes.add(this.deck.get(0));
            this.deck.remove(0);
        }

        for (Joueur joueur: joueursList){
            for (int i = 0; i < 2; i++) {
                joueur.donnerCarte(this.deck.get(0));
                this.deck.remove(0);
            }
        }


        jouerTour();
    }

    /**
     * Méthode permettant de jouer la première partie d'un tour : distribution des cartes et demander les propositions de chaque joueurs. Si le nombre de cartes dans le deck est strictement inférieur au nombre de joueurs, on appelle la méthode dernierTour()
     */
    private void jouerTour(){
        this.proposition = false;
        setChanged();
        notifyObservers();
        if (this.deck.size() >= this.joueursList.size()){
            ajouterTour();
            if (this.nbTours != 1){
                distribuerCarteTour();
            }
            this.nbJoueursPropositions = 0;
            demanderProposition();
        } else {
            dernierTour();
        }
    }

    /**
     * Méthode permettant de continuer le déroulement d'un tour, après que tous les joueurs aient proposés leurs cartes. On détermine alors le premier joueur qui doit jouer, et on demande à ce joueur de piocher une carte.
     */
    private void continuerTour(){
        this.proposition = true;
        setChanged();
        notifyObservers();

        cli.afficherPropositions();

        Joueur premierJoueur = premierJoueur(this.joueursList);

        this.joueursPasDejaJoues.addAll(this.joueursList);

        demanderPioche(premierJoueur);
    }

    /**
     * Méthode permettant d'effectuer le dernier tour : on récupère la carte restante de chaque joueur, puis on calcule une première fois le score, afin d'effectuer la distribution des trophées, et on recalcule à nouveau le score pour avoir le classement final.
     */
    private void dernierTour(){
        for (Joueur joueur : joueursList){
            joueur.recupererCarteDernierTour();
        }

        cli.afficherTrophees();
        cli.afficherAttributionTrophees();
        CS.calculerScore();
        calculerClassement();
        CS.attribuerTrophes();
        CS.calculerScore();
        calculerClassement();
        trophes.clear();
        cli.afficherCalculScore();
        cli.afficherClassement();
        this.partieTerminee = true;
        setChanged();
        notifyObservers();
        Utils.attendre(15000);
        System.exit(0);
    }

    /**
     * Méthode permettant de traiter lorsqu'un joueur propose une carte
     * @param carteVisible la carte visible proposée par le joueur
     */
    public void traitementProposer(Carte carteVisible){
        Main.showDebug();
        if (this.tourDeProposer.getMain().get(0).equals(carteVisible)){
            this.tourDeProposer.proposerCartes(carteVisible, this.tourDeProposer.getMain().get(1));
        } else {
            this.tourDeProposer.proposerCartes(carteVisible, this.tourDeProposer.getMain().get(0));
        }
        this.nbJoueursPropositions++;
        this.tourDeProposer = null;
        setChanged();
        notifyObservers();
        demanderProposition();
    }

    /**
     * Méthode permettant de demander sa proposition à chaque joueur, en effectuant une action différente s'il s'agit d'un joueur humain ou virtuel. Si tous les joueurs ont effectués leur proposition, on continue alors le tour
     */
    private void demanderProposition(){
        Main.showDebug();
        if (this.nbJoueursPropositions < this.joueursList.size()){
            if (this.joueursList.get(this.nbJoueursPropositions) instanceof JoueurHumain){
                this.tourDeProposer = this.joueursList.get(this.nbJoueursPropositions);
                setChanged();
                notifyObservers();
                cli.afficherProposerCartes(this.tourDeProposer);
            } else {
                setChanged();
                notifyObservers();
                cli.afficherAutreJoueurJoue(this.joueursList.get(this.nbJoueursPropositions));
                JoueurVirtuel joueurVirtuel = (JoueurVirtuel) this.joueursList.get(this.nbJoueursPropositions);
                joueurVirtuel.proposerCarte();
                this.nbJoueursPropositions++;
                demanderProposition();
            }
        } else {
            continuerTour();
        }
    }

    /**
     * Méthode permettant de demander à un joueur de piocher une carte chez les autres joueurs. On détermine ici quels sont les joueurs qui ont déjà piochés dans le tour, mais également les joueurs chez qui il est possible de piocher
     * @param joueur le joueur qui doit piocher
     */
    public void demanderPioche(Joueur joueur){
        setChanged();
        notifyObservers();
        this.joueursDispos.clear();
        for (Joueur j : this.joueursList) {
            if (j.getCarteVisible() != null && j.getCarteCachee() != null) {
                if(j != joueur){
                    joueursDispos.add(j);
                }
            }
        }
        if (joueursDejaJoues.contains(joueur)){
            if (joueursPasDejaJoues.size() == 1 ){
                joueur = premierJoueur(joueursPasDejaJoues);
            } else {
                List<Joueur> tempJoueursPasDejaJoues = new ArrayList<>();
                tempJoueursPasDejaJoues.addAll(joueursPasDejaJoues);
                tempJoueursPasDejaJoues.remove(joueur);
                joueur = premierJoueur(tempJoueursPasDejaJoues);
            }
        }
        if (joueursDejaJoues.size() == joueursList.size()){
            return;
        }
        this.joueursDejaJoues.add(joueur);
        this.joueursPasDejaJoues.remove(joueur);
        if (Main.DEBUG) {
            System.out.println(joueursPasDejaJoues);
        }
        cli.afficherTourJoueur(joueur);
        if (joueursPasDejaJoues.size() == 1 && joueur == joueursDejaJoues.get(0)){
            joueursDispos.add(joueur);
        }
        if (joueursDispos.size() == 0){
            return;
        }
        if(joueur instanceof JoueurVirtuel){
            ((JoueurVirtuel) joueur).piocherCarte(this, joueursDispos, joueur);
        } else {
            cli.afficherPiocherCarte(joueursDispos, joueur);
            this.tourDePiocher = joueur;
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Méthode utilisée lorsque le joueur (réel ou virtuel) va piocher la carte visible d'un autre joueur, et va demander à ce joueur de piocher à son tour (tel que défini dans les règles du Jest)
     * @param joueurTour le joueur qui a pioché
     * @param joueurChoisi le joueur choisi par le joueur qui a pioché
     * @param cartePiochee la carte piochée par le joueur
     */
    public void callbackTraitementPioche(Joueur joueurTour, Joueur joueurChoisi, Carte cartePiochee){
        if (this.tourDePiocher != null){
            joueurTour.ajouterCarteJest(cartePiochee);
            joueurChoisi.retirerCartePiochee(cartePiochee);
            this.tourDePiocher = null;
        }
        setChanged();
        notifyObservers();
        cli.afficherCartePiochee(joueurTour, joueurChoisi, cartePiochee);
        if (this.joueursDejaJoues.size() != this.joueursList.size()){
            demanderPioche(joueurChoisi);
        } else {
            this.joueursDejaJoues.clear();
            jouerTour();
        }
    }

    /**
     * Méthode utilisée lorsque le joueur (réel ou virtuel) va piocher la carte cachée d'un autre joueur, et va demander à ce joueur de piocher à son tour (tel que défini dans les règles du Jest)
     * @param joueurTour le joueur qui a pioché
     * @param joueurChoisi le joueur choisi par le joueur qui a pioché
     */
    public void callbackTraitementPioche(Joueur joueurTour, Joueur joueurChoisi){
        if (this.tourDePiocher != null){
            joueurTour.ajouterCarteJest(joueurChoisi.getCarteCachee());
            joueurChoisi.retirerCartePiochee(joueurChoisi.getCarteCachee());
            this.tourDePiocher = null;
        }
        cli.afficherCartePiochee(joueurTour, joueurChoisi);
        setChanged();
        notifyObservers();
        if (this.joueursDejaJoues.size() != this.joueursList.size()){
            demanderPioche(joueurChoisi);
        } else {
            this.joueursDejaJoues.clear();
            jouerTour();
        }
    }

    /**
     * Méthode permettant d'incrémenter le compteur de tours
     */
    private void ajouterTour(){
        this.nbTours++;
        cli.afficherNouveauTour();
        setChanged();
        notifyObservers();
    }

    /**
     * Méthode permettant de définir qui est le premier joueur qui doit piocher parmi une liste de joueurs en comparant leurs cartes visibles
     * @param joueurs une liste de joueurs qui peuvent piocher
     * @return le premier joueur qui doit piocher
     */
    public Joueur premierJoueur(List<Joueur> joueurs){
        Joueur premierJoueur = null;
        for (int i = 0; i < joueurs.size(); i++) {
            if (i==0){
                premierJoueur = joueurs.get(0);
            } else {
                if (premierJoueur.getCarteVisible().getValeurNum() == joueurs.get(i).getCarteVisible().getValeurNum()){
                    if (premierJoueur.getCarteVisible().getCouleurNum() < joueurs.get(i).getCarteVisible().getCouleurNum()){
                        premierJoueur = joueurs.get(i);
                    }
                } else if (premierJoueur.getCarteVisible().getValeurNum() < joueurs.get(i).getCarteVisible().getValeurNum()) {
                    premierJoueur = joueurs.get(i);
                }
            }
        }
        return premierJoueur;
    }

    /**
     * Méthode permettant de distribuer les cartes des joueurs à chaque tour
     */
    public void distribuerCarteTour(){
        for (Joueur joueur : this.joueursList){
            if (joueur.getCarteVisible() != null){
                this.deckRound.add(joueur.getCarteVisible());
                joueur.setCarteVisible(null);
            } else {
                this.deckRound.add(joueur.getCarteCachee());
                joueur.setCarteCachee(null);
            }
            this.deckRound.add(this.deck.get(0));
            this.deck.remove(0);
        }
        Collections.shuffle(this.deckRound);

        for (Joueur joueur : this.joueursList){
            for (int i = 0; i < 2; i++) {
                joueur.donnerCarte(this.deckRound.get(0));
                this.deckRound.remove(0);
            }
        }
    }

    /**
     * Méthode permettant de calculer le classement des joueurs en se basant sur leur score
     */
    private void calculerClassement(){
        int n = classementJoueurs.length;
        Joueur temp = null;
        for(int i = 0; i < n; i++){
            for(int j=1; j < (n-i); j++){
                if(classementJoueurs[j-1].getScore() < classementJoueurs[j].getScore()){
                    temp = classementJoueurs[j-1];
                    classementJoueurs[j-1] = classementJoueurs[j];
                    classementJoueurs[j] = temp;
                }
            }
        }
    }

    /**
     * Méthode permettant de gérer toute entrée utilisateur réalisée durant le déroulement de la partie
     * @param retour l'entrée réalisée par l'utilisateur
     */
    public void retourConsole(String retour){
        Main.showDebug();
        if (!retour.equals("")){
            if (!(retour.length() > 3)){
                if (this.tourDeProposer != null){
                    try {
                        int choixCarte = (Integer.parseInt(retour)-1);
                        if(choixCarte >= 0 && choixCarte < 2){
                            this.traitementProposer(this.tourDeProposer.getMain().get(choixCarte));
                        } else {
                            cli.afficherErreur("Valeure incorrecte, veuillez réessayer");
                            cli.afficherProposerCartes(this.tourDeProposer);
                        }
                    } catch (Exception e) {
                        cli.afficherErreur("Valeure incorrecte, veuillez réessayer");
                        cli.afficherProposerCartes(this.tourDeProposer);
                    }
                } else if (this.tourDePiocher != null){
                    try {
                        String[] vars = retour.split(" ");
                        Joueur joueurChoisi = null;
                        if (this.getJoueursDispos().size() != 1){
                            if (Integer.parseInt(vars[0]) == 0){
                                joueurChoisi = this.getJoueursDispos().get(Integer.parseInt(vars[0]));
                            } else {
                                joueurChoisi = this.getJoueursDispos().get(Integer.parseInt(vars[0])-1);
                            }
                        } else {
                            joueurChoisi = this.getJoueursDispos().get(0);
                        }
                        if (vars[1].equals("v")) {
                            callbackTraitementPioche(this.tourDePiocher, joueurChoisi, joueurChoisi.getCarteVisible());
                        } else if (vars[1].equals("c")){
                            callbackTraitementPioche(this.tourDePiocher, joueurChoisi);
                        } else {
                            cli.afficherErreur("La valeur entrée pour la carte à piocher est incorrecte, veuillez réessayer.");
                        }
                    } catch (Exception e) {
                        cli.afficherErreur("Valeure incorrecte, veuillez réessayer");
                    }
                } else {
                    cli.afficherErreur("Aucune action à effectuer, attendez votre tour SVP");
                }
            } else {
                cli.afficherErreur("La valeur entrée est incorrecte, veuillez réessayer");
            }
        }
    }

    public ArrayList<Carte> getDeck() {
        return deck;
    }

    public ArrayList<Carte> getTrophes() {
        return trophes;
    }

    public int getNbTours() {
        return nbTours;
    }

    public Joueur getTourDeProposer() {
        return tourDeProposer;
    }

    public Joueur getTourDePiocher() {
        return tourDePiocher;
    }

    public List<Joueur> getJoueursList() {
        return joueursList;
    }

    public ArrayList<Joueur> getJoueursDispos() {
        return joueursDispos;
    }

    public Regles getRegles() {
        return regles;
    }

    public boolean getPartieTerminee(){
        return this.partieTerminee;
    }

    public Joueur[] getClassementJoueurs(){
        return this.classementJoueurs;
    }

    public boolean isProposition() {
        return proposition;
    }
}