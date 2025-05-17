package a;

import java.util.Scanner;

public class NepriatelskaMiestnost extends Miestnost {
    public NepriatelskaMiestnost(String id, String meno, String popis) {
        super(id, meno, popis);
    }

    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        if (!(this instanceof Bezpecna)) {
            System.out.println("V tejto miestnosti cítiš nebezpečenstvo...");
            for (NPC postava : this.postavy) {
                if (postava.getZdravie() > 0 && postava instanceof Nepriatel) {
                    System.out.println("\n" + postava.getMeno() + " sa pripravuje na útok!");
                    postava.interakcia(hrac, battleSystem);
                    return;
                }
            }
        } else {
            System.out.println("Miestnosť je momentálne bezpečná.");
        }
    }

    @Override
    public void prehladat(Hrac hrac) {
        if (!(this instanceof Bezpecna)) {
            System.out.println("Nemôžeš v kľude prehľadať miestnosť, kým sú tu nepriatelia!");
        } else {
            System.out.println("Miestnosť je po boji. Môžeš ju bezpečne prehľadať.");
            najdiPredmet(hrac);
        }
    }

    private void najdiPredmet(Hrac hrac) {
        if (Math.random() < 0.5) {
            Predmet novyPredmet = vytvorNahodnyPredmet();
            pridajPredmet(novyPredmet);
            System.out.println("Našiel si: " + novyPredmet.getMeno());
            System.out.println("Chceš si ho zobrať? (a/n)");
            Scanner scanner = new Scanner(System.in);
            String odpoved = scanner.nextLine().toLowerCase();
            if (odpoved.equals("a")) {
                hrac.getInventar().pridajPredmet(novyPredmet);
                this.predmety.remove(novyPredmet);
                System.out.println("Zobral si: " + novyPredmet.getMeno());
            }
        } else {
            System.out.println("Nenašiel si nič zaujímavé.");
        }
    }
}