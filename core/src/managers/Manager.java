package managers;

import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;

public abstract class Manager<T extends Disposable> {
    protected ArrayList<T> objects;

    protected Manager() {
        reset();
    }

    public void register(T newObject) {
        objects.add(newObject);
    }

    public void reset() {
        objects = new ArrayList<>();
    }

    public void dispose() {
        for (T object : objects) {
            if (object != null) {
                object.dispose();
            }
        }
    }

    public boolean isEmpty() {
        return objects.isEmpty();
    }
}
