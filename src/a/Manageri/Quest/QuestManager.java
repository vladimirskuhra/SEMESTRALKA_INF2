package a.Manageri.Quest;

import a.Postavy.Hrac;
import a.Predmety.Brnenie;
import a.Predmety.Lektvar;
import a.Predmety.Predmet;
import a.Predmety.Zbran;

import java.util.Iterator;
import java.util.List;

/**
 * QuestManager je trieda, ktora spravuje questy (ulohy) pre hraca.
 * Umoznuje pridavat questy, kontrolovat ich splnenie, vyplacat odmeny za splnene questy
 * a zobrazovat aktivne questy hraca.
 *
 * Prepojenie: Spolupracuje s QuestDatabaza (databaza vsetkych questov) a s objektom Hrac,
 * ktory si uklada svoje aktivne questy.
 */
public class QuestManager {

    /**
     * Konstruktor. Mozes si tu ulozit referenciu na QuestDatabaza, ak by si v buducnosti chcel hladat questy globalne.
     */
    public QuestManager(QuestDatabaza questDatabaza) {
        // V tejto implementacii sa databaza questov neuklada,
        // ale mozes si ju ulozit ako atribut a pouzit neskor.
    }

    /**
     * Prida quest hracovi, ak ho este nema aktivny.
     * Ak uz quest ma, vypise upozornenie.
     */
    public boolean pridajQuestPreHraca(Hrac hrac, Quest quest) {
        List<Quest> aktivneQuesty = hrac.getAktivneQuesty();
        for (Quest q : aktivneQuesty) {
            if (q.getNazov().equals(quest.getNazov())) {
                System.out.println("Tento quest uz mas prijaty.");
                return false;
            }
        }
        aktivneQuesty.add(quest);
        System.out.println("Prijal si quest: " + quest.getNazov());
        return true;
    }

    /**
     * Skontroluje vsetky aktivne questy hraca, ci su splnene.
     * Ak je quest splneny, nastavi priznak, vyplati odmenu a vypise spravu.
     */
    public void skontrolujSplneneQuesty(Hrac hrac) {
        List<Quest> aktivneQuesty = hrac.getAktivneQuesty();
        Iterator<Quest> it = aktivneQuesty.iterator();

        while (it.hasNext()) {
            Quest quest = it.next();
            // Ak quest este nie je splneny a hrac splnil podmienky, odmeni ho
            if (!quest.isSplneny() && maHracSplnenyQuest(hrac, quest)) {
                quest.setSplneny(true);
                System.out.println("Splnil si quest: " + quest.getNazov() + "!");
                vyplatOdmenu(hrac, quest);
                // it.remove(); // ak chces splnene questy rovno zmazat z aktivnych
            }
        }
    }

    /**
     * Zisti, ci hrac splnil quest podla toho, kolko ma v inventari cieloveho predmetu.
     */
    private boolean maHracSplnenyQuest(Hrac hrac, Quest quest) {
        String cielovyPredmet = quest.getCielovyPredmet();
        int cielovyPocet = quest.getCielovyPocet();

        int pocet = 0;
        for (Predmet p : hrac.getInventar().getPredmety()) {
            if (p.getMeno().equalsIgnoreCase(cielovyPredmet)) {
                pocet++;
            }
        }
        return pocet >= cielovyPocet;
    }

    /**
     * Vyplati odmenu za splneny quest. Podporuje exp alebo predmety.
     * Ak je v odmena "exp", prida skusenosti, inak vytvori predmet podla nazvu.
     */
    private void vyplatOdmenu(Hrac hrac, Quest quest) {
        String odmena = quest.getReward().toLowerCase();
        if (odmena.contains("exp")) {
            int exp = extrahujCisloZOdmeny(odmena);
            if (exp > 0) {
                hrac.pridajExp(exp);
                System.out.println("Ziskal si " + exp + " skusenosti!");
            }
        } else {
            Predmet odmenenyPredmet = vytvorPredmetPodlaNazvu(odmena);
            if (odmenenyPredmet != null) {
                hrac.getInventar().pridajPredmet(odmenenyPredmet);
                System.out.println("Dostal si odmenu: " + odmenenyPredmet.getMeno());
            } else {
                System.out.println("Dostal si specialnu odmenu: " + quest.getReward());
            }
        }
    }

    /**
     * Extrahuje cislo (pocet exp) z retazca s odmenou ("30exp" => 30).
     */
    private int extrahujCisloZOdmeny(String odmena) {
        String[] slova = odmena.split("[^0-9]+");
        for (String s : slova) {
            if (!s.isEmpty()) return Integer.parseInt(s);
        }
        return 0;
    }

    /**
     * Vytvori predmet podla nazvu v odmenovom retazci. Podporuje lektvar, mec, stit.
     */
    private Predmet vytvorPredmetPodlaNazvu(String nazov) {
        if (nazov.contains("lektvar")) {
            return new Lektvar("lektvar_odmena", "Liecivy elixir", "Odmena za quest", 30);
        } else if (nazov.contains("mec")) {
            return new Zbran("zbran_odmena", "Strieborny mec", "Odmena za quest", 10, 30);
        } else if (nazov.contains("stit")) {
            return new Brnenie("brnenie_odmena", "Ocelovy stit", "Odmena za quest", 7, 20);
        }
        return null;
    }

    /**
     * Vypise vsetky aktivne questy hraca.
     */
    public void zobrazAktivneQuesty(Hrac hrac) {
        List<Quest> aktivne = hrac.getAktivneQuesty();
        if (aktivne.isEmpty()) {
            System.out.println("Nemas ziadne aktivne questy.");
            return;
        }
        System.out.println("Tvoje aktivne questy:");
        for (Quest q : aktivne) {
            System.out.println(q);
        }
    }

}