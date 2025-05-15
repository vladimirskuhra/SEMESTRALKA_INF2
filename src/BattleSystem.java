import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BattleSystem {
    private final Scanner scanner;
    private final Random random = new Random();
    private final QuestManager questManager; // <<< PRIDANÉ

    public BattleSystem(Scanner scanner, QuestManager questManager) { // <<< ÚPRAVA KONŠTRUKTORA
        this.scanner = scanner;
        this.questManager = questManager;
    }

    // Nová metóda pre boj s viacerými nepriateľmi
    public void boj(Hrac hrac, List<Nepriatel> nepriatelia) {
        System.out.println("\n=== ZAČÍNA BOJ ===");
        System.out.println("Tvojím súperom sú:");
        for (int i = 0; i < nepriatelia.size(); i++) {
            System.out.println((i+1) + ". " + nepriatelia.get(i).getMeno());
        }

        boolean bojAktivny = true;

        while (bojAktivny && hrac.getZdravie() > 0 && !nepriatelia.isEmpty()) {
            // Výpis stavu
            System.out.println("\n" + hrac.getMeno() + ": Zdravie=" + hrac.getZdravie());
            for (int i = 0; i < nepriatelia.size(); i++) {
                Nepriatel n = nepriatelia.get(i);
                System.out.println((i+1) + ". " + n.getMeno() + ": Zdravie=" + n.getZdravie());
            }

            // Hráčova akcia
            System.out.println("\nČo urobíš?");
            System.out.println("1. Útok na vybraného nepriateľa");
            System.out.println("2. Použiť elixír/predmet");
            System.out.println("3. Pokus o útek");

            System.out.print("Tvoja voľba: ");
            int volba = getNumericInput(1, 3);

            switch(volba) {
                case 1:
                    // Výber cieľa útoku
                    System.out.print("Vyber číslo nepriateľa na útok: ");
                    int idx = getNumericInput(1, nepriatelia.size()) - 1;
                    Nepriatel ciel = nepriatelia.get(idx);
                    hrac.utok(ciel);
                    if (ciel.getZdravie() <= 0) {
                        System.out.println(ciel.getMeno() + " bol porazený!");
                        ciel.dropDoMiestnosti(); // loot
                        nepriatelia.remove(ciel);

                        // >>>>>>>> DOPLNENÉ: Kontrola questov po zabití nepriateľa!
                        questManager.skontrolujSplneneQuesty(hrac);

                        if (nepriatelia.isEmpty()) {
                            System.out.println("Všetci nepriatelia boli porazení!");
                            // >>>>>>>> DOPLNENÉ: Kontrola questov po skončení boja!
                            questManager.skontrolujSplneneQuesty(hrac);
                            questManager.zobrazAktivneQuesty(hrac);
                            return;
                        }
                    }
                    break;
                case 2:
                    if (pouzitLektvar(hrac)) {
                        System.out.println("Použil si elixír/predmet.");
                    } else {
                        System.out.println("Nevybral si žiadny predmet, strácaš kolo!");
                    }
                    break;
                case 3:
                    int hodKockou = random.nextInt(20) + 1;
                    if (hodKockou > 10) {
                        System.out.println("Úspešne si utiekol!");
                        questManager.zobrazAktivneQuesty(hrac);
                        return;
                    } else {
                        System.out.println("Nepodarilo sa ti utiecť!");
                    }
                    break;
            }

            // Kolo nepriateľov
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


    private void nepriatelAIAkcia(Nepriatel nepriatel, Hrac hrac) { // nepriatel sa rozhoduje pomocou primitivneho AI
        int akcia = random.nextInt(100);
        if (nepriatel.getZdravie() < 8 && akcia < 20) { // 20% šanca na útek pri nízkom zdraví
            System.out.println(nepriatel.getMeno() + " sa pokúša utiecť!");
            int hod = random.nextInt(20) + 1;
            if (hod > 12) {
                System.out.println(nepriatel.getMeno() + " úspešne utiekol!");
                nepriatel.setZdravie(0); // Simuluj "zmiznutie" z boja
            } else {
                System.out.println(nepriatel.getMeno() + " sa pokúsil utiecť, ale nepodarilo sa!");
                nepriatel.utok(hrac); // aj tak útočí
            }
        } else if (akcia < 30) { // 10% šanca na obranu
            nepriatel.obrana();
        } else {
            nepriatel.utok(hrac); // 70% šanca na útok
        }
    }


    private boolean pouzitLektvar(Hrac hrac) {
        List<Predmet> predmety = hrac.getInventar().getPredmety();
        List<Predmet> lektvary = predmety.stream().filter(p -> p instanceof Lektvar).toList();

        if (lektvary.isEmpty()) {
            System.out.println("Nemáš žiadne lektvary!");
            return false;
        }

        System.out.println("\nKtorý lektvar chceš použiť?");
        for (int i = 0; i < lektvary.size(); i++) {
            System.out.println((i+1) + ". " + lektvary.get(i).getMeno());
        }
        System.out.println("0. Späť");
        System.out.print("Tvoja voľba: ");
        int volba = getNumericInput(0, lektvary.size());
        if (volba == 0) return false;
        Lektvar lektvar = (Lektvar) lektvary.get(volba - 1);
        lektvar.pouzitie(hrac); // lektvar sa odstrani automaticky z invetnaru po pouziti, bomba fest

        return true;
    }

    private int getNumericInput(int min, int max) {
        int volba = -1;
        while (volba < min || volba > max) {
            try {
                String vstup = scanner.nextLine();
                volba = Integer.parseInt(vstup);
                if (volba < min || volba > max) {
                    System.out.print("Zadaj číslo od " + min + " do " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Zadaj platné číslo: ");
            }
        }
        return volba;
    }
}