package a.Manageri;

import a.Manageri.Quest.QuestDatabaza;
import a.Manageri.Quest.QuestManager;
import a.Miestnosti.*;
import a.Postavy.Hrac;
import a.Postavy.NPC.Nepriatelia.Goblin;
import a.Postavy.NPC.Nepriatelia.GoblinLukostrelec;
import a.Postavy.NPC.Nepriatelia.Pavuk;
import a.Postavy.NPC.Nepriatelia.Warg;
import a.Postavy.NPC.Specialne.GoblinKral;
import a.Postavy.NPC.Specialne.HadankoveDvere;
import a.Postavy.NPC.Specialne.Obchodnik;
import a.Predmety.Lektvar;
import a.Predmety.Zbran;

import java.util.ArrayList;
import java.util.List;

/**
 * DungeonManager je trieda zodpovedna za vytvaranie a spravu celeho dungeonu.
 * Uklada zoznam miestnosti, vytvara mapu a umiestnuje postavy a predmety.
 * V ramci polymorfizmu pouziva rozne typy miestnosti (Vstupna, Nepriatelska, atd.)
 * Prepojenia: DungeonManager spolupracuje s BattleSystem, QuestDatabaza a QuestManager.
 */
public class DungeonManager {
    private Dungeon dungeon;
    private final BattleSystem battleSystem;
    private final QuestDatabaza questDatabaza;
    private final QuestManager questManager;

    /**
     * Konstruktor prijima odkazy na hlavne manazery, aby mohol vytvarat miestnosti a implementovat logiku hry.
     */
    public DungeonManager(BattleSystem battleSystem, QuestDatabaza questDatabaza, QuestManager questManager) {
        this.battleSystem = battleSystem;
        this.questDatabaza = questDatabaza;
        this.questManager = questManager;
    }

    /**
     * Vrati referenciu na databazu questov.
     */
    public QuestDatabaza getQuestDatabaza() {
        return this.questDatabaza;
    }

    /**
     * Metoda vytvori dungeon (mapu hry) s miestnostami, predmetmi a postavami.
     * Priklady polymorfizmu: kazda miestnost je konkretna podtrieda Miestnost.
     * Algoritmus:
     * 1. Vytvori jednotlive miestnosti a ich objekty.
     * 2. Prepoji miestnosti cez vychody.
     * 3. Prida vsetky miestnosti do zoznamu a nastavi aktualnu miestnost.
     */
    public void vytvorDungeon(Hrac hrac) {
        List<Miestnost> miestnosti = new ArrayList<>();

        // Vytvorenie miestnosti roznych typov (polymorfizmus)
        Miestnost vstup = new VstupnaMiestnost("m1", "Vstup do jaskyne",
                "Stojis pri vchode do temnej jaskyne. Zo stien kvapka voda a vzduch je vlhky.");

        Miestnost chodba = new ChodbaMiestnost("m2", "Dlha chodba",
                "Dlaha tmava chodba, osvetlena len niekolkymi faklami. Na konci vidis rozvetvenie.");

        Miestnost sklad = new PokladovaMiestnost("m3", "Sklad",
                "Miestnost plna bedni a polic. Vyzera to ako sklad goblinov.");
        sklad.pridajPredmet(new Zbran("zbran2", "Goblin tesak", "Ostry zahnuty noz", 8, 8));
        sklad.pridajPredmet(new Lektvar("lektvar2", "Silny liecivy elixir", "Lieciaci elixir s intenzivnym ucinkom", 30));

        Miestnost hadankovaKomnata = new HadankovaMiestnost("m10", "Hadankova komnata",
                "Za dverami tejto zvlastnej miestnosti ta caka skuska rozumu.");
        // Hadankove dvere ako specialna NPC postava
        HadankoveDvere hadankoveDvere = new HadankoveDvere("dvere1", "Hadankove dvere",
                "Masivne dvere s hadankou vyrytou do kamena.", hadankovaKomnata);
        hadankovaKomnata.pridajPostavu(hadankoveDvere);

        Miestnost gobliniTab = new NepriatelskaMiestnost("m4", "Goblin tabor",
                "Mala jaskyna premenena na tabor goblinov. Vidiet niekolko provizornych lozok a ohnisko.");
        gobliniTab.pridajPostavu(new Goblin("goblin1", "Goblin strazca", "Maly goblin so zubkami", null, 30, 5, 2, 0.15));
        gobliniTab.pridajPostavu(new Goblin("goblin2", "Goblin bojovnik", "Vacsi goblin s rapkanom", null, 40, 7, 3, 0.15));

        Miestnost odpocivadlo = new OdpocivadloMiestnost("m5", "Ticha jaskyna",
                "Pokojna jaskyna s malym potocikom. Vyzera to ako bezpecne miesto na odpocinok.");

        Miestnost obchod = new ObchodnaMiestnost("m6", "Goblin trh",
                "Prekvapivo, v tejto jaskyni je maly goblin trh. Jeden z goblinov vyzera priatelsky.");
        obchod.pridajPostavu(
                new Obchodnik("obchodnik1", "Grumli", "Starsi goblin obchodnik", null, 50, 3, 10, this.questDatabaza, this.questManager)
        );

        Miestnost tron = new NepriatelskaMiestnost("m7", "Tronna sien",
                "Velka sien s tronom z kosti. Na trone sedi Goblin kral, obklopeny lukostrelcami.");
        tron.pridajPostavu(
                new GoblinKral("boss1", "Goblin kral", "Mohutny goblin s korunou a zezlom", null, 100, 15, 8, 0.07)
        );
        tron.pridajPostavu(
                new GoblinLukostrelec("archer1", "Goblin lukostrelec", "Chudy goblin s lukom", null, 40, 10, 3, 0.2)
        );
        tron.pridajPostavu(
                new GoblinLukostrelec("archer2", "Goblin lukostrelec", "Chudy goblin s lukom", null, 40, 10, 3, 0.2)
        );

        Miestnost pavuciBrloh = new NepriatelskaMiestnost("m8", "Pavuci brloh",
                "Jaskyna pokryta pavucinami. Zo stropu visia huste siete.");
        pavuciBrloh.pridajPostavu(
                new Pavuk("pavuk1", "Obrovsky pavuk", "Chlpata prisera velkosti psa", null, 60, 12, 4, 0.50)
        );

        Miestnost wargDoupie = new NepriatelskaMiestnost("m9", "Wargovsky brloh",
                "Temna jaskyna plna kosti a zvyskov koristi. Citit zapach seliem.");
        wargDoupie.pridajPostavu(
                new Warg("warg1", "Divy warg", "Velky vlku podobny tvor s ostrymi zubami", null, 70, 14, 6, 0.15)
        );

        // Prepojenie miestnosti cez vychody, vytvara mapu
        vstup.pridajVychod("vychod", chodba);

        chodba.pridajVychod("zapad", vstup);
        chodba.pridajVychod("sever", sklad);
        chodba.pridajVychod("vychod", gobliniTab);
        chodba.pridajVychod("juh", odpocivadlo);

        sklad.pridajVychod("sever", obchod);
        sklad.pridajVychod("juhovychod", gobliniTab);
        sklad.pridajVychod("juh", chodba);

        gobliniTab.pridajVychod("zapad", chodba);
        gobliniTab.pridajVychod("vychod", tron);
        gobliniTab.pridajVychod("juh", hadankovaKomnata);

        odpocivadlo.pridajVychod("sever", chodba);
        odpocivadlo.pridajVychod("vychod", pavuciBrloh);

        obchod.pridajVychod("sever", sklad);

        tron.pridajVychod("zapad", gobliniTab);
        tron.pridajVychod("juh", pavuciBrloh);

        pavuciBrloh.pridajVychod("zapad", odpocivadlo);
        pavuciBrloh.pridajVychod("sever", tron);
        pavuciBrloh.pridajVychod("vychod", wargDoupie);

        wargDoupie.pridajVychod("zapad", pavuciBrloh);

        hadankovaKomnata.pridajVychod("sever", gobliniTab);

        // Pridanie vsetkych miestnosti do zoznamu
        miestnosti.add(vstup);
        miestnosti.add(chodba);
        miestnosti.add(sklad);
        miestnosti.add(gobliniTab);
        miestnosti.add(odpocivadlo);
        miestnosti.add(obchod);
        miestnosti.add(tron);
        miestnosti.add(pavuciBrloh);
        miestnosti.add(wargDoupie);
        miestnosti.add(hadankovaKomnata);

        // Vytvorenie objektu dungeon a nastavenie startovacej miestnosti
        this.dungeon = new Dungeon(miestnosti);
        this.dungeon.nastavAktualnuMiestnost(vstup);
    }

    /**
     * Vrati objekt Dungeon (obsahuje miestnosti, aktualnu miestnost a mapu).
     */
    public Dungeon getDungeon() {
        return this.dungeon;
    }
}