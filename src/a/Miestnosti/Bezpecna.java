package a.Miestnosti;

/**
 * Rozhranie pre miestnosti, ktore su bezpecne (nehrozi v nich boj ani pasca).
 *
 * Preco je to takto navrhnute:
 * - Rozhranie (interface) sluzi ako znacka (marker), ktoru mozu podtriedy miestnosti implementovat,
 *   aby sa dalo lahko zistit, ci je miestnost bezpecna (napr. pre odpocivanie).
 * - Pouziva default metodu (od Javy 8), aby kazda trieda, ktora rozhranie implementuje,
 *   mala automaticky metodu isBezpecna(), ktora vracia true.
 * - Toto all-in-one riesenie je jednoduchsie ako pridavat logiku do kazdej podtriedy.
 *
 * V praxi: Ak chces zistit, ci je miestnost bezpecna, overis 'instanceof Bezpecna' alebo zavolas isBezpecna().
 */
public interface Bezpecna {
    /**
     * Vrati true pre bezpecne miestnosti (implementujuce toto rozhranie).
     */
    default boolean isBezpecna() {
        return true;
    }
}