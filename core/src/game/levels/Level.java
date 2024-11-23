package game.levels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import game.Game;
import game.map.Door;
import game.map.GameMap;
import game.map.RoomsArray;
import game.rooms.BossRoom;
import game.rooms.Room;
import game.rooms.ShopRoom;
import game.utilities.Camera2D;
import game.utilities.Direction;
import game.utilities.Hitbox;
import utilities.Group;
import utilities.Render;
import utilities.Screen;
import utilities.audio.Song;

import java.io.Serializable;

public abstract class Level extends Group implements Serializable {
    private static final long serialVersionUID = 6236234L;
    private static final String START_TEXT_KEY = "StartText";

    private Song levelSong;
    private Song auxSong;

    public static Camera2D camera;
    private OrthogonalTiledMapRenderer renderer;
    private final GameMap map;
    private boolean isInShop;
    private boolean isInBoss;

    private static final float OFFSET = 34f;
    private static final Hitbox HITBOX = new Hitbox(48f, 48f, 272f, 144f);

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
        Game.chat.createFull(START_TEXT_KEY, "Adel habia vivido una vida sencilla, sin grandes ambiciones ni logros destacables. Era un joven amable, pero siempre sentia que le faltaba algo para cumplir su proposito. Un dia, mientras caminaba por las calles vacias de su ciudad, un accidente lo alcanzo y, en un parpadeo, su vida mortal llego a su fin.\n" +
                "\n" +
                "Al despertar, Adel no se encontraba en el cielo, ni en un tranquilo descanso eterno. En su lugar, estaba en un desierto de oscuridad y fuego: el inframundo. Montañas de piedra negra se levantaban sobre un rio de lava que serpenteaba en la distancia. Extrañas criaturas susurraban en las sombras, y el aire estaba cargado de miedo y desesperanza.\n" +
                "\n" +
                "De repente, una figura imponente se acerco a él. Un ser encapuchado, con alas desgarradas y ojos llameantes, le hablo con una voz grave: \"Adel, has sido enviado aqui porque tu alma no ha sido juzgada. No fuiste ni malvado ni virtuoso. Tu destino esta en tus manos. Para alcanzar el cielo, deberas superar los desafios de este reino oscuro. Si fracasas, te quedaras aqui por toda la eternidad.\"\n" +
                "\n" +
                "Sin otra opcion, Adel acepto el desafio. Comenzo su travesia a traves de las tierras infernales, enfrentandose a grotescas bestias que surgian del suelo ardiente");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        renderer = new OrthogonalTiledMapRenderer(map.getCurrent().getMap());
        camera.update();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        renderer.setView(camera);
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
        return camera;
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

    public Hitbox getHitbox() {
        if (Render.isDebugging()) {
            Matrix4 mat = Render.sr.getProjectionMatrix();
            Render.sr.setProjectionMatrix(camera.combined);
            HITBOX.drawShape(Render.sr);
            Render.sr.setProjectionMatrix(mat);
        }
        return HITBOX;
    }

    public Door[] getDoors() {
        if (Render.isDebugging()) {
            Matrix4 mat = Render.sr.getProjectionMatrix();
            Render.sr.setProjectionMatrix(camera.combined);
            for (Door door : map.getCurrent().getDoors()) {
                door.getHitbox().drawShape(Render.sr);
            }
            Render.sr.setProjectionMatrix(mat);
        }

        return map.getCurrent().getDoors();
    }
}
