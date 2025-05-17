package a.Manageri.Quest;

import a.Postavy.Hrac;
import a.Predmety.Brnenie;
import a.Predmety.Lektvar;
import a.Predmety.Predmet;
import a.Predmety.Zbran;

import java.util.Iterator;
import java.util.List;

public class QuestManager {
    private final QuestDatabaza questDatabaza;

    public QuestManager(QuestDatabaza questDatabaza) {
        this.questDatabaza = questDatabaza;
    }

    public boolean pridajQuestPreHraca(Hrac hrac, Quest quest) {
        List<Quest> aktivneQuesty = hrac.getAktivneQuesty();
        for (Quest q : aktivneQuesty) {
            if (q.getNazov().equals(quest.getNazov())) {
                System.out.println("Tento quest už máš prijatý.");
                return false;
            }
        }
        aktivneQuesty.add(quest);
        System.out.println("Prijal si quest: " + quest.getNazov());
        return true;
    }

    public void skontrolujSplneneQuesty(Hrac hrac) {
        List<Quest> aktivneQuesty = hrac.getAktivneQuesty();
        Iterator<Quest> it = aktivneQuesty.iterator();

        while (it.hasNext()) {
            Quest quest = it.next();
            if (!quest.isSplneny() && maHracSplnenyQuest(hrac, quest)) {
                quest.setSplneny(true);
                System.out.println("Splnil si quest: " + quest.getNazov() + "!");
                vyplatOdmenu(hrac, quest);
                // it.remove(); // ak chceš splnené questy rovno zmazať
            }
        }
    }

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

    private void vyplatOdmenu(Hrac hrac, Quest quest) {
        String odmena = quest.getReward().toLowerCase();
        if (odmena.contains("exp")) {
            int exp = extrahujCisloZOdmeny(odmena);
            if (exp > 0) {
                hrac.pridajExp(exp);
                System.out.println("Získal si " + exp + " skúseností!");
            }
        } else {
            Predmet odmenenyPredmet = vytvorPredmetPodlaNazvu(odmena);
            if (odmenenyPredmet != null) {
                hrac.getInventar().pridajPredmet(odmenenyPredmet);
                System.out.println("Dostal si odmenu: " + odmenenyPredmet.getMeno());
            } else {
                System.out.println("Dostal si špeciálnu odmenu: " + quest.getReward());
            }
        }
    }

    private int extrahujCisloZOdmeny(String odmena) {
        String[] slova = odmena.split("[^0-9]+");
        for (String s : slova) {
            if (!s.isEmpty()) return Integer.parseInt(s);
        }
        return 0;
    }

    private Predmet vytvorPredmetPodlaNazvu(String nazov) {
        if (nazov.contains("lektvar")) {
            return new Lektvar("lektvar_odmena", "Liečivý elixír", "Odmena za quest", 30);
        } else if (nazov.contains("meč")) {
            return new Zbran("zbran_odmena", "Strieborný meč", "Odmena za quest", 10, 30);
        } else if (nazov.contains("štít")) {
            return new Brnenie("brnenie_odmena", "Oceľový štít", "Odmena za quest", 7, 20);
        }
        return null;
    }

    public void zobrazAktivneQuesty(Hrac hrac) {
        List<Quest> aktivne = hrac.getAktivneQuesty();
        if (aktivne.isEmpty()) {
            System.out.println("Nemáš žiadne aktívne questy.");
            return;
        }
        System.out.println("Tvoje aktívne questy:");
        for (Quest q : aktivne) {
            System.out.println(q);
        }
    }

    public void zobrazVsetkyQuesty() {
        List<Quest> vsetky = this.questDatabaza.getVsetkyQuesty();
        if (vsetky.isEmpty()) {
            System.out.println("V databáze nie sú žiadne questy.");
            return;
        }
        System.out.println("Všetky questy:");
        for (Quest q : vsetky) {
            System.out.println(q);
        }
    }
}