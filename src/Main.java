//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();
    private static Hrac hrac;
    private static Dungeon dungeon;
    private static boolean hraAktivna = true;

    public static void main(String[] args) {
        System.out.println("Vitaj v dobrodružstve v Goblínej jaskyni!");
        System.out.println("=========================================");

        vytvorHraca();
        vytvorDungeon();

        // Hlavný herný cyklus
        while (hraAktivna && hrac.getZdravie() > 0) {
            zobrazInfoOMiestnosti();
            spracujAkciu();
        }

        if (hrac.getZdravie() <= 0) {
            System.out.println("\nKONIEC HRY - Tvoj hrdina " + hrac.getMeno() + " padol v boji!");
        } else {
            System.out.println("\nĎakujeme za hranie!");
        }
    }

    private static void vytvorHraca() {
        System.out.print("Zadaj meno svojho dobrodruha: ");
        String menoHraca = scanner.nextLine();

        // Vytvorenie hráča s východzími hodnotami
        hrac = new Hrac("hrac1", menoHraca, "Odvážny dobrodruh", null, 100, 10, 5, TypCharakteru.HRAC);

        // Pridanie základnej výbavy
        Predmet mec = new Zbran("zbran1", "Krátky meč", "Jednoduchý meč", 5);
        Predmet stit = new Brnenie("brnenie1", "Kožená zbroj", "Jednoduchá kožená zbroj", 2);
        Predmet lektvar = new Lektvar("lektvar1", "Liečivý elixír", "Malý liečivý elixír", 20);

        hrac.getInventar().pridajPredmet(mec);
        hrac.getInventar().pridajPredmet(stit);
        hrac.getInventar().pridajPredmet(lektvar);

        System.out.println("\nVitaj, " + menoHraca + "! Tvoje dobrodružstvo sa začína...");
    }

    private static void vytvorDungeon() {
        List<Miestnost> miestnosti = new ArrayList<>();

        // Vytvorenie rôznych miestností
        Miestnost vstup = new MiestnostVstup("m1", "Vstup do jaskyne",
                "Stojíš pri vchode do temnej jaskyne. Zo stien kvapká voda a vzduch je vlhký.", null);

        Miestnost chodba = new MiestnostChodba("m2", "Dlhá chodba",
                "Dlhá tmavá chodba, osvetlená len niekoľkými fakľami. Na konci vidíš rozvetvenie.", null);

        Miestnost miestnostSPokladmi = new MiestnostSPokladmi("m3", "Sklad",
                "Miestnosť plná bední a políc. Vyzerá to ako sklad goblinov.", null);
        miestnostSPokladmi.pridajPredmet(new Zbran("zbran2", "Gobliní tesák", "Ostrý zahnutý nôž", 8));
        miestnostSPokladmi.pridajPredmet(new Lektvar("lektvar2", "Silný liečivý elixír", "Liečivý elixír s intenzívnym účinkom", 30));

        Miestnost miestnostSNepriatelmi = new MiestnostSNepriatelmi("m4", "Gobliní tábor",
                "Malá jaskyňa premenená na tábor goblinov. Vidieť niekoľko provizórnych lôžok a ohnisko.", null);
        miestnostSNepriatelmi.pridajPostavu(new Goblin("goblin1", "Gobliní strážca", "Malý goblin so zúbkami", null, 30, 5, 2));
        miestnostSNepriatelmi.pridajPostavu(new Goblin("goblin2", "Gobliní bojovník", "Väčší goblin s rapkáňom", null, 40, 7, 3));

        Miestnost miestnostSOdpocivadlom = new MiestnostSOdpocivadlom("m5", "Tichá jaskyňa",
                "Pokojná jaskyňa s malým potôčikom. Vyzerá to ako bezpečné miesto na odpočinok.", null);

        Miestnost miestnostSObchodnikom = new MiestnostSObchodnikom("m6", "Gobliní trh",
                "Prekvapivo, v tejto jaskyni je malý gobliní trh. Jeden z goblinov vyzerá priateľsky.", null);
        miestnostSObchodnikom.pridajPostavu(new Obchodnik("obchodnik1", "Grumli", "Starší gobliní obchodník", null, 50, 3, 10));

        Miestnost bossovaMiestnost = new MiestnostSNepriatelmi("m7", "Trónna sieň",
                "Veľká sieň s trónom z kostí. Na tróne sedí Gobliní kráľ, obklopený lukostrelcami.", null);
        bossovaMiestnost.pridajPostavu(new GoblinKral("boss1", "Gobliní kráľ", "Mohutný goblin s korunou a žezlom", null, 100, 15, 8));
        bossovaMiestnost.pridajPostavu(new GoblinLukostrelec("archer1", "Gobliní lukostrelec", "Chudý goblin s lukom", null, 40, 10, 3));
        bossovaMiestnost.pridajPostavu(new GoblinLukostrelec("archer2", "Gobliní lukostrelec", "Chudý goblin s lukom", null, 40, 10, 3));

        // Pridanie pavúkov a wargov do ďalších miestností
        Miestnost pavuciaBrloh = new MiestnostSNepriatelmi("m8", "Pavúčí brloh",
                "Jaskyňa pokrytá pavučinami. Zo stropu visia husté siete.", null);
        pavuciaBrloh.pridajPostavu(new Pavuk("pavuk1", "Obrovský pavúk", "Chĺpaty pavúk veľkosti psa", null, 60, 12, 4));

        Miestnost wargDoupie = new MiestnostSNepriatelmi("m9", "Wargie dúpä",
                "Temná jaskyňa plná kostí a zvyškov koristi. Cítiš zápach šeliem.", null);
        wargDoupie.pridajPostavu(new Warg("warg1", "Divý warg", "Veľký vlku podobný tvor s ostrými zubmi", null, 70, 14, 6));

        // Pridanie miestností do zoznamu
        miestnosti.add(vstup);
        miestnosti.add(chodba);
        miestnosti.add(miestnostSPokladmi);
        miestnosti.add(miestnostSNepriatelmi);
        miestnosti.add(miestnostSOdpocivadlom);
        miestnosti.add(miestnostSObchodnikom);
        miestnosti.add(bossovaMiestnost);
        miestnosti.add(pavuciaBrloh);
        miestnosti.add(wargDoupie);

        // Vytvorenie dungeonua nastavenie aktuálnej miestnosti
        dungeon = new Dungeon(miestnosti);
        dungeon.nastavAktualnuMiestnost(vstup);
    }

    private static void zobrazInfoOMiestnosti() {
        Miestnost aktualna = dungeon.getAktualnaMiestnost();

        System.out.println("\n--- " + aktualna.getMeno() + " ---");
        System.out.println(aktualna.getPopis());

        // Zobraz predmety v miestnosti
        if (!aktualna.getPredmety().isEmpty()) {
            System.out.println("\nV miestnosti vidíš:");
            for (Predmet predmet : aktualna.getPredmety()) {
                System.out.println("- " + predmet.getMeno() + ": " + predmet.getPopis());
            }
        }

        // Zobraz postavy v miestnosti
        if (!aktualna.getPostavy().isEmpty()) {
            System.out.println("\nV miestnosti sa nachádzajú:");
            for (Charakter postava : aktualna.getPostavy()) {
                if (postava.getZdravie() > 0) {
                    System.out.println("- " + postava.getMeno() + ": " + postava.getPopis());
                }
            }
        }
    }

    private static void spracujAkciu() {
        System.out.println("\nČo chceš robiť?");
        System.out.println("1. Preskúmať miestnosť");
        System.out.println("2. Zobraziť inventár");
        System.out.println("3. Zobraziť stav postavy");
        System.out.println("4. Prejsť do inej miestnosti");
        System.out.println("5. Interagovať s postavou/predmetom");
        System.out.println("6. Ukončiť hru");

        System.out.print("\nTvoja voľba: ");
        int volba = getNumericInput(1, 6);

        switch(volba) {
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
                interaguj();
                break;
            case 6:
                System.out.println("\nNaozaj chceš ukončiť hru? (a/n)");
                String odpoved = scanner.nextLine().toLowerCase();
                if (odpoved.equals("a")) {
                    hraAktivna = false;
                }
                break;
        }
    }

    private static void preskumajMiestnost() {
        Miestnost aktualna = dungeon.getAktualnaMiestnost();
        aktualna.prehladat(hrac);
    }

    private static void zobrazInventar() {
        System.out.println("\n--- Tvoj inventár ---");
        List<Predmet> predmety = hrac.getInventar().getPredmety();

        if (predmety.isEmpty()) {
            System.out.println("Tvoj inventár je prázdny.");
            return;
        }

        for (int i = 0; i < predmety.size(); i++) {
            Predmet predmet = predmety.get(i);
            System.out.println((i+1) + ". " + predmet.getMeno() + ": " + predmet.getPopis());
        }

        System.out.println("\nChceš použiť nejaký predmet? (a/n)");
        String odpoved = scanner.nextLine().toLowerCase();

        if (odpoved.equals("a")) {
            System.out.print("Ktorý predmet chceš použiť? (1-" + predmety.size() + "): ");
            int index = getNumericInput(1, predmety.size()) - 1;
            predmety.get(index).pouzitie(hrac);
        }
    }

    private static void zobrazStavPostavy() {
        System.out.println("\n--- Stav postavy ---");
        System.out.println("Meno: " + hrac.getMeno());
        System.out.println("Zdravie: " + hrac.getZdravie() + "/100");
        System.out.println("Sila: " + hrac.getSila());
        System.out.println("Obrana: " + hrac.getObrana());

        // Zobrazenie aktívnej zbrane a brnenia
        Predmet aktivnaZbran = hrac.getInventar().getAktivnaZbran();
        Predmet aktivneBrnenie = hrac.getInventar().getAktivneBrnenie();

        if (aktivnaZbran != null) {
            System.out.println("Aktívna zbraň: " + aktivnaZbran.getMeno());
        }

        if (aktivneBrnenie != null) {
            System.out.println("Aktívne brnenie: " + aktivneBrnenie.getMeno());
        }
    }

    private static void zmenMiestnost() {
        List<Miestnost> miestnosti = dungeon.getMiestnosti();

        System.out.println("\nDo ktorej miestnosti chceš ísť?");
        for (int i = 0; i < miestnosti.size(); i++) {
            Miestnost m = miestnosti.get(i);
            if (!m.equals(dungeon.getAktualnaMiestnost())) {
                System.out.println((i+1) + ". " + m.getMeno());
            }
        }

        System.out.print("\nTvoja voľba (0 pre návrat): ");
        int volba = getNumericInput(0, miestnosti.size());

        if (volba != 0) {
            dungeon.pohybDoMiestnosti(miestnosti.get(volba - 1));
        }
    }

    private static void interaguj() {
        Miestnost aktualna = dungeon.getAktualnaMiestnost();
        List<Charakter> postavy = aktualna.getPostavy();

        if (postavy.isEmpty()) {
            System.out.println("V miestnosti nie je s čím interagovať.");
            return;
        }

        System.out.println("\nS kým chceš interagovať?");
        int zivychPostav = 0;

        for (int i = 0; i < postavy.size(); i++) {
            Charakter postava = postavy.get(i);
            if (postava.getZdravie() > 0) {
                System.out.println((i+1) + ". " + postava.getMeno());
                zivychPostav++;
            }
        }

        if (zivychPostav == 0) {
            System.out.println("V miestnosti nie sú žiadne živé postavy.");
            return;
        }

        System.out.print("\nTvoja voľba (0 pre návrat): ");
        int volba = getNumericInput(0, postavy.size());

        if (volba != 0) {
            Charakter vybranaPostava = postavy.get(volba - 1);
            vybranaPostava.interakcia(hrac);
        }
    }

    // Metóda na overenie numerického vstupu
    private static int getNumericInput(int min, int max) {
        int volba = -1;

        while (volba < min || volba > max) {
            try {
                String vstup = scanner.nextLine();
                volba = Integer.parseInt(vstup);

                if (volba < min || volba > max) {
                    System.out.print("Zadaj číslo od " + min + " do " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Zadaj platné číslo: ");
            }
        }

        return volba;
    }

    // Pomocná metóda pre všetky súboje
    public static void bojovySystem(Hrac hrac, Charakter nepriatel) {
        System.out.println("\n=== ZAČÍNA BOJ ===");
        System.out.println("Tvojím súperom je: " + nepriatel.getMeno());

        boolean bojAktivny = true;

        while (bojAktivny && hrac.getZdravie() > 0 && nepriatel.getZdravie() > 0) {
            // Zobrazenie stavu
            System.out.println("\n" + hrac.getMeno() + ": Zdravie=" + hrac.getZdravie() +
                    ", " + nepriatel.getMeno() + ": Zdravie=" + nepriatel.getZdravie());

            // Možnosti hráča
            System.out.println("\nČo urobíš?");
            System.out.println("1. Útok");
            System.out.println("2. Použiť predmet");
            System.out.println("3. Pokus o útek");

            System.out.print("Tvoja voľba: ");
            int volba = getNumericInput(1, 3);

            switch(volba) {
                case 1: // Útok
                    vykonajUtokHraca(hrac, nepriatel);
                    break;
                case 2: // Použitie predmetu
                    if (pouzitPredmet(hrac)) {
                        System.out.println("Použil si predmet.");
                    } else {
                        System.out.println("Nevybral si žiadny predmet, strácaš kolo!");
                    }
                    break;
                case 3: // Útek
                    int hodKockou = random.nextInt(20) + 1;
                    if (hodKockou > 10) {
                        System.out.println("Hod kockou: " + hodKockou + " - Úspešne si utiekol!");
                        bojAktivny = false;
                        return;
                    } else {
                        System.out.println("Hod kockou: " + hodKockou + " - Nepodarilo sa ti utiecť!");
                    }
                    break;
            }

            // Kontrola, či nepriateľ stále žije
            if (nepriatel.getZdravie() <= 0) {
                System.out.println("\n" + nepriatel.getMeno() + " bol porazený!");
                System.out.println("Vyhral si boj!");
                return;
            }

            // Útok nepriateľa
            System.out.println("\n" + nepriatel.getMeno() + " útočí!");
            vykonajUtokNepriatela(nepriatel, hrac);

            // Kontrola, či hráč stále žije
            if (hrac.getZdravie() <= 0) {
                System.out.println("\nPadol si v boji!");
                bojAktivny = false;
            }
        }
    }

    private static void vykonajUtokHraca(Hrac hrac, Charakter nepriatel) {
        int hodKockou = random.nextInt(20) + 1;
        System.out.println("\nHodil si kockou d20: " + hodKockou);

        if (hodKockou >= 10) { // Úspešný útok
            // Výpočet poškodenia: d8 + útočná sila
            int poskodenie = random.nextInt(8) + 1 + hrac.getSila();
            int skutocnePoskodenie = Math.max(1, poskodenie - nepriatel.getObrana());

            System.out.println("Úspešný zásah! Spôsobil si " + skutocnePoskodenie + " bodov poškodenia.");
            nepriatel.setZdravie(nepriatel.getZdravie() - skutocnePoskodenie);
        } else {
            System.out.println("Netrafil si! " + nepriatel.getMeno() + " sa vyhol tvojmu útoku.");
        }
    }

    private static void vykonajUtokNepriatela(Charakter nepriatel, Hrac hrac) {
        int hodKockou = random.nextInt(20) + 1;
        System.out.println(nepriatel.getMeno() + " hodil kockou d20: " + hodKockou);

        if (hodKockou >= 8) { // Úspešný útok
            // Výpočet poškodenia: d6 + útočná sila nepriateľa
            int poskodenie = random.nextInt(6) + 1 + nepriatel.getSila();
            int skutocnePoskodenie = Math.max(1, poskodenie - hrac.getObrana());

            System.out.println("Zasiahol ťa! Utrpel si " + skutocnePoskodenie + " bodov poškodenia.");
            hrac.setZdravie(hrac.getZdravie() - skutocnePoskodenie);
        } else {
            System.out.println("Vyhol si sa! " + nepriatel.getMeno() + " ťa netrafil.");
        }
    }

    private static boolean pouzitPredmet(Hrac hrac) {
        List<Predmet> predmety = hrac.getInventar().getPredmety();

        if (predmety.isEmpty()) {
            System.out.println("Nemáš žiadne predmety!");
            return false;
        }

        System.out.println("\nKtorý predmet chceš použiť?");
        for (int i = 0; i < predmety.size(); i++) {
            System.out.println((i+1) + ". " + predmety.get(i).getMeno());
        }
        System.out.println("0. Späť");

        System.out.print("Tvoja voľba: ");
        int volba = getNumericInput(0, predmety.size());

        if (volba == 0) {
            return false;
        }

        Predmet vybranyPredmet = predmety.get(volba - 1);
        vybranyPredmet.pouzitie(hrac);
        return true;
    }
}