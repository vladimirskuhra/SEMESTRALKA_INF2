public class Zbran extends Predmet {
    public Zbran(String id, String meno, String popis, int bonusUtoku) {
        super(id, meno, popis, bonusUtoku);
    }

    @Override
    public void pouzitie(Hrac hrac) {
        System.out.println("Aktivoval si zbraň: " + getMeno());
        hrac.getInventar().nastavAktivnuZbran(this);
    }

    // Bonus k útoku
    public int getBonusUtoku() {
        return getHodnota();
    }
}