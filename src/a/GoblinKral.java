package a;

import java.util.Random;
import java.util.Scanner;

public class GoblinKral extends Nepriatel implements InteraktivnaPostava {
    public GoblinKral(String id, String meno, String popis, Miestnost miestnost,
                      int zdravie, int sila, int obrana, double sancaNaZastrasenie) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana, sancaNaZastrasenie);
    }

    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        System.out.println("Gobliní kráľ sa dvíha zo svojho trónu!");
        System.out.println("\"Ty sa opovažuješ vstúpiť do mojho kráľovstva, smrteľník?\" zreve na teba.");
        System.out.println("Jeho lukostrelci na teba mieria svojimi zbraňami.");

        System.out.println("\n1. Vyzvať goblinieho kráľa na súboj");
        System.out.println("2. Pokúsiť sa vyjednávať");
        System.out.println("3. Prijať skúšku rozumu (hadanka)");
        System.out.println("4. Pokúsiť sa utiecť");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Tvoja voľba: ");

        try {
            int volba = Integer.parseInt(scanner.nextLine());

            switch (volba) {
                case 1:
                    System.out.println("\"Tak budiž! Zomrieš rukou Goblinieho kráľa aj jeho lukostrelcov!\"");
                    zacniBojSoVsetkymi(hrac, battleSystem);
                    break;
                case 2:
                    System.out.println("\"S dobrodruhmi nevyjednávam!\" odpovedá kráľ a dáva signál svojim lukostrelcom.");
                    System.out.println("Lukostrelci vystrelili svoje šípy!");
                    int damage = new Random().nextInt(10) + 5;
                    hrac.setZdravie(hrac.getZdravie() - damage);
                    System.out.println("Zasiahli ťa šípy! Stratil si " + damage + " životov.");
                    // Boj pokračuje len proti bossovi!
                    zacniBojLenSoSebou(hrac, battleSystem);
                    break;
                case 3:
                    if (polozHadanku(hrac)) {
                        System.out.println("Gobliní kráľ uznáva tvoju múdrosť a vzdáva sa! Získavaš jeho poklad a ukončuješ boj bez krvi.");
                    } else {
                        System.out.println("Zlyhal si v hádanke! Kráľ sa rozčúli a útočí na teba osobne!");
                        zacniBojLenSoSebou(hrac, battleSystem);
                    }
                    break;
                case 4:
                    utekZBoja(hrac, battleSystem);
                    break;
                default:
                    System.out.println("Tvoje váhanie nahnevalo kráľa! Okamžite útočí!");
                    zacniBojLenSoSebou(hrac, battleSystem);
            }
        } catch (NumberFormatException e) {
            System.out.println("Tvoje váhanie nahnevalo kráľa! Okamžite útočí!");
            zacniBojLenSoSebou(hrac, battleSystem);
        }
    }

    private boolean polozHadanku(Hrac hrac) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Gobliní kráľ ťa skúša hádankou:");
        String hadanka = "Som všade, no nikde nie som. Nemám telo, no pohybujem svetom. Čo som?";
        String odpoved = "tien";

        System.out.println(hadanka);
        System.out.print("Tvoja odpoveď: ");
        String vstup = scanner.nextLine().trim().toLowerCase();

        if (vstup.contains(odpoved)) {
            System.out.println("Správne! Gobliní kráľ je ohromený tvojou múdrosťou.");
            return true;
        } else {
            System.out.println("Nesprávna odpoveď!");
            return false;
        }
    }

    private void utekZBoja(Hrac hrac, BattleSystem battleSystem) {
        Random random = new Random();
        int utekHod = random.nextInt(20) + 1;

        System.out.println("Pokúšaš sa utiecť z trónnej siene... Hod kockou: " + utekHod);

        if (utekHod > 15) {
            System.out.println("Neuveriteľne, podarilo sa ti uniknúť z trónnej siene!");
        } else {
            System.out.println("Gobliní lukostrelci ťa zasiahli do nohy! Nemôžeš utiecť.");
            int damage = random.nextInt(8) + 3;
            hrac.setZdravie(hrac.getZdravie() - damage);
            System.out.println("Stratil si " + damage + " životov.");
            zacniBojLenSoSebou(hrac, battleSystem);
        }
    }

    @Override
    public void utok(Utocnik ciel) {
        if (ciel instanceof Hrac) {
            System.out.println(getMeno() + " útočí so svojim žezlom!");

            ciel.prijmiZasah(this.getSila() + 5);
        }
    }

    @Override
    public void obrana() {
        System.out.println(getMeno() + " zdvíha svoj štít na obranu!");
        this.docasnaObrana = 5;
    }

    @Override
    public void prijmiZasah(int silaUtoku) {
        int efektivnaObrana = this.obrana + this.docasnaObrana;
        int zranenie = silaUtoku - efektivnaObrana;
        if (zranenie > 0) {
            this.setZdravie(this.getZdravie() - zranenie);
            System.out.println(getMeno() + " dostal zásah za " + zranenie + " (obrana " + efektivnaObrana + ").");
            if (this.getZdravie() <= 0) {
                System.out.println(getMeno() + " padol porazený!");
            }
        } else {
            System.out.println(getMeno() + " odrazil útok!");
        }
        this.docasnaObrana = 0;
    }
}