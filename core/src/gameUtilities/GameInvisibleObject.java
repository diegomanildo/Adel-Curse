package gameUtilities;

import com.badlogic.gdx.graphics.g2d.Batch;
import utilities.exceptions.NotVisibleObjectException;

public abstract class GameInvisibleObject extends GameObject {
    @Override
    public void draw(Batch batch) {
        throw new NotVisibleObjectException("The object " + getClass().getName() + " is not visible and you can not draw it");
    }
}
