// Dedia z nej konkrétne typy: Nepriatel, Obchodnik, QuestGiver, atď.

public abstract class NPC extends Charakter {
    // Prípadne ďalšie špecifické vlastnosti pre všetky NPC
    // (napr. zoznam dialógov, reputácia, stav questov...)

    public NPC(String id, String meno, String popis, Miestnost miestnost, int zdravie, int sila, int obrana) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana);
    }

    // Každé NPC musí polymorfne riešiť interakciu s hráčom

   public abstract void interakcia(Hrac hrac, BattleSystem battleSystem);

    // Ak niektoré NPC vie niečo použiť (napr. elixír na seba), môžeš to tu implementovať,
    // inak nechaj default (nič).

    // Ak je NPC útočná (napr. nepriateľ), bude overrideovať túto metódu;
    // pokojné NPC môže mať prázdnu implementáciu.
    @Override
    public void utok(Utocnik ciel) {
        // Default: nič
    }

    // Obrana – override len ak niektoré NPC má špeciálnu obranu.
    @Override
    public void obrana() {
        // Default: nič
    }
}