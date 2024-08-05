package game.rooms;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import game.utilities.Camera2D;
import utilities.Actor;
import utilities.FilePaths;

public abstract class Room extends Actor {
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private Camera2D camera;

    public static final float OFFSET = 17f;

    public Room(String tmxFile) {
        this.map = new TmxMapLoader().load(FilePaths.ROOMS + tmxFile);
        this.renderer = new OrthogonalTiledMapRenderer(map);

        super.setWidth(map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class));
        super.setHeight(map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        renderer.setView(camera);
        renderer.render();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (camera == null) {
            initCamera();
        }
        camera.update();
    }

    private void initCamera() {
        camera = (Camera2D) getStage().getCamera();
        camera.zoom = 1.5f;
        setWidth(map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class));
        setHeight(map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class));
        setSize(getWidth() - OFFSET * 2f, getHeight() - OFFSET * 2f);

        camera.setPosition(getMiddleX() + OFFSET, getMiddleY() + OFFSET);
        setPosition(getX() + OFFSET, getY() + OFFSET);
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        camera.viewportWidth = width;
        camera.update();
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);
        camera.viewportHeight = height;
        camera.update();
    }
}
