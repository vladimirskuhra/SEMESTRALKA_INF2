import java.util.*;

public class GoblinKral extends Nepriatel implements InteraktivnaPostava {
    private final BattleSystem battleSystem;
    private Miestnost miestnost;

    public GoblinKral(String id, String meno, String popis, Miestnost miestnost,
                      int zdravie, int sila, int obrana, double sancaNaZastrasenie, BattleSystem battleSystem) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana, sancaNaZastrasenie);
        this.battleSystem = battleSystem;
        this.miestnost = miestnost;
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
                    // BOJ hráč vs boss + lukostrelci
                    List<Nepriatel> nepriatelia = new ArrayList<>();
                    // Vyhľadaj všetkých nepriateľov v tejto miestnosti (trónna sieň)
                    for (Charakter c : miestnost.getPostavy()) {
                        if (c instanceof Nepriatel) {
                            nepriatelia.add((Nepriatel) c);
                        }
                    }
                    battleSystem.boj(hrac, nepriatelia);
                    break;
                case 2:
                    System.out.println("\"S dobrodruhmi nevyjednávam!\" odpovedá kráľ a dáva signál svojim lukostrelcom.");
                    System.out.println("Lukostrelci vystrelili svoje šípy!");
                    int damage = new Random().nextInt(10) + 5;
                    hrac.setZdravie(hrac.getZdravie() - damage);
                    System.out.println("Zasiahli ťa šípy! Stratil si " + damage + " životov.");
                    // Boj pokračuje len proti bossovi!
                    List<Nepriatel> boss = List.of(this);
                    battleSystem.boj(hrac, boss);
                    break;
                case 3:
                    if (polozHadanku(hrac)) {
                        System.out.println("Gobliní kráľ uznáva tvoju múdrosť a vzdáva sa! Získavaš jeho poklad a ukončuješ boj bez krvi.");
                        // Pridaj odmenu alebo splň quest podľa potreby
                    } else {
                        System.out.println("Zlyhal si v hádanke! Kráľ sa rozčúli a útočí na teba osobne!");
                        List<Nepriatel> bossOnly = List.of(this);
                        battleSystem.boj(hrac, bossOnly);
                    }
                    break;
                case 4:
                    utekZBoja(hrac);
                    break;
                default:
                    System.out.println("Tvoje váhanie nahnevalo kráľa! Okamžite útočí!");
                    List<Nepriatel> boss2 = List.of(this);
                    battleSystem.boj(hrac, boss2);
            }
        } catch (NumberFormatException e) {
            System.out.println("Tvoje váhanie nahnevalo kráľa! Okamžite útočí!");
            List<Nepriatel> boss2 = List.of(this);
            battleSystem.boj(hrac, boss2);
        }
    }

    private boolean polozHadanku(Hrac hrac) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Gobliní kráľ ťa skúša hádankou:");
        String hadanka = "Som všade, no nikde nie som. Nemám telo, no pohybujem svetom. Čo som?";
        String odpoved = "tien"; // Prípadne viac správnych odpovedí

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
            List<Nepriatel> boss = List.of(this);
            battleSystem.boj(hrac, boss);
        }
    }

    @Override
    public void utok(Utocnik ciel) {
        if (ciel instanceof Hrac) {
            System.out.println(getMeno() + " útočí so svojim žezlom!");
            // Príklad: spôsobí extra damage
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