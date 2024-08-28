package game.levels;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import game.map.GameMap;
import game.map.RoomsArray;
import game.rooms.BossRoom;
import game.rooms.Room;
import game.rooms.ShopRoom;
import game.utilities.Camera2D;
import game.utilities.Direction;
import utilities.Group;
import utilities.Render;
import utilities.Screen;
import utilities.audio.Song;

public abstract class Level extends Group {
    private Song levelSong;
    private Song auxSong;

    public static Camera camera;
    private OrthogonalTiledMapRenderer renderer;
    private final GameMap map;
    private boolean isInShop;
    private boolean isInBoss;

    private static final float OFFSET = 34f;

    protected Level(Song levelSong, int quantity, int rows, int cols, RoomsArray rooms) {
        this.levelSong = levelSong;

        map = new GameMap(quantity, rows, cols, rooms);
        addActor(map);
        camera = new Camera2D();
        camera.viewportWidth = map.getCurrent().getWidth() - OFFSET;
        camera.viewportHeight = map.getCurrent().getHeight() - OFFSET;
        camera.position.set(getInitX(), getInitY(), 0f);
        camera.update();
        isInShop = false;
        isInBoss = false;
    }

    @Override
    public void show() {
        super.show();
        levelSong.fadeIn(Screen.FADE_TIME, true);
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
        Room current = map.changeRoom(direction);

        if (current instanceof ShopRoom) {
            onChangedToShop();
        } else if (current instanceof BossRoom) {
            onChangedToBoss();
        } else if (isInShop || isInBoss) {
            onChangedToCommon();
        }
    }

    private void onChangedToShop() {
        changeSong(ShopRoom.shopSong);
        isInShop = true;
    }

    private void onChangedToBoss() {
        isInBoss = true;
        changeSong(BossRoom.song);
    }

    private void onChangedToCommon() {
        isInShop = false;
        isInBoss = false;
        returnSong();
    }

    private void changeSong(Song song) {
        levelSong.fadeOut(Screen.FADE_TIME, true);
        auxSong = levelSong;
        levelSong = song;
        levelSong.fadeIn(Screen.FADE_TIME, true);
    }

    private void returnSong() {
        levelSong.fadeOut(Screen.FADE_TIME, true);
        levelSong = auxSong;
        auxSong = null;
        levelSong.fadeIn(Screen.FADE_TIME, true);
    }

    public Song getSong() {
        return levelSong;
    }

    public Camera2D getCamera() {
        return (Camera2D) camera;
    }

    public GameMap getMap() {
        return map;
    }

    public float getInitX() {
        return map.playerInitPosition.x;
    }

    public float getInitY() {
        return map.playerInitPosition.y;
    }
}
