import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BattleSystem {
    private final Scanner scanner;
    private final Random random = new Random();

    public BattleSystem(Scanner scanner) {
        this.scanner = scanner;
    }

    public void boj(Hrac hrac, Charakter nepriatel) {
        System.out.println("\n=== ZAČÍNA BOJ ===");
        System.out.println("Tvojím súperom je: " + nepriatel.getMeno());

        boolean bojAktivny = true;

        while (bojAktivny && hrac.getZdravie() > 0 && nepriatel.getZdravie() > 0) {
            System.out.println("\n" + hrac.getMeno() + ": Zdravie=" + hrac.getZdravie() +
                    ", " + nepriatel.getMeno() + ": Zdravie=" + nepriatel.getZdravie());

            System.out.println("\nČo urobíš?");
            System.out.println("1. Útok");
            System.out.println("2. Použiť predmet");
            System.out.println("3. Pokus o útek");

            System.out.print("Tvoja voľba: ");
            int volba = getNumericInput(1, 3);

            switch(volba) {
                case 1:
                    vykonajUtokHraca(hrac, nepriatel);
                    break;
                case 2:
                    if (pouzitPredmet(hrac)) {
                        System.out.println("Použil si predmet.");
                    } else {
                        System.out.println("Nevybral si žiadny predmet, strácaš kolo!");
                    }
                    break;
                case 3:
                    int hodKockou = random.nextInt(20) + 1;
                    if (hodKockou > 10) {
                        System.out.println("Hod kockou: " + hodKockou + " - Úspešne si utiekol!");
                        bojAktivny = false;
                        return;
                    } else {
                        System.out.println("Hod kockou: " + hodKockou + " - Nepodarilo sa ti utiecť!");
                    }
                    break;
            }

            if (nepriatel.getZdravie() <= 0) {
                System.out.println("\n" + nepriatel.getMeno() + " bol porazený!");
                System.out.println("Vyhral si boj!");
                return;
            }

            System.out.println("\n" + nepriatel.getMeno() + " útočí!");
            vykonajUtokNepriatela(nepriatel, hrac);

            if (hrac.getZdravie() <= 0) {
                System.out.println("\nPadol si v boji!");
                bojAktivny = false;
            }
        }
    }

    private void vykonajUtokHraca(Hrac hrac, Charakter nepriatel) {
        int hodKockou = random.nextInt(20) + 1;
        System.out.println("\nHodil si kockou d20: " + hodKockou);

        if (hodKockou >= 10) {
            int poskodenie = random.nextInt(8) + 1 + hrac.getSila();
            int skutocnePoskodenie = Math.max(1, poskodenie - nepriatel.getObrana());
            System.out.println("Úspešný zásah! Spôsobil si " + skutocnePoskodenie + " bodov poškodenia.");
            nepriatel.setZdravie(nepriatel.getZdravie() - skutocnePoskodenie);
        } else {
            System.out.println("Netrafil si! " + nepriatel.getMeno() + " sa vyhol tvojmu útoku.");
        }
    }

    private void vykonajUtokNepriatela(Charakter nepriatel, Hrac hrac) {
        int hodKockou = random.nextInt(20) + 1;
        System.out.println(nepriatel.getMeno() + " hodil kockou d20: " + hodKockou);

        if (hodKockou >= 8) {
            int poskodenie = random.nextInt(6) + 1 + nepriatel.getSila();
            int skutocnePoskodenie = Math.max(1, poskodenie - hrac.getObrana());
            System.out.println("Zasiahol ťa! Utrpel si " + skutocnePoskodenie + " bodov poškodenia.");
            hrac.setZdravie(hrac.getZdravie() - skutocnePoskodenie);
        } else {
            System.out.println("Vyhol si sa! " + nepriatel.getMeno() + " ťa netrafil.");
        }
    }

    private boolean pouzitPredmet(Hrac hrac) {
        List<Predmet> predmety = hrac.getInventar().getPredmety();

        if (predmety.isEmpty()) {
            System.out.println("Nemáš žiadne predmety!");
            return false;
        }

        System.out.println("\nKtorý predmet chceš použiť?");
        for (int i = 0; i < predmety.size(); i++) {
            System.out.println((i+1) + ". " + predmety.get(i).getMeno());
        }
        System.out.println("0. Späť");

        System.out.print("Tvoja voľba: ");
        int volba = getNumericInput(0, predmety.size());

        if (volba == 0) {
            return false;
        }

        Predmet vybranyPredmet = predmety.get(volba - 1);
        vybranyPredmet.pouzitie(hrac);
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