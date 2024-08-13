package game.levels;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import game.rooms.Room;
import utilities.Actor;

public abstract class Level extends Actor {
    private final Array<Room> rooms;
    private int roomIndex;
    private OrthogonalTiledMapRenderer renderer;
    private Camera camera;

    protected Level() {
        rooms = getRooms();
        roomIndex = 0;
    }

    @Override
    public void show() {
        super.show();
        camera = getStage().getCamera();
        camera.viewportWidth = getCurrentRoom().getWidth();
        camera.viewportHeight = getCurrentRoom().getHeight();
        camera.position.set(100, 100, 0f);
        camera.update();
    }

    public abstract Array<Room> getRooms();

    public float getInitX() {
        return rooms.get(0).getWidth() / 2f;
    }

    public float getInitY() {
        return rooms.get(0).getHeight() / 2f;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        changeRoom();
        camera.update();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        renderer.setView((OrthographicCamera) camera);
        renderer.render();
    }

    public Room getCurrentRoom() {
        return rooms.get(roomIndex);
    }

    public int getRoomIndex() {
        return roomIndex;
    }

    private void changeRoom() {
        renderer = new OrthogonalTiledMapRenderer(rooms.get(roomIndex).getMap());
    }
}
