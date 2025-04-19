import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Obchodnik extends Charakter {
    private List<Predmet> tovar;

    public Obchodnik(String id, String meno, String popis, Position pozicia, int zdravie, int sila, int obrana) {
        super(id, meno, popis, pozicia, zdravie, sila, obrana, TypCharakteru.OBCHODNIK);
        this.tovar = new ArrayList<>();
        vytvorTovar();
    }

    private void vytvorTovar() {
        tovar.add(new Zbran("zbran_shop1", "Kvalitný meč", "Dobre vyvážený meč s ostrou čepeľou", 10));
        tovar.add(new Zbran("zbran_shop2", "Bojová sekera", "Ťažká sekera schopná rozsekať brnenie", 12));
        tovar.add(new Brnenie("brnenie_shop1", "Krúžková košeľa", "Kvalitné krúžkové brnenie", 8));
        tovar.add(new Brnenie("brnenie_shop2", "Kovový štít", "Pevný štít chrániaci pred útokmi", 6));
        tovar.add(new Lektvar("lektvar_shop1", "Silný liečivý elixír", "Koncentrovaný liečivý nápoj", 40));
        tovar.add(new Lektvar("lektvar_shop2", "Elixír odolnosti", "Dočasne zvyšuje odolnosť", 30));
    }

    public List<Predmet> getTovar() {
        return tovar;
    }

    @Override
    public void interakcia(Hrac hrac) {
        System.out.println("\nGobliní obchodník " + getMeno() + " sa na teba škerí.");
        System.out.println("\"Vitaj, dobrodruh! Čo by si rád kúpil alebo predal?\" pýta sa.");

        Scanner scanner = new Scanner(System.in);
        boolean obchodovanieAktivne = true;

        while (obchodovanieAktivne) {
            System.out.println("\nČo chceš robiť?");
            System.out.println("1. Pozrieť tovar na predaj");
            System.out.println("2. Zobrať úlohu");
            System.out.println("3. Premeniť predmety za odmenu");
            System.out.println("4. Odísť od obchodníka");

            System.out.print("Tvoja voľba: ");

            try {
                int volba = Integer.parseInt(scanner.nextLine());

                switch (volba) {
                    case 1:
                        zobrazTovar(hrac);
                        break;
                    case 2:
                        zobrazUlohy(hrac);
                        break;
                    case 3:
                        premienanePredmetov(hrac);
                        break;
                    case 4:
                        System.out.println("\"Príď zase, dobrodruh!\" lúči sa " + getMeno());
                        obchodovanieAktivne = false;
                        break;
                    default:
                        System.out.println("\"Nerozumiem, čo chceš,\" vraví obchodník.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\"Nerozumiem, čo chceš,\" vraví obchodník.");
            }
        }
    }

    private void zobrazTovar(Hrac hrac) {
        System.out.println("\nObchodník ukazuje svoj tovar:");

        if (tovar.isEmpty()) {
            System.out.println("\"Bohužiaľ, momentálne nemám žiadny tovar,\" hovorí obchodník smutne.");
            return;
        }

        for (int i = 0; i < tovar.size(); i++) {
            Predmet predmet = tovar.get(i);
            System.out.println((i+1) + ". " + predmet.getMeno() + " - " + predmet.getPopis() + " (hodnota: " + predmet.getHodnota() + ")");
        }

        System.out.println("\n\"Mám špeciálne ceny pre dobrodruhov ako ty,\" škerí sa obchodník.");
        System.out.println("\"Za každý predmet chcem 2 gobliní uši alebo 3 pavúčie tesáky.\"");

        System.out.println("\nChceš niečo kúpiť? (a/n)");
        Scanner scanner = new Scanner(System.in);
        String odpoved = scanner.nextLine().toLowerCase();

        if (odpoved.equals("a")) {
            System.out.println("\"Bohužiaľ, nevidím u teba žiadne gobliní uši ani pavúčie tesáky,\" vraví obchodník.");
            System.out.println("\"Príď, keď budeš mať čím platiť!\"");
            // Tu by bola implementácia výmeny predmetov
        }
    }

    private void zobrazUlohy(Hrac hrac) {
        System.out.println("\nObchodník ti ponúka úlohy:");
        System.out.println("1. \"Prines mi 5 gobliních uší a odmením ťa špeciálnou zbraňou.\"");
        System.out.println("2. \"Potrebujem 3 pavúčie tesáky na prípravu lieku. Prines mi ich a dostaneš silný lektvar.\"");
        System.out.println("3. \"Ak mi prinesieš klin z koruny Goblinieho kráľa, dám ti to najlepšie brnenie, aké mám.\"");

        System.out.println("\nKtorú úlohu chceš prijať? (1-3, alebo 0 pre žiadnu)");
        Scanner scanner = new Scanner(System.in);

        try {
            int volba = Integer.parseInt(scanner.nextLine());

            if (volba >= 1 && volba <= 3) {
                System.out.println("\"Výborne! Teším sa, keď mi prinesieš potrebné veci,\" hovorí obchodník.");
                // Tu by bola implementácia systému úloh
            } else {
                System.out.println("\"Možno nabudúce,\" povzdychne si obchodník.");
            }
        } catch (NumberFormatException e) {
            System.out.println("\"Nevadí, keď sa rozmyslíš, daj mi vedieť,\" vraví obchodník.");
        }
    }

    private void premienanePredmetov(Hrac hrac) {
        System.out.println("\nObchodník sa pozerá do tvojho inventára:");
        System.out.println("\"Hmm, momentálne nevidím nič, čo by som mohol premeniť na niečo užitočné.\"");
        System.out.println("\"Prines mi špecifické prísady z dungeonu a potom sa dohodneme.\"");

        // Tu by bola implementácia systému premieňania predmetov
    }

    @Override
    public void pouzitie(Hrac hrac) {
        System.out.println("Nemôžeš použiť obchodníka ako predmet.");
    }

    @Override
    public void utok(Utocnik ciel) {
        // Obchodník normálne neútočí, ale bráni sa ak je napadnutý
        if (ciel instanceof Hrac) {
            Hrac hrac = (Hrac) ciel;
            System.out.println("Obchodník " + getMeno() + " vytiahol skrytú dýku na obranu!");
            System.out.println("\"Takto sa odplácaš za moju pohostinnosť?!\" kričí nahnevane.");
            // Tu by bola implementácia obranného boja
        }
    }

    @Override
    public void obrana() {
        System.out.println(getMeno() + " sa skrýva za svojím pultom a volá o pomoc!");
    }
}