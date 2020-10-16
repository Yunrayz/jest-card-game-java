package fr.utt.lo02.cccr.jest.modele.cartes;

/**
 * Enumération sur les valeurs des cartes
 */
public enum CarteValeur {
    AS("As", 1),
    DEUX("Deux", 2),
    TROIS("Trois", 3),
    QUATRE("Quatre", 4),
    CINQ("Cinq", 5),
    SIX("Six", 6),
    SEPT("Sept", 7),
    HUIT("Huit", 8),
    NEUF("Neuf", 9),
    DIX("Dix", 10),
    VALET("Valet", 11),
    DAME("Dame", 12),
    ROI("Roi", 13),
    JOKER("Joker", 0);

    private final String nomLisible;
    private final int valeurNum;

    /**
     * Constructeur
     * @param nomLisible le nom lisible de la valeur
     * @param valeurNum la valeur numérique de la valeur
     */
    CarteValeur(String nomLisible, int valeurNum) {
        this.nomLisible = nomLisible;
        this.valeurNum = valeurNum;
    }

    public String getNomLisible() {
        return nomLisible;
    }

    public int getValeurNum(){
        return valeurNum;
    }
}
