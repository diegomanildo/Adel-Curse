package game;

import game.characters.playables.Adel;
import game.rooms.Room;
import utilities.Render;
import utilities.Screen;

public final class GameScreen extends Screen {
    private final Room room;
    private final Adel adel;

    public GameScreen() {
        if (Render.isDebugging()) {
            Render.camera.zoom = 2f;
        }
        room = new Room("dungeon.tmx");
        adel = new Adel();
    }

    @Override
    public void show() {
        super.show();
        Render.b.setProjectionMatrix(Render.camera.combined);
        Render.sr.setProjectionMatrix(Render.camera.combined);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        adel.move();

        if (!adel.collidesWith(Render.camera.getHitbox())) {
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
        adel.setPosition((room.getWidth() - adel.getWidth()) / 2f, (room.getHeight() - adel.getHeight()) / 2f);
    }
}
