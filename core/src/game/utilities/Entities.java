package game.utilities;

import com.badlogic.gdx.utils.Array;
import game.entities.GameEntity;
import game.entities.characters.Character;
import game.entities.characters.enemies.Enemy;
import game.entities.characters.playables.Playable;

import java.util.ArrayList;

public class Entities extends ArrayList<GameEntity> {
    public ArrayList<Enemy> getEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (GameEntity entity : this) {
            if (entity instanceof Enemy) {
                enemies.add((Enemy) entity);
            }
        }
        return enemies;
    }

    @SuppressWarnings("unchecked")
    public <T extends GameEntity> T getEntity(int id) {
        for (GameEntity e : this) {
            if (e.getId() == id) {
                return (T) e;
            }
        }

        return null;
    }

    public Array<Playable> getPlayers() {
        Array<Playable> players = new Array<>();
        for (GameEntity entity : this) {
            if (entity instanceof Playable) {
                players.add((Playable) entity);
            }
        }
        return players;
    }

    public ArrayList<Character> getCharacters() {
        ArrayList<Character> characters = new ArrayList<>();
        for (GameEntity entity : this) {
            if (entity instanceof Character) {
                characters.add((Character) entity);
            }
        }
        return characters;
    }
}
