import java.util.Random;

public class Goblin extends Nepriatel {
    public Goblin(String id, String meno, String popis, Miestnost miestnost, int zdravie, int sila, int obrana, double sancaNaZastrasenie) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana, sancaNaZastrasenie);
        // Môžeš pridať do inventára zbraň/prípadne iné predmety
    }

    @Override
    public void interakcia(Hrac hrac) {
        System.out.println(getMeno() + " na teba nepriateľsky zavrčí!");
        System.out.println("Goblin tasí svoju zbraň a chystá sa zaútočiť!");
        // Tu môže byť logika na spustenie boja mimo tejto triedy, alebo iba info
    }

    @Override
    public void utok(Utocnik ciel) {
        // Goblin útočí, použije aktívnu zbraň ak má, inak základnú silu
        int damage = sila;
        if (inventar.getAktivnaZbran() != null) {
            damage += inventar.getAktivnaZbran().getSila();
            System.out.println(getMeno() + " útočí so zbraňou " + inventar.getAktivnaZbran().getMeno() + " a spôsobí " + damage + " škody!");
        } else {
            System.out.println(getMeno() + " útočí holými rukami a spôsobí " + damage + " škody!");
        }
        ciel.prijmiZasah(damage);
    }

    @Override
    public void obrana() {
        System.out.println(getMeno() + " sa pripravuje na obranu!");
        // Prípadne zvýšenie obrany na jedno kolo
    }
}