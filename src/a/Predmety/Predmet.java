package a.Predmety;

import a.Postavy.Hrac;

/**
 * Abstraktna trieda Predmet reprezentuje zaklad kazdeho predmetu v hre.
 * Obsahuje zakladne atributy a metody, ktore maju vsetky predmety.
 */
public abstract class Predmet {
    // Unikatny identifikator predmetu
    protected String id;
    // Nazov predmetu
    protected String meno;
    // Popis predmetu
    protected String popis;
    // Hodnota predmetu (napr. cena v obchode)
    protected int hodnota;

    /**
     * Konstruktor nastavi zakladne atributy predmetu.
     * @param id unikatny identifikator
     * @param meno nazov predmetu
     * @param popis popis predmetu
     * @param hodnota hodnota predmetu
     */
    public Predmet(String id, String meno, String popis, int hodnota) {
        this.id = id;
        this.meno = meno;
        this.popis = popis;
        this.hodnota = hodnota;
    }

    /**
     * Getter na id predmetu.
     * @return id predmetu
     */
    public String getId() {
        return this.id;
    }

    /**
     * Getter na meno predmetu.
     * @return meno predmetu
     */
    public String getMeno() {
        return this.meno;
    }

    /**
     * Getter na popis predmetu.
     * @return popis predmetu
     */
    public String getPopis() {
        return this.popis;
    }

    /**
     * Getter na hodnotu predmetu.
     * @return hodnota predmetu
     */
    public int getHodnota() {
        return this.hodnota;
    }

    /**
     * Abstraktna metoda, ktoru musi implementovat kazdy potomok.
     * Urcuje co sa stane, ked hrac pouzije predmet.
     * @param hrac hrac, ktory pouziva predmet
     */
    public abstract void pouzitie(Hrac hrac);
}