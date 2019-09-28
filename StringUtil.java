class StringUtil {
    public static boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >='A' && c <='Z');
    }
    public static boolean isNumeric(char c) {
        return (c >= '0' && c <= '9');
    }
    public static boolean isHexa(char c) {
        return (isNumeric(c)) || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F'); 
    }
}