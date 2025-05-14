public class Brnenie extends Predmet {
    private int obrana;

    public Brnenie(String id, String meno, String popis, int hodnota, int obrana) {
        super(id, meno, popis, hodnota);
        this.obrana = obrana;
    }

    public int getObrana() { return obrana; }

    @Override
    public void pouzitie(Hrac hrac) {
        // Môžeš implementovať efekt brnenia pri použití
        System.out.println("Obliekaš si brnenie: " + meno);
    }
}