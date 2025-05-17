package a.Manageri;

import java.util.Scanner;

/**
 * InputUtils je pomocna utilitna trieda, ktora zabezpecuje bezpecne nacitanie
 * ciselneho vstupu od pouzivatela cez konzolu.
 *
 * Preco takyto navrh:
 * - Oddelenie nacitavania a validacie uzivatelskeho vstupu do samostatnej triedy
 *   znamena, ze vsetky manazery a herne triedy mozu jednoducho zavolat tuto metodu,
 *   namiesto opakovania rovnakeho kodu v kazdej casti programu.
 * - Metoda getNumericInput garantuje, ze program nikdy nespadne na zlom vstupnom formate
 *   (napr. ak pouzivatel zada text namiesto cisla) a uzivatel je vzdy vyzvany, kym nezada platne cislo v rozsahu.
 */
public class InputUtils {

    /**
     * Nacita od uzivatela ciselny vstup zo Scanneru v zadanych hraniciach.
     * Ak uzivatel zada necislo alebo cislo mimo rozsah, opakuje vyzvu az kym nesplni podmienky.
     *
     * Algoritmus:
     * 1. Caka na vstup od uzivatela.
     * 2. Skusa ho konvertovat na int.
     * 3. Ak je format zly alebo mimo rozsah, vypise vyzvu a caka znova.
     *
     * @param scanner Scanner, ktory cita vstup od uzivatela
     * @param min minimalna povolena hodnota (vratane)
     * @param max maximalna povolena hodnota (vratane)
     * @return platne cislo v rozsahu <min, max>
     */
    public static int getNumericInput(Scanner scanner, int min, int max) {
        int volba = -1;
        while (volba < min || volba > max) {
            try {
                String vstup = scanner.nextLine(); // nacita vstup od uzivatela
                volba = Integer.parseInt(vstup);   // pokusi sa konvertovat na int
                if (volba < min || volba > max) {
                    System.out.print("Zadaj cislo od " + min + " do " + max + ": ");
                }
            } catch (NumberFormatException e) {
                // Ak nie je mozne konvertovat na int, vypise vyzvu
                System.out.print("Zadaj platne cislo: ");
            }
        }
        return volba;
    }
}