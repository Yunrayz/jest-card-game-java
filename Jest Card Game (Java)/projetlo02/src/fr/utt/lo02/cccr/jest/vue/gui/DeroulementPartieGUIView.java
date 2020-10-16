package fr.utt.lo02.cccr.jest.vue.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import javax.swing.border.*;

import fr.utt.lo02.cccr.jest.Utils;
import fr.utt.lo02.cccr.jest.controleur.PartieControleur;
import fr.utt.lo02.cccr.jest.modele.joueurs.Joueur;
import net.miginfocom.swing.*;

/**
 * Classe gérant la GUI de déroulement d'une partie
 */
public class DeroulementPartieGUIView extends JFrame implements Observer, Runnable {
    /**
     * Constructeur, créant un thread dédié à la GUI
     * @param controleur le contrôleur de la partie {@link fr.utt.lo02.cccr.jest.controleur.PartieControleur}
     */
    public DeroulementPartieGUIView(PartieControleur controleur) {
        this.controleur = controleur;
        this.t = new Thread(this);
        this.t.setName("GUI - Déroulement Partie");
        this.t.start();
    }

    /**
     * Méthode appelée lorsqu'on actionne le bouton correspondant à la carte 1 pour la proposition de carte
     */
    private void buttonPropositionCarte1ActionPerformed(ActionEvent e) {
        this.panelProposition.setVisible(false);
        controleur.traitementProposer(controleur.getTourDeProposer().getMain().get(0));
    }

    /**
     Méthode appelée lorsqu'on actionne le bouton correspondant à la carte 2 pour la proposition de carte
     */
    private void buttonPropositionCarte2ActionPerformed(ActionEvent e) {
        this.panelProposition.setVisible(false);
        controleur.traitementProposer(controleur.getTourDeProposer().getMain().get(1));
    }

    /**
     * Méthode appelée lorsqu'une action sur une des cartes (bouton) lorsque c'est au joueur humain de piocher une carte
     */
    private void buttonPiocherCarteActionPerformed(ActionEvent e) {
        String[] vars = e.getActionCommand().split(" ");
        Joueur joueurChoisi = null;
        if (controleur.getJoueursDispos().size() != 1){
            if (Integer.parseInt(vars[0]) == 0){
                joueurChoisi = controleur.getJoueursDispos().get(Integer.parseInt(vars[0]));
            } else {
                joueurChoisi = controleur.getJoueursDispos().get(Integer.parseInt(vars[0])-1);
            }
        } else {
            joueurChoisi = controleur.getJoueursDispos().get(0);
        }
        if (vars[1].equals("v")) {
            controleur.callbackTraitementPioche(controleur.getTourDePiocher(), joueurChoisi, joueurChoisi.getCarteVisible());
        } else {
            controleur.callbackTraitementPioche(controleur.getTourDePiocher(), joueurChoisi);
        }
    }

    /**
     * Méthode permettant d'initialiser les composants
     */
    private void initComponents() {
        labelNbTours = new JLabel();
        labelDeckImage = new JLabel();
        panelTrophes = new JPanel();
        trophes1 = new JLabel();
        trophes2 = new JLabel();
        labelCartesRestantes = new JLabel();
        labelResultat = new JLabel();
        panelProposition = new JPanel();
        labelPropositionJoueur = new JLabel();
        labelDemandeCarte = new JLabel();
        buttonPropositionCarte1 = new JButton();
        buttonPropositionCarte2 = new JButton();
        panelAffichageCartesProposees = new JPanel();
        panelAffichageJoueur1 = new JPanel();
        buttonPiocherCarteVisibleJ1 = new JButton();
        buttonPiocherCarteCacheeJ1 = new JButton();
        labelAffichageNomJoueur1 = new JLabel();
        labelCarteVisibleJoueur1 = new JLabel();
        labelCarteCacheeJoueur1 = new JLabel();
        panelAffichageJoueur2 = new JPanel();
        buttonPiocherCarteVisibleJ2 = new JButton();
        buttonPiocherCarteCacheeJ2 = new JButton();
        labelAffichageNomJoueur2 = new JLabel();
        labelCarteVisibleJoueur2 = new JLabel();
        labelCarteCacheeJoueur2 = new JLabel();
        panelAffichageJoueur3 = new JPanel();
        buttonPiocherCarteVisibleJ3 = new JButton();
        buttonPiocherCarteCacheeJ3 = new JButton();
        labelAffichageNomJoueur3 = new JLabel();
        labelCarteVisibleJoueur3 = new JLabel();
        labelCarteCacheeJoueur3 = new JLabel();
        panelAffichageJoueur4 = new JPanel();
        buttonPiocherCarteVisibleJ4 = new JButton();
        buttonPiocherCarteCacheeJ4 = new JButton();
        labelAffichageNomJoueur4 = new JLabel();
        labelCarteVisibleJoueur4 = new JLabel();
        labelCarteCacheeJoueur4 = new JLabel();

        //======== this ========
        setTitle("Jest - Partie en Cours!");
        setResizable(false);
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- labelNbTours ----
        labelNbTours.setFont(labelNbTours.getFont().deriveFont(labelNbTours.getFont().getStyle() | Font.BOLD, labelNbTours.getFont().getSize() + 10f));
        labelNbTours.setHorizontalAlignment(SwingConstants.CENTER);
        labelNbTours.setText("Tour n\u00b0 1");
        contentPane.add(labelNbTours, "cell 0 0 9 1");

        //---- labelDeckImage ----
        labelDeckImage.setIcon(new ImageIcon(getClass().getResource("/fr/utt/lo02/cccr/jest/modele/cartes/images/autre/dos.png")));
        contentPane.add(labelDeckImage, "cell 1 2");

        //======== panelTrophes ========
        {
            panelTrophes.setBorder(new TitledBorder("Troph\u00e9es"));
            panelTrophes.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[fill]",
                // rows
                "[]"));
            panelTrophes.add(trophes1, "cell 0 0");

            //---- trophes2 ----
            trophes2.setVisible(false);
            panelTrophes.add(trophes2, "cell 1 0");
        }
        contentPane.add(panelTrophes, "cell 3 2 6 1,alignx center,growx 0");

        //---- labelCartesRestantes ----
        labelCartesRestantes.setFont(new Font("Segoe UI", Font.BOLD, 13));
        labelCartesRestantes.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(labelCartesRestantes, "pad -45 0 0 0,cell 1 3 6 1");

        //---- labelResultat ----
        labelResultat.setVisible(false);
        contentPane.add(labelResultat, "cell 0 5 11 1");

        //======== panelProposition ========
        {
            panelProposition.setBorder(new LineBorder(Color.gray, 2, true));
            panelProposition.setVisible(false);
            panelProposition.setLayout(new MigLayout(
                "hidemode 3,alignx center",
                // columns
                "[fill]" +
                "[fill]",
                // rows
                "[]" +
                "[]" +
                "[]"));

            //---- labelPropositionJoueur ----
            labelPropositionJoueur.setVisible(false);
            panelProposition.add(labelPropositionJoueur, "cell 0 0 2 1,alignx center,growx 0");

            //---- labelDemandeCarte ----
            labelDemandeCarte.setText("Quelle carte doit \u00eatre visible ?");
            labelDemandeCarte.setVisible(false);
            panelProposition.add(labelDemandeCarte, "cell 0 1 2 1,alignx center,growx 0");

            //---- buttonPropositionCarte1 ----
            buttonPropositionCarte1.setOpaque(false);
            buttonPropositionCarte1.setContentAreaFilled(false);
            buttonPropositionCarte1.setBorderPainted(false);
            buttonPropositionCarte1.setVisible(false);
            buttonPropositionCarte1.addActionListener(e -> buttonPropositionCarte1ActionPerformed(e));
            panelProposition.add(buttonPropositionCarte1, "cell 0 2");

            //---- buttonPropositionCarte2 ----
            buttonPropositionCarte2.setOpaque(false);
            buttonPropositionCarte2.setContentAreaFilled(false);
            buttonPropositionCarte2.setBorderPainted(false);
            buttonPropositionCarte2.setVisible(false);
            buttonPropositionCarte2.addActionListener(e -> buttonPropositionCarte2ActionPerformed(e));
            panelProposition.add(buttonPropositionCarte2, "cell 1 2");
        }
        contentPane.add(panelProposition, "cell 1 4 8 1");

        //======== panelAffichageCartesProposees ========
        {
            panelAffichageCartesProposees.setBorder(new LineBorder(Color.gray, 2, true));
            panelAffichageCartesProposees.setVisible(false);
            panelAffichageCartesProposees.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]",
                // rows
                "[0]" +
                "[0]"));

            //======== panelAffichageJoueur1 ========
            {
                panelAffichageJoueur1.setVisible(false);
                panelAffichageJoueur1.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[fill]" +
                    "[fill]",
                    // rows
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]"));

                //---- buttonPiocherCarteVisibleJ1 ----
                buttonPiocherCarteVisibleJ1.setText("Prendre");
                buttonPiocherCarteVisibleJ1.setEnabled(false);
                buttonPiocherCarteVisibleJ1.setVisible(false);
                buttonPiocherCarteVisibleJ1.addActionListener(e -> buttonPiocherCarteActionPerformed(e));
                panelAffichageJoueur1.add(buttonPiocherCarteVisibleJ1, "cell 0 3");

                //---- buttonPiocherCarteCacheeJ1 ----
                buttonPiocherCarteCacheeJ1.setText("Prendre");
                buttonPiocherCarteCacheeJ1.setEnabled(false);
                buttonPiocherCarteCacheeJ1.setVisible(false);
                buttonPiocherCarteCacheeJ1.addActionListener(e -> buttonPiocherCarteActionPerformed(e));
                panelAffichageJoueur1.add(buttonPiocherCarteCacheeJ1, "cell 1 3");

                //---- labelAffichageNomJoueur1 ----
                labelAffichageNomJoueur1.setVisible(false);
                panelAffichageJoueur1.add(labelAffichageNomJoueur1, "cell 0 0 2 1,alignx center,growx 0");

                //---- labelCarteVisibleJoueur1 ----
                labelCarteVisibleJoueur1.setVisible(false);
                panelAffichageJoueur1.add(labelCarteVisibleJoueur1, "cell 0 1");

                //---- labelCarteCacheeJoueur1 ----
                labelCarteCacheeJoueur1.setVisible(false);
                panelAffichageJoueur1.add(labelCarteCacheeJoueur1, "cell 1 1");
            }
            panelAffichageCartesProposees.add(panelAffichageJoueur1, "cell 0 1");

            //======== panelAffichageJoueur2 ========
            {
                panelAffichageJoueur2.setVisible(false);
                panelAffichageJoueur2.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[fill]" +
                    "[fill]",
                    // rows
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]"));

                //---- buttonPiocherCarteVisibleJ2 ----
                buttonPiocherCarteVisibleJ2.setText("Prendre");
                buttonPiocherCarteVisibleJ2.setEnabled(false);
                buttonPiocherCarteVisibleJ2.setVisible(false);
                buttonPiocherCarteVisibleJ2.addActionListener(e -> buttonPiocherCarteActionPerformed(e));
                panelAffichageJoueur2.add(buttonPiocherCarteVisibleJ2, "cell 0 3");

                //---- buttonPiocherCarteCacheeJ2 ----
                buttonPiocherCarteCacheeJ2.setText("Prendre");
                buttonPiocherCarteCacheeJ2.setEnabled(false);
                buttonPiocherCarteCacheeJ2.setVisible(false);
                buttonPiocherCarteCacheeJ2.addActionListener(e -> buttonPiocherCarteActionPerformed(e));
                panelAffichageJoueur2.add(buttonPiocherCarteCacheeJ2, "cell 1 3");

                //---- labelAffichageNomJoueur2 ----
                labelAffichageNomJoueur2.setVisible(false);
                panelAffichageJoueur2.add(labelAffichageNomJoueur2, "cell 0 0 2 1,alignx center,growx 0");

                //---- labelCarteVisibleJoueur2 ----
                labelCarteVisibleJoueur2.setVisible(false);
                panelAffichageJoueur2.add(labelCarteVisibleJoueur2, "cell 0 1");

                //---- labelCarteCacheeJoueur2 ----
                labelCarteCacheeJoueur2.setVisible(false);
                panelAffichageJoueur2.add(labelCarteCacheeJoueur2, "cell 1 1");
            }
            panelAffichageCartesProposees.add(panelAffichageJoueur2, "cell 1 1");

            //======== panelAffichageJoueur3 ========
            {
                panelAffichageJoueur3.setVisible(false);
                panelAffichageJoueur3.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[fill]" +
                    "[fill]",
                    // rows
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]"));

                //---- buttonPiocherCarteVisibleJ3 ----
                buttonPiocherCarteVisibleJ3.setText("Prendre");
                buttonPiocherCarteVisibleJ3.setEnabled(false);
                buttonPiocherCarteVisibleJ3.setVisible(false);
                buttonPiocherCarteVisibleJ3.addActionListener(e -> buttonPiocherCarteActionPerformed(e));
                panelAffichageJoueur3.add(buttonPiocherCarteVisibleJ3, "cell 0 3");

                //---- buttonPiocherCarteCacheeJ3 ----
                buttonPiocherCarteCacheeJ3.setText("Prendre");
                buttonPiocherCarteCacheeJ3.setEnabled(false);
                buttonPiocherCarteCacheeJ3.setVisible(false);
                buttonPiocherCarteCacheeJ3.addActionListener(e -> buttonPiocherCarteActionPerformed(e));
                panelAffichageJoueur3.add(buttonPiocherCarteCacheeJ3, "cell 1 3");

                //---- labelAffichageNomJoueur3 ----
                labelAffichageNomJoueur3.setVisible(false);
                panelAffichageJoueur3.add(labelAffichageNomJoueur3, "cell 0 0 2 1,alignx center,growx 0");

                //---- labelCarteVisibleJoueur3 ----
                labelCarteVisibleJoueur3.setVisible(false);
                panelAffichageJoueur3.add(labelCarteVisibleJoueur3, "cell 0 1");

                //---- labelCarteCacheeJoueur3 ----
                labelCarteCacheeJoueur3.setVisible(false);
                panelAffichageJoueur3.add(labelCarteCacheeJoueur3, "cell 1 1");
            }
            panelAffichageCartesProposees.add(panelAffichageJoueur3, "cell 2 1");

            //======== panelAffichageJoueur4 ========
            {
                panelAffichageJoueur4.setVisible(false);
                panelAffichageJoueur4.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[fill]" +
                    "[fill]",
                    // rows
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]"));

                //---- buttonPiocherCarteVisibleJ4 ----
                buttonPiocherCarteVisibleJ4.setText("Prendre");
                buttonPiocherCarteVisibleJ4.setEnabled(false);
                buttonPiocherCarteVisibleJ4.setVisible(false);
                buttonPiocherCarteVisibleJ4.addActionListener(e -> buttonPiocherCarteActionPerformed(e));
                panelAffichageJoueur4.add(buttonPiocherCarteVisibleJ4, "cell 0 3");

                //---- buttonPiocherCarteCacheeJ4 ----
                buttonPiocherCarteCacheeJ4.setText("Prendre");
                buttonPiocherCarteCacheeJ4.setEnabled(false);
                buttonPiocherCarteCacheeJ4.setVisible(false);
                buttonPiocherCarteCacheeJ4.addActionListener(e -> buttonPiocherCarteActionPerformed(e));
                panelAffichageJoueur4.add(buttonPiocherCarteCacheeJ4, "cell 1 3");

                //---- labelAffichageNomJoueur4 ----
                labelAffichageNomJoueur4.setVisible(false);
                panelAffichageJoueur4.add(labelAffichageNomJoueur4, "cell 0 0 2 1,alignx center,growx 0");

                //---- labelCarteVisibleJoueur4 ----
                labelCarteVisibleJoueur4.setVisible(false);
                panelAffichageJoueur4.add(labelCarteVisibleJoueur4, "cell 0 1");

                //---- labelCarteCacheeJoueur4 ----
                labelCarteCacheeJoueur4.setVisible(false);
                panelAffichageJoueur4.add(labelCarteCacheeJoueur4, "cell 1 1");
            }
            panelAffichageCartesProposees.add(panelAffichageJoueur4, "cell 3 1");
        }
        contentPane.add(panelAffichageCartesProposees, "cell 1 5 8 1,alignx center,growx 0");
        pack();
        setLocationRelativeTo(getOwner());
    }

    private JLabel labelNbTours;
    private JLabel labelDeckImage;
    private JPanel panelTrophes;
    private JLabel trophes1;
    private JLabel trophes2;
    private JLabel labelCartesRestantes;
    private JLabel labelResultat;
    private JPanel panelProposition;
    private JLabel labelPropositionJoueur;
    private JLabel labelDemandeCarte;
    private JButton buttonPropositionCarte1;
    private JButton buttonPropositionCarte2;
    private JPanel panelAffichageCartesProposees;
    private JPanel panelAffichageJoueur1;
    private JButton buttonPiocherCarteVisibleJ1;
    private JButton buttonPiocherCarteCacheeJ1;
    private JLabel labelAffichageNomJoueur1;
    private JLabel labelCarteVisibleJoueur1;
    private JLabel labelCarteCacheeJoueur1;
    private JPanel panelAffichageJoueur2;
    private JButton buttonPiocherCarteVisibleJ2;
    private JButton buttonPiocherCarteCacheeJ2;
    private JLabel labelAffichageNomJoueur2;
    private JLabel labelCarteVisibleJoueur2;
    private JLabel labelCarteCacheeJoueur2;
    private JPanel panelAffichageJoueur3;
    private JButton buttonPiocherCarteVisibleJ3;
    private JButton buttonPiocherCarteCacheeJ3;
    private JLabel labelAffichageNomJoueur3;
    private JLabel labelCarteVisibleJoueur3;
    private JLabel labelCarteCacheeJoueur3;
    private JPanel panelAffichageJoueur4;
    private JButton buttonPiocherCarteVisibleJ4;
    private JButton buttonPiocherCarteCacheeJ4;
    private JLabel labelAffichageNomJoueur4;
    private JLabel labelCarteVisibleJoueur4;
    private JLabel labelCarteCacheeJoueur4;
    private PartieControleur controleur;
    private Thread t;
    private JPanel[] panelsAffichageCartes;
    private JLabel[] labelsNomsJoueursAffichageCartes;
    private JLabel[] labelsCVAffichageCartes;
    private JLabel[] labelsCCAffichageCartes;
    private JButton[] buttonsCartesVisibles;
    private JButton[] buttonsCartesCachees;

    /**
     * Démarre le thread créé dans le constructeur
     */
    @Override
    public void run() {
        initComponents();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Méthode permettant de mettre à jour la GUI après les appels des méthodes setChanged() et notifyObservers() dans le contrôleur de la création de partie
     */
    @Override
    public void update(Observable observable, Object o) {
        if (controleur.getDeck().size() >= controleur.getJoueursList().size())
        this.trophes1.setIcon(new ImageIcon(getClass().getResource(controleur.getTrophes().get(0).getCheminImage())));
        this.trophes1.setVisible(true);
        if (controleur.getTrophes().size() > 1){
            this.trophes2.setIcon(new ImageIcon(getClass().getResource(controleur.getTrophes().get(1).getCheminImage())));
            this.trophes2.setVisible(true);
        }
        this.panelsAffichageCartes = new JPanel[]{panelAffichageJoueur1, panelAffichageJoueur2, panelAffichageJoueur3, panelAffichageJoueur4};
        this.labelsNomsJoueursAffichageCartes = new JLabel[]{labelAffichageNomJoueur1, labelAffichageNomJoueur2, labelAffichageNomJoueur3, labelAffichageNomJoueur4};
        this.labelsCVAffichageCartes = new JLabel[]{labelCarteVisibleJoueur1, labelCarteVisibleJoueur2, labelCarteVisibleJoueur3, labelCarteVisibleJoueur4};
        this.labelsCCAffichageCartes = new JLabel[]{labelCarteCacheeJoueur1, labelCarteCacheeJoueur2, labelCarteCacheeJoueur3, labelCarteCacheeJoueur4};
        this.buttonsCartesVisibles = new JButton[]{buttonPiocherCarteVisibleJ1, buttonPiocherCarteVisibleJ2, buttonPiocherCarteVisibleJ3, buttonPiocherCarteVisibleJ4};
        this.buttonsCartesCachees = new JButton[]{buttonPiocherCarteCacheeJ1, buttonPiocherCarteCacheeJ2, buttonPiocherCarteCacheeJ3, buttonPiocherCarteCacheeJ4};

        this.labelCartesRestantes.setText(controleur.getDeck().size() + " cartes restantes");
        this.labelNbTours.setText("Tour n° " + controleur.getNbTours());
        this.labelCartesRestantes.setText(controleur.getDeck().size() + " cartes restantes");
        if (controleur.getTourDeProposer() != null){
            afficherProposition(controleur.getTourDeProposer());
        } else {
            cacherProposition();
        }
        if (controleur.isProposition()){
            afficherCartesProposees();
        } else {
            cacherCartesProposees();
        }
        if (controleur.getTourDePiocher() != null){
            demanderPioche();
        }
        if (controleur.getPartieTerminee()){
            StringBuffer sb = new StringBuffer();
            sb.append("<html>Classement : ");
            for (int i = 0; i < controleur.getClassementJoueurs().length; i++) {
                sb.append("<br />" + (i+1) + ". " + controleur.getClassementJoueurs()[i] + " : (" + controleur.getClassementJoueurs()[i].getJest() + ") [" + controleur.getClassementJoueurs()[i].getScore() + "]");
            }
            sb.append("<br />Gagnant : " + controleur.getClassementJoueurs()[0] + "</html>");
            this.labelResultat.setText(sb.toString());
            this.labelResultat.setVisible(true);
            this.revalidate();
            this.pack();
        }
    }

    /**
     * Méthode appelée dans le update() permettant d'afficher les cartes proposées par chaque joueur
     */
    private void afficherCartesProposees(){
        this.panelAffichageCartesProposees.setVisible(true);
        for (int i = 0; i < this.controleur.getJoueursList().size(); i++) {
            this.panelsAffichageCartes[i].setVisible(true);
            this.labelsNomsJoueursAffichageCartes[i].setText(this.controleur.getJoueursList().get(i).toString());
            this.labelsNomsJoueursAffichageCartes[i].setVisible(true);
            this.buttonsCartesVisibles[i].setVisible(true);
            this.buttonsCartesCachees[i].setVisible(true);
            if (this.controleur.getJoueursList().get(i).getCarteVisible() != null){
                this.labelsCVAffichageCartes[i].setIcon(new ImageIcon(getClass().getResource(this.controleur.getJoueursList().get(i).getCarteVisible().getCheminImage())));
                this.labelsCVAffichageCartes[i].setVisible(true);
            } else {
                this.labelsCVAffichageCartes[i].setVisible(false);
                this.buttonsCartesVisibles[i].setVisible(false);
                this.buttonsCartesCachees[i].setVisible(false);
            }
            if (this.controleur.getJoueursList().get(i).getCarteCachee() != null){
                this.labelsCCAffichageCartes[i].setIcon(new ImageIcon(getClass().getResource(this.controleur.getJoueursList().get(i).getCarteCachee().getCheminDosCarte())));
                this.labelsCCAffichageCartes[i].setVisible(true);
            } else {
                this.labelsCCAffichageCartes[i].setVisible(false);
                this.buttonsCartesCachees[i].setVisible(false);
                this.buttonsCartesVisibles[i].setVisible(false);
            }
            if (this.controleur.getJoueursList().get(i).equals(this.controleur.getTourDePiocher()) && this.controleur.getJoueursDispos().size() != 1) {
                this.buttonsCartesCachees[i].setEnabled(false);
                this.buttonsCartesVisibles[i].setEnabled(false);
            }
        }
        this.revalidate();
        this.pack();
    }

    /**
     * Méthode appelée dans le update() permettant de cacher les cartes proposées par les joueurs
     */
    private void cacherCartesProposees(){
        this.panelAffichageCartesProposees.setVisible(false);
        for (int i = 0; i < this.controleur.getJoueursList().size(); i++) {
            this.panelsAffichageCartes[i].setVisible(false);
            this.labelsNomsJoueursAffichageCartes[i].setText(this.controleur.getJoueursList().get(i).toString());
            this.labelsNomsJoueursAffichageCartes[i].setVisible(false);
            this.labelsCVAffichageCartes[i].setVisible(false);
            this.labelsCCAffichageCartes[i].setVisible(false);
        }
        this.revalidate();
        this.pack();
    }

    /**
     * Méthode permettant d'afficher la demande de proposition de cartes à un joueur
     * @param joueur le joueur qui doit proposer
     */
    private void afficherProposition(Joueur joueur){
        this.panelProposition.setVisible(true);
        this.labelPropositionJoueur.setText("Tour de " + joueur);
        this.labelPropositionJoueur.setVisible(true);
        this.labelDemandeCarte.setVisible(true);
        this.buttonPropositionCarte1.setIcon(new ImageIcon(getClass().getResource(joueur.getMain().get(0).getCheminImage())));
        this.buttonPropositionCarte1.setVisible(true);
        this.buttonPropositionCarte2.setIcon(new ImageIcon(getClass().getResource(joueur.getMain().get(1).getCheminImage())));
        this.buttonPropositionCarte2.setVisible(true);
        this.revalidate();
        this.pack();
    }

    /**
     * Méthode permettant de cacher la demande de proposition de cartes à un joueur
     */
    private void cacherProposition(){
        this.panelProposition.setVisible(false);
        this.labelPropositionJoueur.setVisible(false);
        this.labelDemandeCarte.setVisible(false);
        this.buttonPropositionCarte1.setVisible(false);
        this.buttonPropositionCarte2.setVisible(false);
        this.revalidate();
        this.pack();
    }

    /**
     * Méthode permettant de demander à un joueur humain de piocher
     */
    private void demanderPioche(){
        for (int i = 0; i < controleur.getJoueursList().size(); i++) {
            if (controleur.getJoueursDispos().contains(controleur.getJoueursList().get(i))){
                this.buttonsCartesVisibles[i].setActionCommand(i + " v");
                this.buttonsCartesCachees[i].setActionCommand(i + " c");
                this.buttonsCartesVisibles[i].setEnabled(true);
                this.buttonsCartesCachees[i].setEnabled(true);
            }
        }
        this.revalidate();
        this.pack();
    }
}