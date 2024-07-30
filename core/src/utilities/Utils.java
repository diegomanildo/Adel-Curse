package utilities;

public final class Utils {
    public static Random r = new Random();

    public static <T extends Number> void sleep(T millis) {
        try {
            Thread.sleep((long) millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
