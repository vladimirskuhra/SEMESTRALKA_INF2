public class Hrac extends Charakter {
    public Hrac(String id, String meno, String popis, Miestnost miestnost, int zdravie, int sila, int obrana) {
        super(id, meno, popis, miestnost, zdravie, sila, obrana);
    }

    @Override
    public void utok(Utocnik ciel) {
        if (inventar.getAktivnaZbran() == null) {
            System.out.println("Nemáš žiadnu zbraň, útočíš holými rukami!");
            ciel.prijmiZasah(sila);
        } else {
            int damage = inventar.getAktivnaZbran().getSila() + sila;
            ciel.prijmiZasah(damage);
            System.out.println(getMeno() + " útočí so zbraňou " + inventar.getAktivnaZbran().getMeno()
                    + " a spôsobí " + damage + " škody!");
        }
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