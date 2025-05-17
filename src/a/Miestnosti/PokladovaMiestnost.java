package a.Miestnosti;

import a.Manageri.BattleSystem;
import a.Postavy.Hrac;
import a.Predmety.Predmet;

import java.util.ArrayList;
import java.util.Scanner;

public class PokladovaMiestnost extends Miestnost {
    public PokladovaMiestnost(String id, String meno, String popis) {
        super(id, meno, popis);
    }

    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        prehladat(hrac);
    }

    @Override
    public void prehladat(Hrac hrac) {
        if (!this.prehladana) {
            if (!this.predmety.isEmpty()) {
                System.out.println("Našiel si poklady:");
                for (Predmet predmet : this.predmety) {
                    System.out.println("- " + predmet.getMeno());
                }
                System.out.println("Chceš si ich zobrať? (a/n)");
                Scanner scanner = new Scanner(System.in);
                String odpo = scanner.nextLine().toLowerCase();
                if (odpo.equals("a")) {
                    for (Predmet predmet : new ArrayList<>(this.predmety)) {
                        hrac.getInventar().pridajPredmet(predmet);
                        this.predmety.remove(predmet);
                        System.out.println("Zobral si: " + predmet.getMeno());
                    }
                }
            } else {
                System.out.println("Nič si nenašiel.");
            }
            this.prehladana = true;
        } else {
            System.out.println("Túto miestnosť si už prehľadal.");
        }
    }
}