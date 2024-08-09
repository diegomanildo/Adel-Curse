package utilities;

public enum FilePaths {
    IMAGES("imgs/"),
    AUDIO("audio/"),
    FONTS("fonts/"),
    BACKGROUNDS(IMAGES + "backgrounds/"),
    CHARACTERS(IMAGES + "characters/"),
    ROOMS("rooms/");

    private final String filePath;

    FilePaths(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return filePath;
    }
}
