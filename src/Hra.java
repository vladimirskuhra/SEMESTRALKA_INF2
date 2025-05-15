import java.util.Scanner;

public class Hra {
    private final Scanner scanner = new Scanner(System.in);

    // Najprv quest databáza a manager (musia byť pred BattleSystem!)
    private final QuestDatabaza questDatabaza = new QuestDatabaza("questy.txt");
    private final QuestManager questManager = new QuestManager(questDatabaza);

    // BattleSystem potrebuje aj questManager
    private final BattleSystem battleSystem = new BattleSystem(scanner, questManager);

    // DungeonManager potrebuje battleSystem, questDatabaza, questManager
    private final DungeonManager dungeonManager = new DungeonManager(battleSystem, questDatabaza, questManager);

    // PlayerManager potrebuje battleSystem
    private final PlayerManager playerManager = new PlayerManager(scanner, battleSystem);

    // InteractionManager potrebuje všetko vyššie
    private final InteractionManager interactionManager = new InteractionManager(scanner, playerManager, dungeonManager, battleSystem, questManager);

    // SaveLoadManager potrebuje dungeonManager a playerManager
    private final SaveLoadManager saveLoadManager = new SaveLoadManager(dungeonManager, playerManager);

    // MenuPrinter potrebuje všetko
    private final MenuPrinter menuPrinter = new MenuPrinter(scanner, playerManager, dungeonManager, interactionManager, battleSystem, questManager, saveLoadManager);

    private boolean hraAktivna = true;

    public void start() {
        System.out.println("Vitaj v dobrodružstve v Goblínej jaskyni!");
        System.out.println("=========================================");

        playerManager.vytvorHraca();
        dungeonManager.vytvorDungeon(playerManager.getHrac());

        // Hlavný cyklus
        while (hraAktivna && playerManager.getHrac().getZdravie() > 0) {
            menuPrinter.zobrazInfoOMiestnosti();
            menuPrinter.spracujAkciu(this);

            // Game over podmienka
            if (playerManager.getHrac().getZdravie() <= 0) {
                System.out.println("\nKONIEC HRY - Tvoj hrdina " + playerManager.getHrac().getMeno() + " padol v boji!");
            }
        }
        if (hraAktivna && playerManager.getHrac().getZdravie() > 0) {
            System.out.println("\nĎakujeme za hranie!");
        }
    }

    public void ukoncitHru() {
        hraAktivna = false;
    }
}