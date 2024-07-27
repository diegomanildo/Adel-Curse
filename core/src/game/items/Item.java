package game.items;

import game.characters.playables.Playable;

public abstract class Item {

    public Item() {}

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
}
