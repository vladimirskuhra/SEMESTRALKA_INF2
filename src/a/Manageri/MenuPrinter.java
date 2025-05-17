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

/**
 * MenuPrinter je trieda zodpovedna za zobrazovanie informacii o hre
 * a za spracovanie uzivatelskych volieb v hlavnom menu.
 * Prepojuje interakcie medzi hracom, dungeon managerom, subojmi, questami a ukladanim hry.
 */
public class MenuPrinter {
    // Vstupny scanner pre nacitanie volieb od uzivatela
    private final Scanner scanner;
    // Manazer hraca - poskytuje informacie o hracovi a jeho inventari
    private final PlayerManager playerManager;
    // Manazer dungeonu - poskytuje aktualnu miestnost a jej stav
    private final DungeonManager dungeonManager;
    // Manazer interakcii - sprostredkuje interakcie s NPC a predmetmi
    private final InteractionManager interactionManager;
    // System boja - spusta suboje s nepriatelmi
    private final BattleSystem battleSystem;
    // Manazer questov - zobrazuje a kontroluje questy
    private final QuestManager questManager;
    // Manazer ukladania a nacitavania hry
    private final SaveLoadManager saveLoadManager;

    /**
     * Konstruktor prijima vsetky potrebne manazery a scanner pre vstup.
     */
    public MenuPrinter(Scanner scanner, PlayerManager pm, DungeonManager dm, InteractionManager im, BattleSystem bs, QuestManager qm, SaveLoadManager slm) {
        this.scanner = scanner;
        this.playerManager = pm;
        this.dungeonManager = dm;
        this.interactionManager = im;
        this.battleSystem = bs;
        this.questManager = qm;
        this.saveLoadManager = slm;
    }

    /**
     * Vypise aktualne informacie o miestnosti (nazov, popis, predmety, postavy).
     * Pouziva polymorfizmus na vypis roznych typov postav a predmetov.
     */
    public void zobrazInfoOMiestnosti() {
        Miestnost aktualna = this.dungeonManager.getDungeon().getAktualnaMiestnost();

        System.out.println("\n--- " + aktualna.getMeno() + " ---");
        System.out.println(aktualna.getPopis());

        // Zobrazenie predmetov v miestnosti
        if (!aktualna.getPredmety().isEmpty()) {
            System.out.println("\nV miestnosti vidis:");
            for (Predmet predmet : aktualna.getPredmety()) {
                System.out.println("- " + predmet.getMeno() + ": " + predmet.getPopis());
            }
        }

        // Zobrazenie postav v miestnosti, len tych co maju zdravie > 0
        if (!aktualna.getPostavy().isEmpty()) {
            System.out.println("\nV miestnosti sa nachadzaju:");
            for (Charakter postava : aktualna.getPostavy()) {
                if (postava.getZdravie() > 0) {
                    System.out.println("- " + postava.getMeno() + ": " + postava.getPopis());
                }
            }
        }
    }

    /**
     * Spracuje uzivatelsku akciu z hlavneho menu.
     * Zabezpecuje pohyb, boj, zobrazenie inventara, questy, ulozenie/nacitanie hry a ukoncenie hry.
     * @param game Referencia na hlavny objekt Hra (pre ukoncenie cyklu).
     */
    public void spracujAkciu(Hra game) {
        Miestnost aktualna = this.dungeonManager.getDungeon().getAktualnaMiestnost();

        // Zistenie, ci su v miestnosti nepriatelia
        List<NPC> postavy = aktualna.getPostavy();
        boolean suNepriatelia = false;
        for (NPC npc : postavy) {
            if (npc instanceof Nepriatel && npc.getZdravie() > 0) {
                suNepriatelia = true;
                break;
            }
        }

        // Vypis hlavneho menu a dynamicke pridavanie poloziek podla stavu miestnosti
        System.out.println("\nCo chces robit?");
        System.out.println("1. Preskumat miestnost");
        System.out.println("2. Zobrazit inventar");
        System.out.println("3. Zobrazit stav postavy");
        System.out.println("4. Prejst do inej miestnosti");
        System.out.println("5. Interagovat s postavou/predmetom");
        System.out.println("6. Zobrazit aktivne questy");
        System.out.println("7. Ukoncit hru");
        int next = 8;
        if (aktualna instanceof Bezpecna) {
            System.out.println(next + ". Odpocinut si");
            next++;
        }
        System.out.println(next + ". Ulozit hru");
        next++;
        System.out.println(next + ". Nacitat hru");
        next++;
        int utokVolba = -1;
        if (suNepriatelia) {
            utokVolba = next;
            System.out.println(next + ". Zautocit na nepriatela v miestnosti");
            next++;
        }
        int maxVolba = next - 1;
        int volba = InputUtils.getNumericInput(this.scanner, 1, maxVolba);

        // Odpocivanie v bezpecnej miestnosti
        if ((aktualna instanceof Bezpecna) && volba == 8) {
            Hrac hrac = this.playerManager.getHrac();
            hrac.setZdravie(100);
            System.out.println("Oddychol si si. Zdravie doplnene!");
            return;
        }

        // Spustenie suboja ak je vybrate utocenie na nepriatela
        if (utokVolba != -1 && volba == utokVolba) {
            Hrac hrac = this.playerManager.getHrac();
            List<Nepriatel> nepriatelia = new ArrayList<>();
            for (NPC npc : postavy) {
                if (npc instanceof Nepriatel && npc.getZdravie() > 0) {
                    nepriatelia.add((Nepriatel) npc);
                }
            }
            if (nepriatelia.isEmpty()) {
                System.out.println("V miestnosti nie je ziaden nepriatel na boj.");
                return;
            }

            System.out.println("Zacina boj so vsetkymi nepriatelmi v miestnosti!");
            this.battleSystem.boj(hrac, nepriatelia);
            return;
        }
        // Spracovanie ostatnych volieb podla zadaneho cisla
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
                System.out.println("\nNaozaj chces ukoncit hru? (a/n)");
                String odpoved = this.scanner.nextLine().toLowerCase();
                if (odpoved.equals("a")) {
                    game.ukoncitHru();
                }
                break;
            case 8:
                if (aktualna instanceof Bezpecna) { // odpocinok
                    break;
                }
            case 9:
                try {
                    this.saveLoadManager.ulozHru("save.txt");
                    System.out.println("a.Manageri.Hra bola uspesne ulozena.");
                } catch (Exception e) {
                    System.out.println("Chyba pri ukladani hry: " + e.getMessage());
                }
                break;
            case 10:
                if (aktualna instanceof Bezpecna) { // nacitanie hry
                    try {
                        this.saveLoadManager.nacitajHru("save.txt");
                        System.out.println("a.Manageri.Hra bola uspesne nacitana.");
                    } catch (Exception e) {
                        System.out.println("Chyba pri nacitani hry: " + e.getMessage());
                    }
                }
                break;
        }
    }

    /**
     * Pomocna metoda pre preskumanie miestnosti.
     * Vola metodu prehladat na aktualnej miestnosti.
     */
    private void preskumajMiestnost() {
        Miestnost aktualna = this.dungeonManager.getDungeon().getAktualnaMiestnost();
        aktualna.prehladat(this.playerManager.getHrac());
    }

    /**
     * Pomocna metoda na zobrazenie inventara hraca a pouzitie predmetu.
     */
    private void zobrazInventar() {
        System.out.println("\n--- Tvoj inventar ---");
        List<Predmet> predmety = this.playerManager.getHrac().getInventar().getPredmety();

        if (predmety.isEmpty()) {
            System.out.println("Tvoj inventar je prazdny.");
            return;
        }

        for (int i = 0; i < predmety.size(); i++) {
            Predmet predmet = predmety.get(i);
            System.out.println((i + 1) + ". " + predmet.getMeno() + ": " + predmet.getPopis());
        }

        System.out.println("\nChces pouzit nejaky predmet? (a/n)");
        String odpoved = this.scanner.nextLine().toLowerCase();

        if (odpoved.equals("a")) {
            System.out.print("Ktory predmet chces pouzit? (1-" + predmety.size() + "): ");
            int index = InputUtils.getNumericInput(this.scanner, 1, predmety.size()) - 1;
            predmety.get(index).pouzitie(this.playerManager.getHrac());
        }
    }

    /**
     * Pomocna metoda na vypisanie stavu postavy (hraca).
     */
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
            System.out.println("Aktivna zbran: " + aktivnaZbran.getMeno());
        }

        if (aktivneBrnenie != null) {
            System.out.println("Aktivne brnenie: " + aktivneBrnenie.getMeno());
        }
    }

    /**
     * Pomocna metoda pre pohyb do inej miestnosti.
     * Zobrazi dostupne vychody a umozni vyber hracovi.
     */
    private void zmenMiestnost() {
        Miestnost aktualna = this.dungeonManager.getDungeon().getAktualnaMiestnost();
        Map<String, Miestnost> vychody = aktualna.getVychody();

        if (vychody.isEmpty()) {
            System.out.println("\nZ tejto miestnosti nevedu ziadne vychody.");
            return;
        }

        System.out.println("\nDostupne vychody:");
        int i = 1;
        java.util.Map<Integer, String> volby = new java.util.HashMap<>();
        for (String smer : vychody.keySet()) {
            System.out.println(i + ". " + smer + " (" + vychody.get(smer).getMeno() + ")");
            volby.put(i, smer);
            i++;
        }
        System.out.println("0. Navrat");

        int volba = InputUtils.getNumericInput(this.scanner, 0, vychody.size());

        if (volba != 0) {
            String smer = volby.get(volba);
            boolean uspesne = this.dungeonManager.getDungeon().pohybPodlaSmeru(smer);
            if (uspesne) {
                System.out.println("Presuvas sa " + smer + " do miestnosti: " + this.dungeonManager.getDungeon().getAktualnaMiestnost().getMeno());
            }
        }
    }
}