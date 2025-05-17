package a;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Obchodnik extends NPC implements InteraktivnaPostava {
    private final List<Predmet> tovar;
    private final QuestDatabaza questDatabaza;
    private final QuestManager questManager;

    public Obchodnik(String id, String meno, String popis, Miestnost miestnost, int zdravie, int sila, int obrana, QuestDatabaza databaza, QuestManager questManager) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana);
        this.tovar = new ArrayList<>();
        vytvorTovar();
        this.questDatabaza = databaza;
        this.questManager = questManager;
    }


    private void vytvorTovar() {
        this.tovar.add(new Zbran("zbran_shop1", "Kvalitný meč", "Dobre vyvážený meč s ostrou čepeľou", 10, 10));
        this.tovar.add(new Zbran("zbran_shop2", "Bojová sekera", "Ťažká sekera schopná rozsekať brnenie", 10, 12));
        this.tovar.add(new Brnenie("brnenie_shop1", "Krúžková košeľa", "Kvalitné krúžkové brnenie", 50, 8));
        this.tovar.add(new Brnenie("brnenie_shop2", "Kovový štít", "Pevný štít chrániaci pred útokmi", 6, 2));
        this.tovar.add(new Lektvar("lektvar_shop1", "Silný liečivý elixír", "Koncentrovaný liečivý nápoj", 40));
        this.tovar.add(new Lektvar("lektvar_shop2", "Elixír odolnosti", "Dočasne zvyšuje odolnosť", 30));
    }

    public List<Predmet> getTovar() {
        return this.tovar;
    }

    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        System.out.println("\nObchodník " + getMeno() + " sa na teba škerí.");
        System.out.println("\"Vitaj, dobrodruh! Čo by si rád kúpil alebo predal?\"");

        Scanner scanner = new Scanner(System.in);
        boolean obchodovanieAktivne = true;

        while (obchodovanieAktivne) {
            System.out.println("\nČo chceš robiť?");
            System.out.println("1. Pozrieť tovar na predaj");
            System.out.println("2. Zobrať úlohu");
            System.out.println("3. Premeniť predmety za odmenu");
            System.out.println("4. Odísť od obchodníka");
            System.out.println("5. Zobraziť moje aktívne úlohy");
            System.out.print("Tvoja voľba: ");

            try {
                int volba = Integer.parseInt(scanner.nextLine());

                switch (volba) {
                    case 1:
                        zobrazTovar(hrac);
                        break;
                    case 2:
                        ponukniQuest(hrac);
                        break;
                    case 3:
                        premienanePredmetov(hrac);
                        break;
                    case 4:
                        System.out.println("\"Príď zase, dobrodruh!\" lúči sa " + getMeno());
                        obchodovanieAktivne = false;
                        break;
                    case 5:
                        this.questManager.zobrazAktivneQuesty(hrac);
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

        if (this.tovar.isEmpty()) {
            System.out.println("\"Bohužiaľ, momentálne nemám žiadny tovar,\" hovorí obchodník smutne.");
            return;
        }

        for (int i = 0; i < this.tovar.size(); i++) {
            Predmet predmet = this.tovar.get(i);
            System.out.println((i + 1) + ". " + predmet.getMeno() + " - " + predmet.getPopis() + " (hodnota: " + predmet.getHodnota() + ")");
        }

        System.out.println("\n\"Mám špeciálne ceny pre dobrodruhov ako ty,\" škerí sa obchodník.");
        System.out.println("\"Za každý predmet chcem 2 gobliní uši alebo 3 pavúčie tesáky.\"");

        System.out.println("\nChceš niečo kúpiť? (a/n)");
        Scanner scanner = new Scanner(System.in);
        String odpoved = scanner.nextLine().toLowerCase();

        if (odpoved.equals("a")) {
            System.out.println("\"Bohužiaľ, nevidím u teba žiadne gobliní uši ani pavúčie tesáky,\" vraví obchodník.");
            System.out.println("\"Príď, keď budeš mať čím platiť!\"");
        }
    }

    public void ponukniQuest(Hrac hrac) {
        Quest quest = this.questDatabaza.getNahodnyQuestPreLevel(hrac.getLevel());
        if (quest == null) {
            System.out.println("Nemám pre teba žiadne vhodné úlohy.");
            return;
        }

        System.out.println("Obchodník ti ponúka úlohu:");
        System.out.println(quest.getNazov() + ": " + quest.getPopis());
        System.out.println("Odmena: " + quest.getReward());

        System.out.print("Chceš prijať túto úlohu? (a/n): ");
        Scanner scanner = new Scanner(System.in);
        String odpoved = scanner.nextLine().trim().toLowerCase();
        if (odpoved.equals("a")) {
            this.questManager.pridajQuestPreHraca(hrac, quest);
        } else {
            System.out.println("Možno nabudúce!");
        }
    }

    private void premienanePredmetov(Hrac hrac) {
        System.out.println("\nObchodník sa pozerá do tvojho inventára:");
        System.out.println("\"Hmm, momentálne nevidím nič, čo by som mohol premeniť na niečo užitočné.\"");
        System.out.println("\"Prines mi špecifické prísady z dungeonu a potom sa dohodneme.\"");
    }
}