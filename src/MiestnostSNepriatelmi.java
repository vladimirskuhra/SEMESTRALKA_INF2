import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.Scanner;

public class MiestnostSNepriatelmi extends Miestnost {
    public MiestnostSNepriatelmi(String id, String meno, String popis, Position pozicia) {
        super(id, meno, popis, pozicia);
    }

    @Override
    public void prehladat(Hrac hrac) {
        System.out.println("Opatrne sa rozhliadaš po miestnosti...");

        boolean suTuNepriatelia = false;
        for (Charakter postava : postavy) {
            if (postava.getZdravie() > 0 &&
                    (postava instanceof Goblin || postava instanceof GoblinKral ||
                            postava instanceof GoblinLukostrelec || postava instanceof Pavuk ||
                            postava instanceof Warg)) {
                suTuNepriatelia = true;
                break;
            }
        }

        if (suTuNepriatelia) {
            System.out.println("Nemôžeš v kľude prehľadať miestnosť, kým sú tu nepriatelia!");
        } else {
            System.out.println("Prehľadávaš miestnosť po boji...");

            // 50% šanca na nájdenie náhodného predmetu
            boolean nasielPredmet = Math.random() < 0.5;

            if (nasielPredmet) {
                // Vytvor náhodný predmet
                Predmet novyPredmet = vytvorNahodnyPredmet();
                pridajPredmet(novyPredmet);

                System.out.println("Našiel si: " + novyPredmet.getMeno());

                System.out.println("Chceš si ho zobrať? (a/n)");
                Scanner scanner = new Scanner(System.in);
                String odpoved = scanner.nextLine().toLowerCase();

                if (odpoved.equals("a")) {
                    hrac.getInventar().pridajPredmet(novyPredmet);
                    predmety.remove(novyPredmet);
                    System.out.println("Zobral si: " + novyPredmet.getMeno());
                }
            } else {
                System.out.println("Nenašiel si nič zaujímavé.");
            }
        }
    }

    private Predmet vytvorNahodnyPredmet() {
        int nahoda = (int)(Math.random() * 3);

        switch(nahoda) {
            case 0:
                return new Zbran("zbran_random", "Malý dýka", "Ostrý gobliní nôž", 6);
            case 1:
                return new Brnenie("brnenie_random", "Kožená vesta", "Jednoduchá ochrana", 3);
            case 2:
                return new Lektvar("lektvar_random", "Malý lektvar", "Liečivý elixír", 15);
            default:
                return new Lektvar("lektvar_basic", "Liečivý nápoj", "Jednoduchý liečivý nápoj", 10);
        }
    }

    @Override
    public void interakcia(Hrac hrac) {
        System.out.println("V tejto miestnosti cítiš nebezpečenstvo...");

        // Kontrola, či sú v miestnosti nepriateľské postavy
        for (Charakter postava : new ArrayList<>(postavy)) {
            if (postava.getZdravie() > 0 &&
                    (postava instanceof Goblin || postava instanceof GoblinKral ||
                            postava instanceof GoblinLukostrelec || postava instanceof Pavuk ||
                            postava instanceof Warg)) {

                System.out.println("\n" + postava.getMeno() + " sa pripravuje na útok!");
                postava.interakcia(hrac);
                return; // Po interakcii s jedným nepriateľom končíme
            }
        }

        System.out.println("Miestnosť je momentálne bezpečná.");
    }

    @Override
    public void pouzitie(Hrac hrac) {
        // Miestnosť nemožno "použiť"
        System.out.println("Nie je možné použiť miestnosť.");
    }
}