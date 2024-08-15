package utilities;

public final class Utils {
    public static void sleep(float millis) {
        try {
            Thread.sleep((long) millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
