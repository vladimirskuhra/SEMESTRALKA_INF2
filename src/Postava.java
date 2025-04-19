import javax.swing.text.Position;

public abstract class Postava {
    protected String id;
    protected String meno;
    protected String popis;
    protected Position pozicia;

    public Postava(String id, String meno, String popis, Position pozicia) {
        this.id = id;
        this.meno = meno;
        this.popis = popis;
        this.pozicia = pozicia;
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

    public abstract void interakcia(Hrac hrac);
}