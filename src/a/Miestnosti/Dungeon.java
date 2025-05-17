package a.Miestnosti;

import java.util.List;

public class Dungeon {
    private final List<Miestnost> miestnosti;
    private Miestnost aktualnaMiestnost;

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


    // Pohyb hráča podľa smeru, ak existuje východ tým smerom
    public boolean pohybPodlaSmeru(String smer) {
        Miestnost ciel = this.aktualnaMiestnost.getVychody().get(smer);
        if (ciel != null) {
            this.aktualnaMiestnost = ciel;
            return true;
        }
        System.out.println("Týmto smerom sa nedá ísť.");
        return false;
    }
}