import javax.swing.text.Position;

public interface Utocnik {
    void utok(Utocnik ciel);
    void obrana();
    void pohyb(Position novaPozicia);
}