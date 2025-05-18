package a.Postavy.NPC.Nepriatelia;

import a.Manageri.BattleSystem;
import a.Miestnosti.Miestnost;
import a.Postavy.Hrac;
import a.Postavy.Utocnik;

import java.util.Random;

/**
 * Trieda GoblinLukostrelec predstavuje specialneho nepriatela - goblina lukostrelca.
 * Dedi zo spolocnej triedy Nepriatel a implementuje vlastne spravanie pre utok aj obranu.
 */
public class GoblinLukostrelec extends Nepriatel {

    /**
     * Konstruktor pre goblina lukostrelca, nastavi vsetky atributy.
     * @param id unikatny identifikator goblina lukostrelca
     * @param meno meno goblina lukostrelca
     * @param popis popis goblina lukostrelca
     * @param miestnost miestnost, v ktorej sa nepriatel nachadza
     * @param zdravie pociatocne zdravie
     * @param sila pociatocna sila
     * @param obrana pociatocna obrana
     * @param sancaNaZastrasenie pravdepodobnost, s ktorou sa necha zastrasit
     */
    public GoblinLukostrelec(String id, String meno, String popis, Miestnost miestnost, int zdravie, int sila, int obrana, double sancaNaZastrasenie) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana, sancaNaZastrasenie);
    }

    /**
     * Goblin lukostrelec sa brani tak, ze ustupi a na jedno kolo si zvysi obranu.
     */
    @Override
    public void obrana() {
        System.out.println(getMeno() + " ustupuje do bezpecnejsej pozicie!");
        this.docasnaObrana = 5; // zvysena obrana na jedno kolo
    }

    /**
     * Interakcia hraca s goblinom lukostrelcom - iba nieco zamrmle, nebojuje priamo na zaciatku.
     * @param hrac hrac
     * @param battleSystem system na riadenie boja
     */
    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        System.out.println(getMeno() + " nieco zamrmlal v goblinstine, ale nerozumies mu.");
    }

    /**
     * Goblin lukostrelec utoci vystrelenim sipu na ciel. Sposobuje nahodne zranenie plus svoju silu.
     * @param ciel ciel utoku
     */
    @Override
    public void utok(Utocnik ciel) {
        if (ciel instanceof Hrac hrac) {
            System.out.println(getMeno() + " vystreli sip na " + hrac.getMeno() + "!");
            int damage = new Random().nextInt(6) + 4 + this.sila; // 4-9 plus sila
            hrac.prijmiZasah(damage);
        }
    }
}