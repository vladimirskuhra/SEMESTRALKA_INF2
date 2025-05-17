package a.Predmety;

import a.Postavy.Hrac;

public abstract class Predmet {
    protected String id;
    protected String meno;
    protected String popis;
    protected int hodnota;

    public Predmet(String id, String meno, String popis, int hodnota) {
        this.id = id;
        this.meno = meno;
        this.popis = popis;
        this.hodnota = hodnota;
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

    public int getHodnota() {
        return this.hodnota;
    }

    public abstract void pouzitie(Hrac hrac);
}