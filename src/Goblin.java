import javax.swing.text.Position;

public class Goblin extends Charakter {
    public Goblin(String id, String meno, String popis, Position pozicia, int zdravie, int sila, int obrana) {
        super(id, meno, popis, pozicia, zdravie, sila, obrana, TypCharakteru.GOBLIN);
    }

    @Override
    public void interakcia(Hrac hrac) {
        // Boj s hráčom
    }

    @Override
    public void pouzitie(Hrac hrac) {

    }
}