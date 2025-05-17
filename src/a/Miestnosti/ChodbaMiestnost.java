package a.Miestnosti;

import a.Manageri.BattleSystem;
import a.Postavy.Hrac;

/**
 * Trieda ChodbaMiestnost reprezentuje miestnost typu chodba,
 * ktora sluzi ako bezpecny prechod medzi inymi miestnostami v dungeone.
 *
 * Preco je to takto navrhnute:
 * - Dedi zo spolocnej abstraktnej triedy Miestnost a implementuje rozhranie Bezpecna,
 *   co zarucuje, ze v tejto miestnosti nehrozi hracovi boj ani pasca (moze tu napr. odpocivat).
 * - Interakcia aj prehladavanie su minimalne - chodba je len prechodovy priestor,
 *   nie je tu ziadny obsah ani specialna udalost.
 * - OOP principy: Kazda miestnost ma vlastnu funkcionalitu, ale zdieľa spolocne rozhranie/abstrakciu.
 */
public class ChodbaMiestnost extends Miestnost implements Bezpecna {
    public ChodbaMiestnost(String id, String meno, String popis) {
        super(id, meno, popis);
    }

    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        System.out.println("Chodba, z kazdej strany vedu dalsie cesty.");
    }

    @Override
    public void prehladat(Hrac hrac) {
        System.out.println("Prehladavas chodbu. Je to len prechod medzi miestnostami.");
    }
}