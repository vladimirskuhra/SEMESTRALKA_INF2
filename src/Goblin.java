import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Goblin extends Nepriatel implements InteraktivnaPostava {
    public Goblin(String id, String meno, String popis, Miestnost miestnost, int zdravie, int sila, int obrana, double sancaNaZastrasenie) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana, sancaNaZastrasenie);
    }

    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        System.out.println(getMeno() + " na teba nepriateľsky zavrčí!");
        System.out.println("Goblin tasí svoju zbraň a chystá sa zaútočiť!");
        // Spusti boj so všetkými nepriateľmi v miestnosti
        List<Nepriatel> nepriateliaVMiestnosti = new ArrayList<>();
        for (NPC npc : getMiestnost().getPostavy()) {
            if (npc instanceof Nepriatel && npc.getZdravie() > 0) {
                nepriateliaVMiestnosti.add((Nepriatel) npc);
            }
        }
        battleSystem.boj(hrac, nepriateliaVMiestnosti);
    }

    @Override
    public void utok(Utocnik ciel) {
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
    }
}