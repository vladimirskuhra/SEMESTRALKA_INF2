package a.Manageri;

import a.Manageri.Quest.QuestManager;
import a.Miestnosti.Miestnost;
import a.Postavy.NPC.NPC;
import a.Postavy.NPC.Specialne.InteraktivnaPostava;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        Miestnost aktualna = this.dungeonManager.getDungeon().getAktualnaMiestnost();
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
            int volba = InputUtils.getNumericInput(this.scanner, 0, 1);
            if (volba == 1) {
                this.questManager.zobrazAktivneQuesty(this.playerManager.getHrac());
            }
            return;
        }

        System.out.println("\nS kým chceš interagovať?");
        for (int i = 0; i < interaktivnePostavy.size(); i++) {
            NPC postava = interaktivnePostavy.get(i);
            System.out.println((i + 1) + ". " + postava.getMeno());
        }
        System.out.println("0. Návrat");
        System.out.println((interaktivnePostavy.size() + 1) + ". Zobraziť aktívne questy");

        System.out.print("\nTvoja voľba (0 pre návrat): ");
        int volba = InputUtils.getNumericInput(this.scanner, 0, interaktivnePostavy.size() + 1);

        if (volba == 0) {
        } else if (volba == interaktivnePostavy.size() + 1) {
            this.questManager.zobrazAktivneQuesty(this.playerManager.getHrac());
        } else {
            InteraktivnaPostava vybrana = (InteraktivnaPostava) interaktivnePostavy.get(volba - 1);
            vybrana.interakcia(this.playerManager.getHrac(), this.battleSystem);
        }
    }
}