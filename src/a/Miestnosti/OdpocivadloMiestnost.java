package a.Miestnosti;

import a.Manageri.BattleSystem;
import a.Postavy.Hrac;

import java.util.Scanner;

/**
 * OdpocivadloMiestnost je špeciálny typ miestnosti, kde si hráč môže bezpečne oddýchnuť a vyliečiť zranenia.
 *
 * Prečo je to takto navrhnuté:
 * - Implementuje rozhranie Bezpecna – v tejto miestnosti nehrozí boj ani pasce.
 * - V interakcii je hráčovi ponúknuté, či si chce oddýchnuť (odpočinok obnoví zdravie).
 * - Ak hráč miestnosť prehľadáva, automaticky si oddýchne a obnoví zdravie.
 * - Regenerácia zdravia je pevne nastavená (napr. +25 bodov), ale nikdy neprekročí maximum (100).
 *
 * OOP princípy:
 * - Polymorfizmus: vlastná implementácia interakcie a prehľadávania pre tento typ miestnosti.
 * - Zapúzdrenie: logika oddychu je uzavretá v privátnej metóde odpocinok().
 * - Rozširiteľnosť: ak by si chcel iný spôsob liečenia alebo alternatívny efekt odpočinku, stačí upraviť odpocinok().
 */
public class OdpocivadloMiestnost extends Miestnost implements Bezpecna {
    public OdpocivadloMiestnost(String id, String meno, String popis) {
        super(id, meno, popis);
    }

    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        System.out.println("Chceš si odpočinúť? (a/n)");
        Scanner scanner = new Scanner(System.in);
        String odp = scanner.nextLine().toLowerCase();
        if (odp.equals("a")) {
            odpocinok(hrac);
        }
    }

    @Override
    public void prehladat(Hrac hrac) {
        System.out.println("Prehľadávaš odpočívadlo. Je tu pokojné miesto na oddych.");
        odpocinok(hrac);
    }

    /**
     * Obnoví hráčovi časť zdravia (maximálne do 100).
     */
    private void odpocinok(Hrac hrac) {
        int maxZdravie = 100;
        int aktualneZdravie = hrac.getZdravie();
        if (aktualneZdravie < maxZdravie) {
            int regeneracia = 25;
            int noveZdravie = Math.min(aktualneZdravie + regeneracia, maxZdravie);
            int vyliecene = noveZdravie - aktualneZdravie;
            hrac.setZdravie(noveZdravie);
            System.out.println("Odpočívaš... Regeneruješ " + vyliecene + " bodov zdravia.");
        } else {
            System.out.println("Tvoje zdravie je už plné.");
        }
    }
}