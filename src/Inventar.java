import java.util.ArrayList;
import java.util.List;

public class Inventar {
    private List<Predmet> predmety;
    private Zbran aktivnaZbran;
    private Brnenie aktivneBrnenie;

    public Inventar() {
        this.predmety = new ArrayList<>();
        this.aktivnaZbran = null;
        this.aktivneBrnenie = null;
    }

    public List<Predmet> getPredmety() {
        return predmety;
    }

    public void pridajPredmet(Predmet predmet) {
        predmety.add(predmet);

        // Automaticky nastav prvú zbraň/brnenie ako aktívne
        if (predmet instanceof Zbran && aktivnaZbran == null) {
            aktivnaZbran = (Zbran) predmet;
            System.out.println("Aktivoval si zbraň: " + predmet.getMeno());
        } else if (predmet instanceof Brnenie && aktivneBrnenie == null) {
            aktivneBrnenie = (Brnenie) predmet;
            System.out.println("Aktivoval si brnenie: " + predmet.getMeno());
        }
    }

    public void odstranPredmet(Predmet predmet) {
        if (predmet == aktivnaZbran) {
            aktivnaZbran = null;
        } else if (predmet == aktivneBrnenie) {
            aktivneBrnenie = null;
        }
        predmety.remove(predmet);
    }

    public Zbran getAktivnaZbran() {
        return aktivnaZbran;
    }

    public void nastavAktivnuZbran(Zbran zbran) {
        if (predmety.contains(zbran)) {
            this.aktivnaZbran = zbran;
            System.out.println("Aktivoval si zbraň: " + zbran.getMeno());
        } else {
            System.out.println("Túto zbraň nemáš v inventári!");
        }
    }

    public Brnenie getAktivneBrnenie() {
        return aktivneBrnenie;
    }

    public void nastavAktivneBrnenie(Brnenie brnenie) {
        if (predmety.contains(brnenie)) {
            this.aktivneBrnenie = brnenie;
            System.out.println("Aktivoval si brnenie: " + brnenie.getMeno());
        } else {
            System.out.println("Toto brnenie nemáš v inventári!");
        }
    }
}