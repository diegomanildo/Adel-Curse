package game.screens;

import utilities.Image;
import utilities.SubScreen;

public abstract class ImageSubScreen extends SubScreen {
    public ImageSubScreen(String path) {
        super();
        Image image = new Image(path);
        stage.addActor(image);
    }
}
