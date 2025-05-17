package a.Postavy.NPC;

import a.Manageri.BattleSystem;
import a.Miestnosti.Miestnost;
import a.Postavy.Charakter;
import a.Postavy.Hrac;

public abstract class NPC extends Charakter {
    public NPC(String id, String meno, String popis, Miestnost miestnost, int zdravie, int sila, int obrana) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana);
    }

    public abstract void interakcia(Hrac hrac, BattleSystem battleSystem);
}