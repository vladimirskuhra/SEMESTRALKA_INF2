package a.Postavy.NPC;

import a.Manageri.BattleSystem;
import a.Miestnosti.Miestnost;
import a.Postavy.Charakter;
import a.Postavy.Hrac;

/**
 * Abstraktna trieda NPC (Non-Player Character) predstavuje zaklad pre vsetky nehracske postavy v hre.
 * Dedi zo spolocnej triedy Charakter a pridava abstraktnu metodu pre interakciu s hracom.
 */
public abstract class NPC extends Charakter {

    /**
     * Konstruktor nastavi vsetky zakladne atributy NPC postavy.
     * @param id unikatny identifikator postavy
     * @param meno meno NPC
     * @param popis popis NPC
     * @param miestnost miestnost, v ktorej sa NPC nachadza
     * @param zdravie pociatocne zdravie NPC
     * @param sila pociatocna sila NPC
     * @param obrana pociatocna obrana NPC
     */
    public NPC(String id, String meno, String popis, Miestnost miestnost, int zdravie, int sila, int obrana) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana);
    }

    /**
     * Abstraktna metoda, ktoru musi implementovat kazdy potomok.
     * Definuje sposob interakcie NPC s hracom a pripadne so subojovym systemom.
     * @param hrac instancia hraca, s ktorym NPC interaguje
     * @param battleSystem subojovy system, ak je relevantny pre interakciu
     */
    public abstract void interakcia(Hrac hrac, BattleSystem battleSystem);
}