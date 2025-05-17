package a;

import java.util.Scanner;

public class OdpocivadloMiestnost extends Miestnost implements Bezpecna {
    public OdpocivadloMiestnost(String id, String meno, String popis) {
        super(id, meno, popis);
    }

    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        System.out.println("Chceš si odpočinúť? (a/n)");
        Scanner scanner = new Scanner(System.in);
        String odp = scanner.nextLine().toLowerCase();
        if (odp.equals("a")) {
            odpocinok(hrac);
        }
    }

    @Override
    public void prehladat(Hrac hrac) {
        System.out.println("Prehľadávaš odpočívadlo. Je tu pokojné miesto na oddych.");
        odpocinok(hrac);
    }

    private void odpocinok(Hrac hrac) {
        int maxZdravie = 100;
        int aktualneZdravie = hrac.getZdravie();
        if (aktualneZdravie < maxZdravie) {
            int regeneracia = 25;
            int noveZdravie = Math.min(aktualneZdravie + regeneracia, maxZdravie);
            int vyliecene = noveZdravie - aktualneZdravie;
            hrac.setZdravie(noveZdravie);
            System.out.println("Odpočívaš... Regeneruješ " + vyliecene + " bodov zdravia.");
        } else {
            System.out.println("Tvoje zdravie je už plné.");
        }
    }
}