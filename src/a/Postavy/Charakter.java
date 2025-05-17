package a.Postavy;

import a.Miestnosti.Miestnost;
import a.Predmety.Inventar;

public abstract class Charakter {
    protected String id;
    protected String meno;
    protected String popis;
    protected Miestnost miestnost;
    protected int zdravie;
    protected int sila;
    protected int obrana;
    protected Inventar inventar;
    protected int docasnaObrana = 0;


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

    public String getId() {
        return this.id;
    }

    public String getMeno() {
        return this.meno;
    }

    public String getPopis() {
        return this.popis;
    }

    public Miestnost getMiestnost() {
        return this.miestnost;
    }

    public void setMiestnost(Miestnost miestnost) {
        this.miestnost = miestnost;
    }

    public int getZdravie() {
        return this.zdravie;
    }

    public void setZdravie(int zdravie) {
        this.zdravie = zdravie;
    }

    public int getSila() {
        return this.sila;
    }

    public int getObrana() {
        return this.obrana;
    }

    public Inventar getInventar() {
        return this.inventar;
    }
}