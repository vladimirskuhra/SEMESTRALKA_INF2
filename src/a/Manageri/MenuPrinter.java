package a.Manageri;

import a.Manageri.Quest.QuestManager;
import a.Miestnosti.Bezpecna;
import a.Miestnosti.Miestnost;
import a.Postavy.Charakter;
import a.Postavy.Hrac;
import a.Postavy.NPC.NPC;
import a.Postavy.NPC.Nepriatelia.Nepriatel;
import a.Predmety.Predmet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuPrinter {
    private final Scanner scanner;
    private final PlayerManager playerManager;
    private final DungeonManager dungeonManager;
    private final InteractionManager interactionManager;
    private final BattleSystem battleSystem;
    private final QuestManager questManager;
    private final SaveLoadManager saveLoadManager;

    public MenuPrinter(Scanner scanner, PlayerManager pm, DungeonManager dm, InteractionManager im, BattleSystem bs, QuestManager qm, SaveLoadManager slm) {
        this.scanner = scanner;
        this.playerManager = pm;
        this.dungeonManager = dm;
        this.interactionManager = im;
        this.battleSystem = bs;
        this.questManager = qm;
        this.saveLoadManager = slm;
    }

    public void zobrazInfoOMiestnosti() {
        Miestnost aktualna = this.dungeonManager.getDungeon().getAktualnaMiestnost();

        System.out.println("\n--- " + aktualna.getMeno() + " ---");
        System.out.println(aktualna.getPopis());

        if (!aktualna.getPredmety().isEmpty()) {
            System.out.println("\nV miestnosti vidíš:");
            for (Predmet predmet : aktualna.getPredmety()) {
                System.out.println("- " + predmet.getMeno() + ": " + predmet.getPopis());
            }
        }

        if (!aktualna.getPostavy().isEmpty()) {
            System.out.println("\nV miestnosti sa nachádzajú:");
            for (Charakter postava : aktualna.getPostavy()) {
                if (postava.getZdravie() > 0) {
                    System.out.println("- " + postava.getMeno() + ": " + postava.getPopis());
                }
            }
        }
    }

    public void spracujAkciu(Hra game) {
        Miestnost aktualna = this.dungeonManager.getDungeon().getAktualnaMiestnost();

        List<NPC> postavy = aktualna.getPostavy();
        boolean suNepriatelia = false;
        for (NPC npc : postavy) {
            if (npc instanceof Nepriatel && npc.getZdravie() > 0) {
                suNepriatelia = true;
                break;
            }
        }

        System.out.println("\nČo chceš robiť?");
        System.out.println("1. Preskúmať miestnosť");
        System.out.println("2. Zobraziť inventár");
        System.out.println("3. Zobraziť stav postavy");
        System.out.println("4. Prejsť do inej miestnosti");
        System.out.println("5. Interagovať s postavou/predmetom");
        System.out.println("6. Zobraziť aktívne questy");
        System.out.println("7. Ukončiť hru");
        int next = 8;
        if (aktualna instanceof Bezpecna) {
            System.out.println(next + ". Odpočinúť si");
            next++;
        }
        System.out.println(next + ". Uložiť hru");
        next++;
        System.out.println(next + ". Načítať hru");
        next++;
        int utokVolba = -1;
        if (suNepriatelia) {
            utokVolba = next;
            System.out.println(next + ". Zaútočiť na nepriateľa v miestnosti");
            next++;
        }
        int maxVolba = next - 1;
        int volba = InputUtils.getNumericInput(this.scanner, 1, maxVolba);

        if ((aktualna instanceof Bezpecna) && volba == 8) {
            Hrac hrac = this.playerManager.getHrac();
            hrac.setZdravie(100);
            System.out.println("Oddýchol si si. Zdravie doplnené!");
            return;
        }

        if (utokVolba != -1 && volba == utokVolba) {
            Hrac hrac = this.playerManager.getHrac();
            List<Nepriatel> nepriatelia = new ArrayList<>();
            for (NPC npc : postavy) {
                if (npc instanceof Nepriatel && npc.getZdravie() > 0) {
                    nepriatelia.add((Nepriatel) npc);
                }
            }
            if (nepriatelia.isEmpty()) {
                System.out.println("V miestnosti nie je žiadny nepriateľ na boj.");
                return;
            }

            System.out.println("Začína boj so všetkými nepriateľmi v miestnosti!");
            this.battleSystem.boj(hrac, nepriatelia);
            return;
        }
        // Ostatné voľby
        int odchodVolba = (aktualna instanceof Bezpecna) ? 9 : 8;
        int ulozVolba = (aktualna instanceof Bezpecna) ? 9 : 8;
        int nacitajVolba = (aktualna instanceof Bezpecna) ? 10 : 9;
        switch (volba) {
            case 1:
                preskumajMiestnost();
                break;
            case 2:
                zobrazInventar();
                break;
            case 3:
                zobrazStavPostavy();
                break;
            case 4:
                zmenMiestnost();
                break;
            case 5:
                this.interactionManager.interaguj();
                break;
            case 6:
                this.questManager.zobrazAktivneQuesty(this.playerManager.getHrac());
                break;
            case 7:
                System.out.println("\nNaozaj chceš ukončiť hru? (a/n)");
                String odpoved = this.scanner.nextLine().toLowerCase();
                if (odpoved.equals("a")) {
                    game.ukoncitHru();
                }
                break;
            case 8:
                if (aktualna instanceof Bezpecna) { // odpočinok
                    break;
                }
            case 9:
                try {
                    this.saveLoadManager.ulozHru("save.txt");
                    System.out.println("a.Manageri.Hra bola úspešne uložená.");
                } catch (Exception e) {
                    System.out.println("Chyba pri ukladaní hry: " + e.getMessage());
                }
                break;
            case 10:
                if (aktualna instanceof Bezpecna) { // načítať hru
                    try {
                        this.saveLoadManager.nacitajHru("save.txt");
                        System.out.println("a.Manageri.Hra bola úspešne načítaná.");
                    } catch (Exception e) {
                        System.out.println("Chyba pri načítaní hry: " + e.getMessage());
                    }
                }
                break;
        }
    }

    private void preskumajMiestnost() {
        Miestnost aktualna = this.dungeonManager.getDungeon().getAktualnaMiestnost();
        aktualna.prehladat(this.playerManager.getHrac());
    }

    private void zobrazInventar() {
        System.out.println("\n--- Tvoj inventár ---");
        List<Predmet> predmety = this.playerManager.getHrac().getInventar().getPredmety();

        if (predmety.isEmpty()) {
            System.out.println("Tvoj inventár je prázdny.");
            return;
        }

        for (int i = 0; i < predmety.size(); i++) {
            Predmet predmet = predmety.get(i);
            System.out.println((i + 1) + ". " + predmet.getMeno() + ": " + predmet.getPopis());
        }

        System.out.println("\nChceš použiť nejaký predmet? (a/n)");
        String odpoved = this.scanner.nextLine().toLowerCase();

        if (odpoved.equals("a")) {
            System.out.print("Ktorý predmet chceš použiť? (1-" + predmety.size() + "): ");
            int index = InputUtils.getNumericInput(this.scanner, 1, predmety.size()) - 1;
            predmety.get(index).pouzitie(this.playerManager.getHrac());
        }
    }

    private void zobrazStavPostavy() {
        System.out.println("\n--- Stav postavy ---");
        Hrac hrac = this.playerManager.getHrac();
        System.out.println("Meno: " + hrac.getMeno());
        System.out.println("Zdravie: " + hrac.getZdravie() + "/100");
        System.out.println("Sila: " + hrac.getSila());
        System.out.println("Obrana: " + hrac.getObrana());

        Predmet aktivnaZbran = hrac.getInventar().getAktivnaZbran();
        Predmet aktivneBrnenie = hrac.getInventar().getAktivneBrnenie();

        if (aktivnaZbran != null) {
            System.out.println("Aktívna zbraň: " + aktivnaZbran.getMeno());
        }

        if (aktivneBrnenie != null) {
            System.out.println("Aktívne brnenie: " + aktivneBrnenie.getMeno());
        }
    }

    private void zmenMiestnost() {
        Miestnost aktualna = this.dungeonManager.getDungeon().getAktualnaMiestnost();
        Map<String, Miestnost> vychody = aktualna.getVychody();

        if (vychody.isEmpty()) {
            System.out.println("\nZ tejto miestnosti nevedú žiadne východy.");
            return;
        }

        System.out.println("\nDostupné východy:");
        int i = 1;
        java.util.Map<Integer, String> volby = new java.util.HashMap<>();
        for (String smer : vychody.keySet()) {
            System.out.println(i + ". " + smer + " (" + vychody.get(smer).getMeno() + ")");
            volby.put(i, smer);
            i++;
        }
        System.out.println("0. Návrat");

        int volba = InputUtils.getNumericInput(this.scanner, 0, vychody.size());

        if (volba != 0) {
            String smer = volby.get(volba);
            boolean uspesne = this.dungeonManager.getDungeon().pohybPodlaSmeru(smer);
            if (uspesne) {
                System.out.println("Presúvaš sa " + smer + " do miestnosti: " + this.dungeonManager.getDungeon().getAktualnaMiestnost().getMeno());
            }
        }
    }
}