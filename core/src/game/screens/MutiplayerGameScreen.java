package game.screens;

import game.entities.GameEntity;
import game.entities.characters.playables.Adel;
import game.levels.Level1;
import game.utilities.Entities;
import utilities.Timer;

public final class MutiplayerGameScreen extends AbstractGameScreen {
    public MutiplayerGameScreen() {
        super();
        timer = new Timer();
        entities = new Entities();

        level = new Level1();
        Adel p1 = new Adel();
        p1.setPosition(level.getInitX() - p1.getWidth() / 2f, level.getInitY() - p1.getHeight() / 2f);

        Adel p2 = new Adel();
        p2.setPosition(level.getInitX() - p2.getWidth() / 2f, level.getInitY() - p2.getHeight() / 2f);

        stage.addActor(level);
        createPlayer(p1);
        createPlayer(p2);

        stage.getActors().forEach(actor -> {
            if (actor instanceof GameEntity) {
                entities.add((GameEntity) actor);
            }
        });
    }
}