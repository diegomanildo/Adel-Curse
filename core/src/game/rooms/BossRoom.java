package game.rooms;

import game.Game;
import game.entities.characters.enemies.bosses.DeadEye;
import game.map.RoomKinds;
import utilities.audio.Song;

public final class BossRoom extends Room {
    public static Song song;
    private final DeadEye deadEye;

    public BossRoom() {
        super("boss/boss_1.tmx", RoomKinds.BOSS);
        song = new Song("Music", "songs/Death to Eva.mp3");
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
        if (Game.deathScreen.isShowing()) {
            song.stop();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        deadEye.dispose();
    }
}
