package game.rooms;

import com.badlogic.gdx.utils.Array;
import game.Game;
import game.entities.characters.enemies.DeadEye;
import game.map.RoomKinds;
import utilities.audio.Song;

public final class BossRoom extends Room {
    public static Song song;
    private final DeadEye deadEye;

    private static Array<String> bossSongs = new Array<>();

    static {
        bossSongs.add("songs/deathToEva.mp3");
        bossSongs.add("songs/spiderFunk.mp3");
    }

    public BossRoom() {
        super("boss/boss_1.tmx", RoomKinds.BOSS);
        song = new Song("Music", bossSongs.random());
        deadEye = new DeadEye();
        deadEye.setPosition(172f, 115f);
    }

    @Override
    public void show() {
        super.show();
        createEntity(deadEye);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (Game.deathScreen.isShowing() || Game.winScreen.isShowing()) {
            song.stop();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        deadEye.dispose();
    }
}
