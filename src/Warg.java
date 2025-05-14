import javax.swing.text.Position;
import java.util.Random;
import java.util.Scanner;

public class Warg extends NPC {

    private BattleSystem battleSystem;

    public Warg(String id, String meno, String popis, Position pozicia, int zdravie, int sila, int obrana, BattleSystem battleSystem) {
        super(id, meno, popis, pozicia, zdravie, sila, obrana);
        this.battleSystem = battleSystem;
    }

    @Override
    public void interakcia(Hrac hrac) {
        System.out.println(getMeno() + " na teba vyceril svoje ostré zuby a hlboko zavrčal!");
        System.out.println("Obrovský vlku podobný tvor sa pripravuje na skok!");

        System.out.println("\n1. Zaútočiť");
        System.out.println("2. Pokúsiť sa zastrašiť warga");
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
        Random random = new Random();
        int zastraseniaHod = random.nextInt(20) + 1;

        System.out.println("Pokúšaš sa zastrašiť warga hlasným výkrikom a mávaním zbraňou... Hod kockou: " + zastraseniaHod);

        if (zastraseniaHod > 14) { // Warg je ťažšie zastrašiteľný
            System.out.println("Warg cúva a kňučí! Podarilo sa ti ho zastrašiť!");
            System.out.println("Warg sa stiahol a umožňuje ti bezpečný prechod.");
        } else {
            System.out.println("Warg považuje tvoj pokus o zastrašenie za výzvu a útočí s väčšou zúrivosťou!");
            int damage = random.nextInt(8) + 4;
            hrac.setZdravie(hrac.getZdravie() - damage);
            System.out.println("Warg ťa pohrýzol! Stratil si " + damage + " životov.");
            this.utok(hrac);
        }
    }

    private void utekZBoja(Hrac hrac) {
        Random random = new Random();
        int utekHod = random.nextInt(20) + 1;

        System.out.println("Pokúšaš sa utiecť pred wargom... Hod kockou: " + utekHod);

        if (utekHod > 15) { // Warg je rýchly, ťažšie pred ním utiecť
            System.out.println("Neuveriteľne, podarilo sa ti utiecť pred wargom!");
        } else {
            System.out.println("Warg je príliš rýchly! Dobehol ťa a skočil ti na chrbát!");
            int damage = random.nextInt(10) + 5;
            hrac.setZdravie(hrac.getZdravie() - damage);
            System.out.println("Stratil si " + damage + " životov.");
            this.utok(hrac);
        }
    }

    @Override
    public void pouzitie(Hrac hrac) {
        System.out.println("Nemôžeš použiť warga ako predmet.");
    }

    @Override
    public void utok(Utocnik ciel) {
        if (ciel instanceof Hrac) {
            Hrac hrac = (Hrac) ciel;
            System.out.println(getMeno() + " skáče na teba s otvorenou papuľou plnou ostrých zubov!");
            battleSystem.boj(hrac, this);
        }
    }

    @Override
    public void obrana() {
        System.out.println(getMeno() + " cúva a pripravuje sa na výpad!");
        // Tu by bola logika pre zvýšenie obrany na jedno kolo
    }
}