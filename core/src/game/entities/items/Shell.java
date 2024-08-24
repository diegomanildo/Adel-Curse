package game.entities.items;

public class Shell extends Item {
    public Shell() {
        super(ItemQuality.Rare, "+Protection", "shell.png", 2, 1);
    }

    @Override
    protected void applyEffect() {

    }
}
