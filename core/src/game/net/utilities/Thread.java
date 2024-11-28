package game.net.utilities;

public class Thread extends java.lang.Thread {
    protected static void internalSleep(long millis) {
        try {
            sleep(millis);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
