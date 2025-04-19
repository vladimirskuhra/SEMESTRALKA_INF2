import javax.swing.text.Position;
import java.util.Scanner;

public class MiestnostVstup extends Miestnost {
    public MiestnostVstup(String id, String meno, String popis, Position pozicia) {
        super(id, meno, popis, pozicia);
    }

    @Override
    public void prehladat(Hrac hrac) {
        System.out.println("Prechádzaš vstupnú miestnosť. Okrem východov z jaskyne tu nie je nič zvláštne.");
    }

    @Override
    public void interakcia(Hrac hrac) {
        System.out.println("Si pri vstupe do dungonu. Môžeš ísť hlbšie alebo sa vrátiť von.");
        System.out.println("Chceš opustiť dungeon? (a/n)");

        Scanner scanner = new Scanner(System.in);
        String odpoved = scanner.nextLine().toLowerCase();

        if (odpoved.equals("a")) {
            System.out.println("Rozhodol si sa opustiť dungeon. Tvoje dobrodružstvo sa končí...");
            // Tu by bola logika pre ukončenie hry
        } else {
            System.out.println("Rozhodol si sa pokračovať v dobrodružstve.");
        }
    }

    @Override
    public void pouzitie(Hrac hrac) {
        // Miestnosť nemožno "použiť"
        System.out.println("Nie je možné použiť miestnosť.");
    }
}