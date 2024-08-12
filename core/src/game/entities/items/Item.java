package game.entities.items;

import game.entities.characters.playables.Playable;
import game.utilities.MovableObject;

public abstract class Item extends MovableObject {

    public Item(String texturePath, int columns, int rows) {
        super(texturePath, columns, rows, 0.4f);
    }

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

    public abstract void applyEffect(Playable character);

    public abstract ItemQuality getQuality();

    public abstract String getDescription();

    public void removeItem (){}
}
