package zsy.jason;

public class Jason {
    private Jason() {
    }

    public static <T> T build(String file) {
        T result = (T)new Object();

        Class c = result.getClass();
        return result;
    }
}
