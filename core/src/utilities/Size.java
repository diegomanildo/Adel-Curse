package utilities;

public class Size {
    public float width;
    public float height;

    public Size(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public Size() {
        this(0f, 0f);
    }

    public static Size getResolution(String resolution) {
        String[] parts = resolution.split("x");
        return new Size(Float.parseFloat(parts[0]), Float.parseFloat(parts[1]));
    }

    @Override
    public String toString() {
        return width + "x" + height;
    }
}
