package a;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestDatabaza {
    private final List<Quest> questy = new ArrayList<>();

    public QuestDatabaza(String cestaKSuboru) {
        nacitajQuestyZoSuboru(cestaKSuboru);
    }

    private void nacitajQuestyZoSuboru(String cesta) {
        try (BufferedReader br = new BufferedReader(new FileReader(cesta))) {
            String riadok;
            String nazov = null, popis = null, predmet = null, reward = null;
            int pocet = 0, minLevel = 1;
            while ((riadok = br.readLine()) != null) {
                if (riadok.startsWith("názov:")) {
                    nazov = riadok.substring(6).trim();
                } else if (riadok.startsWith("popis:")) {
                    popis = riadok.substring(6).trim();
                } else if (riadok.startsWith("predmet:")) {
                    predmet = riadok.substring(8).trim();
                } else if (riadok.startsWith("pocet:")) {
                    pocet = Integer.parseInt(riadok.substring(6).trim());
                } else if (riadok.startsWith("minLevel:")) {
                    minLevel = Integer.parseInt(riadok.substring(9).trim());
                } else if (riadok.startsWith("reward:")) {
                    reward = riadok.substring(7).trim();
                } else if (riadok.equals("---")) {
                    this.questy.add(new Quest(nazov, popis, predmet, pocet, minLevel, reward));
                    nazov = popis = predmet = reward = null;
                    pocet = 0;
                    minLevel = 1;
                }
            }
        } catch (IOException e) {
            System.out.println("Chyba pri načítaní questov: " + e.getMessage());
        }
    }

    public List<Quest> getQuestsPreLevel(int level) {
        List<Quest> vhodne = new ArrayList<>();
        for (Quest q : this.questy) {
            if (q.getMinLevel() <= level) vhodne.add(q);
        }
        return vhodne;
    }

    public Quest getNahodnyQuestPreLevel(int level) {
        List<Quest> vhodne = getQuestsPreLevel(level);
        if (vhodne.isEmpty()) return null;
        Random r = new Random();
        return vhodne.get(r.nextInt(vhodne.size()));
    }

    public List<Quest> getVsetkyQuesty() {
        return this.questy;
    }
}