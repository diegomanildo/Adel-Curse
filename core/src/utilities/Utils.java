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

    public static String toCommonText(String text) {
        StringBuilder ret = new StringBuilder();
        ret.append(text.charAt(0));

        for (int i = 1; i < text.length(); i++) {
            if (Character.isUpperCase(text.charAt(i))) {
                ret.append(' ');
            }

            ret.append(Character.toLowerCase(text.charAt(i)));
        }

        return ret.toString();
    }

    public static String toCamelCase(String text) {
        String[] words = text.split("[\\s_-]+");

        StringBuilder camelCase = new StringBuilder(words[0].toLowerCase());

        for (int i = 1; i < words.length; i++) {
            String word = words[i];
            if (!word.isEmpty()) {
                camelCase.append(word.substring(0, 1).toUpperCase());
                camelCase.append(word.substring(1).toLowerCase());
            }
        }

        return camelCase.toString();
    }
}
