import javax.swing.text.Position;

public class Hrac extends Charakter {
    public Hrac(String id, String meno, String popis, Position pozicia, int zdravie, int sila, int obrana, TypCharakteru typ) {
        super(id, meno, popis, pozicia, zdravie, sila, obrana, TypCharakteru.HRAC);
    }

    @Override
    public void interakcia(Hrac hrac) {
        // Hráč neinteraguje sám so sebou
        System.out.println("Nemôžeš interagovať sám so sebou.");
    }

    @Override
    public void pouzitie(Hrac hrac) {
        // Nie je možné používať hráča ako predmet
    }

    @Override
    public void utok(Utocnik ciel) {
        // Implementácia útoku proti nepriateľovi
        if (ciel instanceof Charakter) {
            Charakter cielovaPostava = (Charakter) ciel;
            System.out.println("Útočíš na " + cielovaPostava.getMeno() + "!");

            // Bojový systém je implementovaný v Main.bojovySystem
            Main.bojovySystem(this, cielovaPostava);
        }
    }

    @Override
    public void obrana() {
        // Implementácia obrany - zvýšenie obrany na jedno kolo
        System.out.println("Pripravuješ sa na obranu. Tvoja obrana sa dočasne zvýšila.");
        // Logika zvýšenia obrany by tu bola implementovaná
    }
}