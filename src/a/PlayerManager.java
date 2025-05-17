package a;

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
        String menoHraca = this.scanner.nextLine();

        this.hrac = new Hrac("hrac1", menoHraca, "Odvážny dobrodruh", null, 100, 10, 5);


        Predmet mec = new Zbran("zbran1", "Krátky meč", "Jednoduchý meč", 5, 5);
        Predmet stit = new Brnenie("brnenie1", "Kožená zbroj", "Jednoduchá kožená zbroj", 5, 2);
        Predmet lektvar = new Lektvar("lektvar1", "Liečivý elixír", "Malý liečivý elixír", 20);

        this.hrac.getInventar().pridajPredmet(mec);
        this.hrac.getInventar().pridajPredmet(stit);
        this.hrac.getInventar().pridajPredmet(lektvar);

        System.out.println("\nVitaj, " + menoHraca + "! Tvoje dobrodružstvo sa začína...");
    }

    public Hrac getHrac() {
        return this.hrac;
    }
}