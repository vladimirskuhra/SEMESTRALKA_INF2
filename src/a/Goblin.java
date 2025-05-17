package a;

public class Goblin extends Nepriatel implements InteraktivnaPostava {
    public Goblin(String id, String meno, String popis, Miestnost miestnost, int zdravie, int sila, int obrana, double sancaNaZastrasenie) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana, sancaNaZastrasenie);
    }

    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        System.out.println(getMeno() + " na teba nepriateľsky zavrčí!");
        System.out.println(getMeno() + " tasí svoju zbraň a chystá sa zaútočiť!");
        zacniBojSoVsetkymi(hrac, battleSystem);
    }

    @Override
    public void utok(Utocnik ciel) {
        int damage = this.sila;
        if (this.inventar.getAktivnaZbran() != null) {
            damage += this.inventar.getAktivnaZbran().getSila();
            System.out.println(getMeno() + " útočí so zbraňou " + this.inventar.getAktivnaZbran().getMeno() + " a spôsobí " + damage + " škody!");
        } else {
            System.out.println(getMeno() + " útočí holými rukami a spôsobí " + damage + " škody!");
        }
        ciel.prijmiZasah(damage);
    }

    @Override
    public void obrana() {
        System.out.println(getMeno() + " sa pripravuje na obranu!");
    }
}