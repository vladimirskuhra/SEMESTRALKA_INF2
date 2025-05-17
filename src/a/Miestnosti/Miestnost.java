package a.Miestnosti;

import a.Manageri.BattleSystem;
import a.Postavy.Hrac;
import a.Postavy.NPC.NPC;
import a.Predmety.Brnenie;
import a.Predmety.Lektvar;
import a.Predmety.Predmet;
import a.Predmety.Zbran;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Miestnost {
    protected final String id;
    protected final String meno;
    protected final String popis;
    protected final List<NPC> postavy = new ArrayList<>();
    protected final List<Predmet> predmety = new ArrayList<>();
    protected boolean prehladana = false;
    protected final Map<String, Miestnost> vychody = new HashMap<>();

    public Miestnost(String id, String meno, String popis) {
        this.id = id;
        this.meno = meno;
        this.popis = popis;
    }

    public String getId() {
        return this.id;
    }

    public String getMeno() {
        return this.meno;
    }

    public String getPopis() {
        return this.popis;
    }

    public List<NPC> getPostavy() {
        return this.postavy;
    }

    public List<Predmet> getPredmety() {
        return this.predmety;
    }

    public Map<String, Miestnost> getVychody() {
        return this.vychody;
    }

    public void pridajVychod(String smer, Miestnost miestnost) {
        this.vychody.put(smer, miestnost);
    }

    public void pridajPostavu(NPC postava) {
        this.postavy.add(postava);
        postava.setMiestnost(this);
    }

    public void pridajPredmet(Predmet predmet) {
        this.predmety.add(predmet);
    }

    public void odstranPredmet(Predmet predmet) {
        this.predmety.remove(predmet);
    }

    // Polymorfné metódy
    public abstract void interakcia(Hrac hrac, BattleSystem battleSystem);

    public abstract void prehladat(Hrac hrac);

    // Helper pre získanie náhodného predmetu
    protected Predmet vytvorNahodnyPredmet() {
        int nahoda = (int) (Math.random() * 3);
        switch (nahoda) {
            case 0:
                return new Zbran("zbran_random", "Malá dýka", "Ostrý gobliní nôž", 6, 6);
            case 1:
                return new Brnenie("brnenie_random", "Kožená vesta", "Jednoduchá ochrana", 3, 2);
            case 2:
            default:
                return new Lektvar("lektvar_random", "Malý lektvar", "Liečivý elixír", 15);
        }
    }
}