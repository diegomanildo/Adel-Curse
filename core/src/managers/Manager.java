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
        objects.forEach(Disposable::dispose);
    }

    public boolean isEmpty() {
        return objects.isEmpty();
    }
}
