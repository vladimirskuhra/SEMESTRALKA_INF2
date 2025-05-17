package a.Miestnosti;

import a.Manageri.BattleSystem;
import a.Postavy.Hrac;
import a.Postavy.NPC.NPC;
import a.Postavy.NPC.Specialne.HadankoveDvere;

/**
 * HadankovaMiestnost je špeciálny typ miestnosti, kde sa nachádza hádanka na vyriešenie (napr. dvere s hádankou).
 *
 * Prečo je to takto navrhnuté:
 * - Dedi zo spoločnej abstraktnej triedy Miestnost, preto má všetky jej základné vlastnosti (id, meno, popis, postavy, predmety, východy, ...).
 * - Metóda interakcia rieši logiku hádanky - prejde všetky postavy v miestnosti a ak nájde inštanciu HadankoveDvere (špeciálne NPC),
 *   vyvolá na nich ich vlastnú interakciu (tá môže obsahovať logiku hádania, odomykania atď).
 * - Ak v miestnosti nie je žiadna postava typu HadankoveDvere, vypíše sa oznam.
 * - Prehľadávanie miestnosti je jednoduché - okrem hádanky tu nie sú ďalšie predmety ani špeciálne akcie.
 *
 * OOP princípy:
 * - Polymorfizmus: interakcia a prehladat sú predefinované špecificky pre tento typ miestnosti.
 * - Rozšíriteľnosť: ak by si chcel iný typ miestnosti s hádankou, môžeš jednoducho vytvoriť ďalšiu podtriedu, alebo rozšíriť túto.
 */
public class HadankovaMiestnost extends Miestnost {
    public HadankovaMiestnost(String id, String meno, String popis) {
        super(id, meno, popis);
    }

    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        // Predpokladáme, že v miestnosti je NPC HadankoveDvere
        for (NPC postava : this.postavy) {
            if (postava instanceof HadankoveDvere) {
                postava.interakcia(hrac, battleSystem);
                return;
            }
        }
        System.out.println("V miestnosti nie je žiadna hádanka.");
    }

    @Override
    public void prehladat(Hrac hrac) {
        System.out.println("V tejto miestnosti môžeš riešiť hádanku, ale nič iné tu nie je.");
    }
}