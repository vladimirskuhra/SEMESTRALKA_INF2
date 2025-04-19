import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.Scanner;

public class MiestnostSPokladmi extends Miestnost {
    private boolean prehladana;

    public MiestnostSPokladmi(String id, String meno, String popis, Position pozicia) {
        super(id, meno, popis, pozicia);
        this.prehladana = false;
    }

    @Override
    public void prehladat(Hrac hrac) {
        if (!prehladana) {
            System.out.println("Začínaš prehľadávať miestnosť...");

            if (!predmety.isEmpty()) {
                System.out.println("Našiel si:");
                for (Predmet predmet : predmety) {
                    System.out.println("- " + predmet.getMeno());
                }

                System.out.println("\nChceš si zobrať tieto predmety? (a/n)");
                Scanner scanner = new Scanner(System.in);
                String odpoved = scanner.nextLine().toLowerCase();

                if (odpoved.equals("a")) {
                    // Presun predmetov do inventára hráča
                    for (Predmet predmet : new ArrayList<>(predmety)) {
                        hrac.getInventar().pridajPredmet(predmet);
                        System.out.println("Zobral si: " + predmet.getMeno());
                        predmety.remove(predmet);
                    }
                }
            } else {
                System.out.println("Miestnosť si už prehľadal, nie je tu nič zaujímavé.");
            }

            prehladana = true;
        } else {
            System.out.println("Túto miestnosť si už dôkladne prehľadal.");
        }
    }

    @Override
    public void interakcia(Hrac hrac) {
        prehladat(hrac);
    }

    @Override
    public void pouzitie(Hrac hrac) {
        // Miestnosť nemožno "použiť"
        System.out.println("Nie je možné použiť miestnosť.");
    }
}