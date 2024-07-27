package game;

import com.badlogic.gdx.Input;
import game.characters.playables.Adel;
import game.rooms.Room;
import game.utilities.Camera2D;
import menu.MainMenuScreen;
import utilities.Render;
import utilities.Screen;

public final class GameScreen extends Screen {
    private final Camera2D camera;
    private final Room room;
    private final Adel adel;

    public GameScreen() {
        camera = new Camera2D(Render.screenSize.width, Render.screenSize.height);
        camera.setToOrtho(false);
        room = new Room("dungeon.tmx", camera);
        adel = new Adel();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (Render.io.isKeyPressed(Input.Keys.ESCAPE)) {
            MainMenuScreen.backgroundSong.fadeIn(FADE_TIME);
            Render.setScreen(new MainMenuScreen());
        }

        adel.move();

        camera.update();
        room.render();
        Render.b.begin();
        adel.draw();
        Render.b.end();

        if (!adel.collidesWith(camera.getHitbox())) {
            moveCamera();
        }
    }

    private void moveCamera() {
        if (adel.getX() < Render.camera.getLeft()) {
            Render.camera.moveTo(Render.camera.position.x - Render.camera.viewportWidth, Render.camera.position.y, FADE_TIME / 2f);
        } else if (adel.getX() > Render.camera.getRight()) {
            Render.camera.moveTo(Render.camera.position.x + Render.camera.viewportWidth, Render.camera.position.y, FADE_TIME / 2f);
        }

        if (adel.getY() < Render.camera.getBottom()) {
            Render.camera.moveTo(Render.camera.position.x, Render.camera.position.y - Render.camera.viewportHeight, FADE_TIME / 2f);
        } else if (adel.getY() > Render.camera.getTop()) {
            Render.camera.moveTo(Render.camera.position.x, Render.camera.position.y + Render.camera.viewportHeight, FADE_TIME / 2f);
        }
    }

    @Override
    public void resize(int w, int h) {
        super.resize(w, h);
        adel.center();
    }
}
