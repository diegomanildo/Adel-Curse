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

    protected Room(String fileMap, Camera2D camera) {
        this.map = new TmxMapLoader().load(fileMap);
        this.renderer = new OrthogonalTiledMapRenderer(map);
        this.camera = camera;
    }

    protected Room(String fileMap) {
        this(fileMap, new Camera2D());
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
}
