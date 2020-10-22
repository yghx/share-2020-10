public class HelloC {
    public static native void sayHelloC();

    static {
        System.loadLibrary("HelloC");
    }
}
