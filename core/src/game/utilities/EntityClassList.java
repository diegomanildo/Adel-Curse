package game.utilities;

import com.badlogic.gdx.utils.Array;
import game.entities.GameEntity;

public final class EntityClassList extends Array<Class<? extends GameEntity>> {
    @SafeVarargs
    public EntityClassList(Class<? extends GameEntity>... entities) {
        addAll(entities);
    }

    public GameEntity getRandomEntity() {
        try {
            return super.random().getDeclaredConstructor().newInstance();
        } catch(Exception e) {
            throw new RuntimeException("Entity is not valid (" + e.getClass() + ")");
        }
    }
}
