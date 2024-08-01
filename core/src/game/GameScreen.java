package game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import game.entities.characters.enemies.Enemy;
import game.entities.characters.enemies.Skeleton;
import game.entities.characters.playables.Adel;
import game.hud.Hud;
import game.utilities.Camera2D;
import menu.BasicMainMenuScreen;
import menu.MainMenuScreen;
import utilities.Actor;
import utilities.Log;
import utilities.Render;
import utilities.Screen;
import utilities.io.Song;

import java.util.ArrayList;

public final class GameScreen extends Screen {
    private final Hud hud;
    public static ArrayList<Enemy> enemies;
    private final Adel adel;
    private final Song song;

    public GameScreen() {
        super();
        enemies = new ArrayList<>();

        adel = new Adel();
        adel.setPosition(Render.screenSize.width / 2f, Render.screenSize.height / 2f);
        hud = new Hud(adel);

        Skeleton skeleton = new Skeleton();
        skeleton.setPosition(40f, adel.getY());

        enemies.add(skeleton);

        song = new Song("Music", "game/music/UndeadIntro.mp3", "game/music/Undead.mp3");

        stage.getViewport().setCamera(new OrthographicCamera());

        stage.addActor(skeleton);
        stage.addActor(adel);

        stage.getActors().forEach(a -> {
            if (Actor.isEnemy(a)) {
                enemies.add((Enemy) a);
            }
        });
    }

    @Override
    public void show() {
        super.show();
        hud.show();
        BasicMainMenuScreen.backgroundSong.fadeOut(FADE_TIME);
        song.fadeIn(FADE_TIME, true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        hud.render(delta);

        if (stage.isKeyPressed(Input.Keys.ESCAPE)) {
            exit();
        }

        if (stage.isKeyPressed(Input.Keys.J)) {
            adel.damage(1);
        }

        if (!adel.collidesWith(Camera2D.getHitbox(Render.camera))) {
            moveCamera();
        }
    }

    private void moveCamera() {
        OrthographicCamera camera = stage.getCamera();
        float offsetX = adel.getWidth();
        float offsetY = adel.getHeight();

        if (adel.getX() < Camera2D.getLeft(camera) - offsetX * 2f) {
            Camera2D.moveTo(camera, camera.position.x - camera.viewportWidth, camera.position.y, FADE_TIME / 2f);
            Log.debug("Moving camera left");
        } else if (adel.getX() > Camera2D.getRight(camera) + offsetX) {
            Camera2D.moveTo(camera, camera.position.x + camera.viewportWidth, camera.position.y, FADE_TIME / 2f);
            Log.debug("Moving camera right");
        }

        if (adel.getY() < Camera2D.getBottom(camera) - offsetY * 2f) {
            Camera2D.moveTo(camera, camera.position.x, camera.position.y - camera.viewportHeight, FADE_TIME / 2f);
            Log.debug("Moving camera down");
        } else if (adel.getY() > Camera2D.getTop(camera) + offsetY) {
            Camera2D.moveTo(camera, camera.position.x, camera.position.y + camera.viewportHeight, FADE_TIME / 2f);
            Log.debug("Moving camera up");
        }
    }

    private void exit() {
        song.fadeOut(FADE_TIME);
        BasicMainMenuScreen.backgroundSong.fadeIn(FADE_TIME, true);
        Render.setScreen(new MainMenuScreen());
    }

    @Override
    public void resize(int w, int h) {
        super.resize(w, h);
        hud.resize(w, h);
    }

    @Override
    public void dispose() {
        super.dispose();
        hud.dispose();
    }
}
