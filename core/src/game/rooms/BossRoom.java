package game.rooms;

import com.badlogic.gdx.utils.Array;
import game.Game;
import game.entities.characters.enemies.DeadEye;
import game.map.RoomKinds;
import utilities.audio.Song;

public final class BossRoom extends Room {
    public static Song song;
    private final DeadEye deadEye;

    private static final Array<String> BOSS_SONGS = new Array<>();

    static {
        BOSS_SONGS.add("songs/deathToEva.mp3");
        BOSS_SONGS.add("songs/spiderFunk.mp3");
    }

    public BossRoom() {
        super("boss/boss_1.tmx", RoomKinds.BOSS);
        song = new Song("Music", BOSS_SONGS.random());
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
