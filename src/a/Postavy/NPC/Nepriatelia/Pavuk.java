package a.Postavy.NPC.Nepriatelia;

import a.Manageri.BattleSystem;
import a.Miestnosti.Miestnost;
import a.Postavy.Hrac;
import a.Postavy.Utocnik;

import java.util.Random;

/**
 * Trieda Pavuk predstavuje nepriatela typu pavuk.
 * Dedi zo spolocnej triedy Nepriatel a implementuje vlastne spravanie pre utok aj obranu.
 */
public class Pavuk extends Nepriatel {

    /**
     * Konstruktor pre pavuka, nastavi vsetky atributy.
     * @param id unikatny identifikator pavuka
     * @param meno meno pavuka
     * @param popis popis pavuka
     * @param miestnost miestnost, v ktorej sa pavuk nachadza
     * @param zdravie pociatocne zdravie
     * @param sila pociatocna sila
     * @param obrana pociatocna obrana
     * @param sancaNaZastrasenie pravdepodobnost, s ktorou sa pavuk necha zastrasit
     */
    public Pavuk(String id, String meno, String popis, Miestnost miestnost, int zdravie, int sila, int obrana, double sancaNaZastrasenie) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana, sancaNaZastrasenie);
    }

    /**
     * Pavuk sa brani - ustupi a zvysi si docasnu obranu na jedno kolo.
     */
    @Override
    public void obrana() {
        System.out.println(getMeno() + " sa stiahne a pripravi na obranu!");
        this.docasnaObrana = 4;
    }

    /**
     * Interakcia hraca s pavukom - pavuk nereaguje rozhovorom.
     * @param hrac hrac
     * @param battleSystem system na riadenie boja
     */
    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        System.out.println(getMeno() + " momentalne nema naladu na rozpravanie.");
    }

    /**
     * Pavuk utoci jedovatym hryzom, sposobuje nahodne zranenie plus svoju silu, s pravdepodobnostou 20% moze otravit hraca.
     * @param ciel ciel utoku
     */
    @Override
    public void utok(Utocnik ciel) {
        if (ciel instanceof Hrac hrac) {
            System.out.println(getMeno() + " zautoci jedovatym hryzom!");
            int damage = new Random().nextInt(5) + 2 + this.sila; // 2-6 plus sila
            hrac.prijmiZasah(damage);
            if (new Random().nextInt(100) < 20) { // sanca na otravu 20%
                System.out.println(hrac.getMeno() + " je otraveny!");
                // Tu by sa dal pridat efekt jedu cez system efektov
            }
        }
    }
}