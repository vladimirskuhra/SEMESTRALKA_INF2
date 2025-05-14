import java.util.List;
import java.util.Scanner;

public class InteractionManager {
    private final Scanner scanner;
    private final PlayerManager playerManager;
    private final DungeonManager dungeonManager;
    private final BattleSystem battleSystem;

    public InteractionManager(Scanner scanner, PlayerManager pm, DungeonManager dm, BattleSystem bs) {
        this.scanner = scanner;
        this.playerManager = pm;
        this.dungeonManager = dm;
        this.battleSystem = bs;
    }

    public void interaguj() {
        Miestnost aktualna = dungeonManager.getDungeon().getAktualnaMiestnost();
        List<Charakter> postavy = aktualna.getPostavy();

        if (postavy.isEmpty()) {
            System.out.println("V miestnosti nie je s čím interagovať.");
            return;
        }

        System.out.println("\nS kým chceš interagovať?");
        int zivychPostav = 0;

        for (int i = 0; i < postavy.size(); i++) {
            Charakter postava = postavy.get(i);
            if (postava.getZdravie() > 0) {
                System.out.println((i+1) + ". " + postava.getMeno());
                zivychPostav++;
            }
        }

        if (zivychPostav == 0) {
            System.out.println("V miestnosti nie sú žiadne živé postavy.");
            return;
        }

        System.out.print("\nTvoja voľba (0 pre návrat): ");
        int volba = getNumericInput(0, postavy.size());

        if (volba != 0) {
            Charakter vybranaPostava = postavy.get(volba - 1);
            vybranaPostava.interakcia(playerManager.getHrac());
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