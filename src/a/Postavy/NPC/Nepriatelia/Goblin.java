package a.Postavy.NPC.Nepriatelia;

import a.Manageri.BattleSystem;
import a.Miestnosti.Miestnost;
import a.Postavy.Hrac;
import a.Postavy.Utocnik;

/**
 * Trieda Goblin predstavuje konkretneho nepriatela - goblina.
 * Dedi zo spolocnej triedy Nepriatel a moze menit spravanie v interakcii, utoku ci obrane.
 */
public class Goblin extends Nepriatel {

    /**
     * Konstruktor pre goblina, nastavi vsetky atributy.
     * @param id unikatny identifikator goblina
     * @param meno meno goblina
     * @param popis popis goblina
     * @param miestnost miestnost, v ktorej sa goblin nachadza
     * @param zdravie pociatocne zdravie goblina
     * @param sila pociatocna sila goblina
     * @param obrana pociatocna obrana goblina
     * @param sancaNaZastrasenie pravdepodobnost, s ktorou sa goblin necha zastrasit
     */
    public Goblin(String id, String meno, String popis, Miestnost miestnost, int zdravie, int sila, int obrana, double sancaNaZastrasenie) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana, sancaNaZastrasenie);
    }

    /**
     * Definuje interakciu hraca s goblinom - vyvola boj so vsetkymi nepriatelmi v miestnosti.
     * @param hrac hrac
     * @param battleSystem system na riadenie boja
     */
    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        System.out.println(getMeno() + " na teba nepriatelsky zavrci!");
        System.out.println(getMeno() + " tasi svoju zbran a chysta sa zautocit!");
        zacniBojSoVsetkymi(hrac, battleSystem);
    }

    /**
     * Goblin utoci - ak ma zbran, pouzije ju, inak utoci rukami.
     * @param ciel ciel utoku
     */
    @Override
    public void utok(Utocnik ciel) {
        int damage = this.sila;
        if (this.inventar.getAktivnaZbran() != null) {
            damage += this.inventar.getAktivnaZbran().getSila();
            System.out.println(getMeno() + " utoci so zbranou " + this.inventar.getAktivnaZbran().getMeno() + " a sposobi " + damage + " skody!");
        } else {
            System.out.println(getMeno() + " utoci holymi rukami a sposobi " + damage + " skody!");
        }
        ciel.prijmiZasah(damage);
    }

    /**
     * Goblin sa brani - vypise pripravu na obranu.
     */
    @Override
    public void obrana() {
        System.out.println(getMeno() + " sa pripravuje na obranu!");
    }
}