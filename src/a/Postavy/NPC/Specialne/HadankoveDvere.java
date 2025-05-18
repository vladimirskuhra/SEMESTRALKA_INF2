package a.Postavy.NPC.Specialne;

import a.Manageri.BattleSystem;
import a.Miestnosti.Miestnost;
import a.Postavy.Hrac;
import a.Postavy.NPC.NPC;
import a.Postavy.NPC.Nepriatelia.Nepriatel;
import a.Postavy.NPC.Nepriatelia.Warg;
import a.Predmety.Lektvar;
import a.Predmety.Predmet;
import a.Predmety.SpecialnyPredmet;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Trieda HadankoveDvere predstavuje specialnu interaktivnu NPC, ktora otestuje hraca hadankou s dverami.
 * Ak hrac spravne odpovie, ziska odmenu, inak sa musi postavit nepriatelovi.
 */
public class HadankoveDvere extends NPC implements InteraktivnaPostava {
    // Odkaz na miestnost, kde sa dvere nachadzaju
    private final Miestnost miestnost;

    /**
     * Konstruktor pre hadankove dvere.
     * @param id unikatny identifikator dveri
     * @param meno meno dveri
     * @param popis popis dveri
     * @param miestnost miestnost, kde sa nachadzaju
     */
    public HadankoveDvere(String id, String meno, String popis, Miestnost miestnost) {
        super(id, meno, popis, miestnost, 1, 0, 0);
        this.miestnost = miestnost;
    }

    /**
     * Hracovi sa zobrazi hadanka s dvoma dverami. Spravna odpoved prinesie odmenu,
     * nespravna vyvola suboj s Wargom.
     * @param hrac hrac
     * @param battleSystem system na riadenie boja
     */
    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        System.out.println("Pred tebou su dvoje dvere. Na dlazke lezia dva listky:");
        System.out.println("Na prvom: 'Za tymito dverami sa nachadza warg.'");
        System.out.println("Na druhom: 'Za oboma dverami sa nachadza warg.'");
        System.out.println("Na stene je odkaz:");
        System.out.println("Len ak je napís na prvych dverach pravdivy, skryva sa za nimi lektvar (alebo poklad).");
        System.out.println("Ak je vsak nepravdivy napís na druhych dverach, lektvar sa skryva za nimi.");
        System.out.println("Ktore dvere otvoris? (1 alebo 2)");

        Scanner scanner = new Scanner(System.in);
        String odpoved = scanner.nextLine().trim();
        if (odpoved.equals("2")) {
            System.out.println("Spravne! Otvoril si dvere cislo 2 a nasiel si liecivy elixir.");
            Predmet odpovedPredmet = new SpecialnyPredmet("hadanka_odpoved", "Spravna odpoved na hadanku", "Dokaz, ze si vyriesil hadanku.");
            hrac.getInventar().pridajPredmet(odpovedPredmet);
            hrac.getInventar().pridajPredmet(new Lektvar("lektvar_hadanka", "Liecivy elixir", "Odmena za rozlustenie hadanky.", 30));
        } else {
            System.out.println("To bola nespravna volba! Za dverami sa objavil zurivy warg... a musis bojovat.");
            Warg warg = new Warg("warg_hadanka", "Zurivy warg", "Obrovsky hladny warg, ktory strazi tieto dvere.", this.miestnost, 70, 14, 6, 0.15);
            List<Nepriatel> nepriatelia = new ArrayList<>();
            nepriatelia.add(warg);
            battleSystem.boj(hrac, nepriatelia);
        }
    }
}