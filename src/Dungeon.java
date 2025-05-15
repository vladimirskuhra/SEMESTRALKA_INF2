import java.util.List;

public class Dungeon {
    private List<Miestnost> miestnosti;
    private Miestnost aktualnaMiestnost;

    public Dungeon(List<Miestnost> miestnosti) {
        this.miestnosti = miestnosti;
    }

    public List<Miestnost> getMiestnosti() {
        return miestnosti;
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

    // Pohyb hráča podľa smeru, ak existuje východ tým smerom
    public boolean pohybPodlaSmeru(String smer) {
        Miestnost ciel = aktualnaMiestnost.getVychody().get(smer);
        if (ciel != null) {
            aktualnaMiestnost = ciel;
            return true;
        }
        System.out.println("Týmto smerom sa nedá ísť.");
        return false;
    }

    // Stále môžeš použiť aj túto metódu ak potrebuješ (pri priamom odkaze na miestnosť)
    public void pohybDoMiestnosti(Miestnost novaMiestnost) {
        this.aktualnaMiestnost = novaMiestnost;
    }
}