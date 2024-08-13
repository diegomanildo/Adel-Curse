package languages;

public abstract class Language {
    private static final Language[] LANGUAGES = {
            new EnglishLanguage(),
            new SpanishLanguage()
    };

    public static Language[] getLanguages() {
        return LANGUAGES;
    }

    public static Language get(String language) {
        for (Language l : LANGUAGES) {
            if (l.name().equals(language)) {
                return l;
            }
        }

        throw new RuntimeException("Language not found: " + language);
    }

    public abstract String name();

    public abstract String playBtn();
    public abstract String controlsBtn();
    public abstract String songSelectorBtn();
    public abstract String settingsBtn();
    public abstract String quitBtn();

    public abstract String pressAKeyFor();

    public abstract String fullScreen();
    public abstract String apply();
    public abstract String language();
    public abstract String video();
    public abstract String resolution();
    public abstract String fps();
    public abstract String volume();
}
