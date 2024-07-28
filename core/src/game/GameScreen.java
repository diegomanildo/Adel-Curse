package game;

import game.characters.playables.Adel;
import game.rooms.Room;
import utilities.Render;
import utilities.Screen;

public final class GameScreen extends Screen {
    private final Adel adel;
    private final Room room;

    public GameScreen() {
        if (Render.isDebugging()) {
            Render.camera.zoom = 2f;
        }
        room = new Room("dungeon.tmx", Render.camera);
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
    }

    @Override
    public void resize(int w, int h) {
        super.resize(w, h);
        adel.setPosition((room.getWidth() - adel.getWidth()) / 2f, (room.getHeight() - adel.getHeight()) / 2f);
    }
}
