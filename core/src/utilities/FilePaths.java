package utilities;

public enum FilePaths {
    IMAGES("imgs/"),
    AUDIO("audio/"),
    FONTS("fonts/"),
    BACKGROUNDS(IMAGES + "backgrounds/"),
    CHARACTERS(IMAGES + "characters/");

    private String filePath;

    FilePaths(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return filePath;
    }
}
