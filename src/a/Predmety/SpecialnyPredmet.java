package a.Predmety;

import a.Postavy.Hrac;

/**
 * Trieda SpecialnyPredmet predstavuje predmet, ktory sluzi ako dokaz o splneni ulohy.
 * Dedi zo spolocnej abstraktnej triedy Predmet.
 */
public class SpecialnyPredmet extends Predmet {

    /**
     * Konstruktor vytvori specialny predmet s hodnotou 0.
     * @param id unikatny identifikator
     * @param meno nazov predmetu
     * @param popis popis predmetu
     */
    public SpecialnyPredmet(String id, String meno, String popis) {
        super(id, meno, popis, 0);
    }

    /**
     * Metoda pouzitie urcuje, co sa stane, ked hrac pouzije specialny predmet.
     * V tomto pripade sa len vypise informacia, ze predmet sluzi ako dokaz o splneni ulohy.
     * @param hrac hrac, ktory pouziva predmet
     */
    @Override
    public void pouzitie(Hrac hrac) {
        System.out.println("Tento predmet sluzi ako dokaz o splneni ulohy.");
    }
}