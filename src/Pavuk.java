import java.util.Random;

public class Pavuk extends Nepriatel {
    public Pavuk(String id, String meno, String popis, Miestnost miestnost, int zdravie, int sila, int obrana, double sancaNaZastrasenie) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana, sancaNaZastrasenie);
    }

    @Override
    public void obrana() {
        System.out.println(getMeno() + " sa stiahne a pripraví na obranu!");
        this.docasnaObrana = 4; // zvýšená obrana na jedno kolo
    }

    @Override
    public void interakcia(Hrac hrac) {
        System.out.println(getMeno() + " momentálne nemá náladu na rozprávanie.");
    }

    @Override
    public void utok(Utocnik ciel) {
        if (ciel instanceof Hrac) {
            Hrac hrac = (Hrac) ciel;
            System.out.println(getMeno() + " zaútočí jedovatým hryzom!");
            int damage = new Random().nextInt(5) + 2 + this.sila; // napr. 2-6 plus sila
            hrac.prijmiZasah(damage);
            if (new Random().nextInt(100) < 20) { // 20% šanca na otravu
                System.out.println(hrac.getMeno() + " je otrávený!");
                // Tu môžeš implementovať efekt jedu
            }
        }
    }
}