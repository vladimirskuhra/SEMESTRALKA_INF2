import javax.swing.text.Position;
import java.util.Scanner;

public class MiestnostSOdpocivadlom extends Miestnost {
    public MiestnostSOdpocivadlom(String id, String meno, String popis, Position pozicia) {
        super(id, meno, popis, pozicia);
    }

    @Override
    public void prehladat(Hrac hrac) {
        System.out.println("Prehľadávaš odpočívadlo. Je to pokojné miesto, kde si môžeš obnoviť sily.");
    }

    @Override
    public void interakcia(Hrac hrac) {
        System.out.println("Toto miesto vyzerá bezpečne. Chceš si tu odpočinúť? (a/n)");

        Scanner scanner = new Scanner(System.in);
        String odpoved = scanner.nextLine().toLowerCase();

        if (odpoved.equals("a")) {
            int aktualneZdravie = hrac.getZdravie();
            int maxZdravie = 100; // Predpokladáme, že max zdravie je 100

            if (aktualneZdravie < maxZdravie) {
                int regeneracia = 25; // Regeneruje 25 bodov zdravia
                int noveZdravie = Math.min(aktualneZdravie + regeneracia, maxZdravie);
                int vyliecene = noveZdravie - aktualneZdravie;

                hrac.setZdravie(noveZdravie);

                System.out.println("Odpočívaš a regeneruješ svoje sily...");
                System.out.println("Obnovil si " + vyliecene + " bodov zdravia.");
                System.out.println("Tvoje aktuálne zdravie: " + noveZdravie + "/" + maxZdravie);
            } else {
                System.out.println("Tvoje zdravie je už plné, nepotrebuješ si odpočinúť.");
            }
        } else {
            System.out.println("Rozhodol si sa neodpočívať a pokračovať ďalej.");
        }
    }

    @Override
    public void pouzitie(Hrac hrac) {
        // Miestnosť nemožno "použiť"
        System.out.println("Nie je možné použiť miestnosť.");
    }
}