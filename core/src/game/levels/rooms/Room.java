package game.levels.rooms;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import game.utilities.Camera2D;
import game.utilities.GameObject;
import utilities.FilePaths;
import utilities.Render;

public abstract class Room extends GameObject {
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private final Camera2D camera;

    private static final float OFFSET = 17f;

    public Room(String tmxFile) {
        this.camera = Render.camera;
        this.map = new TmxMapLoader().load(FilePaths.ROOMS + tmxFile);
        this.renderer = new OrthogonalTiledMapRenderer(map);

        this.camera.setPosition(getX(), getY());
        this.camera.setSize(getWidth() - OFFSET * 2f, getHeight() - OFFSET * 2f);
        this.camera.update();
    }

    @Override
    public void draw(Batch b) {
        Render.b.setProjectionMatrix(camera.combined);
        Render.sr.setProjectionMatrix(camera.combined);
        camera.update();
        renderer.setView(camera);

        renderer.render();

        if (Render.isDebugging()) {
            drawHitbox(camera.getLeft(), camera.getBottom(), camera.getX(), camera.getY(), getWidth() - OFFSET * 2f, getHeight() - OFFSET * 2f);
        }
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        set(x, getY());
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        set(getX(), y);
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

    @Override
    public float getWidth() {
        return map.getProperties().get("width", Integer.class) * getTileWidth();
    }

    @Override
    public float getHeight() {
        return map.getProperties().get("height", Integer.class) * getTileHeight();
    }

    private float getTileWidth() {
        return map.getProperties().get("tilewidth", Integer.class);
    }

    private float getTileHeight() {
        return map.getProperties().get("tileheight", Integer.class);
    }

    private void set(float x, float y) {
        renderer.getBatch().begin();
        renderer.getBatch().getTransformMatrix().setToTranslation(x, y, 0f);
        renderer.getBatch().end();
    }
}
