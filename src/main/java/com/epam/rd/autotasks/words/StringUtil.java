package com.epam.rd.autotasks.words;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class StringUtil {

    public static int countEqualIgnoreCaseAndSpaces(String[] words, String sample) {
        int result = 0;
        if(sample == null || words == null || words.equals(null) || words.length == 0) {
            return 0;
        }
        else {
            for (String word : words) {
                String str1 = word.trim();
                String sample1 = sample.trim();
                if(sample1.equalsIgnoreCase(str1)) result++;
            }
        }
        return result;
    }

    public static String[] splitWords(String text) {
        String result[];

            if ( text == null || text.isEmpty() || text.equals(" ") || text.matches("^\\W+|\\W+$"))  {
                return null;
            }
        result = text.replaceAll("^\\W+|\\W+$", "").split("\\W+");
            return result;
        }


    public static String convertPath(String path, boolean toWin) {
        StringBuilder sb = new StringBuilder();

        if (path == null || path.isEmpty() || (path.contains("/") && path.contains("\\"))
                || path.contains("///") || path.contains("~~") || path.contains("\\C:\\")
                || path.contains("/~") || path.contains("~\\")) {
            return null;
        }

        if (!toWin) {

            if (path.equals("C:\\")) {
                return sb.append("/").toString();
            } else if (path.equals("C:\\User")) {
                return sb.append("~").toString();
            } else if (path.equals("C:\\User\\")) {
                return sb.append("~/").toString();
            }

            String res = path.replace('\\', '/');
            String resultUnix = res.replaceFirst("C:/User", "~").replaceFirst("C:", "");

            return resultUnix;

        } else {

            if (path.equals("/")) {
                return sb.append("C:\\").toString();
            } else if (path.equals("~")) {
                return sb.append("C:\\User").toString();
            } else if (path.startsWith("/")) {
                sb.append("C:");
            } else if (path.startsWith("~")) {
                sb.delete(0, 1).append("C:\\User");
            }

        }
        String resultW = sb + path.replace('/', '\\');
        String resultWin = resultW.replaceAll("~", "");

        return resultWin;

    }


    public static String joinWords(String[] words) {
        if(words == null || words.length == 0) {
            return null;
        }
        int counterEmptyWords = 0;

        StringBuilder join = new StringBuilder();
        join.append("[");
        for (String word : words) {
            if(word.length() != 0)
            join.append(word).append(",").append(" ");
            else {
                counterEmptyWords++;
                if(counterEmptyWords == words.length) return null;
            }
        }
        String result = join.deleteCharAt(join.length() - 1).deleteCharAt(join.length() - 1).append("]").toString();
        if(result.length() == 1) return null;
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Test 1: countEqualIgnoreCaseAndSpaces");
        String[] words = new String[]{" WordS    \t", "words", "w0rds", "WOR  DS", };
        String sample = "words   ";
        int countResult = countEqualIgnoreCaseAndSpaces(words, sample);
        System.out.println("Result: " + countResult);
        int expectedCount = 2;
        System.out.println("Must be: " + expectedCount);

        System.out.println("Test 2: splitWords");
        String text = "   ,, first, second!!!! third";
        String[] splitResult = splitWords(text);
        System.out.println("Result : " + Arrays.toString(splitResult));
        String[] expectedSplit = new String[]{"first", "second", "third"};
        System.out.println("Must be: " + Arrays.toString(expectedSplit));

        System.out.println("Test 3: convertPath");
        String unixPath = "/some/unix/path";
       /* String convertResult = convertPath(unixPath, true);*/
        String convertResult = convertPath(unixPath, false);
        System.out.println("Result: " + convertResult);
        String expectedWinPath = "C:\\some\\unix\\path";
        System.out.println("Must be: " + expectedWinPath);

       System.out.println("Test 4: joinWords");
        String[] toJoin = new String[]{"go", "with", "the", "", "FLOW"};
        String joinResult = joinWords(toJoin);
        System.out.println("Result: " + joinResult);
        String expectedJoin = "[go, with, the, FLOW]";
        System.out.println("Must be: " + expectedJoin);
    }
}