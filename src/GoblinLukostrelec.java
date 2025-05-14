import javax.swing.text.Position;
import java.util.Random;
import java.util.Scanner;

public class GoblinLukostrelec extends NPC {
    private BattleSystem battleSystem;

    public GoblinLukostrelec(String id, String meno, String popis, Position pozicia, int zdravie, int sila, int obrana, BattleSystem battleSystem) {
        super(id, meno, popis, pozicia, zdravie, sila, obrana);
        this.battleSystem = battleSystem;
    }

    @Override
    public void interakcia(Hrac hrac) {
        System.out.println(getMeno() + " na teba namieri svoj luk!");
        System.out.println("\"Stoj, votrelec!\" zasyčí goblin.");

        System.out.println("\n1. Zaútočiť");
        System.out.println("2. Pokúsiť sa skryť za prekážku");
        System.out.println("3. Pokúsiť sa utiecť");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Tvoja voľba: ");

        try {
            int volba = Integer.parseInt(scanner.nextLine());

            switch (volba) {
                case 1:
                    this.utok(hrac);
                    break;
                case 2:
                    skrytSa(hrac);
                    break;
                case 3:
                    utekZBoja(hrac);
                    break;
                default:
                    System.out.println("Neplatná voľba. Lukostrelec vystrelil šíp!");
                    this.utok(hrac);
            }
        } catch (NumberFormatException e) {
            System.out.println("Neplatná voľba. Lukostrelec vystrelil šíp!");
            this.utok(hrac);
        }
    }

    private void skrytSa(Hrac hrac) {
        Random random = new Random();
        int skrytHod = random.nextInt(20) + 1;

        System.out.println("Pokúšaš sa skryť... Hod kockou: " + skrytHod);

        if (skrytHod > 12) {
            System.out.println("Podarilo sa ti skryť za kameň! Získavaš výhodu v boji.");
            // Logika pre výhodu v boji - napr. bonus k útoku
        } else {
            System.out.println("Nepodarilo sa ti skryť! Lukostrelec ťa vidí a strieľa!");
            int damage = random.nextInt(6) + 3;
            hrac.setZdravie(hrac.getZdravie() - damage);
            System.out.println("Šíp ťa zasiahol! Stratil si " + damage + " životov.");
            this.utok(hrac);
        }
    }

    private void utekZBoja(Hrac hrac) {
        Random random = new Random();
        int utekHod = random.nextInt(20) + 1;

        System.out.println("Pokúšaš sa utiecť... Hod kockou: " + utekHod);

        if (utekHod > 10) {
            System.out.println("Podarilo sa ti utiecť pred lukostrelcom!");
        } else {
            System.out.println("Pri úteku ťa zasiahol šíp!");
            int damage = random.nextInt(8) + 2;
            hrac.setZdravie(hrac.getZdravie() - damage);
            System.out.println("Stratil si " + damage + " životov.");
            this.utok(hrac);
        }
    }

    @Override
    public void pouzitie(Hrac hrac) {
        System.out.println("Nemôžeš použiť goblinieho lukostrelca ako predmet.");
    }

    @Override
    public void utok(Utocnik ciel) {
        if (ciel instanceof Hrac) {
            Hrac hrac = (Hrac) ciel;
            System.out.println(getMeno() + " na teba strieľa z luku!");
            battleSystem.boj(hrac, this);
        }
    }

    @Override
    public void obrana() {
        System.out.println(getMeno() + " ustupuje do bezpečnejšej pozície!");
        // Tu by bola logika pre zvýšenie obrany na jedno kolo
    }
}