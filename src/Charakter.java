public abstract class Charakter implements Utocnik {
    protected String id;
    protected String meno;
    protected String popis;
    protected Miestnost miestnost;
    protected int zdravie;
    protected int sila;
    protected int obrana;
    protected Inventar inventar;
    protected int docasnaObrana = 0; // zvýšená obrana na 1 kolo


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
        return id;
    }
    public String getMeno() {
        return meno;
    }
    public String getPopis() {
        return popis;
    }
    public Miestnost getMiestnost() {
        return miestnost;
    }
    public void setMiestnost(Miestnost miestnost) {
        this.miestnost = miestnost;
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

    @Override
    public void pohyb(Miestnost novaMiestnost) {
        this.miestnost = novaMiestnost;
    }

    @Override
    public abstract void utok(Utocnik ciel);

    @Override
    public abstract void obrana();

    @Override
    public abstract void prijmiZasah(int silaUtoku);
}