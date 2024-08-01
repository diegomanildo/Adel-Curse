package game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import game.entities.characters.enemies.Enemy;
import game.entities.characters.enemies.Skeleton;
import game.entities.characters.playables.Adel;
import game.utilities.Camera2D;
import menu.BasicMainMenuScreen;
import menu.MainMenuScreen;
import utilities.Actor;
import utilities.Render;
import utilities.Screen;
import utilities.io.Song;

import java.util.ArrayList;

public final class GameScreen extends Screen {
    public static ArrayList<Enemy> enemies;
    private final Adel adel;
    private final Song song;

    public GameScreen() {
        super();
        enemies = new ArrayList<>();

        adel = new Adel();
        adel.setPosition(Render.screenSize.width / 2f, Render.screenSize.height / 2f);

        Skeleton skeleton = new Skeleton();
        skeleton.setPosition(40f, adel.getY());

        enemies.add(skeleton);

        song = new Song("Music", "game/music/UndeadIntro.mp3", "game/music/Undead.mp3");

        stage.getViewport().setCamera(new OrthographicCamera());

        stage.addActor(adel);
        stage.addActor(skeleton);

        stage.getActors().forEach(a -> {
            if (Actor.isEnemy(a)) {
                enemies.add((Enemy) a);
            }
        });
    }

    @Override
    public void show() {
        super.show();
        BasicMainMenuScreen.backgroundSong.fadeOut(FADE_TIME);
        song.fadeIn(FADE_TIME, true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (!adel.collidesWith(Camera2D.getBounds(Render.camera))) {
            moveCamera();
        }

        if (stage.isKeyPressed(Input.Keys.ESCAPE)) {
            exit();
        }
    }

    private void moveCamera() {
        OrthographicCamera camera = Render.camera;
        float offsetX = adel.getWidth();
        float offsetY = adel.getHeight();

        if (adel.getX() < Camera2D.getLeft(camera) - offsetX * 2f) {
            Camera2D.moveTo(camera, camera.position.x - camera.viewportWidth, camera.position.y, FADE_TIME / 2f);
        } else if (adel.getX() > Camera2D.getRight(camera) + offsetX) {
            Camera2D.moveTo(camera, camera.position.x + camera.viewportWidth, camera.position.y, FADE_TIME / 2f);
        }

        if (adel.getY() < Camera2D.getBottom(camera) - offsetY * 2f) {
            Camera2D.moveTo(camera, camera.position.x, camera.position.y - camera.viewportHeight, FADE_TIME / 2f);
        } else if (adel.getY() > Camera2D.getTop(camera) + offsetY) {
            Camera2D.moveTo(camera, camera.position.x, camera.position.y + camera.viewportHeight, FADE_TIME / 2f);
        }
    }

    private void exit() {
        song.fadeOut(FADE_TIME);
        BasicMainMenuScreen.backgroundSong.fadeIn(FADE_TIME, true);
        Render.setScreen(new MainMenuScreen());
    }
}
