import javax.swing.text.Position;

public abstract class NPC extends Charakter {
    public NPC(String id, String meno, String popis, Position pozicia, int zdravie, int sila, int obrana) {
        super(id, meno, popis, pozicia, zdravie, sila, obrana);
    }

    /**
     * Dropne predmet po porazení/získaní.
     * Každý potomok môže override-nuť podľa potreby.
     */
    public Predmet dropniPredmet() {
        // Default: žiadny predmet, override-ne len nepriateľ alebo obchodník
        return null;
    }

    /**
     * Pohyb NPC. Obchodník override-ne na prázdnu metódu.
     */
    public void pohyb() {
        // Základná logika pohybu NPC (nepriatelia sa môžu pohybovať)
    }

    /**
     * Útok na hráča. Override-ne len nepriateľ.
     */
    public void utok(Hrac hrac) {
        // Default: nič nerobí (obchodník neútočí)
    }

    /**
     * Komunikácia s hráčom. Override-ne obchodník, quest giver, atď.
     */
    public void komunikuj(Hrac hrac) {
        // Default: nič nerobí
    }

    /**
     * Zastrašenie NPC minihrou. Override-ne len nepriatelia, ktorí môžu byť zastrašení.
     */
    public void zastras(Hrac hrac) {
        // Default: nič nerobí
    }
}