import javax.swing.text.Position;

public class Hrac extends Charakter {
    public Hrac(String id, String meno, String popis, Position pozicia, int zdravie, int sila, int obrana, TypCharakteru typ) {
        super(id, meno, popis, pozicia, zdravie, sila, obrana, TypCharakteru.HRAC);
    }

    @Override
    public void interakcia(Hrac hrac) {
        // Hráč neinteraguje sám so sebou
    }

    @Override
    public void pouzitie(Hrac hrac) {

    }

    @Override
    public int getZdravie() {
        return super.getZdravie();
    }

    @Override
    public void setZdravie(int zdravie) {
        super.setZdravie(zdravie);
    }

    @Override
    public int getSila() {
        return super.getSila();
    }

    @Override
    public int getObrana() {
        return super.getObrana();
    }

    @Override
    public Inventar getInventar() {
        return super.getInventar();
    }

    @Override
    public TypCharakteru getTyp() {
        return super.getTyp();
    }

    @Override
    public void utok(Utocnik ciel) {
        super.utok(ciel);
    }

    @Override
    public void obrana() {
        super.obrana();
    }

    @Override
    public void pohyb(Position novaPozicia) {
        super.pohyb(novaPozicia);
    }
}