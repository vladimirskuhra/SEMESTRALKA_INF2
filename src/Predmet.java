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

    public String getId() { return id; }
    public String getMeno() { return meno; }
    public String getPopis() { return popis; }
    public int getHodnota() { return hodnota; }

    public abstract void pouzitie(Hrac hrac);
}