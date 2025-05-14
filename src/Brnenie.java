public class Brnenie extends Predmet {
    public Brnenie(String id, String meno, String popis, int bonusObrany) {
        super(id, meno, popis, bonusObrany);
    }

    @Override
    public void pouzitie(Hrac hrac) {
        System.out.println("Obliekol so si brnenie: " + getMeno());
        hrac.getInventar().nastavAktivneBrnenie(this);
    }

    // Bonus k obrane
    public int getBonusObrany() {
        return getHodnota();
    }
}