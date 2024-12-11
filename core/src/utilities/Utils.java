package utilities;

import java.lang.management.ManagementFactory;
import java.util.Random;

public final class Utils {
    public static Random r = new Random();

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean isDebugging() {
        for (String arg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
            if (arg.contains("-agentlib:jdwp")) {
                return true;
            }
        }

        return false;
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
