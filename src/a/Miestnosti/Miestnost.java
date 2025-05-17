package a.Miestnosti;

import a.Predmety.Brnenie;
import a.Predmety.Lektvar;
import a.Predmety.Predmet;
import a.Postavy.NPC.NPC;
import a.Postavy.Hrac;
import a.Manageri.BattleSystem;
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

    public String getId() { return id; }
    public String getMeno() { return meno; }
    public String getPopis() { return popis; }
    public List<NPC> getPostavy() { return postavy; }
    public List<Predmet> getPredmety() { return predmety; }
    public Map<String, Miestnost> getVychody() { return vychody; }

    public void pridajVychod(String smer, Miestnost miestnost) {
        vychody.put(smer, miestnost);
    }
    public void pridajPostavu(NPC postava) {
        postavy.add(postava);
        postava.setMiestnost(this);
    }
    public void pridajPredmet(Predmet predmet) {
        predmety.add(predmet);
    }
    public void odstranPredmet(Predmet predmet) {
        predmety.remove(predmet);
    }

    // Každá miestnosť musí implementovať svoju interakciu a prehľadávanie
    public abstract void interakcia(Hrac hrac, BattleSystem battleSystem);
    public abstract void prehladat(Hrac hrac);

    // Helper pre získanie náhodného predmetu
    protected Predmet vytvorNahodnyPredmet() {
        int nahoda = (int) (Math.random() * 3);
        switch (nahoda) {
            case 0: return new Zbran("zbran_random", "Malá dýka", "Ostrý gobliní nôž", 6, 6);
            case 1: return new Brnenie("brnenie_random", "Kožená vesta", "Jednoduchá ochrana", 3, 2);
            case 2:
            default: return new Lektvar("lektvar_random", "Malý lektvar", "Liečivý elixír", 15);
        }
    }
}