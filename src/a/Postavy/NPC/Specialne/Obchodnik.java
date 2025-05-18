package a.Postavy.NPC.Specialne;

import a.Manageri.BattleSystem;
import a.Manageri.Quest.Quest;
import a.Manageri.Quest.QuestDatabaza;
import a.Manageri.Quest.QuestManager;
import a.Miestnosti.Miestnost;
import a.Postavy.Hrac;
import a.Postavy.NPC.NPC;
import a.Predmety.Brnenie;
import a.Predmety.Lektvar;
import a.Predmety.Predmet;
import a.Predmety.Zbran;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Trieda Obchodnik predstavuje specialne NPC, s ktorym moze hrac obchodovat, brat questy a menit predmety za odmenu.
 * Dedi zo spolocnej triedy NPC a implementuje rozhranie InteraktivnaPostava.
 */
public class Obchodnik extends NPC implements InteraktivnaPostava {
    // Zoznam tovaru, ktory obchodnik ponuka
    private final List<Predmet> tovar;
    // Quest databaza pre ponukanie uloh
    private final QuestDatabaza questDatabaza;
    // Quest manager na spravu uloh hraca
    private final QuestManager questManager;

    /**
     * Konstruktor nastavi obchodnika, jeho tovar a reference na system uloh.
     * @param id unikatny identifikator obchodnika
     * @param meno meno obchodnika
     * @param popis popis
     * @param miestnost miestnost, kde sa nachadza
     * @param zdravie zdravie obchodnika
     * @param sila sila obchodnika
     * @param obrana obrana obchodnika
     * @param databaza quest databaza
     * @param questManager quest manager
     */
    public Obchodnik(String id, String meno, String popis, Miestnost miestnost, int zdravie, int sila, int obrana, QuestDatabaza databaza, QuestManager questManager) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana);
        this.tovar = new ArrayList<>();
        vytvorTovar();
        this.questDatabaza = databaza;
        this.questManager = questManager;
    }

    /**
     * Naplni tovar obchodnika preddefinovanymi predmetmi.
     */
    private void vytvorTovar() {
        this.tovar.add(new Zbran("zbran_shop1", "Kvalitny mec", "Dobre vyvazeny mec s ostrou cepeľou", 10, 10));
        this.tovar.add(new Zbran("zbran_shop2", "Bojova sekera", "Tazka sekera schopna rozsekat brnenie", 10, 12));
        this.tovar.add(new Brnenie("brnenie_shop1", "Kruzková kosela", "Kvalitne kruzkové brnenie", 50, 8));
        this.tovar.add(new Brnenie("brnenie_shop2", "Kovovy stit", "Pevny stit chranici pred utokmi", 6, 2));
        this.tovar.add(new Lektvar("lektvar_shop1", "Silny liecivy elixir", "Koncentrovany liecivy napoj", 40));
        this.tovar.add(new Lektvar("lektvar_shop2", "Elixir odolnosti", "Docasne zvysuje odolnost", 30));
    }

    /**
     * Vrati zoznam tovaru obchodnika.
     * @return zoznam predmetov
     */
    public List<Predmet> getTovar() {
        return this.tovar;
    }

    /**
     * Interakcia s obchodnikom - hlavne menu: nakup, questy, premiena predmetov, odchod, zobrazenie uloh.
     * @param hrac hrac
     * @param battleSystem system na riadenie boja (nepouziva sa priamo)
     */
    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        System.out.println("\nObchodnik " + getMeno() + " sa na teba skeri.");
        System.out.println("\"Vitaj, dobrodruh! Co by si rad kupil alebo predal?\"");

        Scanner scanner = new Scanner(System.in);
        boolean obchodovanieAktivne = true;

        while (obchodovanieAktivne) {
            System.out.println("\nCo chces robit?");
            System.out.println("1. Pozriet tovar na predaj");
            System.out.println("2. Zobrat ulohu");
            System.out.println("3. Premenit predmety za odmenu");
            System.out.println("4. Odist od obchodnika");
            System.out.println("5. Zobrazit moje aktivne ulohy");
            System.out.print("Tvoja volba: ");

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
                        System.out.println("\"Prid zase, dobrodruh!\" luci sa " + getMeno());
                        obchodovanieAktivne = false;
                        break;
                    case 5:
                        this.questManager.zobrazAktivneQuesty(hrac);
                        break;
                    default:
                        System.out.println("\"Nerozumiem, co chces,\" vravi obchodnik.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\"Nerozumiem, co chces,\" vravi obchodnik.");
            }
        }
    }

    /**
     * Zobrazi tovar obchodnika a vypise podmienky nakupu.
     * @param hrac hrac (na buduce moze obsahovat logiku pre platbu)
     */
    private void zobrazTovar(Hrac hrac) {
        System.out.println("\nObchodnik ukazuje svoj tovar:");

        if (this.tovar.isEmpty()) {
            System.out.println("\"Bohuzial, momentalne nemam ziadny tovar,\" hovori obchodnik smutne.");
            return;
        }

        for (int i = 0; i < this.tovar.size(); i++) {
            Predmet predmet = this.tovar.get(i);
            System.out.println((i + 1) + ". " + predmet.getMeno() + " - " + predmet.getPopis() + " (hodnota: " + predmet.getHodnota() + ")");
        }

        System.out.println("\n\"Mam specialne ceny pre dobrodruhov ako ty,\" skeri sa obchodnik.");
        System.out.println("\"Za kazdy predmet chcem 2 goblini usi alebo 3 pavučie tesaky.\"");

        System.out.println("\nChces nieco kupit? (a/n)");
        Scanner scanner = new Scanner(System.in);
        String odpoved = scanner.nextLine().toLowerCase();

        if (odpoved.equals("a")) {
            System.out.println("\"Bohuzial, nevidim u teba ziadne goblini usi ani pavučie tesaky,\" vravi obchodnik.");
            System.out.println("\"Prid, ked budes mat cim platit!\"");
        }
    }

    /**
     * Ponukne hracovi quest z databazy vhodny pre jeho level.
     * Ak hrac suhlasi, prida quest do jeho uloh.
     * @param hrac hrac
     */
    public void ponukniQuest(Hrac hrac) {
        Quest quest = this.questDatabaza.getNahodnyQuestPreLevel(hrac.getLevel());
        if (quest == null) {
            System.out.println("Nemam pre teba ziadne vhodne ulohy.");
            return;
        }

        System.out.println("Obchodnik ti ponuka ulohu:");
        System.out.println(quest.getNazov() + ": " + quest.getPopis());
        System.out.println("Odmena: " + quest.getReward());

        System.out.print("Chces prijat tuto ulohu? (a/n): ");
        Scanner scanner = new Scanner(System.in);
        String odpoved = scanner.nextLine().trim().toLowerCase();
        if (odpoved.equals("a")) {
            this.questManager.pridajQuestPreHraca(hrac, quest);
        } else {
            System.out.println("Mozno nabuduce!");
        }
    }

    /**
     * Zatial len vypise, ze nema co menit, placeholder pre buducu funkcionalitu.
     * @param hrac hrac
     */
    private void premienanePredmetov(Hrac hrac) {
        System.out.println("\nObchodnik sa pozera do tvojho inventara:");
        System.out.println("\"Hmm, momentalne nevidim nic, co by som mohol premenit na nieco uzitocne.\"");
        System.out.println("\"Prines mi specificke prisady z dungeonu a potom sa dohodneme.\"");
    }
}