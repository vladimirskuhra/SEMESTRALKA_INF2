package a.Manageri;

import a.Manageri.Quest.QuestManager;
import a.Miestnosti.Miestnost;
import a.Postavy.NPC.NPC;
import a.Postavy.NPC.Specialne.InteraktivnaPostava;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * InteractionManager sa stara o interakcie hraca s postavami v miestnosti.
 * Zabezpecuje vyber interaktivnej postavy a sprostredkovava dialogy alebo suboje.
 * Prepojenia: Pouziva PlayerManager, DungeonManager, BattleSystem, QuestManager.
 */
public class InteractionManager {
    private final Scanner scanner;
    private final PlayerManager playerManager;
    private final DungeonManager dungeonManager;
    private final BattleSystem battleSystem;
    private final QuestManager questManager;

    /**
     * Konstruktor prepoji hlavne manazery, aby mohol sprostredkovat interakcie.
     */
    public InteractionManager(Scanner scanner, PlayerManager pm, DungeonManager dm, BattleSystem bs, QuestManager qm) {
        this.scanner = scanner;
        this.playerManager = pm;
        this.dungeonManager = dm;
        this.battleSystem = bs;
        this.questManager = qm;
    }

    /**
     * Metoda zabezpeci interakciu s postavami v miestnosti.
     * Algoritmus:
     * 1. Ziska aktualnu miestnost a najde interaktivne postavy (polymorfizmus cez InteraktivnaPostava).
     * 2. Ak nie su ziadne, ponukne moznost zobrazit questy alebo sa vratit.
     * 3. Inak vypise zoznam postav, necha uzivatela zvolit a spusti interakciu.
     */
    public void interaguj() {
        Miestnost aktualna = this.dungeonManager.getDungeon().getAktualnaMiestnost();
        List<NPC> postavy = aktualna.getPostavy();

        List<NPC> interaktivnePostavy = new ArrayList<>();
        for (NPC postava : postavy) {
            // Filter na zive a interaktivne postavy
            if (postava.getZdravie() > 0 && postava instanceof InteraktivnaPostava) {
                interaktivnePostavy.add(postava);
            }
        }

        if (interaktivnePostavy.isEmpty()) {
            System.out.println("V miestnosti nie je ziadna postava, s ktorou by si mohol interagovat.");
            System.out.println("Ak chces, mozes si zobrazit aktivne questy (vyber prislusnu moznost).");
            System.out.println("0. Navrat");
            System.out.println("1. Zobrazit aktivne questy");
            int volba = InputUtils.getNumericInput(this.scanner, 0, 1);
            if (volba == 1) {
                this.questManager.zobrazAktivneQuesty(this.playerManager.getHrac());
            }
            return;
        }

        System.out.println("\nS kym chces interagovat?");
        for (int i = 0; i < interaktivnePostavy.size(); i++) {
            NPC postava = interaktivnePostavy.get(i);
            System.out.println((i + 1) + ". " + postava.getMeno());
        }
        System.out.println("0. Navrat");
        System.out.println((interaktivnePostavy.size() + 1) + ". Zobrazit aktivne questy");

        System.out.print("\nTvoja volba (0 pre navrat): ");
        int volba = InputUtils.getNumericInput(this.scanner, 0, interaktivnePostavy.size() + 1);

        if (volba == 0) {
            // navrat bez interakcie
        } else if (volba == interaktivnePostavy.size() + 1) {
            this.questManager.zobrazAktivneQuesty(this.playerManager.getHrac());
        } else {
            InteraktivnaPostava vybrana = (InteraktivnaPostava) interaktivnePostavy.get(volba - 1);
            vybrana.interakcia(this.playerManager.getHrac(), this.battleSystem);
        }
    }
}