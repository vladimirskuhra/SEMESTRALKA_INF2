import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Miestnost {
    private final String id;
    private final String meno;
    private final String popis;
    private final TypMiestnosti typ;
    private final List<NPC> postavy = new ArrayList<>();
    private final List<Predmet> predmety = new ArrayList<>();
    private boolean prehladana = false;

    // Prepojenie miestností - východy: smer (napr. "sever") -> cieľová miestnosť
    private final Map<String, Miestnost> vychody = new HashMap<>();

    public Miestnost(String id, String meno, String popis, TypMiestnosti typ) {
        this.id = id;
        this.meno = meno;
        this.popis = popis;
        this.typ = typ;
    }

    public String getId() { return id; }
    public String getMeno() { return meno; }
    public String getPopis() { return popis; }
    public TypMiestnosti getTyp() { return typ; }
    public List<NPC> getPostavy() { return postavy; }
    public List<Predmet> getPredmety() { return predmety; }

    // Prepojenie miestností
    public void pridajVychod(String smer, Miestnost miestnost) {
        vychody.put(smer, miestnost);
    }

    public Map<String, Miestnost> getVychody() {
        return vychody;
    }

    // "Bezpečná" je len ak nie je žiadny živý nepriateľ alebo je to ODPOCIVADLO
    public boolean isBezpecna() {
        if (typ == TypMiestnosti.ODPOCIVADLO) {
            return true;
        }
        for (NPC postava : postavy) {
            if (postava instanceof Nepriatel && postava.getZdravie() > 0) {
                return false;
            }
        }
        return true;
    }

    public void pridajPostavu(NPC postava) {
        postavy.add(postava);
        postava.setMiestnost(this);
    }
    public void pridajPredmet(Predmet p) { predmety.add(p); }
    public void odstranPredmet(Predmet predmet) { predmety.remove(predmet); }

    public void prehladat(Hrac hrac) {
        switch (typ) {
            case OBCHODNIK:
                System.out.println("Vidíš obchodníka s rôznym tovarom na predaj.");
                break;
            case ODPOCIVADLO:
                System.out.println("Prehľadávaš odpočívadlo. Je tu pokojné miesto na oddych.");
                odpocinok(hrac);
                break;
            case NEPRIATELIA:
                if (suNepriatelia()) {
                    System.out.println("Nemôžeš v kľude prehľadať miestnosť, kým sú tu nepriatelia!");
                } else {
                    System.out.println("Miestnosť je po boji. Môžeš ju bezpečne prehľadať.");
                    najdiPredmet(hrac);
                }
                break;
            case POKLAD:
                if (!prehladana) {
                    if (!predmety.isEmpty()) {
                        System.out.println("Našiel si poklady:");
                        for (Predmet predmet : predmety) {
                            System.out.println("- " + predmet.getMeno());
                        }
                        System.out.println("Chceš si ich zobrať? (a/n)");
                        Scanner scanner = new Scanner(System.in);
                        String odpo = scanner.nextLine().toLowerCase();
                        if (odpo.equals("a")) {
                            for (Predmet predmet : new ArrayList<>(predmety)) {
                                hrac.getInventar().pridajPredmet(predmet);
                                predmety.remove(predmet);
                                System.out.println("Zobral si: " + predmet.getMeno());
                            }
                        }
                    } else {
                        System.out.println("Nič si nenašiel.");
                    }
                    prehladana = true;
                } else {
                    System.out.println("Túto miestnosť si už prehľadal.");
                }
                break;
            case VSTUP:
                System.out.println("Prechádzaš vstupnú miestnosť. Okrem východov tu nie je nič zvláštne.");
                break;
            case CHODBA:
                System.out.println("Prehľadávaš chodbu. Je to len prechod medzi miestnosťami.");
                break;
            case BEZPECNA:
            default:
                if (predmety.isEmpty()) {
                    System.out.println("V miestnosti si nič nenašiel.");
                } else {
                    System.out.println("V miestnosti nachádzaš:");
                    for (Predmet p : predmety) {
                        System.out.println("- " + p.getMeno() + ": " + p.getPopis());
                    }
                }
                break;
        }
    }

    public void interakcia(Hrac hrac) {
        switch (typ) {
            case OBCHODNIK:
                for (NPC postava : postavy) {
                    if (postava instanceof Obchodnik && postava.getZdravie() > 0) {
                        postava.interakcia(hrac);
                        return;
                    }
                }
                System.out.println("V miestnosti nie je žiadny obchodník.");
                break;
            case ODPOCIVADLO:
                System.out.println("Chceš si odpočinúť? (a/n)");
                Scanner scanner = new Scanner(System.in);
                String odp = scanner.nextLine().toLowerCase();
                if (odp.equals("a")) {
                    odpocinok(hrac);
                }
                break;
            case NEPRIATELIA:
                if (suNepriatelia()) {
                    System.out.println("V tejto miestnosti cítiš nebezpečenstvo...");
                    for (NPC postava : postavy) {
                        if (postava.getZdravie() > 0 && postava instanceof Nepriatel) {
                            System.out.println("\n" + postava.getMeno() + " sa pripravuje na útok!");
                            postava.interakcia(hrac);
                            return;
                        }
                    }
                } else {
                    System.out.println("Miestnosť je momentálne bezpečná.");
                }
                break;
            case POKLAD:
                prehladat(hrac);
                break;
            case VSTUP:
                System.out.println("Si pri vstupe do dungonu. Chceš opustiť dungeon? (a/n)");
                Scanner sc = new Scanner(System.in);
                String odpoved = sc.nextLine().toLowerCase();
                if (odpoved.equals("a")) {
                    System.out.println("Rozhodol si sa opustiť dungeon. Dobrodružstvo končí.");
                } else {
                    System.out.println("Pokračuješ v dobrodružstve.");
                }
                break;
            case CHODBA:
                System.out.println("Chodba, z každej strany vedú ďalšie cesty.");
                break;
            case BEZPECNA:
            default:
                System.out.println("Tu je pokoj, nič zvláštne sa nedeje.");
                break;
        }
    }

    public void pouzitie(Hrac hrac) {
        System.out.println("Nie je možné použiť miestnosť.");
    }

    private boolean suNepriatelia() {
        for (Charakter postava : postavy) {
            if (postava instanceof Nepriatel && postava.getZdravie() > 0) {
                return true;
            }
        }
        return false;
    }

    private void odpocinok(Hrac hrac) {
        int maxZdravie = 100;
        int aktualneZdravie = hrac.getZdravie();
        if (aktualneZdravie < maxZdravie) {
            int regeneracia = 25;
            int noveZdravie = Math.min(aktualneZdravie + regeneracia, maxZdravie);
            int vyliecene = noveZdravie - aktualneZdravie;
            hrac.setZdravie(noveZdravie);
            System.out.println("Odpočívaš... Regeneruješ " + vyliecene + " bodov zdravia.");
        } else {
            System.out.println("Tvoje zdravie je už plné.");
        }
    }

    private void najdiPredmet(Hrac hrac) {
        if (Math.random() < 0.5) {
            Predmet novyPredmet = vytvorNahodnyPredmet();
            pridajPredmet(novyPredmet);
            System.out.println("Našiel si: " + novyPredmet.getMeno());
            System.out.println("Chceš si ho zobrať? (a/n)");
            Scanner scanner = new Scanner(System.in);
            String odpoved = scanner.nextLine().toLowerCase();
            if (odpoved.equals("a")) {
                hrac.getInventar().pridajPredmet(novyPredmet);
                predmety.remove(novyPredmet);
                System.out.println("Zobral si: " + novyPredmet.getMeno());
            }
        } else {
            System.out.println("Nenašiel si nič zaujímavé.");
        }
    }

    private Predmet vytvorNahodnyPredmet() {
        int nahoda = (int)(Math.random() * 3);
        switch(nahoda) {
            case 0:
                return new Zbran("zbran_random", "Malá dýka", "Ostrý gobliní nôž", 6, 6);
            case 1:
                return new Brnenie("brnenie_random", "Kožená vesta", "Jednoduchá ochrana", 3, 2);
            case 2:
            default:
                return new Lektvar("lektvar_random", "Malý lektvar", "Liečivý elixír", 15);
        }
    }
}