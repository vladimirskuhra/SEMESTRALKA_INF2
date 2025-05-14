import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.List;

public class Hrac extends Charakter {
    private int level;
    private List<Quest> aktivneQuesty = new ArrayList<>();

    public Hrac(String id, String meno, String popis, Position pozicia, int zdravie, int sila, int obrana) {
        super(id, meno, popis, pozicia, zdravie, sila, obrana);
        this.level = 1;
    }

    public void zobrazQuesty() {
        if (aktivneQuesty.isEmpty()) {
            System.out.println("Žiadne aktívne questy.");
            return;
        }
        for (Quest q : aktivneQuesty) {
            System.out.println(q);
        }
    }

    public void prijmiQuest(Quest q) {
        if (aktivneQuesty.contains(q)) {
            System.out.println("Tento quest už máš!");
        } else {
            aktivneQuesty.add(q);
            System.out.println("Prijal si quest: " + q.getNazov());
        }
    }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public List<Quest> getAktivneQuesty() { return aktivneQuesty; }

    @Override
    public void interakcia(Hrac hrac) {
        System.out.println("Nemôžeš interagovať sám so sebou.");
    }

    @Override
    public void pouzitie(Hrac hrac) { }

    @Override
    public void utok(Utocnik ciel) {
        if (ciel instanceof Charakter) {
            Charakter cielovaPostava = (Charakter) ciel;
            System.out.println("Útočíš na " + cielovaPostava.getMeno() + "!");
            Main.bojovySystem(this, cielovaPostava);
        }
    }

    @Override
    public void obrana() {
        System.out.println("Pripravuješ sa na obranu. Tvoja obrana sa dočasne zvýšila.");
    }
}