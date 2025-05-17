package a.Miestnosti;

import a.Manageri.BattleSystem;
import a.Postavy.Hrac;
import a.Postavy.NPC.NPC;
import a.Postavy.NPC.Specialne.Obchodnik;

/**
 * Trieda ObchodnaMiestnost reprezentuje miestnost, kde sa nachadza obchodnik.
 * Je to bezpecna miestnost - nehrozi tu boj ani pasce.
 *
 * Preco je to takto navrhnute:
 * - Implementuje rozhranie Bezpecna, cize je garantovane, ze v tejto miestnosti nie je nebezpecenstvo.
 * - Interakcia prebieha iba s NPC typu Obchodnik (ak je pritomny a nazive), inak sa vypise oznam hracovi.
 * - Prehladavanie miestnosti len informuje hraca, ze tu je obchodnik s tovarom – ziadne skryte poklady ani specialne udalosti tu nie su.
 *
 * OOP principy:
 * - Polymorfizmus: metoda interakcia je specificka pre tento typ miestnosti a vola logiku obchodovania priamo na NPC obchodnikovi.
 * - Zapuzdrenie: logika nakupu/predaja je v NPC Obchodnik, miestnost sluzi len ako "kontajner" pre interakciu.
 */
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
        System.out.println("V miestnosti nie je ziadny obchodnik.");
    }

    @Override
    public void prehladat(Hrac hrac) {
        System.out.println("Vidiš obchodnika s roznym tovarom na predaj.");
    }
}