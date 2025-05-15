public class Quest {
    private final String nazov;
    private final String popis;
    private final String cielovyPredmet;
    private final int cielovyPocet;
    private final int minLevel;
    private final String reward;
    private boolean splneny = false;

    public Quest(String nazov, String popis, String cielovyPredmet, int cielovyPocet, int minLevel, String reward) {
        this.nazov = nazov;
        this.popis = popis;
        this.cielovyPredmet = cielovyPredmet;
        this.cielovyPocet = cielovyPocet;
        this.minLevel = minLevel;
        this.reward = reward;
    }

    public String getNazov() { return this.nazov; }
    public String getPopis() { return this.popis; }
    public String getCielovyPredmet() { return this.cielovyPredmet; }
    public int getCielovyPocet() { return this.cielovyPocet; }
    public int getMinLevel() { return this.minLevel; }
    public String getReward() { return this.reward; }
    public boolean isSplneny() { return this.splneny; }
    public void setSplneny(boolean splneny) { this.splneny = splneny; }

    @Override
    public String toString() {
        return this.nazov + " | " + this.popis + " | Potrebné: " + this.cielovyPocet + "x " + this.cielovyPredmet +
                " | Odmena: " + this.reward + " | Min. level: " + this.minLevel + (this.splneny ? " | SPLNENÉ" : "");
    }
}