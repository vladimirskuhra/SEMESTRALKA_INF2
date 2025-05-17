package a.Miestnosti;

import a.Manageri.BattleSystem;
import a.Postavy.Hrac;

import java.util.Scanner;

/**
 * VstupnaMiestnost je miestnost, kde hrac zacina alebo moze opustit dungeon.
 * Dedi zo spolocnej triedy Miestnost a je bezpecna, nehrozi tu boj ani pasca.
 */
public class VstupnaMiestnost extends Miestnost implements Bezpecna {

    /**
     * Konstruktor vytvori objekt vstupnej miestnosti s danym id, menom a popisom.
     * @param id identifikator miestnosti
     * @param meno nazov miestnosti
     * @param popis textovy popis miestnosti
     */
    public VstupnaMiestnost(String id, String meno, String popis) {
        super(id, meno, popis);
    }

    /**
     * Metoda interakcia umoznuje hracovi rozhodnut sa, ci chce opustit dungeon.
     * Ak hrac zvoli "a", vypise spravu o ukonceni dobrodruzstva.
     * Ak zvoli inu odpoved, pokracuje v hre.
     * @param hrac objekt hraca v miestnosti
     * @param battleSystem system subojov (tu sa nepouziva)
     */
    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        System.out.println("Si pri vstupe do dungonu. Chces opustit dungeon? (a/n)");
        Scanner sc = new Scanner(System.in);
        String odpoved = sc.nextLine().toLowerCase();
        if (odpoved.equals("a")) {
            System.out.println("Rozhodol si sa opustit dungeon. Dobrodruzstvo konci.");
        } else {
            System.out.println("Pokracujes v dobrodruzstve.");
        }
    }

    /**
     * Metoda prehladat vypise informaciu o miestnosti.
     * V tejto miestnosti nie su ziadne predmety ani specialne udalosti.
     * @param hrac objekt hraca v miestnosti
     */
    @Override
    public void prehladat(Hrac hrac) {
        System.out.println("Prechadzas vstupnu miestnost. Okrem vychodov tu nie je nic zvlastne.");
    }
}