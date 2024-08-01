package game.entities.characters.playables;

public class Adel extends Playable {
    public Adel() {
        super("adel/adel.png", "adel/bullet.png");
    }

    @Override
    protected int getInitMaxHp() {
        return 100;
    }

    @Override
    protected int getInitDamage() {
        return 3;
    }
}
