package a;

import java.util.ArrayList;
import java.util.List;

public abstract class Nepriatel extends NPC implements Utocnik {
    protected double sancaNaZastrasenie;
    protected boolean jeOdzbrojeny = false;

    public Nepriatel(String id, String meno, String popis, Miestnost miestnost,
                     int zdravie, int sila, int obrana, double sancaNaZastrasenie) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana);
        this.sancaNaZastrasenie = sancaNaZastrasenie;
    }

    /**
     * Boj so všetkými živými nepriateľmi v aktuálnej miestnosti
     */
    public void zacniBojSoVsetkymi(Hrac hrac, BattleSystem battleSystem) {
        List<Nepriatel> nepriatelia = new ArrayList<>();
        for (NPC npc : getMiestnost().getPostavy()) {
            if (npc instanceof Nepriatel && npc.getZdravie() > 0) {
                nepriatelia.add((Nepriatel) npc);
            }
        }
        battleSystem.boj(hrac, nepriatelia);
    }

    /**
     * Boj len s týmto nepriateľom (napr. boss fight, špeciálny duel)
     */
    public void zacniBojLenSoSebou(Hrac hrac, BattleSystem battleSystem) {
        List<Nepriatel> lenTento = new ArrayList<>();
        lenTento.add(this);
        battleSystem.boj(hrac, lenTento);
    }

    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        System.out.println(getMeno() + " na teba nepriateľsky zavrčí!");
        System.out.println(getMeno() + " tasí zbraň a chystá sa zaútočiť!");
        zacniBojSoVsetkymi(hrac, battleSystem);
    }

    public void dropDoMiestnosti() {
        if (this.miestnost != null && this.inventar != null) {
            for (Predmet p : this.inventar.getPredmety()) {
                this.miestnost.pridajPredmet(p);
            }
            this.inventar.getPredmety().clear();
        }
    }

    public boolean pokusZastrasenie() {
        double hod = Math.random();
        if (hod < this.sancaNaZastrasenie && !this.jeOdzbrojeny) {
            this.jeOdzbrojeny = true;
            System.out.println(getMeno() + " je zastrašený a púšťa zbraň!");
            if (this.miestnost != null && this.inventar != null) {
                Zbran zbran = this.inventar.odoberAktivnuZbran();
                if (zbran != null) this.miestnost.pridajPredmet(zbran);
            }
            return true;
        }
        System.out.println(getMeno() + " odolal zastrašeniu.");
        return false;
    }

    @Override
    public void utok(Utocnik ciel) {
        if (this.jeOdzbrojeny || this.inventar.getAktivnaZbran() == null) {
            System.out.println(getMeno() + " je odzbrojený a nemôže zaútočiť!");
        } else {
            int damage = this.inventar.getAktivnaZbran().getSila() + this.sila;
            ciel.prijmiZasah(damage);
            System.out.println(getMeno() + " útočí so zbraňou " + this.inventar.getAktivnaZbran().getMeno()
                    + " a spôsobí " + damage + " škody!");
        }
    }

    @Override
    public void obrana() {
    }

    @Override
    public void prijmiZasah(int silaUtoku) {
        int zranenie = silaUtoku - this.obrana;
        if (zranenie > 0) {
            this.zdravie -= zranenie;
            System.out.println(getMeno() + " dostal zásah za " + zranenie + " (obrana " + this.obrana + ").");
            if (this.zdravie <= 0) {
                System.out.println(getMeno() + " zomiera!");
                for (Predmet p : this.inventar.getPredmety()) {
                    this.miestnost.pridajPredmet(p);
                }
                this.inventar.getPredmety().clear();
            }
        } else {
            System.out.println(getMeno() + " odrazil útok, žiadne zranenie.");
        }
    }
}