public class Quest {
    private String nazov;
    private String popis;
    private String cielovyPredmet;
    private int cielovyPocet;
    private int minLevel;
    private String reward;
    private boolean splneny = false;

    public Quest(String nazov, String popis, String cielovyPredmet, int cielovyPocet, int minLevel, String reward) {
        this.nazov = nazov;
        this.popis = popis;
        this.cielovyPredmet = cielovyPredmet;
        this.cielovyPocet = cielovyPocet;
        this.minLevel = minLevel;
        this.reward = reward;
    }

    public String getNazov() { return nazov; }
    public String getPopis() { return popis; }
    public String getCielovyPredmet() { return cielovyPredmet; }
    public int getCielovyPocet() { return cielovyPocet; }
    public int getMinLevel() { return minLevel; }
    public String getReward() { return reward; }
    public boolean isSplneny() { return splneny; }
    public void setSplneny(boolean splneny) { this.splneny = splneny; }

    @Override
    public String toString() {
        return nazov + " | " + popis + " | Potrebné: " + cielovyPocet + "x " + cielovyPredmet +
                " | Odmena: " + reward + " | Min. level: " + minLevel + (splneny ? " | SPLNENÉ" : "");
    }
}