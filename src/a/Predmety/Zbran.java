package a.Predmety;

import a.Postavy.Hrac;

public class Zbran extends Predmet {
    private final int sila;

    public Zbran(String id, String meno, String popis, int hodnota, int sila) {
        super(id, meno, popis, hodnota);
        this.sila = sila;
    }

    public int getSila() {
        return this.sila;
    }

    @Override
    public void pouzitie(Hrac hrac) {
        System.out.println("Používaš zbraň: " + this.meno);
    }
}