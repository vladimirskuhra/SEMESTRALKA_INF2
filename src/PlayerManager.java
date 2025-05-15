import java.util.Scanner;

public class PlayerManager {
    private final Scanner scanner;
    private final BattleSystem battleSystem;
    private Hrac hrac;

    public PlayerManager(Scanner scanner, BattleSystem battleSystem) {
        this.scanner = scanner;
        this.battleSystem = battleSystem;
    }

    public void vytvorHraca() {
        System.out.print("Zadaj meno svojho dobrodruha: ");
        String menoHraca = scanner.nextLine();

        hrac = new Hrac("hrac1", menoHraca, "Odvážny dobrodruh", null, 100, 10, 5);

        // Pridanie základnej výbavy
        Predmet mec = new Zbran("zbran1", "Krátky meč", "Jednoduchý meč", 5, 5);
        Predmet stit = new Brnenie("brnenie1", "Kožená zbroj", "Jednoduchá kožená zbroj", 5, 2);
        Predmet lektvar = new Lektvar("lektvar1", "Liečivý elixír", "Malý liečivý elixír", 20);

        hrac.getInventar().pridajPredmet(mec);
        hrac.getInventar().pridajPredmet(stit);
        hrac.getInventar().pridajPredmet(lektvar);

        System.out.println("\nVitaj, " + menoHraca + "! Tvoje dobrodružstvo sa začína...");
    }

    public Hrac getHrac() {
        return hrac;
    }
}