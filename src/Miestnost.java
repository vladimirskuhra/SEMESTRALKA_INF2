import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.List;

public abstract class Miestnost implements Interakcia {
    protected String id;
    protected String meno;
    protected String popis;
    protected Position pozicia;
    protected List<Predmet> predmety;
    protected List<Charakter> postavy;

    public Miestnost(String id, String meno, String popis, Position pozicia) {
        this.id = id;
        this.meno = meno;
        this.popis = popis;
        this.pozicia = pozicia;
        this.predmety = new ArrayList<>();
        this.postavy = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getMeno() {
        return meno;
    }

    public String getPopis() {
        return popis;
    }

    public Position getPozicia() {
        return pozicia;
    }

    public List<Predmet> getPredmety() {
        return predmety;
    }

    public List<Charakter> getPostavy() {
        return postavy;
    }

    public abstract void prehladat(Hrac hrac);

    public void pridajPredmet(Predmet predmet) {
        predmety.add(predmet);
    }

    public void pridajPostavu(Charakter postava) {
        postavy.add(postava);
    }
}