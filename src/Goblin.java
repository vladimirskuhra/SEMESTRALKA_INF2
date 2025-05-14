import javax.swing.text.Position;
import java.util.Random;
import java.util.Scanner;

public class Goblin extends NPC {
    private BattleSystem battleSystem;

    public Goblin(String id, String meno, String popis, Position pozicia, int zdravie, int sila, int obrana, BattleSystem battleSystem) {
        super(id, meno, popis, pozicia, zdravie, sila, obrana);
        this.battleSystem = battleSystem;
    }

    @Override
    public void interakcia(Hrac hrac) {
        System.out.println(getMeno() + " na teba nepriateľsky zavrčí!");
        System.out.println("Goblin tasí svoju zbraň a chystá sa zaútočiť!");

        System.out.println("\n1. Zaútočiť");
        System.out.println("2. Pokúsiť sa utiecť");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Tvoja voľba: ");

        try {
            int volba = Integer.parseInt(scanner.nextLine());

            switch (volba) {
                case 1:
                    this.utok(hrac);
                    break;
                case 2:
                    utekZBoja(hrac);
                    break;
                default:
                    System.out.println("Neplatná voľba. Goblin využíva tvoje zaváhanie!");
                    this.utok(hrac);
            }
        } catch (NumberFormatException e) {
            System.out.println("Neplatná voľba. Goblin využíva tvoje zaváhanie!");
            this.utok(hrac);
        }
    }

    private void utekZBoja(Hrac hrac) {
        Random random = new Random();
        int utekHod = random.nextInt(20) + 1;

        System.out.println("Pokúšaš sa utiecť... Hod kockou: " + utekHod);

        if (utekHod > 10) {
            System.out.println("Podarilo sa ti utiecť pred goblinom!");
        } else {
            System.out.println("Goblin ťa dobehol! Útočí na teba!");
            this.utok(hrac);
        }
    }

    @Override
    public void pouzitie(Hrac hrac) {
        System.out.println("Nemôžeš použiť goblina ako predmet.");
    }

    @Override
    public void utok(Utocnik ciel) {
        if (ciel instanceof Hrac) {
            Hrac hrac = (Hrac) ciel;
            System.out.println(getMeno() + " útočí na teba!");
            battleSystem.boj(hrac, this);
        }
    }

    @Override
    public void obrana() {
        System.out.println(getMeno() + " sa pripravuje na obranu!");
        // Tu by bola logika pre zvýšenie obrany na jedno kolo
    }

    @Override
    public Predmet dropniPredmet() {
        return super.dropniPredmet();
    }

    @Override
    public void zastras(Hrac hrac) {
        super.zastras(hrac);
    }
}