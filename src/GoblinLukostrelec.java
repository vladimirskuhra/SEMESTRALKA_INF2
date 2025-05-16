import java.util.Random;

public class GoblinLukostrelec extends Nepriatel {
    public GoblinLukostrelec(String id, String meno, String popis, Miestnost miestnost, int zdravie, int sila, int obrana, double sancaNaZastrasenie) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana, sancaNaZastrasenie);
    }

    // V tejto hre nie je interaktívna postava – boj sa riadi BattleSystemom!
    // Ak chceš, môžeš doplniť špeciálne AI v BattleSystem, alebo tu vlastné metódy
    @Override
    public void obrana() {
        System.out.println(getMeno() + " ustupuje do bezpečnejšej pozície!");
        this.docasnaObrana = 5; // zvýšená obrana na jedno kolo
    }

    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        System.out.println(getMeno() + " niečo zamrmlal v goblinštine, ale nerozumieš mu.");
    }

    // Ak chceš špeciálnu logiku útoku, override utok()
    @Override
    public void utok(Utocnik ciel) {
        if (ciel instanceof Hrac) {
            Hrac hrac = (Hrac) ciel;
            System.out.println(getMeno() + " vystrelí šíp na " + hrac.getMeno() + "!");
            int damage = new Random().nextInt(6) + 4 + this.sila; // napr. 4-9 plus sila
            hrac.prijmiZasah(damage);
        }
    }
}