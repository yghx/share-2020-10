public class HelloCTest {

    public static void main(String[] args) {
        HelloC.sayHelloC();
    }

    static {
        System.loadLibrary("HelloC");
    }
}
