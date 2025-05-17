package a.Postavy;

import a.Manageri.Quest.Quest;
import a.Miestnosti.Miestnost;

import java.util.ArrayList;
import java.util.List;

public class Hrac extends Charakter implements Utocnik {
    private int level;
    private int exp;
    private int expNaLevel;
    private final List<Quest> aktivneQuesty;

    public Hrac(String id, String meno, String popis, Miestnost miestnost, int zdravie, int sila, int obrana) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana);
        this.level = 1;
        this.exp = 0;
        this.expNaLevel = 100;
        this.aktivneQuesty = new ArrayList<>();
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public void setZdravie(int zdravie) {
        this.zdravie = zdravie;
    }

    public void setSila(int sila) {
        this.sila = sila;
    }

    public void setObrana(int obrana) {
        this.obrana = obrana;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }

    public List<Quest> getAktivneQuesty() {
        return this.aktivneQuesty;
    }

    public void prijmiQuest(Quest quest) {
        this.aktivneQuesty.add(quest);
        System.out.println("Prijal si úlohu: " + quest.getNazov());
    }

    public void pridajExp(int kolko) {
        this.exp += kolko;
        System.out.println("Získal si " + kolko + " skúseností. (Aktuálne: " + this.exp + "/" + this.expNaLevel + ")");
        while (this.exp >= this.expNaLevel) {
            this.exp -= this.expNaLevel;
            this.level++;
            this.expNaLevel += 50; // môžeš upraviť podľa potreby
            System.out.println("LEVEL UP! Nový level: " + this.level);
            this.zdravie += 10;
            this.sila += 2;
            this.obrana += 2;
            System.out.println("Získal si: +10 zdravie, +2 sila, +2 obrana.");
        }
    }

    @Override
    public void utok(Utocnik ciel) {
        int damage = this.sila;
        if (this.inventar.getAktivnaZbran() != null) {
            damage += this.inventar.getAktivnaZbran().getSila();
            System.out.println(getMeno() + " útočí so zbraňou " + this.inventar.getAktivnaZbran().getMeno() + " a spôsobí " + damage + " škody!");
        } else {
            System.out.println("Nemáš žiadnu zbraň, útočíš holými rukami a spôsobíš " + damage + " škody!");
        }
        ciel.prijmiZasah(damage);
    }

    @Override
    public void obrana() {
        System.out.println(getMeno() + " zdvíha svoj štít na obranu!");
        this.docasnaObrana = 5; // +5 obrana na jedno kolo wohoo
    }

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
            System.out.println(getMeno() + " dostal zásah za " + zranenie + " (obrana " + efektivnaObrana + ").");
            if (this.zdravie <= 0) {
                System.out.println(getMeno() + " zomiera! Game Over.");
            }
        } else {
            System.out.println(getMeno() + " odrazil útok, žiadne zranenie.");
        }
        this.docasnaObrana = 0;
    }
}