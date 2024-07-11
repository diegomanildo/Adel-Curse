package gameUtilities;

public class Bullet extends GameAnimation {
    public Bullet(String folderPath, float x, float y, float frameDuration) {
        super(folderPath, x, y, 2, 4, frameDuration);
    }

    public Bullet(String folderPath, float frameDuration) {
        this(folderPath, 0f, 0f, frameDuration);
    }
}
