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

        Miestnost vstup = new Miestnost("m1", "Vstup do jaskyne",
                "Stojíš pri vchode do temnej jaskyne. Zo stien kvapká voda a vzduch je vlhký.", TypMiestnosti.VSTUP);

        Miestnost chodba = new Miestnost("m2", "Dlhá chodba",
                "Dlhá tmavá chodba, osvetlená len niekoľkými fakľami. Na konci vidíš rozvetvenie.", TypMiestnosti.CHODBA);

        Miestnost sklad = new Miestnost("m3", "Sklad",
                "Miestnosť plná bední a políc. Vyzerá to ako sklad goblinov.", TypMiestnosti.POKLAD);
        sklad.pridajPredmet(new Zbran("zbran2", "Gobliní tesák", "Ostrý zahnutý nôž", 8, 8));
        sklad.pridajPredmet(new Lektvar("lektvar2", "Silný liečivý elixír", "Liečivý elixír s intenzívnym účinkom", 30));

        // Hádanková miestnosť s dverami
        Miestnost hadankovaKomnata = new Miestnost("m10", "Hádanková komnata",
                "Za dverami tejto zvláštnej miestnosti ťa čaká skúška rozumu.", TypMiestnosti.HADANKOVA);
        HadankoveDvere hadankoveDvere = new HadankoveDvere("dvere1", "Hádankové dvere",
                "Masívne dvere s hádankou vyrytú do kameňa.", hadankovaKomnata, battleSystem);
        hadankovaKomnata.pridajPostavu(hadankoveDvere);

        Miestnost gobliniTab = new Miestnost("m4", "Gobliní tábor",
                "Malá jaskyňa premenená na tábor goblinov. Vidieť niekoľko provizórnych lôžok a ohnisko.", TypMiestnosti.NEPRIATELIA);
        gobliniTab.pridajPostavu(new Goblin("goblin1", "Gobliní strážca", "Malý goblin so zúbkami", null, 30, 5, 2, 0.15));
        gobliniTab.pridajPostavu(new Goblin("goblin2", "Gobliní bojovník", "Väčší goblin s rapkáňom", null, 40, 7, 3, 0.15));

        Miestnost odpocivadlo = new Miestnost("m5", "Tichá jaskyňa",
                "Pokojná jaskyňa s malým potôčikom. Vyzerá to ako bezpečné miesto na odpočinok.", TypMiestnosti.ODPOCIVADLO);

        Miestnost obchod = new Miestnost("m6", "Gobliní trh",
                "Prekvapivo, v tejto jaskyni je malý gobliní trh. Jeden z goblinov vyzerá priateľsky.", TypMiestnosti.OBCHODNIK);
        obchod.pridajPostavu(
                new Obchodnik("obchodnik1", "Grumli", "Starší gobliní obchodník", null, 50, 3, 10, questDatabaza, questManager)
        );

        Miestnost tron = new Miestnost("m7", "Trónna sieň",
                "Veľká sieň s trónom z kostí. Na tróne sedí Gobliní kráľ, obklopený lukostrelcami.", TypMiestnosti.NEPRIATELIA);
        tron.pridajPostavu(
                new GoblinKral("boss1", "Gobliní kráľ", "Mohutný goblin s korunou a žezlom", null, 100, 15, 8,0.07, battleSystem)
        );
        tron.pridajPostavu(
                new GoblinLukostrelec("archer1", "Gobliní lukostrelec", "Chudý goblin s lukom", null, 40, 10, 3, 0.2)
        );
        tron.pridajPostavu(
                new GoblinLukostrelec("archer2", "Gobliní lukostrelec", "Chudý goblin s lukom", null, 40, 10, 3, 0.2)
        );

        Miestnost pavuciBrloh = new Miestnost("m8", "Pavúčí brloh",
                "Jaskyňa pokrytá pavučinami. Zo stropu visia husté siete.", TypMiestnosti.NEPRIATELIA);
        pavuciBrloh.pridajPostavu(
                new Pavuk("pavuk1", "Obrovský pavúk", "Chĺpaty pavúk veľkosti psa", null, 60, 12, 4, 0.50)
        );

        Miestnost wargDoupie = new Miestnost("m9", "Wargie dúpä",
                "Temná jaskyňa plná kostí a zvyškov koristi. Cítiš zápach šeliem.", TypMiestnosti.NEPRIATELIA);
        wargDoupie.pridajPostavu(
                new Warg("warg1", "Divý warg", "Veľký vlku podobný tvor s ostrými zubmi", null, 70, 14, 6, 0.15, battleSystem)
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

        dungeon = new Dungeon(miestnosti);
        dungeon.nastavAktualnuMiestnost(vstup);
    }

    public Dungeon getDungeon() {
        return dungeon;
    }
}