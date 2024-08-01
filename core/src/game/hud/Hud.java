package game.hud;

import com.badlogic.gdx.graphics.OrthographicCamera;
import utilities.Screen;

public class Hud extends Screen {
    public Hud() {
        super(new OrthographicCamera());
    }

    @Override
    public void render(float delta) {
        super.render(delta, false);
    }
}
