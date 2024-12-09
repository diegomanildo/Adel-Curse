package menu;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import utilities.*;

public final class LoadingScreen extends Screen {
    private final ProgressBar progressBar;

    public LoadingScreen() {
        super();

        progressBar = new ProgressBar(0f, 1f, 0.01f, false);
        Image background = new Image(FilePaths.BACKGROUNDS + "bgScreen.png");
        background.setSize(Render.screenSize.width, Render.screenSize.height);

        Table table = new Table();
        table.setFillParent(true);
        table.bottom();
        table.add(progressBar).width(1000f).height(100f).bottom().padBottom(100f);

        stage.addActor(background);
        stage.addActor(table);

        // Start the loading
        Render.assetManager.queueAssets();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (Render.assetManager.update()) {
            Render.setScreen(new MainMenuScreen());
        }

        // MUESTRA EL PORCENTAJE DE CARGA DE LA PANTALLA
        progressBar.setValue(Render.assetManager.getProgress());
    }
}
