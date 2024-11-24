package game.screens;

import game.entities.characters.playables.Adel;
import game.levels.Level1;

public final class OnePlayerGameScreen extends AbstractGameScreen {
    public OnePlayerGameScreen() {
        super();

        level = new Level1();
        Adel p1 = new Adel();
        p1.setPosition(level.getInitX() - p1.getWidth() / 2f, level.getInitY() - p1.getHeight() / 2f);

        stage.addActor(level);
        stage.addActor(p1);
    }
}