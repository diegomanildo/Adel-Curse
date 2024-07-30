package game.rooms;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import game.utilities.Camera2D;
import utilities.FilePaths;
import utilities.Render;

public abstract class Room extends Actor implements Disposable {
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private final Camera2D camera;

    public static final float OFFSET = 17f;

    public Room(String tmxFile) {
        this.camera = Render.camera;
        this.map = new TmxMapLoader().load(FilePaths.ROOMS + tmxFile);
        this.renderer = new OrthogonalTiledMapRenderer(map);

        setWidth(map.getProperties().get("width", Integer.class) * getTileWidth());
        setHeight(map.getProperties().get("height", Integer.class) * getTileHeight());

        this.camera.setSize(getWidth() - OFFSET * 2f, getHeight() - OFFSET * 2f);
        this.camera.update();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        camera.update();
        renderer.setView(camera);

        renderer.render();
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
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
