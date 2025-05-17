package a;

public class HadankovaMiestnost extends Miestnost {
    public HadankovaMiestnost(String id, String meno, String popis) {
        super(id, meno, popis);
    }


    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        // Predpokladáme, že v miestnosti je NPC HadankoveDvere
        for (NPC postava : postavy) {
            if (postava instanceof HadankoveDvere) {
                postava.interakcia(hrac, battleSystem);
                return;
            }
        }
        System.out.println("V miestnosti nie je žiadna hádanka.");
    }
    @Override
    public void prehladat(Hrac hrac) {
        System.out.println("V tejto miestnosti môžeš riešiť hádanku, ale nič iné tu nie je.");
    }
}
