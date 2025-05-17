package a;

public abstract class NPC extends Charakter {
    public NPC(String id, String meno, String popis, Miestnost miestnost, int zdravie, int sila, int obrana) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana);
    }

    public abstract void interakcia(Hrac hrac, BattleSystem battleSystem);
}