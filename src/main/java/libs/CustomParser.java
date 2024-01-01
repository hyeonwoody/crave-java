package libs;

public class CustomParser {


    public static char fromHex(char ch) {
        return Character.isDigit(ch) ? (char) (ch - '0') : (char) (Character.toLowerCase(ch) - 'a' + 10);
    }
}
