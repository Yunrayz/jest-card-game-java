package fr.utt.lo02.cccr.jest.vue.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import javax.swing.border.*;

import fr.utt.lo02.cccr.jest.controleur.MainControleur;
import net.miginfocom.swing.*;

/**
 * Classe gérant la GUI pour la configuration d'une nouvelle partie
 */
public class NouvellePartieGUIView extends JFrame implements Observer, Runnable {
    /**
     * Constructeur, créant un thread dédié à la GUI
     * @param controleur le contrôleur de création de partie {@link fr.utt.lo02.cccr.jest.controleur.MainControleur}
     */
    public NouvellePartieGUIView(MainControleur controleur) {
        this.controleur = controleur;
        this.t = new Thread(this);
        this.t.setName("GUI - Configuration");
        this.t.start();
    }

    /**
     * Méthode appelée lorsqu'une action sur le bouton permettant de valider les noms des joueurs humain est détecté, renvoyant les données au contrôleur
     */
    private void buttonValiderNomsJoueursHumainsActionPerformed(ActionEvent e) {
        for (int i = 0; i < controleur.getNbJoueursHumains(); i++) {
            if (this.textFieldsJoueurs[i].getText().equals("")){
                labelErreurNomsJoueurs.setText("Tous les joueurs doivent avoir un nom.");
                labelErreurNomsJoueurs.setVisible(true);
                this.pack();
                break;
            } else if (i == (controleur.getNbJoueursHumains()-1)){
                for (JTextField textFieldsJoueur : this.textFieldsJoueurs) {
                    if (textFieldsJoueur.isVisible() && textFieldsJoueur.isEnabled()) {
                        if (controleur.getNomsJoueurs().contains(textFieldsJoueur.getText())){
                            labelErreurNomsJoueurs.setText("Plusieurs joueurs ne peuvent pas avoir le même nom.");
                            labelErreurNomsJoueurs.setVisible(true);
                            break;
                        }
                        this.labelErreurNomsJoueurs.setVisible(false);
                        controleur.ajouterNomJoueur(textFieldsJoueur.getText());
                    }
                }
            }
        }
    }

    /**
     * Méthode appelée lorsqu'une action sur le comboBox du nombre de joueurs humains est détecté, renvoyant les données au contrôleur
     */
    private void comboBoxNbJoueursHumainsActionPerformed(ActionEvent e) {
        if (controleur.getNbJoueursHumains() == -1){
            controleur.setNbJoueursHumains(comboBoxNbJoueursHumains.getSelectedIndex());
        }
    }

    /**
     * Méthode appelée lorsqu'une action sur le comboBox du nombre de joueurs virtuels est détecté, renvoyant les données au contrôleur
     */
    private void comboBoxNbJoueursVirtuelsActionPerformed(ActionEvent e) {
        if (controleur.getNbJoueursvirtuels() == -1){
            controleur.setNbJoueursvirtuels(Integer.parseInt(comboBoxNbJoueursVirtuels.getSelectedItem().toString()));
            this.comboBoxNbJoueursVirtuels.setEnabled(false);
        }
    }

    /**
     * Méthode appelée lorsqu'une action sur le comboBox de la variante choisie est détecté, renvoyant les données au contrôleur
     */
    private void comboBoxVarianteActionPerformed(ActionEvent e) {
        if (controleur.getRegleChoisie() == null){
            controleur.setRegleChoisie(this.comboBoxVariante.getSelectedIndex());
            this.comboBoxVariante.setEnabled(false);
        }
    }

    /**
     * Méthode appelée lorsqu'une action sur le comboBox des extensions est détecté, renvoyant les données au contrôleur
     */
    private void comboBoxExtensionActionPerformed(ActionEvent e) {
        controleur.setExtensionChoisie(this.comboBoxExtension.getSelectedIndex()-1);
    }

    /**
     * Initialisation des composants de la GUI
     */
    private void initComponents() {
        labelLogo = new JLabel();
        labelNouvellePartie = new JLabel();
        labelNbJoueursHumains = new JLabel();
        comboBoxNbJoueursHumains = new JComboBox<>();
        labelErreurNomsJoueurs = new JLabel();
        buttonValiderNomsJoueursHumains = new JButton();
        labelNomJ1 = new JLabel();
        textFieldNomJ1 = new JTextField();
        labelNomJ2 = new JLabel();
        textFieldNomJ2 = new JTextField();
        labelNomJ3 = new JLabel();
        textFieldNomJ3 = new JTextField();
        labelNomJ4 = new JLabel();
        textFieldNomJ4 = new JTextField();
        labelNbJoueursVirtuels = new JLabel();
        comboBoxNbJoueursVirtuels = new JComboBox<>();
        labelVariante = new JLabel();
        comboBoxVariante = new JComboBox();
        labelExtension = new JLabel();
        comboBoxExtension = new JComboBox();

        //======== this ========
        setResizable(false);
        setTitle("Jest - Nouvelle Partie");
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
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- labelLogo ----
        labelLogo.setIcon(new ImageIcon(getClass().getResource("/fr/utt/lo02/cccr/jest/vue/gui/images/jest.png")));
        contentPane.add(labelLogo, "cell 5 0");

        //---- labelNouvellePartie ----
        labelNouvellePartie.setText("=== Configurer une nouvelle partie ===");
        labelNouvellePartie.setHorizontalAlignment(SwingConstants.CENTER);
        labelNouvellePartie.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 16));
        contentPane.add(labelNouvellePartie, "cell 5 2");

        //---- labelNbJoueursHumains ----
        labelNbJoueursHumains.setText("Nombre de joueurs humains");
        contentPane.add(labelNbJoueursHumains, "cell 5 4");

        //---- comboBoxNbJoueursHumains ----
        comboBoxNbJoueursHumains.setModel(new DefaultComboBoxModel<>(new String[] {
            "0",
            "1",
            "2",
            "3",
            "4"
        }));
        comboBoxNbJoueursHumains.addActionListener(e -> comboBoxNbJoueursHumainsActionPerformed(e));
        contentPane.add(comboBoxNbJoueursHumains, "cell 5 4,width 200:200:200");

        //---- labelErreurNomsJoueurs ----
        labelErreurNomsJoueurs.setHorizontalAlignment(SwingConstants.CENTER);
        labelErreurNomsJoueurs.setBackground(new Color(255, 102, 102));
        labelErreurNomsJoueurs.setBorder(new LineBorder(new Color(255, 51, 51), 2, true));
        labelErreurNomsJoueurs.setOpaque(true);
        labelErreurNomsJoueurs.setForeground(new Color(51, 51, 51));
        labelErreurNomsJoueurs.setFont(labelErreurNomsJoueurs.getFont().deriveFont(labelErreurNomsJoueurs.getFont().getStyle() | Font.BOLD));
        labelErreurNomsJoueurs.setVisible(false);
        contentPane.add(labelErreurNomsJoueurs, "cell 5 9");

        //---- buttonValiderNomsJoueursHumains ----
        buttonValiderNomsJoueursHumains.setIcon(new ImageIcon(getClass().getResource("/fr/utt/lo02/cccr/jest/vue/gui/images/check_mark.png")));
        buttonValiderNomsJoueursHumains.setBorderPainted(false);
        buttonValiderNomsJoueursHumains.setContentAreaFilled(false);
        buttonValiderNomsJoueursHumains.setFocusPainted(false);
        buttonValiderNomsJoueursHumains.setBackground(new Color(51, 51, 51));
        buttonValiderNomsJoueursHumains.setOpaque(false);
        buttonValiderNomsJoueursHumains.setVisible(false);
        buttonValiderNomsJoueursHumains.addActionListener(e -> buttonValiderNomsJoueursHumainsActionPerformed(e));
        contentPane.add(buttonValiderNomsJoueursHumains, "cell 5 10,alignx center,growx 0,width 35:35:35");

        //---- labelNomJ1 ----
        labelNomJ1.setText("Nom du joueur 1");
        labelNomJ1.setVisible(false);
        contentPane.add(labelNomJ1, "cell 5 5");

        //---- textFieldNomJ1 ----
        textFieldNomJ1.setVisible(false);
        contentPane.add(textFieldNomJ1, "cell 5 5,width 200:200:200");

        //---- labelNomJ2 ----
        labelNomJ2.setText("Nom du joueur 2");
        labelNomJ2.setVisible(false);
        contentPane.add(labelNomJ2, "cell 5 6");

        //---- textFieldNomJ2 ----
        textFieldNomJ2.setVisible(false);
        contentPane.add(textFieldNomJ2, "cell 5 6,width 200:200:200");

        //---- labelNomJ3 ----
        labelNomJ3.setText("Nom du joueur 3");
        labelNomJ3.setVisible(false);
        contentPane.add(labelNomJ3, "cell 5 7");

        //---- textFieldNomJ3 ----
        textFieldNomJ3.setVisible(false);
        contentPane.add(textFieldNomJ3, "cell 5 7,width 200:200:200");

        //---- labelNomJ4 ----
        labelNomJ4.setText("Nom du joueur 4");
        labelNomJ4.setVisible(false);
        contentPane.add(labelNomJ4, "cell 5 8");

        //---- textFieldNomJ4 ----
        textFieldNomJ4.setVisible(false);
        contentPane.add(textFieldNomJ4, "cell 5 8,width 200:200:200");

        //---- labelNbJoueursVirtuels ----
        labelNbJoueursVirtuels.setText("Nombre de joueurs virtuels");
        contentPane.add(labelNbJoueursVirtuels, "cell 5 11");

        //---- comboBoxNbJoueursVirtuels ----
        comboBoxNbJoueursVirtuels.setModel(new DefaultComboBoxModel<>(new String[] {
            "0",
            "1",
            "2",
            "3",
            "4"
        }));
        comboBoxNbJoueursVirtuels.setEnabled(false);
        comboBoxNbJoueursVirtuels.addActionListener(e -> comboBoxNbJoueursVirtuelsActionPerformed(e));
        contentPane.add(comboBoxNbJoueursVirtuels, "cell 5 11,width 200:200:200");

        //---- labelVariante ----
        labelVariante.setText("Variante");
        contentPane.add(labelVariante, "cell 5 12");

        //---- comboBoxVariante ----
        comboBoxVariante.setEnabled(false);
        comboBoxVariante.addActionListener(e -> comboBoxVarianteActionPerformed(e));
        contentPane.add(comboBoxVariante, "cell 5 12,width 200:200:200");

        //---- labelExtension ----
        labelExtension.setText("Extension");
        contentPane.add(labelExtension, "cell 5 13");

        //---- comboBoxExtension ----
        comboBoxExtension.setEnabled(false);
        comboBoxExtension.addActionListener(e -> comboBoxExtensionActionPerformed(e));
        contentPane.add(comboBoxExtension, "cell 5 13,width 200:200:200");
        pack();
        setLocationRelativeTo(getOwner());
    }

    private JLabel labelLogo;
    private JLabel labelNouvellePartie;
    private JLabel labelNbJoueursHumains;
    private JComboBox<String> comboBoxNbJoueursHumains;
    private JLabel labelErreurNomsJoueurs;
    private JButton buttonValiderNomsJoueursHumains;
    private JLabel labelNomJ1;
    private JTextField textFieldNomJ1;
    private JLabel labelNomJ2;
    private JTextField textFieldNomJ2;
    private JLabel labelNomJ3;
    private JTextField textFieldNomJ3;
    private JLabel labelNomJ4;
    private JTextField textFieldNomJ4;
    private JLabel labelNbJoueursVirtuels;
    private JComboBox<String> comboBoxNbJoueursVirtuels;
    private JLabel labelVariante;
    private JComboBox comboBoxVariante;
    private JLabel labelExtension;
    private JComboBox comboBoxExtension;

    private Thread t;
    private MainControleur controleur;
    private JLabel[] labelsJoueurs;
    private JTextField[] textFieldsJoueurs;

    /**
     * Méthode permettant de mettre à jour la GUI après les appels des méthodes setChanged() et notifyObservers() dans le contrôleur de la création de partie
     */
    @Override
    public void update(Observable o, Object arg) {
        this.comboBoxNbJoueursVirtuels.setEnabled(true);
        if (controleur.isPartieDemarree()){
            this.dispose();
        }
        comboBoxNbJoueursHumains.setSelectedIndex(controleur.getNbJoueursHumains());
        comboBoxNbJoueursHumains.setEnabled(false);
        for (int i=0; i<controleur.getNbJoueursHumains(); i++) {
            this.labelsJoueurs[i].setVisible(true);
            this.textFieldsJoueurs[i].setVisible(true);
        }
        if (controleur.getNbJoueursHumains() > 0){
            buttonValiderNomsJoueursHumains.setVisible(true);
        }
        for (int i = 0; i < this.controleur.getNomsJoueurs().size(); i++){
            this.textFieldsJoueurs[i].setText(this.controleur.getNomsJoueurs().get(i));
            this.textFieldsJoueurs[i].setEnabled(false);
        }
        String[] nbJoueursMax;
        if (controleur.getNbJoueursHumains() == 4){
            nbJoueursMax = new String[]{"0"};
            this.comboBoxNbJoueursVirtuels.setEnabled(false);
        } else {
            nbJoueursMax = new String[]{String.valueOf(3 - controleur.getNbJoueursHumains()), String.valueOf(4 - controleur.getNbJoueursHumains())};
        }
        comboBoxNbJoueursVirtuels.setModel(new DefaultComboBoxModel<>(nbJoueursMax));
        comboBoxNbJoueursVirtuels.setEnabled(false);
        if (controleur.getNomsJoueurs().size() == controleur.getNbJoueursHumains()){
            buttonValiderNomsJoueursHumains.setVisible(false);
            comboBoxNbJoueursVirtuels.setEnabled(true);
        }
        if(controleur.getNbJoueursvirtuels() != -1){
            this.comboBoxVariante.setEnabled(true);
        }
        if (controleur.getRegleChoisie() != null){
            this.comboBoxNbJoueursVirtuels.setEnabled(false);
            String[] nomsExtensions = new String[controleur.getExtensions().length+1];
            nomsExtensions[0] = "Aucune";
            for (int i = 1; i < controleur.getExtensions().length+1; i++) {
                nomsExtensions[i] = String.valueOf(controleur.getExtensions()[i-1]);
            }
            this.comboBoxExtension.setModel(new DefaultComboBoxModel<>(nomsExtensions));
            this.comboBoxExtension.setEnabled(true);

        }
        this.pack();
    }

    /**
     * Démarre le thread créé dans le constructeur, défini le contenu à afficher selon différentes informations spécifiées dans {@link fr.utt.lo02.cccr.jest.Main}
     */
    @Override
    public void run() {
        initComponents();
        this.labelsJoueurs = new JLabel[]{labelNomJ1, labelNomJ2, labelNomJ3, labelNomJ4};
        this.textFieldsJoueurs = new JTextField[]{textFieldNomJ1, textFieldNomJ2, textFieldNomJ3, textFieldNomJ4};
        String[] nomVariantes = new String[controleur.getRegles().length];
        for (int i = 0; i < controleur.getRegles().length; i++) {
            nomVariantes[i] = controleur.getRegles()[i].getNom();
        }
        this.comboBoxVariante.setModel(new DefaultComboBoxModel<>(nomVariantes));
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}