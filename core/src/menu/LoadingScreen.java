package menu;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import utilities.*;

public final class LoadingScreen extends Screen {
    private final ProgressBar progressBar;

    public LoadingScreen() {
        super();

        float step = Utils.r.nextFloat(0.1f, 0.5f);

        progressBar = new ProgressBar(0f, 100f, step, false);
        Image background = new Image(FilePaths.BACKGROUNDS + "loadingScreen.png");
        background.setSize(Render.screenSize.width, Render.screenSize.height);

        Table table = new Table();
        table.setFillParent(true);
        table.bottom();
        table.add(progressBar).width(1000f).height(100f).bottom().padBottom(100f);

        stage.addActor(background);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        progressBar.setValue(progressBar.getValue() + progressBar.getStepSize());

        if (progressBar.getValue() == progressBar.getMaxValue()) {
            Render.setScreen(new MainMenuScreen());
        }
    }
}
