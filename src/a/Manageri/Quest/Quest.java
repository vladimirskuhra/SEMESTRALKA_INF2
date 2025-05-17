package a.Manageri.Quest;

/**
 * Trieda Quest reprezentuje jednu ulohu/quest v hre.
 * Uchovava vsetky informacie potrebne na splnenie a odmenu za quest.
 *
 * Preco je to takto navrhnute:
 * - Vsetky atributy su final, okrem splneny (to je priznak, ci je quest uz hotovy).
 * - Quest je immutable (neda sa zmenit nazov, popis, odmena ...), len priznak splnenia sa moze zmenit pocas hry.
 * - Trieda poskytuje gettery pre vsetky atributy, aby k nim mali pristup manageri alebo hrac.
 */
public class Quest {
    // Nazov questu (unikatny identifikator)
    private final String nazov;
    // Popis questu (pre hraca)
    private final String popis;
    // Meno cieloveho predmetu, ktory treba ziskat pre splnenie
    private final String cielovyPredmet;
    // Pocet cieloveho predmetu
    private final int cielovyPocet;
    // Minimalny level hraca na prijatie questu
    private final int minLevel;
    // Odmena za splnenie questu (moze byt exp alebo nazov predmetu)
    private final String reward;
    // Priznak, ci je quest splneny
    private boolean splneny = false;

    /**
     * Konstruktor nastavi vsetky udaje o queste.
     */
    public Quest(String nazov, String popis, String cielovyPredmet, int cielovyPocet, int minLevel, String reward) {
        this.nazov = nazov;
        this.popis = popis;
        this.cielovyPredmet = cielovyPredmet;
        this.cielovyPocet = cielovyPocet;
        this.minLevel = minLevel;
        this.reward = reward;
    }

    public String getNazov() {
        return this.nazov;
    }

    public String getPopis() {
        return this.popis;
    }

    public String getCielovyPredmet() {
        return this.cielovyPredmet;
    }

    public int getCielovyPocet() {
        return this.cielovyPocet;
    }

    public int getMinLevel() {
        return this.minLevel;
    }

    public String getReward() {
        return this.reward;
    }

    public boolean isSplneny() {
        return this.splneny;
    }

    public void setSplneny(boolean splneny) {
        this.splneny = splneny;
    }

    /**
     * Vypise quest v prehladnej forme pre uzivatela.
     */
    @Override
    public String toString() {
        return this.nazov + " | " + this.popis + " | Potrebne: " + this.cielovyPocet + "x " + this.cielovyPredmet +
                " | Odmena: " + this.reward + " | Min. level: " + this.minLevel + (this.splneny ? " | SPLNENE" : "");
    }
}