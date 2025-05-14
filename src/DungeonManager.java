import java.util.ArrayList;
import java.util.List;

public class DungeonManager {
    private Dungeon dungeon;
    private final BattleSystem battleSystem;

    public DungeonManager(BattleSystem battleSystem) {
        this.battleSystem = battleSystem;
    }

    public void vytvorDungeon(Hrac hrac) {
        List<Miestnost> miestnosti = new ArrayList<>();

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
        miestnostSNepriatelmi.pridajPostavu(new Goblin("goblin1", "Gobliní strážca", "Malý goblin so zúbkami", null, 30, 5, 2, battleSystem));
        miestnostSNepriatelmi.pridajPostavu(new Goblin("goblin2", "Gobliní bojovník", "Väčší goblin s rapkáňom", null, 40, 7, 3, battleSystem));

        Miestnost miestnostSOdpocivadlom = new MiestnostSOdpocivadlom("m5", "Tichá jaskyňa",
                "Pokojná jaskyňa s malým potôčikom. Vyzerá to ako bezpečné miesto na odpočinok.", null);

        Miestnost miestnostSObchodnikom = new MiestnostSObchodnikom("m6", "Gobliní trh",
                "Prekvapivo, v tejto jaskyni je malý gobliní trh. Jeden z goblinov vyzerá priateľsky.", null);
        QuestDatabaza databaza = new QuestDatabaza("src/questy.txt");
        miestnostSObchodnikom.pridajPostavu(new Obchodnik("obchodnik1", "Grumli", "Starší gobliní obchodník", null, 50, 3, 10, databaza));

        Miestnost bossovaMiestnost = new MiestnostSNepriatelmi("m7", "Trónna sieň",
                "Veľká sieň s trónom z kostí. Na tróne sedí Gobliní kráľ, obklopený lukostrelcami.", null);
        bossovaMiestnost.pridajPostavu(new GoblinKral("boss1", "Gobliní kráľ", "Mohutný goblin s korunou a žezlom", null, 100, 15, 8, battleSystem));
        bossovaMiestnost.pridajPostavu(new GoblinLukostrelec("archer1", "Gobliní lukostrelec", "Chudý goblin s lukom", null, 40, 10, 3, battleSystem));
        bossovaMiestnost.pridajPostavu(new GoblinLukostrelec("archer2", "Gobliní lukostrelec", "Chudý goblin s lukom", null, 40, 10, 3, battleSystem));

        Miestnost pavuciBrloh = new MiestnostSNepriatelmi("m8", "Pavúčí brloh",
                "Jaskyňa pokrytá pavučinami. Zo stropu visia husté siete.", null);
        pavuciBrloh.pridajPostavu(new Pavuk("pavuk1", "Obrovský pavúk", "Chĺpaty pavúk veľkosti psa", null, 60, 12, 4, battleSystem));

        Miestnost wargDoupie = new MiestnostSNepriatelmi("m9", "Wargie dúpä",
                "Temná jaskyňa plná kostí a zvyškov koristi. Cítiš zápach šeliem.", null);
        wargDoupie.pridajPostavu(new Warg("warg1", "Divý warg", "Veľký vlku podobný tvor s ostrými zubmi", null, 70, 14, 6, battleSystem));

        miestnosti.add(vstup);
        miestnosti.add(chodba);
        miestnosti.add(miestnostSPokladmi);
        miestnosti.add(miestnostSNepriatelmi);
        miestnosti.add(miestnostSOdpocivadlom);
        miestnosti.add(miestnostSObchodnikom);
        miestnosti.add(bossovaMiestnost);
        miestnosti.add(pavuciBrloh);
        miestnosti.add(wargDoupie);

        dungeon = new Dungeon(miestnosti);
        dungeon.nastavAktualnuMiestnost(vstup);
    }

    public Dungeon getDungeon() {
        return dungeon;
    }
}