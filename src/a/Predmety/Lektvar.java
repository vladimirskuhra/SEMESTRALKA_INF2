package a.Predmety;

import a.Postavy.Hrac;

/**
 * Trieda Lektvar reprezentuje liecivy predmet, ktory moze hrac pouzit na obnovenie zdravia.
 * Dedi zo spolocnej abstraktnej triedy Predmet.
 */
public class Lektvar extends Predmet {

    /**
     * Konstruktor nastavi vsetky atributy lektvaru.
     * Hodnota predstavuje mnozstvo liecenia, ktore lektvar poskytuje.
     * @param id unikatny identifikator
     * @param meno nazov lektvaru
     * @param popis popis lektvaru
     * @param mnozstvoLiecenia kolko zdravia lektvar doplni
     */
    public Lektvar(String id, String meno, String popis, int mnozstvoLiecenia) {
        super(id, meno, popis, mnozstvoLiecenia);
    }

    /**
     * Metoda pouzitie urcuje, co sa stane, ked hrac pouzije lektvar.
     * Zlozitejsi algoritmus:
     * - Zisti aktualne a maximalne zdravie hraca.
     * - Spocita, kolko bodov zdravia je mozne vyliecit (tak, aby nepresiahlo maximum).
     * - Ak je mozne liecit, prida zdravie a odstrani lektvar z inventara.
     * - Ak je zdravie uz plne, nevykonava liecenie.
     * @param hrac hrac, ktory pouziva lektvar
     */
    @Override
    public void pouzitie(Hrac hrac) {
        int aktualneZdravie = hrac.getZdravie();
        int maxZdravie = 100;
        int mozneHojenie = maxZdravie - aktualneZdravie;
        int hojenie = Math.min(getHodnota(), mozneHojenie);

        if (hojenie > 0) {
            System.out.println("Pouzil si " + getMeno() + " a vyliecils sa o " + hojenie + " bodov zdravia.");
            hrac.setZdravie(aktualneZdravie + hojenie);
            hrac.getInventar().odstranPredmet(this);
        } else {
            System.out.println("Tvoje zdravie je uz plne. Nema zmysel pouzit lektvar.");
        }
    }
}