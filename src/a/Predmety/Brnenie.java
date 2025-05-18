package a.Predmety;

import a.Postavy.Hrac;

/**
 * Trieda Brnenie reprezentuje ochranny predmet, ktory moze hrac pouzit pre zvysenie obrany.
 * Dedi zo spolocnej abstraktnej triedy Predmet.
 */
public class Brnenie extends Predmet {
    // Hodnota obrany, ktoru brnenie poskytuje
    private final int obrana;

    /**
     * Konstruktor nastavi vsetky atributy brnenia.
     * @param id unikatny identifikator
     * @param meno nazov brnenia
     * @param popis popis brnenia
     * @param hodnota hodnota brnenia (napr. cena)
     * @param obrana hodnota obrany, ktoru brnenie poskytuje
     */
    public Brnenie(String id, String meno, String popis, int hodnota, int obrana) {
        super(id, meno, popis, hodnota);
        this.obrana = obrana;
    }

    /**
     * Getter na hodnotu obrany brnenia.
     * @return hodnota obrany
     */
    public int getObrana() {
        return this.obrana;
    }

    /**
     * Metoda pouzitie urcuje, co sa stane, ked hrac pouzije brnenie.
     * V tomto pripade iba vypise spravu o obliekani brnenia.
     * @param hrac hrac, ktory pouziva brnenie
     */
    @Override
    public void pouzitie(Hrac hrac) {
        System.out.println("Obliekaš si brnenie: " + this.meno);
    }
}