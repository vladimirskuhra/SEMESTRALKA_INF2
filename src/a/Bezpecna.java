package a;

/**
 * Rozhranie pre miestnosti, ktoré sú bezpečné (nehrozí v nich boj ani pasca).
 */
public interface Bezpecna {
    default boolean isBezpecna() {
        return true;
    }
}