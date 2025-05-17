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

public class SaveLoadManager {
    private final DungeonManager dungeonManager;
    private final PlayerManager playerManager;

    public SaveLoadManager(DungeonManager dm, PlayerManager pm) {
        this.dungeonManager = dm;
        this.playerManager = pm;
    }

    public void ulozHru(String fileName) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {

            Hrac hrac = this.playerManager.getHrac();
            out.println("[HRAC]");
            out.println(hrac.getMeno());
            out.println(hrac.getZdravie());
            out.println(hrac.getSila());
            out.println(hrac.getObrana());
            out.println(hrac.getLevel());

            out.print("INVENTAR:");
            for (Predmet p : hrac.getInventar().getPredmety()) {
                out.print(p.getMeno() + ",");
            }
            out.println();

            Predmet aktivnaZbran = hrac.getInventar().getAktivnaZbran();
            out.println("AKTIVNA_ZBRAN:" + (aktivnaZbran != null ? aktivnaZbran.getMeno() : ""));

            Predmet aktivneBrnenie = hrac.getInventar().getAktivneBrnenie();
            out.println("AKTIVNE_BRNENIE:" + (aktivneBrnenie != null ? aktivneBrnenie.getMeno() : ""));


            out.println("[QUESTY]");
            for (Quest q : hrac.getAktivneQuesty()) {
                out.println(q.getNazov() + "|" + q.isSplneny());
            }


            out.println("[MIESTNOST]");
            out.println(this.dungeonManager.getDungeon().getAktualnaMiestnost().getId());


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

    public void nacitajHru(String fileName) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            String line;
            Hrac hrac = this.playerManager.getHrac();
            Dungeon dungeon = this.dungeonManager.getDungeon();
            Map<String, Miestnost> miestnostiMap = new HashMap<>();
            for (Miestnost m : dungeon.getMiestnosti()) {
                miestnostiMap.put(m.getId(), m);
            }

            while ((line = in.readLine()) != null) {
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
                    // Aktívna zbraň
                    String zbranLine = in.readLine();
                    if (zbranLine.startsWith("AKTIVNA_ZBRAN:")) {
                        String meno = zbranLine.substring(14).trim();
                        if (!meno.isEmpty()) {
                            Predmet zbran = NajdiPredmetPodlaMena(meno);
                            if (zbran != null) hrac.getInventar().nastavAktivnuZbran((Zbran) zbran);
                        }
                    }
                    // Aktívne brnenie
                    String brnenieLine = in.readLine();
                    if (brnenieLine.startsWith("AKTIVNE_BRNENIE:")) {
                        String meno = brnenieLine.substring(17).trim();
                        if (!meno.isEmpty()) {
                            Predmet brnenie = NajdiPredmetPodlaMena(meno);
                            if (brnenie != null) hrac.getInventar().nastavAktivneBrnenie((Brnenie) brnenie);
                        }
                    }
                }
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
                if (line.equals("[MIESTNOST]")) {
                    String miestnostId = in.readLine();
                    Miestnost aktualna = miestnostiMap.get(miestnostId);
                    if (aktualna != null) {
                        dungeon.nastavAktualnuMiestnost(aktualna);
                    }
                }
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
     * Pomocná metóda – vytvor predmet podľa mena, podľa tvojej logiky.
     */
    private Predmet NajdiPredmetPodlaMena(String meno) {

        switch (meno) {
            case "Gobliní tesák":
                return new Zbran("zbran2", "Gobliní tesák", "Ostrý zahnutý nôž", 8, 8);
            case "Silný liečivý elixír":
                return new Lektvar("lektvar2", "Silný liečivý elixír", "Liečivý elixír s intenzívnym účinkom", 30);
            case "Liečivý elixír":
                return new Lektvar("lektvar_hadanka", "Liečivý elixír", "Odmena za rozlúštenie hádanky.", 30);
            // ... doplň ďalšie podľa potreby ...
            default:
                return null;
        }
    }

    /**
     * Pomocná metóda – nájdi quest podľa názvu zo všetkých questov v databáze
     */
    private Quest najdiQuestPodlaNazvu(String nazov) {
        for (Quest q : this.dungeonManager.getQuestDatabaza().getVsetkyQuesty()) {
            if (q.getNazov().equals(nazov)) return q;
        }
        return null;
    }
}