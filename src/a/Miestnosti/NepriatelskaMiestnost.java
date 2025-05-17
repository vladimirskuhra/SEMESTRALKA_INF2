package a.Miestnosti;

import a.Manageri.BattleSystem;
import a.Postavy.Hrac;
import a.Postavy.NPC.NPC;
import a.Postavy.NPC.Nepriatelia.Nepriatel;
import a.Predmety.Predmet;

import java.util.Scanner;

/**
 * Trieda NepriatelskaMiestnost reprezentuje miestnost, v ktorej sa nachadzaju nepriatelia a hrozi tu boj.
 *
 * Preco je to takto navrhnute:
 * - Dedi zo spolocnej abstraktnej triedy Miestnost, takze ma vsetky jej vlastnosti (vychody, predmety, postavy, atd.).
 * - Interakcia v miestnosti je orientovana na suboj:
 *   Ak je v miestnosti nepriatel, automaticky zacne boj s hracom.
 *   Ak je miestnost oznacena ako Bezpecna (napr. po boji), vypise sa informacia o bezpeci.
 * - Prehladavanie je povolene az po boji, inak hrac nemoze prehladavat miestnost.
 * - Ak je miestnost bezpecna, moze hrac po boji najst nahodny predmet, ktory si moze zobrat do inventara.
 *
 * OOP principy:
 * - Polymorfizmus: vlastne predefinovane metody pre interakciu a prehladavanie.
 * - Kombinovanie s marker rozhranim Bezpecna (ak je napriklad miestnost docasne/po boji bezpecna).
 */
public class NepriatelskaMiestnost extends Miestnost {
    public NepriatelskaMiestnost(String id, String meno, String popis) {
        super(id, meno, popis);
    }

    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        if (!(this instanceof Bezpecna)) {
            System.out.println("V tejto miestnosti citis nebezpecenstvo...");
            for (NPC postava : this.postavy) {
                if (postava.getZdravie() > 0 && postava instanceof Nepriatel) {
                    System.out.println("\n" + postava.getMeno() + " sa pripravuje na utok!");
                    postava.interakcia(hrac, battleSystem); // Spustenie suboja
                    return;
                }
            }
        } else {
            System.out.println("Miestnost je momentalne bezpecna.");
        }
    }

    @Override
    public void prehladat(Hrac hrac) {
        if (!(this instanceof Bezpecna)) {
            System.out.println("Nemozeš v klude prehladat miestnost, kym su tu nepriatelia!");
        } else {
            System.out.println("Miestnost je po boji. Mozes ju bezpecne prehladat.");
            najdiPredmet(hrac);
        }
    }

    /**
     * Po boji moze hrac najst nahodny predmet, ktory si moze zobrat.
     */
    private void najdiPredmet(Hrac hrac) {
        if (Math.random() < 0.5) {
            Predmet novyPredmet = vytvorNahodnyPredmet();
            pridajPredmet(novyPredmet);
            System.out.println("Nasiel si: " + novyPredmet.getMeno());
            System.out.println("Chces si ho zobrat? (a/n)");
            Scanner scanner = new Scanner(System.in);
            String odpoved = scanner.nextLine().toLowerCase();
            if (odpoved.equals("a")) {
                hrac.getInventar().pridajPredmet(novyPredmet);
                this.predmety.remove(novyPredmet);
                System.out.println("Zobral si: " + novyPredmet.getMeno());
            }
        } else {
            System.out.println("Nenasiel si nic zaujimave.");
        }
    }
}