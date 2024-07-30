package menu;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import utilities.*;

public class LoadingScreen extends Screen {
    private static final Image BACKGROUND = new Image(FilePaths.BACKGROUNDS + "loadingScreen.png");

    static {
        BACKGROUND.setSize(Render.screenSize.width, Render.screenSize.height);
    }

    public LoadingScreen() {
        super();

        ProgressBar progressBar = new ProgressBar(0f, 100f, 1f, false);

        Table table = new Table();
        table.setFillParent(true);
        table.bottom();
        table.add(progressBar)
                .width(1000f)
                .height(100f)
                .bottom()
                .padBottom(100f);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.getBatch().begin();
        BACKGROUND.draw(stage.getBatch(), 1);
        stage.getBatch().end();
        stage.draw();
    }
}
