public class Lektvar extends Predmet {
    public Lektvar(String id, String meno, String popis, int mnozstvoLiecenia) {
        super(id, meno, popis, mnozstvoLiecenia);
    }

    @Override
    public void pouzitie(Hrac hrac) {
        int aktualneZdravie = hrac.getZdravie();
        int maxZdravie = 100; // Predpokladáme, že max zdravie je 100
        int mozneHojenie = maxZdravie - aktualneZdravie;
        int hojenie = Math.min(getHodnota(), mozneHojenie);

        if (hojenie > 0) {
            System.out.println("Použil si " + getMeno() + " a vyliečil si sa o " + hojenie + " bodov zdravia.");
            hrac.setZdravie(aktualneZdravie + hojenie);
            // Odstránenie lekváru po použití
            hrac.getInventar().odstranPredmet(this);
        } else {
            System.out.println("Tvoje zdravie je už plné. Nemá zmysel použiť lektvar.");
        }
    }
}