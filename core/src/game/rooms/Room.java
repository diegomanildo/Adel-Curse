package game.rooms;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import game.utilities.Camera2D;

public class Room implements Disposable {
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private final Camera2D camera;

    private static final float OFFSET = 17f;

    public Room(String tmxFile, Camera2D camera) {
        this.camera = camera;
        this.map = new TmxMapLoader().load(tmxFile);
        this.renderer = new OrthogonalTiledMapRenderer(map);

        float mapWidth = map.getProperties().get("width", Integer.class) * getTileWidth();
        float mapHeight = map.getProperties().get("height", Integer.class) * getTileHeight();

        this.camera.position.set(new Vector2(mapWidth / 2f, mapHeight / 2f), 0);
        this.camera.update();
        this.camera.viewportWidth = mapWidth - OFFSET * 2f;
        this.camera.viewportHeight = mapHeight - OFFSET * 2f;
    }

    public void render() {
        renderer.setView(camera);
        renderer.render();
    }

    @Override
    public void dispose() {
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
