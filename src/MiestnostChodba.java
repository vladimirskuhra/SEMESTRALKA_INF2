import javax.swing.text.Position;

public class MiestnostChodba extends Miestnost {
    public MiestnostChodba(String id, String meno, String popis, Position pozicia) {
        super(id, meno, popis, pozicia);
    }

    @Override
    public void prehladat(Hrac hrac) {
        System.out.println("Prehľadávaš chodbu. Vyzerá to len ako prechod medzi miestnosťami.");
    }

    @Override
    public void interakcia(Hrac hrac) {
        System.out.println("Chodba vedie ďalej do dungonu. Z každej strany vidíš niekoľko možných ciest.");
    }

    @Override
    public void pouzitie(Hrac hrac) {
        // Miestnosť nemožno "použiť"
        System.out.println("Nie je možné použiť miestnosť.");
    }
}