package a.Manageri.Quest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * QuestDatabaza je trieda, ktora uchovava vsetky questy nacitane zo suboru.
 * Je to centralny zdroj questov pre celu hru.
 * Preco je to takto navrhnute:
 * - Vsetky questy su ulozene na jednom mieste a su nacitane zo suboru, co umoznuje jednoducho menit questy bez zasahu do kodu.
 * - Oddeluje data (questy) od logiky (QuestManager), aby bola aplikacia prehladnejsia a rozsiritelna.
 */
public class QuestDatabaza {
    // Zoznam vsetkych questov (nacitaju sa zo suboru)
    private final List<Quest> questy = new ArrayList<>();

    /**
     * Konstruktor nacita questy zo suboru na zaciatku hry.
     * @param cestaKSuboru cesta k suboru s questami
     */
    public QuestDatabaza(String cestaKSuboru) {
        nacitajQuestyZoSuboru(cestaKSuboru);
    }

    /**
     * Nacitava questy zo suboru.
     * Subor je vo formate (viacero questov oddelenych ---):
     * nazov: ...
     * popis: ...
     * predmet: ...
     * pocet: ...
     * minLevel: ...
     * reward: ...
     * ---
     * Preco je to takto:
     * - Je jednoduche doplnit/odstranit quest bez zmeny kodu, staci upravit subor.
     * - Algoritmus cita riadok po riadku a po kazdom --- vytvori novy quest a prida ho do zoznamu.
     */
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
                    // Reset premennych pre dalsi quest
                    nazov = popis = predmet = reward = null;
                    pocet = 0;
                    minLevel = 1;
                }
            }
        } catch (IOException e) {
            System.out.println("Chyba pri nacitani questov: " + e.getMessage());
        }
    }

    /**
     * Vrati questy vhodne pre zadany level hraca.
     */
    public List<Quest> getQuestsPreLevel(int level) {
        List<Quest> vhodne = new ArrayList<>();
        for (Quest q : this.questy) {
            if (q.getMinLevel() <= level) vhodne.add(q);
        }
        return vhodne;
    }

    /**
     * Vrati nahodny quest vhodny pre dany level hraca.
     */
    public Quest getNahodnyQuestPreLevel(int level) {
        List<Quest> vhodne = getQuestsPreLevel(level);
        if (vhodne.isEmpty()) return null;
        Random r = new Random();
        return vhodne.get(r.nextInt(vhodne.size()));
    }

    /**
     * Vrati vsetky questy v databaze.
     */
    public List<Quest> getVsetkyQuesty() {
        return this.questy;
    }
}