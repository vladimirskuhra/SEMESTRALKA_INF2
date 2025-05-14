import java.util.ArrayList;
import java.util.List;

public class Miestnost {
    private final String id;
    private final String meno;
    private final String popis;
    private final boolean jeBezpecna;
    private final List<NPC> postavy = new ArrayList<>();
    private final List<Predmet> predmety = new ArrayList<>();

    public Miestnost(String id, String meno, String popis, boolean jeBezpecna) {
        this.id = id;
        this.meno = meno;
        this.popis = popis;
        this.jeBezpecna = jeBezpecna;
    }

    public String getId() { return id; }
    public String getMeno() { return meno; }
    public String getPopis() { return popis; }
    public boolean isBezpecna() { return jeBezpecna; }

    public List<NPC> getPostavy() { return postavy; }
    public List<Predmet> getPredmety() { return predmety; }

    public void pridajPostavu(NPC npc) { postavy.add(npc); }
    public void pridajPredmet(Predmet p) { predmety.add(p); }
    public void odstranPredmet(Predmet predmet) { predmety.remove(predmet); }

    public void prehladat(Hrac hrac) {
        if (predmety.isEmpty()) {
            System.out.println("V miestnosti si nič nenašiel.");
            return;
        }
        System.out.println("V miestnosti nachádzaš:");
        for (Predmet p : predmety) {
            System.out.println("- " + p.getMeno() + ": " + p.getPopis());
        }
        // Ďalšia logika pre výber a zdvíhanie predmetov...
    }
}