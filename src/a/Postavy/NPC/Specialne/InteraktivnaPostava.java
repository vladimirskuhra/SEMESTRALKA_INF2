package a.Postavy.NPC.Specialne;

import a.Manageri.BattleSystem;
import a.Postavy.Hrac;

/**
 * Rozhranie InteraktivnaPostava definuje postavu, s ktorou moze hrac interagovat
 * cez metodu interakcia. Moze ist o NPC, ktore poskytuje dialog, quest alebo suboj.
 */
public interface InteraktivnaPostava {

    /**
     * Metoda interakcia urcuje, co sa stane, ked hrac interaguje s touto postavou.
     * @param hrac hrac, ktory interaguje
     * @param battleSystem system na riadenie boja (ak je relevantny)
     */
    void interakcia(Hrac hrac, BattleSystem battleSystem);
}