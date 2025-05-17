package a;

public class ChodbaMiestnost extends Miestnost implements Bezpecna {
    public ChodbaMiestnost(String id, String meno, String popis) {
        super(id, meno, popis);
    }

    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        System.out.println("Chodba, z každej strany vedú ďalšie cesty.");
    }

    @Override
    public void prehladat(Hrac hrac) {
        System.out.println("Prehľadávaš chodbu. Je to len prechod medzi miestnosťami.");
    }
}