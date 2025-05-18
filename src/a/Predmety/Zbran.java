package a.Predmety;

import a.Postavy.Hrac;

/**
 * Trieda Zbran reprezentuje predmet, ktory sluzi na utok a zvysuje silu hraca.
 * Dedi zo spolocnej abstraktnej triedy Predmet.
 */
public class Zbran extends Predmet {
    // Hodnota sily, ktoru zbran poskytuje
    private final int sila;

    /**
     * Konstruktor nastavi vsetky atributy zbrane.
     * @param id unikatny identifikator
     * @param meno nazov zbrane
     * @param popis popis zbrane
     * @param hodnota hodnota zbrane (napr. cena)
     * @param sila hodnota utoku, ktoru zbran poskytuje
     */
    public Zbran(String id, String meno, String popis, int hodnota, int sila) {
        super(id, meno, popis, hodnota);
        this.sila = sila;
    }

    /**
     * Getter na hodnotu sily zbrane.
     * @return hodnota sily
     */
    public int getSila() {
        return this.sila;
    }

    /**
     * Metoda pouzitie urcuje, co sa stane, ked hrac pouzije zbran.
     * V tomto pripade iba vypise spravu o pouziti zbrane.
     * @param hrac hrac, ktory pouziva zbran
     */
    @Override
    public void pouzitie(Hrac hrac) {
        System.out.println("Pouzivas zbran: " + this.meno);
    }
}