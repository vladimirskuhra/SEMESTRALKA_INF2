package a;

import java.util.Scanner;

public class VstupnaMiestnost extends Miestnost implements Bezpecna {
    public VstupnaMiestnost(String id, String meno, String popis) {
        super(id, meno, popis);
    }

    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        System.out.println("Si pri vstupe do dungonu. Chceš opustiť dungeon? (a/n)");
        Scanner sc = new Scanner(System.in);
        String odpoved = sc.nextLine().toLowerCase();
        if (odpoved.equals("a")) {
            System.out.println("Rozhodol si sa opustiť dungeon. Dobrodružstvo končí.");
        } else {
            System.out.println("Pokračuješ v dobrodružstve.");
        }
    }

    @Override
    public void prehladat(Hrac hrac) {
        System.out.println("Prechádzaš vstupnú miestnosť. Okrem východov tu nie je nič zvláštne.");
    }
}