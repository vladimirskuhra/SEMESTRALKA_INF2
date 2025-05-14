import javax.swing.text.Position;

public abstract class Charakter implements Utocnik, Interakcia {
    protected String id;
    protected String meno;
    protected String popis;
    protected Position pozicia;
    protected int zdravie;
    protected int sila;
    protected int obrana;
    protected Inventar inventar;

    public Charakter(String id, String meno, String popis, Position pozicia, int zdravie, int sila, int obrana) {
        this.id = id;
        this.meno = meno;
        this.popis = popis;
        this.pozicia = pozicia;
        this.zdravie = zdravie;
        this.sila = sila;
        this.obrana = obrana;
        this.inventar = new Inventar();
    }

    public String getId() {
        return id;
    }

    public String getMeno() {
        return meno;
    }

    public String getPopis() {
        return popis;
    }

    public Position getPozicia() {
        return pozicia;
    }

    public int getZdravie() {
        return zdravie;
    }

    public void setZdravie(int zdravie) {
        this.zdravie = zdravie;
    }

    public int getSila() {
        return sila;
    }

    public int getObrana() {
        return obrana;
    }

    public Inventar getInventar() {
        return inventar;
    }

    public void pohyb(Position novaPozicia) {
        this.pozicia = novaPozicia;
    }

    // Abstraktné metódy na polymorfizmus
    @Override
    public abstract void interakcia(Hrac hrac);

    @Override
    public abstract void pouzitie(Hrac hrac);

    @Override
    public abstract void utok(Utocnik ciel);

    @Override
    public abstract void obrana();
}