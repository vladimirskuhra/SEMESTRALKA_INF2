import javax.swing.text.Position;
import java.util.Random;
import java.util.Scanner;

public class GoblinKral extends NPC {
    public GoblinKral(String id, String meno, String popis, Position pozicia, int zdravie, int sila, int obrana) {
        super(id, meno, popis, pozicia, zdravie, sila, obrana);
    }

    @Override
    public void interakcia(Hrac hrac) {
        System.out.println("Gobliní kráľ sa dvíha zo svojho trónu!");
        System.out.println("\"Ty sa opovažuješ vstúpiť do mojho kráľovstva, smrteľník?\" zreve na teba.");
        System.out.println("Jeho lukostrelci na teba mieria svojimi zbraňami.");

        System.out.println("\n1. Vyzvať goblinieho kráľa na súboj");
        System.out.println("2. Pokúsiť sa vyjednávať");
        System.out.println("3. Pokúsiť sa utiecť");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Tvoja voľba: ");

        try {
            int volba = Integer.parseInt(scanner.nextLine());

            switch (volba) {
                case 1:
                    System.out.println("\"Tak budiž! Zomrieš rukou Goblinieho kráľa!\" zreve vládca goblinov.");
                    this.utok(hrac);
                    break;
                case 2:
                    System.out.println("\"S dobrodruhmi nevyjednávam!\" odpovedá kráľ a dáva signál svojim lukostrelcom.");
                    System.out.println("Lukostrelci vystrelili svoje šípy!");

                    Random random = new Random();
                    int damage = random.nextInt(10) + 5;
                    hrac.setZdravie(hrac.getZdravie() - damage);

                    System.out.println("Zasiahli ťa šípy! Stratil si " + damage + " životov.");
                    this.utok(hrac);
                    break;
                case 3:
                    utekZBoja(hrac);
                    break;
                default:
                    System.out.println("Tvoje váhanie nahnevalo kráľa! Okamžite útočí!");
                    this.utok(hrac);
            }
        } catch (NumberFormatException e) {
            System.out.println("Tvoje váhanie nahnevalo kráľa! Okamžite útočí!");
            this.utok(hrac);
        }
    }

    private void utekZBoja(Hrac hrac) {
        Random random = new Random();
        int utekHod = random.nextInt(20) + 1;

        System.out.println("Pokúšaš sa utiecť z trónnej siene... Hod kockou: " + utekHod);

        if (utekHod > 15) { // Ťažšie utiecť od kráľa
            System.out.println("Neuveriteľne, podarilo sa ti uniknúť z trónnej siene!");
        } else {
            System.out.println("Gobliní lukostrelci ťa zasiahli do nohy! Nemôžeš utiecť.");
            int damage = random.nextInt(8) + 3;
            hrac.setZdravie(hrac.getZdravie() - damage);
            System.out.println("Stratil si " + damage + " životov.");
            this.utok(hrac);
        }
    }

    @Override
    public void pouzitie(Hrac hrac) {
        System.out.println("Nemôžeš použiť Goblinieho kráľa ako predmet.");
    }

    @Override
    public void utok(Utocnik ciel) {
        if (ciel instanceof Hrac) {
            Hrac hrac = (Hrac) ciel;
            System.out.println("BOSS BATTLE: " + getMeno() + " útočí svojím žezlom!");

            // Implementácia boja je v Main.bojovySystem
            Main.bojovySystem(hrac, this);
        }
    }

    @Override
    public void obrana() {
        System.out.println(getMeno() + " zdvíha svoj štít na obranu!");
        // Tu by bola logika pre zvýšenie obrany na jedno kolo
    }
}