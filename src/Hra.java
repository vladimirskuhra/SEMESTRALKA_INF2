import java.util.Scanner;

public class Hra {
    private final Scanner scanner = new Scanner(System.in);
    private final BattleSystem battleSystem = new BattleSystem(scanner);
    private final DungeonManager dungeonManager = new DungeonManager(battleSystem);
    private final PlayerManager playerManager = new PlayerManager(scanner, battleSystem);
    private final InteractionManager interactionManager = new InteractionManager(scanner, playerManager, dungeonManager, battleSystem);
    private final MenuPrinter menuPrinter = new MenuPrinter(scanner, playerManager, dungeonManager, interactionManager, battleSystem);

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
