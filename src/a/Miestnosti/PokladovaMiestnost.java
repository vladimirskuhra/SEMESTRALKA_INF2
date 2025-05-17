package a.Miestnosti;

import a.Manageri.BattleSystem;
import a.Postavy.Hrac;
import a.Predmety.Predmet;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * PokladovaMiestnost je typ miestnosti, kde hrac moze najst poklady.
 * Dedi zo spolocnej triedy Miestnost.
 */
public class PokladovaMiestnost extends Miestnost {

    /**
     * Konstruktor vytvori novy objekt PokladovaMiestnost s danym id, menom a popisom.
     * @param id identifikator miestnosti
     * @param meno nazov miestnosti
     * @param popis textovy popis miestnosti
     */
    public PokladovaMiestnost(String id, String meno, String popis) {
        super(id, meno, popis);
    }

    /**
     * Metoda interakcia spusti automaticky prehladavanie miestnosti.
     * @param hrac objekt hraca v miestnosti
     * @param battleSystem system subojov (v tejto miestnosti sa nepouziva)
     */
    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        prehladat(hrac);
    }

    /**
     * Metoda prehladat umoznuje hracovi prezriet poklady v miestnosti.
     * Ak miestnost este nebola prehladana, vypise zoznam pokladov a ponukne ich zobrat.
     * Ak hrac odpovie 'a', predmety sa pridaju do inventara hraca a odstrania sa z miestnosti.
     * Prehladavanie je mozne len raz, potom vypise, ze miestnost uz bola prehladana.
     * Tento algoritmus zabezpecuje, ze hrac nemoze poklady brat opakovane.
     *
     * Zlozitejsi algoritmus:
     * - Najprv sa overi, ci miestnost bola prehladana
     * - Potom sa skontroluje, ci su v miestnosti predmety
     * - Ak ano, vypise ich, opyta sa hraca, ci ich chce zobrat
     * - Ak hrac suhlasi, predmety sa pridaju do inventara a odstrania sa z miestnosti (pouziva sa nova ArrayList kvoli odstranovaniu pocas iteracie)
     * - Ak nie su predmety, vypise hlasku o prazdnej miestnosti
     * - Na konci nastavi priznak prehladana na true, aby sa poklady nedali brat opakovane
     *
     * @param hrac objekt hraca v miestnosti
     */
    @Override
    public void prehladat(Hrac hrac) {
        if (!this.prehladana) {
            if (!this.predmety.isEmpty()) {
                System.out.println("Nasiel si poklady:");
                for (Predmet predmet : this.predmety) {
                    System.out.println("- " + predmet.getMeno());
                }
                System.out.println("Chces si ich zobrat? (a/n)");
                Scanner scanner = new Scanner(System.in);
                String odpo = scanner.nextLine().toLowerCase();
                if (odpo.equals("a")) {
                    // Prechadzame cez kopiu zoznamu, aby sa dali predmety odstranit pocas iteracie
                    for (Predmet predmet : new ArrayList<>(this.predmety)) {
                        hrac.getInventar().pridajPredmet(predmet);
                        this.predmety.remove(predmet);
                        System.out.println("Zobral si: " + predmet.getMeno());
                    }
                }
            } else {
                System.out.println("Nic si nenasiel.");
            }
            this.prehladana = true;
        } else {
            System.out.println("Tuto miestnost si uz prehladal.");
        }
    }
}