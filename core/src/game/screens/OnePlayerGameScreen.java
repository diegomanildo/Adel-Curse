package game.screens;

import game.entities.GameEntity;
import game.entities.characters.playables.Adel;
import game.levels.Level1;
import game.utilities.Entities;

public final class OnePlayerGameScreen extends AbstractGameScreen {
    public OnePlayerGameScreen() {
        super();
        entities = new Entities();

        level = new Level1();
        Adel p1 = new Adel();
        p1.setPosition(level.getInitX() - p1.getWidth() / 2f, level.getInitY() - p1.getHeight() / 2f);

        stage.addActor(level);
        stage.addActor(p1);

        stage.getActors().forEach(actor -> {
            if (actor instanceof GameEntity) {
                entities.add((GameEntity) actor);
            }
        });
    }
}