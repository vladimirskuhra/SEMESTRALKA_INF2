package a.Predmety;

import java.util.ArrayList;
import java.util.List;

public class Inventar {
    private final List<Predmet> predmety;
    private Zbran aktivnaZbran;
    private Brnenie aktivneBrnenie;

    public Inventar() {
        this.predmety = new ArrayList<>();
        this.aktivnaZbran = null;
        this.aktivneBrnenie = null;
    }

    public List<Predmet> getPredmety() {
        return this.predmety;
    }

    public void pridajPredmet(Predmet predmet) {
        this.predmety.add(predmet);
        if (predmet instanceof Zbran && this.aktivnaZbran == null) {
            this.aktivnaZbran = (Zbran) predmet;
            System.out.println("Aktivoval si zbraň: " + predmet.getMeno());
        } else if (predmet instanceof Brnenie && this.aktivneBrnenie == null) {
            this.aktivneBrnenie = (Brnenie) predmet;
            System.out.println("Aktivoval si brnenie: " + predmet.getMeno());
        }
    }

    public void odstranPredmet(Predmet predmet) {
        if (predmet == this.aktivnaZbran) this.aktivnaZbran = null;
        if (predmet == this.aktivneBrnenie) this.aktivneBrnenie = null;
        this.predmety.remove(predmet);
    }

    public Zbran getAktivnaZbran() {
        return this.aktivnaZbran;
    }

    public void nastavAktivnuZbran(Zbran zbran) {
        if (this.predmety.contains(zbran)) {
            this.aktivnaZbran = zbran;
            System.out.println("Aktivoval si zbraň: " + zbran.getMeno());
        } else {
            System.out.println("Túto zbraň nemáš v inventári!");
        }
    }

    public Brnenie getAktivneBrnenie() {
        return this.aktivneBrnenie;
    }

    public void nastavAktivneBrnenie(Brnenie brnenie) {
        if (this.predmety.contains(brnenie)) {
            this.aktivneBrnenie = brnenie;
            System.out.println("Aktivoval si brnenie: " + brnenie.getMeno());
        } else {
            System.out.println("Toto brnenie nemáš v inventári!");
        }
    }

    // Vrátí a odstráni aktívnu zbraň (napr. pri odzbrojení)
    public Zbran odoberAktivnuZbran() {
        if (this.aktivnaZbran != null) {
            Zbran z = this.aktivnaZbran;
            this.predmety.remove(z);
            this.aktivnaZbran = null;
            return z;
        }
        return null;
    }
}