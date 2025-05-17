package a;

import java.util.ArrayList;
import java.util.List;

public class DungeonManager {
    private Dungeon dungeon;
    private final BattleSystem battleSystem;
    private final QuestDatabaza questDatabaza;
    private final QuestManager questManager;

    public DungeonManager(BattleSystem battleSystem, QuestDatabaza questDatabaza, QuestManager questManager) {
        this.battleSystem = battleSystem;
        this.questDatabaza = questDatabaza;
        this.questManager = questManager;
    }

    public QuestDatabaza getQuestDatabaza() {
        return this.questDatabaza;
    }

    public void vytvorDungeon(Hrac hrac) {
        List<Miestnost> miestnosti = new ArrayList<>();

        // Každá miestnosť je konkrétny typ (polymorfizmus)
        Miestnost vstup = new VstupnaMiestnost("m1", "Vstup do jaskyne",
                "Stojíš pri vchode do temnej jaskyne. Zo stien kvapká voda a vzduch je vlhký.");

        Miestnost chodba = new ChodbaMiestnost("m2", "Dlhá chodba",
                "Dlhá tmavá chodba, osvetlená len niekoľkými fakľami. Na konci vidíš rozvetvenie.");

        Miestnost sklad = new PokladovaMiestnost("m3", "Sklad",
                "Miestnosť plná bední a políc. Vyzerá to ako sklad goblinov.");
        sklad.pridajPredmet(new Zbran("zbran2", "Gobliní tesák", "Ostrý zahnutý nôž", 8, 8));
        sklad.pridajPredmet(new Lektvar("lektvar2", "Silný liečivý elixír", "Liečivý elixír s intenzívnym účinkom", 30));

        Miestnost hadankovaKomnata = new HadankovaMiestnost("m10", "Hádanková komnata",
                "Za dverami tejto zvláštnej miestnosti ťa čaká skúška rozumu.");
        HadankoveDvere hadankoveDvere = new HadankoveDvere("dvere1", "Hádankové dvere",
                "Masívne dvere s hádankou vyrytú do kameňa.", hadankovaKomnata);
        hadankovaKomnata.pridajPostavu(hadankoveDvere);

        Miestnost gobliniTab = new NepriatelskaMiestnost("m4", "Gobliní tábor",
                "Malá jaskyňa premenená na tábor goblinov. Vidieť niekoľko provizórnych lôžok a ohnisko.");
        gobliniTab.pridajPostavu(new Goblin("goblin1", "Gobliní strážca", "Malý goblin so zúbkami", null, 30, 5, 2, 0.15));
        gobliniTab.pridajPostavu(new Goblin("goblin2", "Gobliní bojovník", "Väčší goblin s rapkáňom", null, 40, 7, 3, 0.15));

        Miestnost odpocivadlo = new OdpocivadloMiestnost("m5", "Tichá jaskyňa",
                "Pokojná jaskyňa s malým potôčikom. Vyzerá to ako bezpečné miesto na odpočinok.");

        Miestnost obchod = new ObchodnaMiestnost("m6", "Gobliní trh",
                "Prekvapivo, v tejto jaskyni je malý gobliní trh. Jeden z goblinov vyzerá priateľsky.");
        obchod.pridajPostavu(
                new Obchodnik("obchodnik1", "Grumli", "Starší gobliní obchodník", null, 50, 3, 10, this.questDatabaza, this.questManager)
        );

        Miestnost tron = new NepriatelskaMiestnost("m7", "Trónna sieň",
                "Veľká sieň s trónom z kostí. Na tróne sedí Gobliní kráľ, obklopený lukostrelcami.");
        tron.pridajPostavu(
                new GoblinKral("boss1", "Gobliní kráľ", "Mohutný goblin s korunou a žezlom", null, 100, 15, 8, 0.07)
        );
        tron.pridajPostavu(
                new GoblinLukostrelec("archer1", "Gobliní lukostrelec", "Chudý goblin s lukom", null, 40, 10, 3, 0.2)
        );
        tron.pridajPostavu(
                new GoblinLukostrelec("archer2", "Gobliní lukostrelec", "Chudý goblin s lukom", null, 40, 10, 3, 0.2)
        );

        Miestnost pavuciBrloh = new NepriatelskaMiestnost("m8", "Pavúčí brloh",
                "Jaskyňa pokrytá pavučinami. Zo stropu visia husté siete.");
        pavuciBrloh.pridajPostavu(
                new Pavuk("pavuk1", "Obrovský pavúk", "Chĺpaty pavúk veľkosti psa", null, 60, 12, 4, 0.50)
        );

        Miestnost wargDoupie = new NepriatelskaMiestnost("m9", "Wargovsky brloh",
                "Temná jaskyňa plná kostí a zvyškov koristi. Cítiš zápach šeliem.");
        wargDoupie.pridajPostavu(
                new Warg("warg1", "Divý warg", "Veľký vlku podobný tvor s ostrými zubmi", null, 70, 14, 6, 0.15)
        );

        // --- Prepojenie miestností cez východy (príklad mapy) ---
        vstup.pridajVychod("východ", chodba);

        chodba.pridajVychod("západ", vstup);
        chodba.pridajVychod("sever", sklad);
        chodba.pridajVychod("východ", gobliniTab);
        chodba.pridajVychod("juh", odpocivadlo);

        sklad.pridajVychod("sever", obchod);
        sklad.pridajVychod("juhovýchod", gobliniTab);
        sklad.pridajVychod("juh", chodba);

        gobliniTab.pridajVychod("západ", chodba);
        gobliniTab.pridajVychod("východ", tron);
        gobliniTab.pridajVychod("juh", hadankovaKomnata);

        odpocivadlo.pridajVychod("sever", chodba);
        odpocivadlo.pridajVychod("východ", pavuciBrloh);

        obchod.pridajVychod("sever", sklad);

        tron.pridajVychod("západ", gobliniTab);
        tron.pridajVychod("juh", pavuciBrloh);

        pavuciBrloh.pridajVychod("západ", odpocivadlo);
        pavuciBrloh.pridajVychod("sever", tron);
        pavuciBrloh.pridajVychod("východ", wargDoupie);

        wargDoupie.pridajVychod("západ", pavuciBrloh);

        hadankovaKomnata.pridajVychod("sever", gobliniTab);

        // --- Pridanie všetkých miestností do zoznamu ---
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

        this.dungeon = new Dungeon(miestnosti);
        this.dungeon.nastavAktualnuMiestnost(vstup);
    }

    public Dungeon getDungeon() {
        return this.dungeon;
    }
}