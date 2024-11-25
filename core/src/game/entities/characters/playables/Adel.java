package game.entities.characters.playables;

public class Adel extends Playable {
    public Adel() {
        this(0);
    }

    public Adel(int index) {
        super(new Stats(6, 1), "adel/adel" + index + ".png", "adel/bullet.png");
    }
}
