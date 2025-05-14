import javax.swing.text.Position;
import java.util.Random;
import java.util.Scanner;

public class Pavuk extends NPC {
    public Pavuk(String id, String meno, String popis, Position pozicia, int zdravie, int sila, int obrana) {
        super(id, meno, popis, pozicia, zdravie, sila, obrana);
    }

    @Override
    public void interakcia(Hrac hrac) {
        System.out.println(getMeno() + " sa k tebe približuje s klepotajúcimi chelicerami!");
        System.out.println("Obrovský pavúk vyzerá pripravený zaútočiť!");

        System.out.println("\n1. Zaútočiť");
        System.out.println("2. Pokúsiť sa vyhnúť pavučinám");
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
                    vyhnucSaPavucinam(hrac);
                    break;
                case 3:
                    utekZBoja(hrac);
                    break;
                default:
                    System.out.println("Neplatná voľba. Pavúk využíva tvoje zaváhanie!");
                    this.utok(hrac);
            }
        } catch (NumberFormatException e) {
            System.out.println("Neplatná voľba. Pavúk využíva tvoje zaváhanie!");
            this.utok(hrac);
        }
    }

    private void vyhnucSaPavucinam(Hrac hrac) {
        Random random = new Random();
        int hodObratnost = random.nextInt(20) + 1;

        System.out.println("Pokúšaš sa vyhnúť pavučinám... Hod kockou: " + hodObratnost);

        if (hodObratnost > 12) {
            System.out.println("Podarilo sa ti vyhnúť pavučinám! Získavaš výhodu v nasledujúcom boji.");
            // Logika pre výhodu v boji
        } else {
            System.out.println("Zachytil si sa v lepkavej pavučine! Pavúk sa k tebe blíži!");
            int damage = random.nextInt(4) + 2;
            hrac.setZdravie(hrac.getZdravie() - damage);
            System.out.println("Pavúk ťa uhryzol! Stratil si " + damage + " životov a cítiš, ako sa jed šíri tvojím telom.");
            this.utok(hrac);
        }
    }

    private void utekZBoja(Hrac hrac) {
        Random random = new Random();
        int utekHod = random.nextInt(20) + 1;

        System.out.println("Pokúšaš sa utiecť... Hod kockou: " + utekHod);

        if (utekHod > 12) {
            System.out.println("Podarilo sa ti utiecť pred pavúkom!");
        } else {
            System.out.println("Pri úteku si sa zamotal do pavučín! Pavúk sa k tebe rýchlo blíži!");
            int damage = random.nextInt(6) + 2;
            hrac.setZdravie(hrac.getZdravie() - damage);
            System.out.println("Stratil si " + damage + " životov.");
            this.utok(hrac);
        }
    }

    @Override
    public void pouzitie(Hrac hrac) {
        System.out.println("Nemôžeš použiť pavúka ako predmet.");
    }

    @Override
    public void utok(Utocnik ciel) {
        if (ciel instanceof Hrac) {
            Hrac hrac = (Hrac) ciel;
            System.out.println(getMeno() + " sa na teba vrhá s jedovatými tesákmi!");

            // Implementácia boja je v Main.bojovySystem
            Main.bojovySystem(hrac, this);
        }
    }

    @Override
    public void obrana() {
        System.out.println(getMeno() + " sa stiahne a pripraví na obranu!");
        // Tu by bola logika pre zvýšenie obrany na jedno kolo
    }
}