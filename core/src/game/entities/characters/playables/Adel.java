package game.entities.characters.playables;

import com.badlogic.gdx.graphics.Color;
import game.screens.MultiplayerGameScreen;

public class Adel extends Playable {
    public Adel() {
        this(0);
    }

    public Adel(int index) {
        super(new Stats(6, 1), "adel/adel" + index + ".png", "adel/bullet" + index + ".png");

        if (MultiplayerGameScreen.client != null) {
            switch (index) {
                case 0:
                    hpLabel.setColor(new Color(0xadd8e6));
                    break;
                case 1:
                    hpLabel.setColor(Color.GREEN);
                    break;
                case 2:
                    hpLabel.setColor(Color.RED);
                    break;
                default:
                    throw new RuntimeException("Invalid Adel skin index: " + index);
            }
        }
    }
}
