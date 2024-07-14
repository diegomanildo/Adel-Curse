package screens;

import utilities.Render;
import com.badlogic.gdx.Gdx;
import utilities.FilePaths;
import utilities.Image;

public final class LoadingScreen extends Screen {
    private final Image background;

    private boolean fadeInFinished;
    private boolean finish;

    private float alpha;
    private float time;
    private float finishTimer;

    private static final float WAIT_TIME = 1f;
    private static final float INCREMENT = 0.007f;

    public LoadingScreen() {
        super();
        background = new Image(FilePaths.BACKGROUNDS + "loadingScreen.png");
        fadeInFinished = false;
        finish = false;
        alpha = 0f;
        time = 0f;
        finishTimer = 0f;
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background.setAlpha(alpha);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Render.clear();
        fadeIn();

        Render.b.begin();
        background.draw();
        Render.b.end();

        fadeOut();
    }

    private void fadeIn() {
        if (!fadeInFinished) {
            alpha += INCREMENT;
            if (alpha > 1f) {
                alpha = 1f;
                fadeInFinished = true;
            }
        } else {
            time += 0.01f;
            if (time > WAIT_TIME) {
                alpha -= INCREMENT;
                if (alpha < 0f) {
                    alpha = 0;
                    finish = true;
                }
            }
        }

        background.setAlpha(alpha);
    }

    private void fadeOut() {
        if (finish) {
            finishTimer += 0.04f;
            if (finishTimer > WAIT_TIME) {
                Render.app.setScreen(new MainMenuScreen());
            }
        }
    }
}
