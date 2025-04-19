import java.util.List;

public class Dungeon {
    private List<Miestnost> miestnosti;
    private Miestnost aktualnaMiestnost;

    public Dungeon(List<Miestnost> miestnosti) {
        // Vytvorenie a inicializácia miestností
        this.miestnosti = miestnosti;
    }

    public Miestnost getAktualnaMiestnost() {
        return aktualnaMiestnost;
    }

    public void nastavAktualnuMiestnost(Miestnost miestnost) {
        this.aktualnaMiestnost = miestnost;
    }

    public void pridajMiestnost(Miestnost miestnost) {
        miestnosti.add(miestnost);
    }

    public void pohybDoMiestnosti(Miestnost novaMiestnost) {
        this.aktualnaMiestnost = novaMiestnost;
    }
}