package fr.utt.lo02.cccr.jest.modele.cartes;

/**
 * Enumération sur les couleurs de cartes
 */
public enum CarteCouleur {
    PIQUE("\u2660", 4),
    TREFLE("\u2663", 3),
    CARREAU("\u2666", 2),
    COEUR("\u2665", 1),
    AUTRE("Autre",0);

    private final String nomLisible;
    private final int valeurCouleur;

    /**
     * Constructeur
     * @param nomLisible le nom lisible de la couleur
     * @param valeurCouleur la valeur de la couleur (Pique > Trefle > Carreau > Coeur > Autre)
     */
    CarteCouleur(String nomLisible, int valeurCouleur) {
        this.nomLisible = nomLisible;
        this.valeurCouleur = valeurCouleur;
    }

    public String getNomLisible() {
        return this.nomLisible;
    }

    public int getValeurCouleur() {
        return this.valeurCouleur;
    }
}
