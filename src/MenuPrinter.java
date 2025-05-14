import java.util.List;
import java.util.Scanner;

public class MenuPrinter {
    private final Scanner scanner;
    private final PlayerManager playerManager;
    private final DungeonManager dungeonManager;
    private final InteractionManager interactionManager;
    private final BattleSystem battleSystem;

    public MenuPrinter(Scanner scanner, PlayerManager pm, DungeonManager dm, InteractionManager im, BattleSystem bs) {
        this.scanner = scanner;
        this.playerManager = pm;
        this.dungeonManager = dm;
        this.interactionManager = im;
        this.battleSystem = bs;
    }

    public void zobrazInfoOMiestnosti() {
        Miestnost aktualna = dungeonManager.getDungeon().getAktualnaMiestnost();

        System.out.println("\n--- " + aktualna.getMeno() + " ---");
        System.out.println(aktualna.getPopis());

        if (!aktualna.getPredmety().isEmpty()) {
            System.out.println("\nV miestnosti vidíš:");
            for (Predmet predmet : aktualna.getPredmety()) {
                System.out.println("- " + predmet.getMeno() + ": " + predmet.getPopis());
            }
        }

        if (!aktualna.getPostavy().isEmpty()) {
            System.out.println("\nV miestnosti sa nachádzajú:");
            for (Charakter postava : aktualna.getPostavy()) {
                if (postava.getZdravie() > 0) {
                    System.out.println("- " + postava.getMeno() + ": " + postava.getPopis());
                }
            }
        }
    }

    public void spracujAkciu(Hra game) {
        Miestnost aktualna = dungeonManager.getDungeon().getAktualnaMiestnost();

        System.out.println("\nČo chceš robiť?");
        System.out.println("1. Preskúmať miestnosť");
        System.out.println("2. Zobraziť inventár");
        System.out.println("3. Zobraziť stav postavy");
        System.out.println("4. Prejsť do inej miestnosti");
        System.out.println("5. Interagovať s postavou/predmetom");
        System.out.println("6. Ukončiť hru");
        if (aktualna.isBezpecna()) {
            System.out.println("7. Odpočinúť si");
        }

            // ... ostatné ako predtým
        int maxVolba = aktualna.isBezpecna() ? 7 : 6;
        int volba = getNumericInput(1, maxVolba);

        if (volba == 7 && aktualna.isBezpecna()) {
            Hrac hrac = playerManager.getHrac();
            hrac.setZdravie(100); // alebo iný spôsob dopĺňania
            System.out.println("Oddýchol si si. Zdravie doplnené!");
        }

        switch(volba) {
            case 1:
                preskumajMiestnost();
                break;
            case 2:
                zobrazInventar();
                break;
            case 3:
                zobrazStavPostavy();
                break;
            case 4:
                zmenMiestnost();
                break;
            case 5:
                interactionManager.interaguj();
                break;
            case 6:
                System.out.println("\nNaozaj chceš ukončiť hru? (a/n)");
                String odpoved = scanner.nextLine().toLowerCase();
                if (odpoved.equals("a")) {
                    game.ukoncitHru();
                }
                break;
        }
    }

    private void preskumajMiestnost() {
        Miestnost aktualna = dungeonManager.getDungeon().getAktualnaMiestnost();
        aktualna.prehladat(playerManager.getHrac());
    }

    private void zobrazInventar() {
        System.out.println("\n--- Tvoj inventár ---");
        List<Predmet> predmety = playerManager.getHrac().getInventar().getPredmety();

        if (predmety.isEmpty()) {
            System.out.println("Tvoj inventár je prázdny.");
            return;
        }

        for (int i = 0; i < predmety.size(); i++) {
            Predmet predmet = predmety.get(i);
            System.out.println((i+1) + ". " + predmet.getMeno() + ": " + predmet.getPopis());
        }

        System.out.println("\nChceš použiť nejaký predmet? (a/n)");
        String odpoved = scanner.nextLine().toLowerCase();

        if (odpoved.equals("a")) {
            System.out.print("Ktorý predmet chceš použiť? (1-" + predmety.size() + "): ");
            int index = getNumericInput(1, predmety.size()) - 1;
            predmety.get(index).pouzitie(playerManager.getHrac());
        }
    }

    private void zobrazStavPostavy() {
        System.out.println("\n--- Stav postavy ---");
        Hrac hrac = playerManager.getHrac();
        System.out.println("Meno: " + hrac.getMeno());
        System.out.println("Zdravie: " + hrac.getZdravie() + "/100");
        System.out.println("Sila: " + hrac.getSila());
        System.out.println("Obrana: " + hrac.getObrana());

        Predmet aktivnaZbran = hrac.getInventar().getAktivnaZbran();
        Predmet aktivneBrnenie = hrac.getInventar().getAktivneBrnenie();

        if (aktivnaZbran != null) {
            System.out.println("Aktívna zbraň: " + aktivnaZbran.getMeno());
        }

        if (aktivneBrnenie != null) {
            System.out.println("Aktívne brnenie: " + aktivneBrnenie.getMeno());
        }
    }

    private void zmenMiestnost() {
        List<Miestnost> miestnosti = dungeonManager.getDungeon().getMiestnosti();
        Miestnost aktualna = dungeonManager.getDungeon().getAktualnaMiestnost();

        System.out.println("\nDo ktorej miestnosti chceš ísť?");
        for (int i = 0; i < miestnosti.size(); i++) {
            Miestnost m = miestnosti.get(i);
            if (!m.equals(aktualna)) {
                System.out.println((i+1) + ". " + m.getMeno());
            }
        }

        System.out.print("\nTvoja voľba (0 pre návrat): ");
        int volba = getNumericInput(0, miestnosti.size());

        if (volba != 0) {
            dungeonManager.getDungeon().pohybDoMiestnosti(miestnosti.get(volba - 1));
        }
    }

    private int getNumericInput(int min, int max) {
        int volba = -1;
        while (volba < min || volba > max) {
            try {
                String vstup = scanner.nextLine();
                volba = Integer.parseInt(vstup);
                if (volba < min || volba > max) {
                    System.out.print("Zadaj číslo od " + min + " do " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Zadaj platné číslo: ");
            }
        }
        return volba;
    }
}