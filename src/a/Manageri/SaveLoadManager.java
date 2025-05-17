package a.Manageri;

import a.Manageri.Quest.Quest;
import a.Miestnosti.Dungeon;
import a.Miestnosti.Miestnost;
import a.Postavy.Charakter;
import a.Postavy.Hrac;
import a.Predmety.Brnenie;
import a.Predmety.Lektvar;
import a.Predmety.Predmet;
import a.Predmety.Zbran;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * SaveLoadManager je trieda zodpovedna za ukladanie a nacitavanie celeho stavu hry do/z suboru.
 * Pomocou tejto triedy je mozne ulozit progres hraca, questy, stav miestnosti, predmetov a postav,
 * a potom vsetko nacitat spat pri obnove hry.
 *
 * Preco je to takto navrhnute:
 * - Vsetky podstatne objekty (hrac, dungeon, miestnosti) su spravovane cez managerov (DungeonManager, PlayerManager).
 *   SaveLoadManager dostane tieto manageri v konstruktore, aby mal pristup k vsetkym potrebnym datam.
 * - Ukladanie i nacitavanie je rozdelene do sekcii ([HRAC], [MIESTNOST] ...) aby bola struktura v subore prehladna,
 *   a aby bola obnova stavu jednoduchsia a robustnejsia.
 * - Pomocne metody na vyhladanie predmetu/questu podla mena zabezpecuju, ze objekty su pri nacitavani spravne vytvorene podla mena zo save suboru.
 */
public class SaveLoadManager {
    private final DungeonManager dungeonManager;
    private final PlayerManager playerManager;

    /**
     * Konstruktor - prepojenie na managerov (aby SaveLoadManager vedel manipulovat s dungeon aj hracom).
     */
    public SaveLoadManager(DungeonManager dm, PlayerManager pm) {
        this.dungeonManager = dm;
        this.playerManager = pm;
    }

    /**
     * Ulozi aktualny stav hry do textoveho suboru.
     * Ulozi vsetky klucove informacie: hraca, inventar, aktivne zbrane/brnenia, questy, stav miestnosti, predmetov, postav.
     * Preco je to takto:
     * - Zapisuje sa vsetko, co moze ovplyvnit stav hry po reloadnuti.
     * - Pouziva sa jednoducha textova struktura s oddelovacmi (sekcie v hranatych zatvorkach).
     */
    public void ulozHru(String fileName) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {

            Hrac hrac = this.playerManager.getHrac();
            // Uloz hraca a jeho statistiky
            out.println("[HRAC]");
            out.println(hrac.getMeno());
            out.println(hrac.getZdravie());
            out.println(hrac.getSila());
            out.println(hrac.getObrana());
            out.println(hrac.getLevel());

            // Uloz inventar
            out.print("INVENTAR:");
            for (Predmet p : hrac.getInventar().getPredmety()) {
                out.print(p.getMeno() + ",");
            }
            out.println();

            // Uloz info o aktivnej zbrani a brneni
            Predmet aktivnaZbran = hrac.getInventar().getAktivnaZbran();
            out.println("AKTIVNA_ZBRAN:" + (aktivnaZbran != null ? aktivnaZbran.getMeno() : ""));

            Predmet aktivneBrnenie = hrac.getInventar().getAktivneBrnenie();
            out.println("AKTIVNE_BRNENIE:" + (aktivneBrnenie != null ? aktivneBrnenie.getMeno() : ""));

            // Uloz stav questov
            out.println("[QUESTY]");
            for (Quest q : hrac.getAktivneQuesty()) {
                out.println(q.getNazov() + "|" + q.isSplneny());
            }

            // Uloz aktualnu miestnost
            out.println("[MIESTNOST]");
            out.println(this.dungeonManager.getDungeon().getAktualnaMiestnost().getId());

            // Uloz rozlozenie predmetov v miestnostiach
            out.println("[MIESTNOSTI_PREDMETY]");
            for (Miestnost m : this.dungeonManager.getDungeon().getMiestnosti()) {
                if (!m.getPredmety().isEmpty()) {
                    out.print(m.getId() + ":");
                    for (Predmet p : m.getPredmety()) {
                        out.print(p.getMeno() + ",");
                    }
                    out.println();
                }
            }

            // Uloz stav postav v miestnostiach
            out.println("[MIESTNOSTI_POSTAVY]");
            for (Miestnost m : this.dungeonManager.getDungeon().getMiestnosti()) {
                if (!m.getPostavy().isEmpty()) {
                    out.print(m.getId() + ":");
                    for (Charakter c : m.getPostavy()) {
                        out.print(c.getMeno() + "|" + c.getZdravie() + ",");
                    }
                    out.println();
                }
            }
        }
    }

    /**
     * Nacita stav hry zo suboru a obnovi vsetky dolezite objekty
     * (hrac, questy, inventar, aktualna miestnost, stav predmetov a postav).
     * Preco je to takto:
     * - Nacitava sa po sekciach, kazda sekcia je jasne oddelena v subore.
     * - Pri nacitavani sa vyuzivaju pomocne metody na vyhladanie objektov podla mena,
     *   aby sa obnovil spravny typ predmetu/questu.
     */
    public void nacitajHru(String fileName) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            String line;
            Hrac hrac = this.playerManager.getHrac();
            Dungeon dungeon = this.dungeonManager.getDungeon();
            // Pre rychle vyhladanie miestnosti podla ID
            Map<String, Miestnost> miestnostiMap = new HashMap<>();
            for (Miestnost m : dungeon.getMiestnosti()) {
                miestnostiMap.put(m.getId(), m);
            }

            while ((line = in.readLine()) != null) {
                // Nacitanie sekcie o hracovi
                if (line.equals("[HRAC]")) {
                    hrac.setMeno(in.readLine());
                    hrac.setZdravie(Integer.parseInt(in.readLine()));
                    hrac.setSila(Integer.parseInt(in.readLine()));
                    hrac.setObrana(Integer.parseInt(in.readLine()));
                    int level = Integer.parseInt(in.readLine());
                    hrac.setLevel(level);
                    String invLine = in.readLine();
                    if (invLine.startsWith("INVENTAR:")) {
                        hrac.getInventar().getPredmety().clear();
                        String[] predmety = invLine.substring(9).split(",");
                        for (String meno : predmety) {
                            if (!meno.trim().isEmpty()) {
                                Predmet p = NajdiPredmetPodlaMena(meno.trim());
                                if (p != null) hrac.getInventar().pridajPredmet(p);
                            }
                        }
                    }
                    // Nastavenie aktivnej zbrane
                    String zbranLine = in.readLine();
                    if (zbranLine.startsWith("AKTIVNA_ZBRAN:")) {
                        String meno = zbranLine.substring(14).trim();
                        if (!meno.isEmpty()) {
                            Predmet zbran = NajdiPredmetPodlaMena(meno);
                            if (zbran != null) hrac.getInventar().nastavAktivnuZbran((Zbran) zbran);
                        }
                    }
                    // Nastavenie aktivneho brnenia
                    String brnenieLine = in.readLine();
                    if (brnenieLine.startsWith("AKTIVNE_BRNENIE:")) {
                        String meno = brnenieLine.substring(17).trim();
                        if (!meno.isEmpty()) {
                            Predmet brnenie = NajdiPredmetPodlaMena(meno);
                            if (brnenie != null) hrac.getInventar().nastavAktivneBrnenie((Brnenie) brnenie);
                        }
                    }
                }
                // Nacitanie questov
                if (line.equals("[QUESTY]")) {
                    hrac.getAktivneQuesty().clear();
                    while ((line = in.readLine()) != null && !line.startsWith("[")) {
                        if (line.trim().isEmpty()) continue;
                        String[] questData = line.split("\\|");
                        String nazov = questData[0];
                        boolean splneny = questData.length > 1 && Boolean.parseBoolean(questData[1]);
                        Quest quest = najdiQuestPodlaNazvu(nazov);
                        if (quest != null) {
                            quest.setSplneny(splneny);
                            hrac.getAktivneQuesty().add(quest);
                        }
                    }
                }
                // Obnova aktualnej miestnosti
                if (line.equals("[MIESTNOST]")) {
                    String miestnostId = in.readLine();
                    Miestnost aktualna = miestnostiMap.get(miestnostId);
                    if (aktualna != null) {
                        dungeon.nastavAktualnuMiestnost(aktualna);
                    }
                }
                // Obnova predmetov v miestnostiach
                if (line.equals("[MIESTNOSTI_PREDMETY]")) {
                    while ((line = in.readLine()) != null && !line.startsWith("[")) {
                        String[] parts = line.split(":");
                        if (parts.length == 2) {
                            Miestnost m = miestnostiMap.get(parts[0]);
                            if (m != null) {
                                m.getPredmety().clear();
                                String[] predmety = parts[1].split(",");
                                for (String meno : predmety) {
                                    if (!meno.trim().isEmpty()) {
                                        Predmet p = NajdiPredmetPodlaMena(meno.trim());
                                        if (p != null) m.pridajPredmet(p);
                                    }
                                }
                            }
                        }
                    }
                }
                // Obnova stavu postav v miestnostiach
                if (line != null && line.equals("[MIESTNOSTI_POSTAVY]")) {
                    while ((line = in.readLine()) != null && !line.startsWith("[")) {
                        String[] parts = line.split(":");
                        if (parts.length == 2) {
                            Miestnost m = miestnostiMap.get(parts[0]);
                            if (m != null) {
                                String[] charakters = parts[1].split(",");
                                for (String ch : charakters) {
                                    if (!ch.trim().isEmpty()) {
                                        String[] cdata = ch.split("\\|");
                                        String meno = cdata[0];
                                        int zdravie = Integer.parseInt(cdata[1]);
                                        for (Charakter c : m.getPostavy()) {
                                            if (c.getMeno().equals(meno)) {
                                                c.setZdravie(zdravie);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Pomocna metoda - na zaklade mena z textu vytvori prislusny objekt typu Predmet.
     * Preco je to takto:
     * - Umozni jednoduche rozsirene v buducnosti, len pridanim dalsieho case.
     * - Pri nacitavani save suboru je jednoznacne, podla mena, vratena spravna instancia.
     */
    private Predmet NajdiPredmetPodlaMena(String meno) {
        switch (meno) {
            case "Gobliní tesák":
                return new Zbran("zbran2", "Gobliní tesák", "Ostrý zahnutý nôž", 8, 8);
            case "Silný liečivý elixír":
                return new Lektvar("lektvar2", "Silný liečivý elixír", "Liečivý elixír s intenzívnym účinkom", 30);
            case "Liečivý elixír":
                return new Lektvar("lektvar_hadanka", "Liečivý elixír", "Odmena za rozlúštenie hádanky.", 30);
            // ... doplnime dalsie podla potreby ...
            default:
                return null;
        }
    }

    /**
     * Pomocna metoda - podla nazvu quest vrat instanciu z databazy questov.
     * Preco je to takto:
     * - Vsetky questy su v QuestDatabaza, takto sa garantuje, ze sa nepomiesa objekt.
     * - Ak quest v save subore neexistuje v databaze, jednoducho sa ignoruje.
     */
    private Quest najdiQuestPodlaNazvu(String nazov) {
        for (Quest q : this.dungeonManager.getQuestDatabaza().getVsetkyQuesty()) {
            if (q.getNazov().equals(nazov)) return q;
        }
        return null;
    }
}