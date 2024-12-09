package utilities;

import java.util.Random;

public final class Utils {
    public static Random r = new Random();

    public static void sleep(float millis) {
        try {
            Thread.sleep((long) millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int findFirstNumber(String input) {
        StringBuilder number = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                number.append(c);
            } else if (number.length() > 0) {
                break;
            }
        }

        return number.length() > 0 ? Integer.parseInt(number.toString()) : -1;
    }
}
