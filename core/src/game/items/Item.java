package game.items;

import com.badlogic.gdx.graphics.Color;
import game.entities.GameEntity;
import game.entities.characters.playables.Playable;
import game.utilities.Direction;
import game.utilities.ItemList;
import utilities.FilePaths;
import utilities.Utils;

public abstract class Item extends GameEntity {
    public static final ItemList ITEMS = new ItemList(
            Candy.class,
            Cap.class,
            Crucifix.class,
            Diamond.class,
            Mushroom.class,
            RandomCard.class,
            Shell.class,
            SkeletonMask.class,
            UpCard.class
    );

    protected Playable owner;
    private final ItemQuality quality;
    private final String description;
    private Direction direction;

    protected Item(ItemQuality quality, String description, String textureName, int columns, int rows) {
        super(FilePaths.ITEMS + textureName, columns, rows, 0.4f);
        this.owner = null;
        this.quality = quality;
        this.description = description;
    }

    public void addToOwner() {
        owner.addItem(this);
        applyEffect();
    }

    public void removeFromOwner() {
        owner.removeItem(this);
    }

    protected abstract void applyEffect();

    public abstract Color getItemColor();

    public String getDescription() {
        return description;
    }

    public ItemQuality getQuality() {
        return quality;
    }

    public void changeOwnerTo(Playable owner) {
        this.owner = owner;
    }

    public Playable getOwner() {
        return owner;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public String getName() {
        String s = getClass().getSimpleName();
        return Utils.toCommonText(s);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Item && this.getName().equals(((Item) obj).getName());
    }
}
