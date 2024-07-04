package screens;

import utilities.Render;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import utilities.FilePaths;
import utilities.Image;

public class LoadingScreen implements Screen {
    private Image background;

    private boolean fadeInFinished = false;
    private boolean finish = false;

    private float alpha = 0;
    private float time = 0f;
    private float finishTimer = 0f;

    private static final float WAIT_TIME = 1f;
    private static final float INCREMENT = 0.007f;

    public LoadingScreen() {
        background = new Image(FilePaths.BACKGROUNDS + "loadingScreen.png");
    }

    @Override
    public void show() {
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background.setAlpha(alpha);
    }

    @Override
    public void render(float delta) {
        Render.clear();

        Render.b.begin();
        background.draw();
        Render.b.end();

        displayMenu();
    }

    private void displayMenu() {
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

        if (finish) {
            finishTimer += 0.04f;
            if (finishTimer > WAIT_TIME) {
                Render.app.setScreen(new MenuScreen());
            }
        }
    }

    @Override
    public void resize(int i, int i1) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {}
}
