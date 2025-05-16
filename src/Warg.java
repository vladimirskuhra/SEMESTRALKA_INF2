import java.util.Random;
import java.util.Scanner;

public class Warg extends Nepriatel {
    private final BattleSystem battleSystem;

    public Warg(String id, String meno, String popis, Miestnost miestnost,
                int zdravie, int sila, int obrana, double sancaNaZastrasenie, BattleSystem battleSystem) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana, sancaNaZastrasenie);
        this.battleSystem = battleSystem;
    }

    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        System.out.println(getMeno() + " na teba vyceril svoje ostré zuby a hlboko zavrčal!");
        System.out.println("Obrovský vlku podobný tvor sa pripravuje na skok!");

        System.out.println("\n1. Zaútočiť");
        System.out.println("2. Pokúsiť sa zastrašiť warga");
        System.out.println("3. Pokúsiť sa utiecť");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Tvoja voľba: ");

        try {
            int volba = Integer.parseInt(scanner.nextLine().trim());
            switch (volba) {
                case 1:
                    this.utok(hrac);
                    break;
                case 2:
                    zastrasitWarga(hrac);
                    break;
                case 3:
                    utekZBoja(hrac);
                    break;
                default:
                    System.out.println("Neplatná voľba. Warg využíva tvoje zaváhanie!");
                    this.utok(hrac);
            }
        } catch (NumberFormatException e) {
            System.out.println("Neplatná voľba. Warg využíva tvoje zaváhanie!");
            this.utok(hrac);
        }
    }

    private void zastrasitWarga(Hrac hrac) {
        if (this.pokusZastrasenie()) {
            System.out.println("Warg cúva a kňučí! Podarilo sa ti ho zastrašiť!");
            System.out.println("Warg sa stiahol a umožňuje ti bezpečný prechod.");
        } else {
            System.out.println("Warg považuje tvoj pokus o zastrašenie za výzvu a útočí s väčšou zúrivosťou!");
            int damage = new Random().nextInt(8) + 4;
            hrac.setZdravie(hrac.getZdravie() - damage);
            System.out.println("Warg ťa pohrýzol! Stratil si " + damage + " životov.");
            this.utok(hrac);
        }
    }

    private void utekZBoja(Hrac hrac) {
        int utekHod = new Random().nextInt(20) + 1;
        System.out.println("Pokúšaš sa utiecť pred wargom... Hod kockou: " + utekHod);

        if (utekHod > 15) {
            System.out.println("Neuveriteľne, podarilo sa ti utiecť pred wargom!");
        } else {
            System.out.println("Warg je príliš rýchly! Dobehol ťa a skočil ti na chrbát!");
            int damage = new Random().nextInt(10) + 5;
            hrac.setZdravie(hrac.getZdravie() - damage);
            System.out.println("Stratil si " + damage + " životov.");
            this.utok(hrac);
        }
    }

    // utok(Utocnik ciel) a prijmiZasah(int) sú už implementované v Nepriatel
    // Ak chceš špeciálne správanie, môžeš override-núť

    // Ak máš v Nepriatel metódu obrana(), môžeš override-nuť podľa potreby:
    @Override
    public void obrana() {
        System.out.println(getMeno() + " cúva a pripravuje sa na výpad!");
        // Tu môže byť špeciálna logika pre zvýšenie obrany na jedno kolo
    }
}