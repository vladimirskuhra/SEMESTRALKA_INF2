package a.Postavy;

import a.Miestnosti.Miestnost;
import a.Predmety.Inventar;

/**
 * Abstraktna trieda Charakter predstavuje zaklad pre vsetky postavy v hre
 * (napr. hrac, nepriatel). Obsahuje zakladne atributy a metody pre postavu.
 */
public abstract class Charakter {
    // Unikatny identifikator postavy
    protected String id;
    // Nazov alebo meno postavy
    protected String meno;
    // Popis postavy
    protected String popis;
    // Aktualna miestnost postavy
    protected Miestnost miestnost;
    // Aktualne zdravie postavy
    protected int zdravie;
    // Zivotna sila postavy (urcuje utocnu silu)
    protected int sila;
    // Obrana postavy (trvala)
    protected int obrana;
    // Inventar postavy
    protected Inventar inventar;
    // Docasna obrana (napr. po pouziti specialnej schopnosti)
    protected int docasnaObrana = 0;

    /**
     * Konstruktor inicializuje zakladne atributy postavy a vytvori jej inventar.
     * @param id unikatny identifikator postavy
     * @param meno nazov alebo meno postavy
     * @param popis popis postavy
     * @param miestnost miestnost, v ktorej sa postava nachadza na zaciatku
     * @param zdravie pociatocne zdravie postavy
     * @param sila utocna sila postavy
     * @param obrana obrana postavy
     */
    public Charakter(String id, String meno, String popis, Miestnost miestnost, int zdravie, int sila, int obrana) {
        this.id = id;
        this.meno = meno;
        this.popis = popis;
        this.miestnost = miestnost;
        this.zdravie = zdravie;
        this.sila = sila;
        this.obrana = obrana;
        this.inventar = new Inventar();
    }

    /**
     * Getter na id postavy.
     * @return id postavy
     */
    public String getId() {
        return this.id;
    }

    /**
     * Getter na meno postavy.
     * @return meno postavy
     */
    public String getMeno() {
        return this.meno;
    }

    /**
     * Getter na popis postavy.
     * @return popis postavy
     */
    public String getPopis() {
        return this.popis;
    }

    /**
     * Getter na miestnost, v ktorej sa postava nachadza.
     * @return miestnost postavy
     */
    public Miestnost getMiestnost() {
        return this.miestnost;
    }

    /**
     * Setter na miestnost postavy.
     * @param miestnost nova miestnost
     */
    public void setMiestnost(Miestnost miestnost) {
        this.miestnost = miestnost;
    }

    /**
     * Getter na aktualne zdravie postavy.
     * @return aktualne zdravie
     */
    public int getZdravie() {
        return this.zdravie;
    }

    /**
     * Setter na aktualne zdravie postavy.
     * @param zdravie nove zdravie
     */
    public void setZdravie(int zdravie) {
        this.zdravie = zdravie;
    }

    /**
     * Getter na silu postavy.
     * @return sila postavy
     */
    public int getSila() {
        return this.sila;
    }

    /**
     * Getter na obranu postavy.
     * @return obrana postavy
     */
    public int getObrana() {
        return this.obrana;
    }

    /**
     * Getter na inventar postavy.
     * @return inventar
     */
    public Inventar getInventar() {
        return this.inventar;
    }
}