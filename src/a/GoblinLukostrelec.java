package a;

import java.util.Random;

public class GoblinLukostrelec extends Nepriatel {
    public GoblinLukostrelec(String id, String meno, String popis, Miestnost miestnost, int zdravie, int sila, int obrana, double sancaNaZastrasenie) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana, sancaNaZastrasenie);
    }


    @Override
    public void obrana() {
        System.out.println(getMeno() + " ustupuje do bezpečnejšej pozície!");
        this.docasnaObrana = 5; // zvýšená obrana na jedno kolo
    }

    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        System.out.println(getMeno() + " niečo zamrmlal v goblinštine, ale nerozumieš mu.");
    }


    @Override
    public void utok(Utocnik ciel) {
        if (ciel instanceof Hrac hrac) {
            System.out.println(getMeno() + " vystrelí šíp na " + hrac.getMeno() + "!");
            int damage = new Random().nextInt(6) + 4 + this.sila; // napr. 4-9 plus sila
            hrac.prijmiZasah(damage);
        }
    }
}