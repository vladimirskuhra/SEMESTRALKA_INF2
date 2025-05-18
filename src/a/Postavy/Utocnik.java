package a.Postavy;

/**
 * Rozhranie Utocnik definuje metody pre postavy, ktore vedia utocit a branit sa v suboji.
 * Triedy implementujuce toto rozhranie musia definovat logiku utoku, obrany a prijatia zasahu.
 */
public interface Utocnik {

    /**
     * Metoda utok vykona utok na ciel, ktory je tiez utocnik.
     * @param ciel ciel utoku
     */
    void utok(Utocnik ciel);

    /**
     * Metoda obrana zabezpeci obranu utocnika pred dalsim utokom.
     */
    void obrana();

    /**
     * Metoda prijmiZasah spracuje zasah, ked je utocnik napadnuty.
     * @param silaUtoku sila, ktorou bol utocnik zasiahnuty
     */
    void prijmiZasah(int silaUtoku);
}