package a.Miestnosti;

import java.util.List;

/**
 * Trieda Dungeon reprezentuje celu mapu dungeonu (herny svet).
 * Uchovava zoznam vsetkych miestnosti a aktualnu miestnost, v ktorej sa hrac nachadza.
 *
 * Preco je to takto navrhnute:
 * - Vsetky miestnosti su v jednom zozname, co umoznuje jednoduche prehladavanie a ukladanie stavu hry.
 * - Aktualna miestnost je referenciou na objekt, nie na index, aby bol pohyb a prepojenie miestnosti jednoduche a robustne.
 * - Pohyb hraca v dungeone je centralizovany v tejto triede (pohybPodlaSmeru), vsetky kontroly smerov su na jednom mieste.
 */
public class Dungeon {
    // Zoznam vsetkych miestnosti v dungeone (mapa)
    private final List<Miestnost> miestnosti;
    // Aktualna miestnost, v ktorej sa hrac prave nachadza
    private Miestnost aktualnaMiestnost;

    /**
     * Konstruktor - nastavi zoznam miestnosti, zvycajne vytvoreny v DungeonManageri.
     */
    public Dungeon(List<Miestnost> miestnosti) {
        this.miestnosti = miestnosti;
    }

    public List<Miestnost> getMiestnosti() {
        return this.miestnosti;
    }

    public Miestnost getAktualnaMiestnost() {
        return this.aktualnaMiestnost;
    }

    public void nastavAktualnuMiestnost(Miestnost miestnost) {
        this.aktualnaMiestnost = miestnost;
    }

    /**
     * Pokusi sa presunut hraca do miestnosti v zadadom smere.
     * Ak taky vychod existuje, aktualizuje aktualnu miestnost a vrati true.
     * Ak nie, vypise chybovu hlasku a vrati false.
     */
    public boolean pohybPodlaSmeru(String smer) {
        Miestnost ciel = this.aktualnaMiestnost.getVychody().get(smer);
        if (ciel != null) {
            this.aktualnaMiestnost = ciel;
            return true;
        }
        System.out.println("Tymto smerom sa neda ist.");
        return false;
    }
}