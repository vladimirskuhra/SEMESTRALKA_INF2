import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HadankoveDvere extends NPC implements InteraktivnaPostava {
    private final BattleSystem battleSystem;
    private final Miestnost miestnost;

    public HadankoveDvere(String id, String meno, String popis, Miestnost miestnost, BattleSystem battleSystem) {
        super(id, meno, popis, miestnost, 1, 0, 0);
        this.battleSystem = battleSystem;
        this.miestnost = miestnost;
    }

    @Override
    public void interakcia(Hrac hrac, BattleSystem battleSystem) {
        System.out.println("Pred tebou sú dvoje dvere. Na dlážke ležia dva lístky:");
        System.out.println("Na prvom: 'Za týmito dverami sa nachádza warg.'");
        System.out.println("Na druhom: 'Za oboma dverami sa nachádza warg.'");
        System.out.println("Na stene je odkaz:");
        System.out.println("Len ak je nápis na prvých dverách pravdivý, skrýva sa za nimi lektvar (alebo poklad).");
        System.out.println("Ak je však nepravdivý nápis na druhých dverách, lektvar sa skrýva za nimi.");
        System.out.println("Ktoré dvere otvoríš? (1 alebo 2)");

        Scanner scanner = new Scanner(System.in);
        String odpoved = scanner.nextLine().trim();
        if (odpoved.equals("2")) {
            System.out.println("Správne! Otvoril si dvere číslo 2 a našiel si liečivý elixír.");
            Predmet odpovedPredmet = new SpecialnyPredmet("hadanka_odpoved", "Správna odpoveď na hádanku", "Dôkaz, že si vyriešil hádanku.");
            hrac.getInventar().pridajPredmet(odpovedPredmet);
            hrac.getInventar().pridajPredmet(new Lektvar("lektvar_hadanka", "Liečivý elixír", "Odmena za rozlúštenie hádanky.", 30));
        } else {
            System.out.println("To bola nesprávna voľba! Za dverami sa objavil zúrivý warg... a musíš bojovať.");
            Warg warg = new Warg("warg_hadanka", "Zúrivý warg", "Obrovský hladný warg, ktorý stráži tieto dvere.", miestnost, 70, 14, 6, 0.15, battleSystem);
            List<Nepriatel> nepriatelia = new ArrayList<>();
            nepriatelia.add(warg);
            battleSystem.boj(hrac, nepriatelia);
        }
    }

    @Override
    public void prijmiZasah(int dmg) {
        System.out.println("Dvere sa nedajú poškodiť útokom.");
    }
}