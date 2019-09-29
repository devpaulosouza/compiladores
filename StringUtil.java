import java.util.Arrays;
import java.util.List;

class StringUtil {
    public static final char EOF = (char)65535;

    private static List<Character> validSymbols = Arrays.asList('=', '(', ')', ',', ';', '<', '>', '!', '*', '/', '+', '-', ' ', '\r', '\n', '\'', EOF, '_', '&', '.', ':', '[', ']', '{', '}', '\"', '!', '?');

    public static boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >='A' && c <='Z');
    }
    public static boolean isNumeric(char c) {
        return (c >= '0' && c <= '9');
    }
    public static boolean isHexa(char c) {
        return (isNumeric(c)) || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F'); 
    }
    public static boolean isValidChar(char c) {
        return isAlpha(c) || isNumeric(c) || validSymbols.contains(c);
    }
}