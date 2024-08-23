package game.levels;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import game.utilities.Camera2D;
import game.utilities.Direction;
import game.utilities.map.GameMap;
import game.utilities.map.RoomsArray;
import utilities.Actor;
import utilities.Render;

public abstract class Level extends Actor {
    private OrthogonalTiledMapRenderer renderer;
    private final GameMap map;
    private Camera camera;

    private static final float OFFSET = 34f;

    protected Level(int quantity, int rows, int cols, RoomsArray rooms) {
        map = new GameMap(quantity, rows, cols, rooms);
        camera = new Camera2D();
        camera.viewportWidth = map.getCurrent().getWidth() - OFFSET;
        camera.viewportHeight = map.getCurrent().getHeight() - OFFSET;
        camera.position.set(getInitX(), getInitY(), 0f);
        camera.update();
    }

    public float getInitX() {
        return map.playerInitPosition.x;
    }

    public float getInitY() {
        return map.playerInitPosition.y;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        renderer = new OrthogonalTiledMapRenderer(map.getCurrent().getMap());
        ((Camera2D) camera).zoom = Render.isDebugging() ? 2f : 1f;
        camera.update();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        renderer.setView((OrthographicCamera) camera);
        renderer.render();
    }

    public void changeRoom(Direction direction) {
        map.changeRoom(direction);
    }

    @Override
    public Camera2D getCamera() {
        return (Camera2D) camera;
    }

    public GameMap getMap() {
        return map;
    }
}
