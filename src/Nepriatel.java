import java.util.Random;

public abstract class Nepriatel extends NPC {
    protected double sancaNaZastrasenie;
    protected boolean jeOdzbrojeny = false;

    public Nepriatel(String id, String meno, String popis, Miestnost miestnost,
                     int zdravie, int sila, int obrana, double sancaNaZastrasenie) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana);
        this.sancaNaZastrasenie = sancaNaZastrasenie;
    }

    public void dropDoMiestnosti() {
        if (miestnost != null && inventar != null) {
            for (Predmet p : inventar.getPredmety()) {
                miestnost.pridajPredmet(p);
            }
            inventar.getPredmety().clear();
        }
    }

    public boolean pokusZastrasenie() {
        double hod = Math.random();
        if (hod < sancaNaZastrasenie && !jeOdzbrojeny) {
            jeOdzbrojeny = true;
            System.out.println(getMeno() + " je zastrašený a púšťa zbraň!");
            if (miestnost != null && inventar != null) {
                Zbran zbran = inventar.odoberAktivnuZbran();
                if (zbran != null) miestnost.pridajPredmet(zbran);
            }
            return true;
        }
        System.out.println(getMeno() + " odolal zastrašeniu.");
        return false;
    }

    @Override
    public void utok(Utocnik ciel) {
        if (jeOdzbrojeny || inventar.getAktivnaZbran() == null) {
            System.out.println(getMeno() + " je odzbrojený a nemôže zaútočiť!");
        } else {
            int damage = inventar.getAktivnaZbran().getSila() + sila;
            ciel.prijmiZasah(damage);
            System.out.println(getMeno() + " útočí so zbraňou " + inventar.getAktivnaZbran().getMeno()
                    + " a spôsobí " + damage + " škody!");
        }
    }

    @Override
    public void obrana() {
        // Možné override v potomkoch
    }

    @Override
    public void prijmiZasah(int silaUtoku) {
        int zranenie = silaUtoku - obrana;
        if (zranenie > 0) {
            zdravie -= zranenie;
            System.out.println(getMeno() + " dostal zásah za " + zranenie + " (obrana " + obrana + ").");
            if (zdravie <= 0) {
                System.out.println(getMeno() + " zomiera!");
                // Drop všetkých predmetov do miestnosti
                for (Predmet p : inventar.getPredmety()) {
                    miestnost.pridajPredmet(p);
                }
                inventar.getPredmety().clear();
            }
        } else {
            System.out.println(getMeno() + " odrazil útok, žiadne zranenie.");
        }
    }
}