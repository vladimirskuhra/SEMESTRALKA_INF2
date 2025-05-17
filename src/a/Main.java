package a;

import a.Manageri.Hra;

/**
 * Hlavna trieda aplikacie.
 * Spusta celu hru a zavola startovaci bod.
 */
public class Main {
    /**
     * Metoda main je vstupny bod celeho programu.
     * Vytvori objekt Hra a spusti metodu start().
     */
    public static void main(String[] args) {
        Hra hra = new Hra();
        hra.start();
    }
}