package a.Manageri;

import a.Postavy.Hrac;
import a.Predmety.Brnenie;
import a.Predmety.Lektvar;
import a.Predmety.Predmet;
import a.Predmety.Zbran;

import java.util.Scanner;

/**
 * PlayerManager sa stara o vytvaranie a spravu hraca, jeho inventaru a zakladnych statistik.
 * Prepojenie: PlayerManager komunikuje s BattleSystem (pre boje a interakcie).
 */
public class PlayerManager {
    private final Scanner scanner;
    private final BattleSystem battleSystem;
    private Hrac hrac;

    /**
     * Konstruktor ulozi referenciu na scanner a battleSystem pre neskorsie pouzitie.
     */
    public PlayerManager(Scanner scanner, BattleSystem battleSystem) {
        this.scanner = scanner;
        this.battleSystem = battleSystem;
    }

    /**
     * Vytvori hraca, nacita meno od pouzivatela, priradi zakladne predmety do inventara.
     * Algoritmus:
     * 1. Nacitaj meno od uzivatela.
     * 2. Vytvor objekt typu Hrac so zakladnymi statistikami.
     * 3. Pridaj mu do inventara zbran, brnenie a lektvar.
     */
    public void vytvorHraca() {
        System.out.print("Zadaj meno svojho dobrodruha: ");
        String menoHraca = this.scanner.nextLine();

        this.hrac = new Hrac("hrac1", menoHraca, "Odvazny dobrodruh", null, 100, 10, 5);

        // Pridanie zakladnych predmetov do inventara
        Predmet mec = new Zbran("zbran1", "Kratky mec", "Jednoduchy mec", 5, 5);
        Predmet stit = new Brnenie("brnenie1", "Kozena zbroj", "Jednoducha kozena zbroj", 5, 2);
        Predmet lektvar = new Lektvar("lektvar1", "Liecivy elixir", "Maly liecivy elixir", 20);

        this.hrac.getInventar().pridajPredmet(mec);
        this.hrac.getInventar().pridajPredmet(stit);
        this.hrac.getInventar().pridajPredmet(lektvar);

        System.out.println("\nVitaj, " + menoHraca + "! Tvoje dobrodruzstvo sa zacina...");
    }

    /**
     * Vrati referenciu na hraca.
     */
    public Hrac getHrac() {
        return this.hrac;
    }
}