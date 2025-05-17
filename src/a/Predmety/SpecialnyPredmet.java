package a.Predmety;

import a.Postavy.Hrac;

public class SpecialnyPredmet extends Predmet {
    public SpecialnyPredmet(String id, String meno, String popis) {
        super(id, meno, popis, 0);
    }

    @Override
    public void pouzitie(Hrac hrac) {
        System.out.println("Tento predmet slúži ako dôkaz o splnení úlohy.");
    }
}