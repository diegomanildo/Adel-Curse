package game.levels;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import game.utilities.Camera2D;
import game.utilities.Direction;
import game.utilities.Map;
import game.utilities.RoomsArray;
import utilities.Actor;
import utilities.Log;
import utilities.Render;

public abstract class Level extends Actor {
    private OrthogonalTiledMapRenderer renderer;
    private final Map map;
    private Camera camera;

    private static final float OFFSET = 34f;

    protected Level(int quantity, int rows, int cols, RoomsArray rooms) {
        try {
            map = new Map(quantity, rows, cols, rooms);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        map.print();
    }

    @Override
    public void show() {
        super.show();
        camera = new Camera2D();
        camera.viewportWidth = map.getCurrent().getWidth() - OFFSET;
        camera.viewportHeight = map.getCurrent().getHeight() - OFFSET;
        camera.position.set(getInitX(), getInitY(), 0f);
        camera.update();
    }

    public float getInitX() {
        return map.initPosition.x;
    }

    public float getInitY() {
        return map.initPosition.y;
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
        switch (direction) {
            case DOWN:
                map.down();
                break;
            case UP:
                map.up();
                break;
            case RIGHT:
                map.right();
                break;
            case LEFT:
                map.left();
                break;
        }
    }

    public boolean isValid(Direction direction) {
        boolean valid = map.isValid(direction);
        Log.log("Valid: " + valid);
        return valid;
    }

    @Override
    public Camera2D getCamera() {
        return (Camera2D) camera;
    }
}
