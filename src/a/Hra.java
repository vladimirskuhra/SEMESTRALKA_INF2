package a;

import java.util.Scanner;

public class Hra {
    private final Scanner scanner = new Scanner(System.in);


    private final QuestDatabaza questDatabaza = new QuestDatabaza("src/questy.txt");
    private final QuestManager questManager = new QuestManager(this.questDatabaza);


    private final BattleSystem battleSystem = new BattleSystem(this.scanner, this.questManager);


    private final DungeonManager dungeonManager = new DungeonManager(this.battleSystem, this.questDatabaza, this.questManager);


    private final PlayerManager playerManager = new PlayerManager(this.scanner, this.battleSystem);


    private final InteractionManager interactionManager = new InteractionManager(this.scanner, this.playerManager, this.dungeonManager, this.battleSystem, this.questManager);


    private final SaveLoadManager saveLoadManager = new SaveLoadManager(this.dungeonManager, this.playerManager);


    private final MenuPrinter menuPrinter = new MenuPrinter(this.scanner, this.playerManager, this.dungeonManager, this.interactionManager, this.battleSystem, this.questManager, this.saveLoadManager);

    private boolean hraAktivna = true;

    public void start() {
        System.out.println("Vitaj v dobrodružstve v Goblínej jaskyni!");
        System.out.println("=========================================");

        this.playerManager.vytvorHraca();
        this.dungeonManager.vytvorDungeon(this.playerManager.getHrac());

        // Hlavný cyklus
        while (this.hraAktivna && this.playerManager.getHrac().getZdravie() > 0) {
            this.menuPrinter.zobrazInfoOMiestnosti();
            this.menuPrinter.spracujAkciu(this);

            // Game over podmienka
            if (this.playerManager.getHrac().getZdravie() <= 0) {
                System.out.println("\nKONIEC HRY - Tvoj hrdina " + this.playerManager.getHrac().getMeno() + " padol v boji!");
            }
        }
        if (this.hraAktivna && this.playerManager.getHrac().getZdravie() > 0) {
            System.out.println("\nĎakujeme za hranie!");
        }
    }

    public void ukoncitHru() {
        this.hraAktivna = false;
    }
}