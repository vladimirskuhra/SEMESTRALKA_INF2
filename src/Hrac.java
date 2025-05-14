public class Hrac extends Charakter {
    public Hrac(String id, String meno, String popis, Miestnost miestnost, int zdravie, int sila, int obrana) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana);
    }

    @Override
    public void utok(Utocnik ciel) {
        int damage = sila;
        if (inventar.getAktivnaZbran() != null) {
            damage += inventar.getAktivnaZbran().getSila();
            System.out.println(getMeno() + " útočí so zbraňou " + inventar.getAktivnaZbran().getMeno() + " a spôsobí " + damage + " škody!");
        } else {
            System.out.println("Nemáš žiadnu zbraň, útočíš holými rukami a spôsobíš " + damage + " škody!");
        }
        ciel.prijmiZasah(damage);
    }

    @Override
    public void obrana() {
        // Prípadná špeciálna obrana hráča
    }

    @Override
    public void prijmiZasah(int silaUtoku) {
        int obranaHodnota = obrana;
        if (inventar.getAktivneBrnenie() != null) {
            obranaHodnota += inventar.getAktivneBrnenie().getObrana();
        }
        int zranenie = silaUtoku - obranaHodnota;
        if (zranenie > 0) {
            zdravie -= zranenie;
            System.out.println(getMeno() + " dostal zásah za " + zranenie + " (obrana " + obranaHodnota + ").");
            if (zdravie <= 0) {
                System.out.println(getMeno() + " zomiera! Game Over.");
            }
        } else {
            System.out.println(getMeno() + " odrazil útok, žiadne zranenie.");
        }
    }
}