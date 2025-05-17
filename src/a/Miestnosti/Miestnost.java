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

/**
 * Abstraktna trieda Miestnost reprezentuje zakladnu logiku miestnosti v dungeone.
 * Sluzi ako spolocny predok pre vsetky typy miestnosti (chodba, pokladova, hadankova, atd.).
 *
 * Preco je to takto navrhnute:
 * - Polia su chranene (protected), aby boli dostupne v podtriedach, ale nie zvonka.
 * - Obsahuje vsetky spolocne atributy miestnosti: id, meno, popis, postavy, predmety, prehladana, vychody.
 * - Interakcia a prehladanie su abstraktne, pretoze kazda miestnost ma vlastne spravanie.
 * - Prepojitelnost: Miestnosti su prepojene cez mapu vychodov (vychody).
 *
 * Priklady OOP:
 * - Polymorfizmus: kazda miestnost implementuje vlastnu verziu interakcie a prehladavania.
 * - Zapuzdrenie: praca s predmetmi, postavami a vychodmi prebieha cez metody.
 */
public abstract class Miestnost {
    // Jedinecny identifikator miestnosti (napr. "m1")
    protected final String id;
    // Nazov miestnosti (napr. "Chodba", "Tronna sien")
    protected final String meno;
    // Popis miestnosti pre hraca
    protected final String popis;
    // Zoznam postav (NPC) nachadzajucich sa v miestnosti
    protected final List<NPC> postavy = new ArrayList<>();
    // Zoznam predmetov v miestnosti
    protected final List<Predmet> predmety = new ArrayList<>();
    // Priznak, ci uz bola miestnost prehladana
    protected boolean prehladana = false;
    // Mapa vychodov z miestnosti (napr. "sever" -> miestnost)
    protected final Map<String, Miestnost> vychody = new HashMap<>();

    /**
     * Konstruktor nastavi zakladne vlastnosti miestnosti.
     * @param id unikatny identifikator
     * @param meno nazov miestnosti
     * @param popis popis miestnosti
     */
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

    /**
     * Prida vychod z tejto miestnosti do inej.
     * @param smer nazov vychodu (napr. "sever")
     * @param miestnost cielova miestnost
     */
    public void pridajVychod(String smer, Miestnost miestnost) {
        vychody.put(smer, miestnost);
    }

    /**
     * Prida postavu do miestnosti a nastavi jej aktualnu miestnost.
     */
    public void pridajPostavu(NPC postava) {
        postavy.add(postava);
        postava.setMiestnost(this);
    }

    /**
     * Prida predmet do miestnosti.
     */
    public void pridajPredmet(Predmet predmet) {
        predmety.add(predmet);
    }

    /**
     * Odstrani predmet z miestnosti.
     */
    public void odstranPredmet(Predmet predmet) {
        predmety.remove(predmet);
    }

    /**
     * Kazda miestnost MUSI implementovat vlastnu interakciu s hracom (napr. dialog, boj, hadanka ...).
     */
    public abstract void interakcia(Hrac hrac, BattleSystem battleSystem);

    /**
     * Kazda miestnost MUSI implementovat vlastne prehladavanie (napr. co sa stane, ked hrac hlada poklady).
     */
    public abstract void prehladat(Hrac hrac);

    /**
     * Helper metoda pre generovanie nahodneho predmetu.
     * Pouziva sa pri prehladavani alebo ako odmena za suboj.
     */
    protected Predmet vytvorNahodnyPredmet() {
        int nahoda = (int) (Math.random() * 3);
        switch (nahoda) {
            case 0: return new Zbran("zbran_random", "Mala dyka", "Ostry goblini noz", 6, 6);
            case 1: return new Brnenie("brnenie_random", "Kozena vesta", "Jednoducha ochrana", 3, 2);
            case 2:
            default: return new Lektvar("lektvar_random", "Maly lektvar", "Liecivy elixir", 15);
        }
    }
}