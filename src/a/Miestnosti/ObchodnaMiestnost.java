package a.Miestnosti;

import a.Manageri.BattleSystem;
import a.Postavy.Hrac;
import a.Postavy.NPC.NPC;
import a.Postavy.NPC.Specialne.Obchodnik;

public class ObchodnaMiestnost extends Miestnost implements Bezpecna {
    public ObchodnaMiestnost(String id, String meno, String popis) {
        super(id, meno, popis);
    }

    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        for (NPC postava : this.postavy) {
            if (postava instanceof Obchodnik && postava.getZdravie() > 0) {
                postava.interakcia(hrac, battleSystem);
                return;
            }
        }
        System.out.println("V miestnosti nie je žiadny obchodník.");
    }

    @Override
    public void prehladat(Hrac hrac) {
        System.out.println("Vidíš obchodníka s rôznym tovarom na predaj.");
    }
}