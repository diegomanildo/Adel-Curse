package game.rooms;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import game.utilities.Camera2D;
import game.utilities.GameObject;

public abstract class Room extends GameObject {
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private final Camera2D camera;

    private static final float OFFSET = 17f;

    protected Room(String fileMap, Camera2D camera) {
        this.map = new TmxMapLoader().load(fileMap);
        this.renderer = new OrthogonalTiledMapRenderer(map);
        this.camera = camera;

        float w = map.getProperties().get("width", Integer.class) * getTileWidth();
        float h = map.getProperties().get("height", Integer.class) * getTileHeight();

        setSize(w - (OFFSET * 2f), h - (OFFSET * 2f));
        setPosition(getX() + w / 2f, getY() + h / 2f);
    }

    @Override
    public void setX(float x) {
        camera.position.x = x;
        camera.update();
    }

    @Override
    public void setY(float y) {
        camera.position.y = y;
        camera.update();
    }

    @Override
    public void setWidth(float width) {
        camera.viewportWidth = width;
        camera.update();
    }

    @Override
    public void setHeight(float height) {
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public float getX() {
        return camera.position.x;
    }

    @Override
    public float getY() {
        return camera.position.y;
    }

    @Override
    public float getWidth() {
        return camera.viewportWidth;
    }

    @Override
    public float getHeight() {
        return camera.viewportHeight;
    }

    @Override
    public void draw(Batch batch) {
        renderer.setView(camera);
        renderer.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        map.dispose();
        renderer.dispose();
    }

    public float getTileWidth() {
        return map.getProperties().get("tilewidth", Integer.class);
    }

    public float getTileHeight() {
        return map.getProperties().get("tileheight", Integer.class);
    }
}
