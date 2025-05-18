package a.Postavy.NPC.Specialne;

import a.Manageri.BattleSystem;
import a.Miestnosti.Miestnost;
import a.Postavy.Hrac;
import a.Postavy.NPC.Nepriatelia.Nepriatel;
import a.Postavy.Utocnik;

import java.util.Random;
import java.util.Scanner;

/**
 * Trieda GoblinKral predstavuje specialneho bosse - goblinieho krala.
 * Dedi zo spolocnej triedy Nepriatel a umoznuje interaktivnu konfrontaciu s viacerimi volbami.
 */
public class GoblinKral extends Nepriatel implements InteraktivnaPostava {

    /**
     * Konstruktor pre goblinieho krala, nastavi vsetky atributy.
     * @param id unikatny identifikator
     * @param meno meno kral
     * @param popis popis krala
     * @param miestnost miestnost, v ktorej sa kral nachadza
     * @param zdravie pociatocne zdravie
     * @param sila pociatocna sila
     * @param obrana pociatocna obrana
     * @param sancaNaZastrasenie pravdepodobnost, s ktorou sa kral necha zastrasit
     */
    public GoblinKral(String id, String meno, String popis, Miestnost miestnost,
                      int zdravie, int sila, int obrana, double sancaNaZastrasenie) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana, sancaNaZastrasenie);
    }

    /**
     * Interaktivna metoda pre konfrontaciu s goblinim kralom.
     * Hrac si moze vybrat suboj, vyjednavanie, hadanku alebo utek.
     * @param hrac hrac
     * @param battleSystem system na riadenie boja
     */
    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        System.out.println("Gobliní kral sa dviha zo svojho tronu!");
        System.out.println("\"Ty sa opovazujes vstupit do mojho kralovstva, smrtelnik?\" zreve na teba.");
        System.out.println("Jeho lukostrelci na teba mieria svojimi zbranami.");

        System.out.println("\n1. Vyzvat goblinieho krala na suboj");
        System.out.println("2. Pokusit sa vyjednavat");
        System.out.println("3. Prijat skusku rozumu (hadanka)");
        System.out.println("4. Pokusit sa utiect");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Tvoja volba: ");

        try {
            int volba = Integer.parseInt(scanner.nextLine());

            switch (volba) {
                case 1:
                    System.out.println("\"Tak budiz! Zomries rukou Goblinieho krala aj jeho lukostrelcov!\"");
                    zacniBojSoVsetkymi(hrac, battleSystem);
                    break;
                case 2:
                    System.out.println("\"S dobrodruhmi nevyjednavam!\" odpoveda kral a dava signal svojim lukostrelcom.");
                    System.out.println("Lukostrelci vystrelili svoje sipy!");
                    int damage = new Random().nextInt(10) + 5;
                    hrac.setZdravie(hrac.getZdravie() - damage);
                    System.out.println("Zasiahli ta sipy! Stratil si " + damage + " zivotov.");
                    // Boj pokracuje len proti bossovi!
                    zacniBojLenSoSebou(hrac, battleSystem);
                    break;
                case 3:
                    if (polozHadanku(hrac)) {
                        System.out.println("Gobliní kral uznava tvoju mudrost a vzdava sa! Ziskavas jeho poklad a ukoncujes boj bez krvi.");
                    } else {
                        System.out.println("Zlyhal si v hadanke! Kral sa rozculi a utoci na teba osobne!");
                        zacniBojLenSoSebou(hrac, battleSystem);
                    }
                    break;
                case 4:
                    utekZBoja(hrac, battleSystem);
                    break;
                default:
                    System.out.println("Tvoje vahani nahnevalo krala! Okamzite utoci!");
                    zacniBojLenSoSebou(hrac, battleSystem);
            }
        } catch (NumberFormatException e) {
            System.out.println("Tvoje vahani nahnevalo krala! Okamzite utoci!");
            zacniBojLenSoSebou(hrac, battleSystem);
        }
    }

    /**
     * Hadankova vyzva od goblinieho krala. Ak hrac odpovie spravne, boj sa nekona.
     * @param hrac hrac
     * @return true ak hrac odpovedal spravne, inak false
     */
    private boolean polozHadanku(Hrac hrac) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Gobliní kral ta skusa hadankou:");
        String hadanka = "Som vsade, no nikde nie som. Nemam telo, no pohybujem svetom. Co som?";
        String odpoved = "tien";

        System.out.println(hadanka);
        System.out.print("Tvoja odpoved: ");
        String vstup = scanner.nextLine().trim().toLowerCase();

        if (vstup.contains(odpoved)) {
            System.out.println("Spravne! Gobliní kral je ohromeny tvojou mudrostou.");
            return true;
        } else {
            System.out.println("Nespravna odpoved!");
            return false;
        }
    }

    /**
     * Pokus o utek z tronne siene. Ak je hod dostatocny, hrac utecie, inak sa spusti suboj.
     * @param hrac hrac
     * @param battleSystem system na riadenie boja
     */
    private void utekZBoja(Hrac hrac, BattleSystem battleSystem) {
        Random random = new Random();
        int utekHod = random.nextInt(20) + 1;

        System.out.println("Pokusas sa utiect z tronne siene... Hod kockou: " + utekHod);

        if (utekHod > 15) {
            System.out.println("Neuveritelne, podarilo sa ti uniknut z tronne siene!");
        } else {
            System.out.println("Gobliní lukostrelci ta zasiahli do nohy! Nemozes utiect.");
            int damage = random.nextInt(8) + 3;
            hrac.setZdravie(hrac.getZdravie() - damage);
            System.out.println("Stratil si " + damage + " zivotov.");
            zacniBojLenSoSebou(hrac, battleSystem);
        }
    }

    /**
     * Boss utoci na ciel specialnym utokom (zepzlo).
     * @param ciel ciel utoku
     */
    @Override
    public void utok(Utocnik ciel) {
        if (ciel instanceof Hrac) {
            System.out.println(getMeno() + " utoci so svojim zezlom!");
            ciel.prijmiZasah(this.getSila() + 5);
        }
    }

    /**
     * Boss si aktivuje docasnu obranu.
     */
    @Override
    public void obrana() {
        System.out.println(getMeno() + " zdviha svoj stit na obranu!");
        this.docasnaObrana = 5;
    }

    /**
     * Boss prijima zasah od supera, zapocita docasnu obranu.
     * @param silaUtoku sila utoku supera
     */
    @Override
    public void prijmiZasah(int silaUtoku) {
        int efektivnaObrana = this.obrana + this.docasnaObrana;
        int zranenie = silaUtoku - efektivnaObrana;
        if (zranenie > 0) {
            this.setZdravie(this.getZdravie() - zranenie);
            System.out.println(getMeno() + " dostal zasah za " + zranenie + " (obrana " + efektivnaObrana + ").");
            if (this.getZdravie() <= 0) {
                System.out.println(getMeno() + " padol porazeny!");
            }
        } else {
            System.out.println(getMeno() + " odrazil utok!");
        }
        this.docasnaObrana = 0;
    }
}