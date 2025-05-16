import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class InteractionManager {
    private final Scanner scanner;
    private final PlayerManager playerManager;
    private final DungeonManager dungeonManager;
    private final BattleSystem battleSystem;
    private final QuestManager questManager;

    public InteractionManager(Scanner scanner, PlayerManager pm, DungeonManager dm, BattleSystem bs, QuestManager qm) {
        this.scanner = scanner;
        this.playerManager = pm;
        this.dungeonManager = dm;
        this.battleSystem = bs;
        this.questManager = qm;
    }

    public void interaguj() {
        Miestnost aktualna = dungeonManager.getDungeon().getAktualnaMiestnost();
        List<NPC> postavy = aktualna.getPostavy();

        List<NPC> interaktivnePostavy = new ArrayList<>();
        for (NPC postava : postavy) {
            if (postava.getZdravie() > 0 && postava instanceof InteraktivnaPostava) {
                interaktivnePostavy.add(postava);
            }
        }

        if (interaktivnePostavy.isEmpty()) {
            System.out.println("V miestnosti nie je žiadna postava, s ktorou by si mohol interagovať.");
            System.out.println("Ak chceš, môžeš si zobraziť aktívne questy (vyber príslušnú možnosť).");
            System.out.println("0. Návrat");
            System.out.println("1. Zobraziť aktívne questy");
            int volba = getNumericInput(0, 1);
            if (volba == 1) {
                questManager.zobrazAktivneQuesty(playerManager.getHrac());
            }
            return;
        }

        System.out.println("\nS kým chceš interagovať?");
        for (int i = 0; i < interaktivnePostavy.size(); i++) {
            NPC postava = interaktivnePostavy.get(i);
            System.out.println((i+1) + ". " + postava.getMeno());
        }
        System.out.println("0. Návrat");
        System.out.println((interaktivnePostavy.size() + 1) + ". Zobraziť aktívne questy");

        System.out.print("\nTvoja voľba (0 pre návrat): ");
        int volba = getNumericInput(0, interaktivnePostavy.size() + 1);

        if (volba == 0) {
            return;
        } else if (volba == interaktivnePostavy.size() + 1) {
            questManager.zobrazAktivneQuesty(playerManager.getHrac());
            return;
        } else {
            InteraktivnaPostava vybrana = (InteraktivnaPostava) interaktivnePostavy.get(volba - 1);
            vybrana.interakcia(playerManager.getHrac(), battleSystem);
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