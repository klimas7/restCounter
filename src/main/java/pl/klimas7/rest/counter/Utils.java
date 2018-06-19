package pl.klimas7.rest.counter;

public class Utils {
    public static String format(String word, Number count) {
        return word + " : " + String.format("%07d", count);
    }
}
