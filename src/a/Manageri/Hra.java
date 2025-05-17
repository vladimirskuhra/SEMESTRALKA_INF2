package a.Manageri;

import a.Manageri.Quest.QuestDatabaza;
import a.Manageri.Quest.QuestManager;

import java.util.Scanner;

/**
 * Trieda Hra je hlavny riadiaci objekt celej hry.
 * Stara sa o inicializaciu vsetkych managerov, prepojenie medzi nimi
 * a riadi hlavny cyklus hry.
 * V tejto triede sa vytvaraju vsetky hlavne casti hry (manazeri, systemy, databazy)
 * a tu prebieha hlavna herna slucka.
 */
public class Hra {
    // Scanner pre nacitanie vstupu od uzivatela
    private final Scanner scanner = new Scanner(System.in);

    // Databaza questov, nacitava questy zo suboru
    private final QuestDatabaza questDatabaza = new QuestDatabaza("src/questy.txt");
    // Manazer questov, stara sa o pridelovanie a kontrolu questov
    private final QuestManager questManager = new QuestManager(this.questDatabaza);
    // System boja, spravuje bitky medzi hracom a nepriatelmi, pouziva questManager
    private final BattleSystem battleSystem = new BattleSystem(this.scanner, this.questManager);
    // Manazer dungeonu, stara sa o miestnosti, pohyb a generovanie mapy
    private final DungeonManager dungeonManager = new DungeonManager(this.battleSystem, this.questDatabaza, this.questManager);
    // Manazer hraca, obsahuje objekt hraca, jeho inventar, vytvara hraca na zaciatku
    private final PlayerManager playerManager = new PlayerManager(this.scanner, this.battleSystem);
    // Manazer interakcii, sprostredkuje interakcie s NPC a objektmi v miestnosti
    private final InteractionManager interactionManager = new InteractionManager(this.scanner, this.playerManager, this.dungeonManager, this.battleSystem, this.questManager);
    // Manazer na ukladanie a nacitanie hry
    private final SaveLoadManager saveLoadManager = new SaveLoadManager(this.dungeonManager, this.playerManager);
    // Objekt zodpovedny za vypisovanie menu a spracovanie uzivatelskych volieb
    private final MenuPrinter menuPrinter = new MenuPrinter(this.scanner, this.playerManager, this.dungeonManager, this.interactionManager, this.battleSystem, this.questManager, this.saveLoadManager);

    // Premenna urcuje, ci hra stale bezi
    private boolean hraAktivna = true;

    /**
     * Metoda start() spusta celu hru a riadi hlavny herny cyklus.
     * Najskor vytvori hraca, dungeon a potom opakuje cyklus,
     * kde zobrazuje informacie o miestnosti a spracuvava akcie od uzivatela.
     * Cyklus sa ukonci, ked hrac umrie alebo zvoli ukoncenie hry.
     */
    public void start() {
        System.out.println("Vitaj v dobrodruzstve v Goblinej jaskyni!");
        System.out.println("=========================================");

        // Vytvorenie hraca cez PlayerManager
        this.playerManager.vytvorHraca();

        // Vytvorenie dungeonu po tom, co uz existuje hrac
        this.dungeonManager.vytvorDungeon(this.playerManager.getHrac());

        // Hlavny herny cyklus
        while (this.hraAktivna && this.playerManager.getHrac().getZdravie() > 0) {
            // Zobrazi informacie o aktualnej miestnosti
            this.menuPrinter.zobrazInfoOMiestnosti();
            // Necha uzivatela zvolit akciu a spracuje ju
            this.menuPrinter.spracujAkciu(this);

            // Kontrola, ci hrac neumrel v kole
            if (this.playerManager.getHrac().getZdravie() <= 0) {
                System.out.println("\nKONIEC HRY - Tvoj hrdina " + this.playerManager.getHrac().getMeno() + " padol v boji!");
            }
        }
        // Ak hra bola ukoncena hracom a hrac nepadol, podakuje za hranie
        if (this.hraAktivna && this.playerManager.getHrac().getZdravie() > 0) {
            System.out.println("\nDakujeme za hranie!");
        }
    }

    /**
     * Metoda na ukoncenie hry z vonkajsieho prostredia (napr. ak ju vyberie hrac v menu)
     */
    public void ukoncitHru() {
        this.hraAktivna = false;
    }
}