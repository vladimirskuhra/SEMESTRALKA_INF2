package a.Postavy.NPC.Nepriatelia;

import a.Manageri.BattleSystem;
import a.Miestnosti.Miestnost;
import a.Postavy.Hrac;
import a.Postavy.NPC.NPC;
import a.Postavy.Utocnik;
import a.Predmety.Predmet;
import a.Predmety.Zbran;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstraktna trieda Nepriatel predstavuje zaklad pre vsetky nepriatelske NPC v hre.
 * Umoznuje zastrasovanie, boj a pracu s inventarom, dedi zo spolocnej triedy NPC a implementuje rozhranie Utocnik.
 */
public abstract class Nepriatel extends NPC implements Utocnik {
    // Sanca, ze sa nepriatel necha zastrasit (0.0 - 1.0)
    protected double sancaNaZastrasenie;
    // True, ak je nepriatel odzbrojeny a nemoze utocit zbranou
    protected boolean jeOdzbrojeny = false;

    /**
     * Konstruktor nastavi vsetky zakladne atributy nepriatela.
     * @param id unikatny identifikator
     * @param meno meno nepriatela
     * @param popis popis nepriatela
     * @param miestnost miestnost, v ktorej sa nachadza
     * @param zdravie pociatocne zdravie
     * @param sila pociatocna sila
     * @param obrana pociatocna obrana
     * @param sancaNaZastrasenie pravdepodobnost, s ktorou sa necha zastrasit
     */
    public Nepriatel(String id, String meno, String popis, Miestnost miestnost,
                     int zdravie, int sila, int obrana, double sancaNaZastrasenie) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana);
        this.sancaNaZastrasenie = sancaNaZastrasenie;
    }

    /**
     * Spusti boj so vsetkymi zivotaschopnymi nepriatelmi v miestnosti.
     * Zlozitejsi algoritmus: prejde vsetky NPC v miestnosti, vyfiltruje zive nepriatelske NPC a spusti suboj cez BattleSystem.
     * @param hrac hrac
     * @param battleSystem system na riadenie boja
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
     * Spusti boj iba s tymto nepriatelom (napr. boss fight alebo specialny duel).
     * @param hrac hrac
     * @param battleSystem system na riadenie boja
     */
    public void zacniBojLenSoSebou(Hrac hrac, BattleSystem battleSystem) {
        List<Nepriatel> lenTento = new ArrayList<>();
        lenTento.add(this);
        battleSystem.boj(hrac, lenTento);
    }

    /**
     * Definuje zakladnu interakciu hraca s nepriatelom: vyvola boj so vsetkymi nepriatelmi v miestnosti.
     * @param hrac hrac
     * @param battleSystem system na riadenie boja
     */
    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        System.out.println(getMeno() + " na teba nepriatelsky zavrci!");
        System.out.println(getMeno() + " tasi zbran a chysta sa zautocit!");
        zacniBojSoVsetkymi(hrac, battleSystem);
    }

    /**
     * Presunie vsetky predmety z inventara nepriatela do miestnosti (napr. po smrti).
     */
    public void dropDoMiestnosti() {
        if (this.miestnost != null && this.inventar != null) {
            for (Predmet p : this.inventar.getPredmety()) {
                this.miestnost.pridajPredmet(p);
            }
            this.inventar.getPredmety().clear();
        }
    }

    /**
     * Pokusi sa zastrasit nepriatela. Ak uspeje, odzbroji ho a zbran padne do miestnosti.
     * @return true ak bol zastraeny, inak false
     */
    public boolean pokusZastrasenie() {
        double hod = Math.random();
        if (hod < this.sancaNaZastrasenie && !this.jeOdzbrojeny) {
            this.jeOdzbrojeny = true;
            System.out.println(getMeno() + " je zastraseny a pusta zbran!");
            if (this.miestnost != null && this.inventar != null) {
                Zbran zbran = this.inventar.odoberAktivnuZbran();
                if (zbran != null) this.miestnost.pridajPredmet(zbran);
            }
            return true;
        }
        System.out.println(getMeno() + " odolal zastraseniu.");
        return false;
    }

    /**
     * Utoci na ciel, ak nie je odzbrojeny a ma zbran. Inak nevie utocit.
     * @param ciel ciel utoku (implementuje Utocnik)
     */
    @Override
    public void utok(Utocnik ciel) {
        if (this.jeOdzbrojeny || this.inventar.getAktivnaZbran() == null) {
            System.out.println(getMeno() + " je odzbrojeny a nemoze zautocit!");
        } else {
            int damage = this.inventar.getAktivnaZbran().getSila() + this.sila;
            ciel.prijmiZasah(damage);
            System.out.println(getMeno() + " utoci so zbranou " + this.inventar.getAktivnaZbran().getMeno()
                    + " a sposobi " + damage + " skody!");
        }
    }

    /**
     * Zakladna obrana, prazdna implementacia (moze byt prepisana potomkami).
     */
    @Override
    public void obrana() {
        // Nepriatel nema specialnu obranu by default
    }

    /**
     * Prijme zasah od supera, vypocita efektivne zranenie a odcita zo zdravia. Ak zomrie, dropne inventar.
     * @param silaUtoku sila utoku supera
     */
    @Override
    public void prijmiZasah(int silaUtoku) {
        int zranenie = silaUtoku - this.obrana;
        if (zranenie > 0) {
            this.zdravie -= zranenie;
            System.out.println(getMeno() + " dostal zasah za " + zranenie + " (obrana " + this.obrana + ").");
            if (this.zdravie <= 0) {
                System.out.println(getMeno() + " zomiera!");
                for (Predmet p : this.inventar.getPredmety()) {
                    this.miestnost.pridajPredmet(p);
                }
                this.inventar.getPredmety().clear();
            }
        } else {
            System.out.println(getMeno() + " odrazil utok, ziadne zranenie.");
        }
    }
}