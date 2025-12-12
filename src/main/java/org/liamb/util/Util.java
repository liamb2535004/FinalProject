package org.liamb.util;

import java.util.Arrays;

public class Util {
    /**
     * converts each word in a string to title case. Assume each word in a string is separated by space.
     * @param str the string to be converted to title case
     * @return the title case string
     */
    public static String toTitleCase(String str) {
        if (str == null || str.isBlank()) {
            return str;
        }
        String lowerCaseStr = str.toLowerCase();
        String[] words = lowerCaseStr.split("\\s+");

        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            if (!word.isEmpty()) {
                words[i] = Character.toUpperCase(word.charAt(0)) + word.substring(1);
            }
        }

        return String.join(" ", words);
    }
}
