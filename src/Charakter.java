import javax.swing.text.Position;

public abstract class Charakter extends Postava implements Utocnik, Interakcia {
    protected int zdravie;
    protected int sila;
    protected int obrana;
    protected Inventar inventar;
    protected TypCharakteru typ; // Pridanie typu postavy

    public Charakter(String id, String meno, String popis, Position pozicia, int zdravie, int sila, int obrana, TypCharakteru typ) {
        super(id, meno, popis, pozicia);
        this.zdravie = zdravie;
        this.sila = sila;
        this.obrana = obrana;
        this.inventar = new Inventar();
        this.typ = typ; // Inicializácia typu postavy
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

    public TypCharakteru getTyp() {
        return typ;
    }

    @Override
    public void utok(Utocnik ciel) {
        // Implementácia útoku
    }

    @Override
    public void obrana() {
        // Implementácia obrany
    }

    @Override
    public void pohyb(Position novaPozicia) {
        this.pozicia = novaPozicia;
    }

    @Override
    public void interakcia(Hrac hrac) {
        // Implementácia interakcie
    }
}