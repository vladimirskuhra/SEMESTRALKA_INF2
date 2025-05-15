import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. Vytvor miestnosť
        Miestnost dungeon = new Miestnost("1", "Temnica", "Temná vlhká miestnosť plná tieňov.", false);

        // 2. Vytvor hráča
        Hrac hrac = new Hrac("hrac1", "Vladislav", "Odvážny dobrodruh", dungeon, 30, 5, 3);

        // 3. Vytvor goblina
        Goblin goblin = new Goblin("goblin1", "Goblin", "Škaredý zelený škriatok", dungeon, 20, 4, 2, 0.3);

        // 4. Pridaj goblinovi zbraň (voliteľné, ale odporúčané pre test)
        Zbran goblinZbran = new Zbran("zbran1", "Hrdzavý meč", "Stará, ale stále ostrá čepeľ.", 10, 3);
        goblin.getInventar().pridajPredmet(goblinZbran);

        // 5. Pridaj hráča aj goblina do miestnosti (ak chceš riešiť zoznam postáv v miestnosti)
        dungeon.pridajPostavu(hrac);
        dungeon.pridajPostavu(goblin);

        // 6. Priprav battle system
        BattleSystem battleSystem = new BattleSystem(sc);

        // 7. Spusti boj
        System.out.println("Stretol si goblina!");
        battleSystem.boj(hrac, goblin);

        System.out.println("\n--- KONEC TESTU ---");
    }
}