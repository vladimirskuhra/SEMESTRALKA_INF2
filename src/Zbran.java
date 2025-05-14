public class Zbran extends Predmet {
    private int sila;

    public Zbran(String id, String meno, String popis, int hodnota, int sila) {
        super(id, meno, popis, hodnota);
        this.sila = sila;
    }

    public int getSila() { return sila; }

    @Override
    public void pouzitie(Hrac hrac) {
        // Môžeš implementovať efekt zbrane pri použití
        System.out.println("Používaš zbraň: " + meno);
    }
}