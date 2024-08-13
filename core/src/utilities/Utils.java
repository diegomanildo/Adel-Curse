package utilities;

public final class Utils {
    public static Random r = new Random();

    public static void sleep(Number millis) {
        try {
            Thread.sleep((long) millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
