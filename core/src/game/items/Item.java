package game.items;

import com.badlogic.gdx.graphics.Color;
import game.entities.GameEntity;
import game.entities.characters.playables.Playable;
import game.utilities.ItemList;
import utilities.FilePaths;

public abstract class Item extends GameEntity {
    public static final ItemList ITEMS = new ItemList(
            Candy.class,
            Cap.class,
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
    public String getName() {
        String s = getClass().getSimpleName();
        StringBuilder ret = new StringBuilder();
        ret.append(s.charAt(0));

        for (int i = 1; i < s.length(); i++) {
            if (Character.isUpperCase(s.charAt(i))) {
                ret.append(' ');
            }

            ret.append(Character.toLowerCase(s.charAt(i)));
        }

        return ret.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Item && this.getName().equals(((Item) obj).getName());
    }
}
