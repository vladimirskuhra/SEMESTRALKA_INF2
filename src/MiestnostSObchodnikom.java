import javax.swing.text.Position;
import java.util.Scanner;

public class MiestnostSObchodnikom extends Miestnost {
    public MiestnostSObchodnikom(String id, String meno, String popis, Position pozicia) {
        super(id, meno, popis, pozicia);
    }

    @Override
    public void prehladat(Hrac hrac) {
        System.out.println("Prehľadávaš miestnosť s obchodníkom. Vidíš rôzne tovary na predaj.");
    }

    @Override
    public void interakcia(Hrac hrac) {
        // Nájdi obchodníka v miestnosti
        for (Charakter postava : postavy) {
            if (postava instanceof Obchodnik && postava.getZdravie() > 0) {
                postava.interakcia(hrac);
                return;
            }
        }

        System.out.println("V miestnosti aktuálne nie je žiadny obchodník.");
    }

    @Override
    public void pouzitie(Hrac hrac) {
        // Miestnosť nemožno "použiť"
        System.out.println("Nie je možné použiť miestnosť.");
    }
}