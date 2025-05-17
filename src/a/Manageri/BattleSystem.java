package a.Manageri;

import a.Manageri.Quest.QuestManager;
import a.Postavy.NPC.Nepriatelia.Nepriatel;
import a.Postavy.Hrac;
import a.Predmety.Lektvar;
import a.Predmety.Predmet;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * BattleSystem sa stara o suboje medzi hracom a nepriatelmi.
 * Obsahuje logiku boja, AI nepriatelov a pouzitie predmetov v suboji.
 * Prepojenia: Pouziva QuestManager na kontrolu questov po porazeni nepriatelov.
 */
public class BattleSystem {
    private final Scanner scanner;
    private final Random random = new Random();
    private final QuestManager questManager;

    /**
     * Konstruktor prijima scanner a questManager pre ovladanie vstupu a kontroly questov.
     */
    public BattleSystem(Scanner scanner, QuestManager questManager) {
        this.scanner = scanner;
        this.questManager = questManager;
    }

    /**
     * Metoda spusta suboj medzi hracom a zoznamom nepriatelov.
     * Algoritmus:
     * 1. Vypise nepriatelov v miestnosti.
     * 2. Hrac si vybera akcie (utok, pouzitie predmetu, uteka).
     * 3. Po hracovi nasleduje kolo vsetkych nepriatelov (AI).
     * 4. Kontroluje sa koniec suboja (vsetci nepriatelia porazeni alebo hrac padol).
     */
    public void boj(Hrac hrac, List<Nepriatel> nepriatelia) {
        System.out.println("\n=== ZACINA BOJ ===");
        System.out.println("Tvojim superom su:");
        for (int i = 0; i < nepriatelia.size(); i++) {
            System.out.println((i + 1) + ". " + nepriatelia.get(i).getMeno());
        }

        boolean bojAktivny = true;

        while (bojAktivny && hrac.getZdravie() > 0 && !nepriatelia.isEmpty()) {
            // Vypis aktualneho stavu
            System.out.println("\n" + hrac.getMeno() + ": Zdravie=" + hrac.getZdravie());
            for (int i = 0; i < nepriatelia.size(); i++) {
                Nepriatel n = nepriatelia.get(i);
                System.out.println((i + 1) + ". " + n.getMeno() + ": Zdravie=" + n.getZdravie());
            }

            // Hracova akcia
            System.out.println("\nCo urobis?");
            System.out.println("1. Utok na vybraneho nepriatela");
            System.out.println("2. Pouzit elixir/predmet");
            System.out.println("3. Pokus o utek");

            System.out.print("Tvoja volba: ");
            int volba = InputUtils.getNumericInput(this.scanner, 1, 3);

            switch (volba) {
                case 1:
                    // Vyber ciela utoku
                    System.out.print("Vyber cislo nepriatela na utok: ");
                    int idx = InputUtils.getNumericInput(this.scanner, 1, nepriatelia.size()) - 1;
                    Nepriatel ciel = nepriatelia.get(idx);
                    hrac.utok(ciel);
                    if (ciel.getZdravie() <= 0) {
                        System.out.println(ciel.getMeno() + " bol porazeny!");
                        ciel.dropDoMiestnosti(); // loot
                        nepriatelia.remove(ciel);

                        this.questManager.skontrolujSplneneQuesty(hrac);

                        if (nepriatelia.isEmpty()) {
                            System.out.println("Vsetci nepriatelia boli porazeni!");

                            this.questManager.skontrolujSplneneQuesty(hrac);
                            this.questManager.zobrazAktivneQuesty(hrac);
                            return;
                        }
                    }
                    break;
                case 2:
                    // Pouzitie lektvaru/predmetu
                    if (pouzitLektvar(hrac)) {
                        System.out.println("Pouzil si elixir/predmet.");
                    } else {
                        System.out.println("Nevybral si ziaden predmet, stracas kolo!");
                    }
                    break;
                case 3:
                    // Pokus o utek (nahodny hod kockou)
                    int hodKockou = this.random.nextInt(20) + 1;
                    if (hodKockou > 10) {
                        System.out.println("Uspesne si utiekol!");
                        this.questManager.zobrazAktivneQuesty(hrac);
                        return;
                    } else {
                        System.out.println("Nepodarilo sa ti utiect!");
                    }
                    break;
            }

            // Kolo vsetkych nepriatelov - AI logika
            for (int i = 0; i < nepriatelia.size(); i++) {
                Nepriatel n = nepriatelia.get(i);
                if (n.getZdravie() > 0) {
                    nepriatelAIAkcia(n, hrac);
                    if (hrac.getZdravie() <= 0) {
                        System.out.println("Padol si v boji!");
                        return;
                    }
                }
            }
        }
    }

    /**
     * Jednoducha AI pre rozhodovanie nepriatela v suboji.
     * Nepriatel sa rozhoduje na zaklade nahody a svojho zdravia.
     */
    private void nepriatelAIAkcia(Nepriatel nepriatel, Hrac hrac) {
        int akcia = this.random.nextInt(100);
        if (nepriatel.getZdravie() < 8 && akcia < 20) { // 20% sanca na utek pri nizkom zdraví
            System.out.println(nepriatel.getMeno() + " sa pokusa utiect!");
            int hod = this.random.nextInt(20) + 1;
            if (hod > 12) {
                System.out.println(nepriatel.getMeno() + " uspesne utiekol!");
                nepriatel.setZdravie(0); // Simulujeme zmiznutie z boja
            } else {
                System.out.println(nepriatel.getMeno() + " sa pokusil utiect, ale nepodarilo sa!");
                nepriatel.utok(hrac); // aj tak utoci
            }
        } else if (akcia < 30) { // 10% sanca na obranu
            nepriatel.obrana();
        } else {
            nepriatel.utok(hrac); // 70% sanca na utok
        }
    }

    /**
     * Hrac si moze vybrat a pouzit lektvar pocas boja.
     * Ak nema ziadny, straca kolo.
     */
    private boolean pouzitLektvar(Hrac hrac) {
        List<Predmet> predmety = hrac.getInventar().getPredmety();
        List<Predmet> lektvary = predmety.stream().filter(p -> p instanceof Lektvar).toList();

        if (lektvary.isEmpty()) {
            System.out.println("Nemas ziadne lektvary!");
            return false;
        }

        System.out.println("\nKtory lektvar chces pouzit?");
        for (int i = 0; i < lektvary.size(); i++) {
            System.out.println((i + 1) + ". " + lektvary.get(i).getMeno());
        }
        System.out.println("0. Spat");
        System.out.print("Tvoja volba: ");
        int volba = InputUtils.getNumericInput(this.scanner, 0, lektvary.size());
        if (volba == 0) return false;
        Lektvar lektvar = (Lektvar) lektvary.get(volba - 1);
        lektvar.pouzitie(hrac); // lektvar sa odstrani automaticky z inventara po pouziti

        return true;
    }
}