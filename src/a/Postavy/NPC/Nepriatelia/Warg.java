package a.Postavy.NPC.Nepriatelia;

import a.Manageri.BattleSystem;
import a.Miestnosti.Miestnost;
import a.Postavy.Hrac;

import java.util.Random;
import java.util.Scanner;

/**
 * Trieda Warg predstavuje silneho nepriatela s interaktivnymi moznostami suboja.
 * Dedi zo spolocnej triedy Nepriatel a umoznuje hracovi volbu v interakcii (utok, zastrasenie, utek).
 */
public class Warg extends Nepriatel {

    /**
     * Konstruktor pre warga, nastavi vsetky atributy.
     * @param id unikatny identifikator warga
     * @param meno meno warga
     * @param popis popis warga
     * @param miestnost miestnost, v ktorej sa warg nachadza
     * @param zdravie pociatocne zdravie
     * @param sila pociatocna sila
     * @param obrana pociatocna obrana
     * @param sancaNaZastrasenie pravdepodobnost, s ktorou sa warg necha zastrasit
     */
    public Warg(String id, String meno, String popis, Miestnost miestnost,
                int zdravie, int sila, int obrana, double sancaNaZastrasenie) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana, sancaNaZastrasenie);
    }

    /**
     * Interaktivna interakcia s wargom. Hrac si moze vybrat, ci utoci, zastrasuje alebo uteka.
     * Ak zada neplatnu volbu, warg okamzite utoci.
     * @param hrac hrac
     * @param battleSystem system na riadenie boja
     */
    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        System.out.println(getMeno() + " na teba vyceril svoje ostre zuby a hlboko zavrcal!");
        System.out.println("Obrovsky vlku podobny tvor sa pripravuje na skok!");

        System.out.println("\n1. Zautocit");
        System.out.println("2. Pokusit sa zastrasit warga");
        System.out.println("3. Pokusit sa utiect");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Tvoja volba: ");

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
                    utekZBoja(hrac, battleSystem);
                    break;
                default:
                    System.out.println("Neplatna volba. Warg vyuziva tvoje zavahanie!");
                    this.utok(hrac);
            }
        } catch (NumberFormatException e) {
            System.out.println("Neplatna volba. Warg vyuziva tvoje zavahanie!");
            this.utok(hrac);
        }
    }

    /**
     * Pokusi sa zastrasit warga. Ak uspeje, warg sa stiahne. Ak nie, zautoci s vacsou zurivostou.
     * @param hrac hrac
     */
    private void zastrasitWarga(Hrac hrac) {
        if (this.pokusZastrasenie()) {
            System.out.println(getMeno() + " cuva a knuci! Podarilo sa ti ho zastrasit!");
            System.out.println(getMeno() + " sa stiahol a umoznuje ti bezpecny prechod.");
        } else {
            System.out.println(getMeno() + " povazuje tvoj pokus o zastrasenie za vyzvu a utoci s vacsou zurivostou!");
            int damage = new Random().nextInt(8) + 4;
            hrac.setZdravie(hrac.getZdravie() - damage);
            System.out.println(getMeno() + " ta pohrýzol! Stratil si " + damage + " zivotov.");
            this.utok(hrac);
        }
    }

    /**
     * Pokus o utek z boja. Ak je hod dostatocny, hrac utecie, inak warg zautoci.
     * @param hrac hrac
     * @param battleSystem system na riadenie boja
     */
    private void utekZBoja(Hrac hrac, BattleSystem battleSystem) {
        int utekHod = new Random().nextInt(20) + 1;
        System.out.println("Pokusas sa utiect pred wargom... Hod kockou: " + utekHod);

        if (utekHod > 15) {
            System.out.println("Neuveritelne, podarilo sa ti utiect pred wargom!");
        } else {
            System.out.println(getMeno() + " je prilis rychly! Dobehol ta a skocil ti na chrbat!");
            int damage = new Random().nextInt(10) + 5;
            hrac.setZdravie(hrac.getZdravie() - damage);
            System.out.println("Stratil si " + damage + " zivotov.");
            this.utok(hrac);
        }
    }

    /**
     * Warg sa brani - cuva a pripravi sa na vypad.
     */
    @Override
    public void obrana() {
        System.out.println(getMeno() + " cuva a pripravuje sa na vypad!");
    }
}