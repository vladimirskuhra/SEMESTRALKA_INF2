package a.Predmety;

import java.util.ArrayList;
import java.util.List;

/**
 * Trieda Inventar sluzi na spravu predmetov, zbrane a brnenia, ktore ma hrac u seba.
 */
public class Inventar {
    // Zoznam vsetkych predmetov v inventari
    private final List<Predmet> predmety;
    // Aktualne aktivovana zbran
    private Zbran aktivnaZbran;
    // Aktualne aktivovane brnenie
    private Brnenie aktivneBrnenie;

    /**
     * Konstruktor vytvori prazdny inventar bez aktivnej zbrane a brnenia.
     */
    public Inventar() {
        this.predmety = new ArrayList<>();
        this.aktivnaZbran = null;
        this.aktivneBrnenie = null;
    }

    /**
     * Getter na zoznam predmetov v inventari.
     * @return zoznam vsetkych predmetov
     */
    public List<Predmet> getPredmety() {
        return this.predmety;
    }

    /**
     * Prida predmet do inventara.
     * Ak je predmet zbran a ziadna nie je aktivna, nastavi ju automaticky ako aktivnu.
     * Ak je predmet brnenie a ziadne nie je aktivne, nastavi ho automaticky ako aktivne.
     * @param predmet pridavany predmet
     */
    public void pridajPredmet(Predmet predmet) {
        this.predmety.add(predmet);
        if (predmet instanceof Zbran && this.aktivnaZbran == null) {
            this.aktivnaZbran = (Zbran) predmet;
            System.out.println("Aktivoval si zbran: " + predmet.getMeno());
        } else if (predmet instanceof Brnenie && this.aktivneBrnenie == null) {
            this.aktivneBrnenie = (Brnenie) predmet;
            System.out.println("Aktivoval si brnenie: " + predmet.getMeno());
        }
    }

    /**
     * Odstrani predmet z inventara.
     * Ak je predmet prave aktivna zbran alebo brnenie, tieto sa nastavia na null.
     * @param predmet predmet na odstranenie
     */
    public void odstranPredmet(Predmet predmet) {
        if (predmet == this.aktivnaZbran) this.aktivnaZbran = null;
        if (predmet == this.aktivneBrnenie) this.aktivneBrnenie = null;
        this.predmety.remove(predmet);
    }

    /**
     * Getter na aktivnu zbran.
     * @return aktualne aktivna zbran, alebo null ak nie je ziadna
     */
    public Zbran getAktivnaZbran() {
        return this.aktivnaZbran;
    }

    /**
     * Nastavi aktivnu zbran, ak sa nachadza v inventari.
     * Ak sa zbran v inventari nenachadza, vypise info hlasku.
     * @param zbran zbran na aktivovanie
     */
    public void nastavAktivnuZbran(Zbran zbran) {
        if (this.predmety.contains(zbran)) {
            this.aktivnaZbran = zbran;
            System.out.println("Aktivoval si zbran: " + zbran.getMeno());
        } else {
            System.out.println("Tuto zbran nemas v inventari!");
        }
    }

    /**
     * Getter na aktivne brnenie.
     * @return aktualne aktivne brnenie, alebo null ak nie je ziadne
     */
    public Brnenie getAktivneBrnenie() {
        return this.aktivneBrnenie;
    }

    /**
     * Nastavi aktivne brnenie, ak sa nachadza v inventari.
     * Ak sa brnenie v inventari nenachadza, vypise info hlasku.
     * @param brnenie brnenie na aktivovanie
     */
    public void nastavAktivneBrnenie(Brnenie brnenie) {
        if (this.predmety.contains(brnenie)) {
            this.aktivneBrnenie = brnenie;
            System.out.println("Aktivoval si brnenie: " + brnenie.getMeno());
        } else {
            System.out.println("Toto brnenie nemas v inventari!");
        }
    }

    /**
     * Odoberie a vrati aktivnu zbran z inventara.
     * Pouziva sa napriklad pri odzbrojeni hraca.
     * Vrati null, ak nie je ziadna zbran aktivna.
     * Zlozitejsi algoritmus: najprv skontroluje ci je aktivna zbran, potom ju odstrani z inventara a nastavi na null.
     * @return odobrata zbran alebo null ak nie je ziadna aktivna
     */
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