package a.Postavy;

import a.Manageri.Quest.Quest;
import a.Miestnosti.Miestnost;

import java.util.ArrayList;
import java.util.List;

/**
 * Trieda Hrac reprezentuje hraca v hre. Dedi zo spolocnej abstraktnej triedy Charakter a implementuje rozhranie Utocnik.
 * Obsahuje vlastnosti pre levelovanie, skusenosti a spravu questov.
 */
public class Hrac extends Charakter implements Utocnik {
    // Aktuálny level hraca
    private int level;
    // Aktualny pocet skusenosti
    private int exp;
    // Pocet skusenosti potrebnych na dalsi level
    private int expNaLevel;
    // Zoznam aktivnych questov
    private final List<Quest> aktivneQuesty;

    /**
     * Konstruktor vytvori hraca s nastavenymi atributmi a prazdnym zoznamom questov.
     * @param id unikatny identifikator hraca
     * @param meno meno hraca
     * @param popis popis hraca
     * @param miestnost miestnost, v ktorej hrac zacina
     * @param zdravie pociatocne zdravie
     * @param sila pociatocna sila
     * @param obrana pociatocna obrana
     */
    public Hrac(String id, String meno, String popis, Miestnost miestnost, int zdravie, int sila, int obrana) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana);
        this.level = 1;
        this.exp = 0;
        this.expNaLevel = 100;
        this.aktivneQuesty = new ArrayList<>();
    }

    /**
     * Setter na meno hraca.
     * @param meno nove meno hraca
     */
    public void setMeno(String meno) {
        this.meno = meno;
    }

    /**
     * Setter na zdravie hraca.
     * @param zdravie nova hodnota zdravia
     */
    public void setZdravie(int zdravie) {
        this.zdravie = zdravie;
    }

    /**
     * Setter na silu hraca.
     * @param sila nova hodnota sily
     */
    public void setSila(int sila) {
        this.sila = sila;
    }

    /**
     * Setter na obranu hraca.
     * @param obrana nova hodnota obrany
     */
    public void setObrana(int obrana) {
        this.obrana = obrana;
    }

    /**
     * Setter na level hraca.
     * @param level novy level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Getter na aktualny level hraca.
     * @return level hraca
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Getter na zoznam aktivnych questov.
     * @return zoznam aktivnych questov
     */
    public List<Quest> getAktivneQuesty() {
        return this.aktivneQuesty;
    }

    /**
     * Prijme novy quest a prida ho do zoznamu aktivnych questov.
     * @param quest quest na prijatie
     */
    public void prijmiQuest(Quest quest) {
        this.aktivneQuesty.add(quest);
        System.out.println("Prijal si ulohu: " + quest.getNazov());
    }

    /**
     * Prida hracovi skusenosti. Ak ich ma dost na level up, zvysi level, zresetuje exp a vylepsi atributy.
     * Zlozitejsi algoritmus: Opakovane kontroluje, ci exp presiahli hranicu na dalsi level, a vykonava level up.
     * @param kolko pocet skusenosti na pridanie
     */
    public void pridajExp(int kolko) {
        this.exp += kolko;
        System.out.println("Ziskal si " + kolko + " skusenosti. (Aktualne: " + this.exp + "/" + this.expNaLevel + ")");
        while (this.exp >= this.expNaLevel) {
            this.exp -= this.expNaLevel;
            this.level++;
            this.expNaLevel += 50; // zvysuje narok pre dalsi level
            System.out.println("LEVEL UP! Novy level: " + this.level);
            this.zdravie += 10;
            this.sila += 2;
            this.obrana += 2;
            System.out.println("Ziskal si: +10 zdravie, +2 sila, +2 obrana.");
        }
    }

    /**
     * Utoci na ciel (implementuje rozhranie Utocnik).
     * Ak ma aktivnu zbran, pridava jej silu k utoku, inak utoci holymi rukami.
     * @param ciel ciel utoku
     */
    @Override
    public void utok(Utocnik ciel) {
        int damage = this.sila;
        if (this.inventar.getAktivnaZbran() != null) {
            damage += this.inventar.getAktivnaZbran().getSila();
            System.out.println(getMeno() + " utoci so zbranou " + this.inventar.getAktivnaZbran().getMeno() + " a sposobi " + damage + " skody!");
        } else {
            System.out.println("Nemas ziadnu zbran, utocis holymi rukami a sposobis " + damage + " skody!");
        }
        ciel.prijmiZasah(damage);
    }

    /**
     * Aktivuje obranu na jedno kolo (docasne zvysi obranu).
     */
    @Override
    public void obrana() {
        System.out.println(getMeno() + " zdviha svoj stit na obranu!");
        this.docasnaObrana = 5; // +5 obrana na jedno kolo
    }

    /**
     * Prijme zasah od supera, vypocita efektivnu obranu a odcita zranenie zo zdravia.
     * Ak ma aktivne brnenie, prirata jeho obranu. Vypise vysledok utoku.
     * Zlozitejsi algoritmus: vypocet efektivnej obrany, aplikovanie docasnej obrany, vypocet zranenia, smrt hraca.
     * @param silaUtoku sila utoku supera
     */
    @Override
    public void prijmiZasah(int silaUtoku) {
        int obranaHodnota = this.obrana;
        int efektivnaObrana = obranaHodnota + this.docasnaObrana;
        if (this.inventar.getAktivneBrnenie() != null) {
            obranaHodnota += this.inventar.getAktivneBrnenie().getObrana();
        }
        int zranenie = silaUtoku - efektivnaObrana;
        if (zranenie > 0) {
            this.zdravie -= zranenie;
            System.out.println(getMeno() + " dostal zasah za " + zranenie + " (obrana " + efektivnaObrana + ").");
            if (this.zdravie <= 0) {
                System.out.println(getMeno() + " zomiera! Game Over.");
            }
        } else {
            System.out.println(getMeno() + " odrazil utok, ziadne zranenie.");
        }
        this.docasnaObrana = 0;
    }
}