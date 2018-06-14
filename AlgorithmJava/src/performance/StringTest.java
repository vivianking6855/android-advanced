package performance;

public class StringTest {

    /**
     * entrance of String Performance Test
     */
    public void start() {
        //ConstStringTest.start();
        //DynamicStringTest.start();
        CutStringTest.start();
        // CreateString.start();
    }

    /**
     * Gets instance with inner static class way
     *
     * @return the instance
     */
    public static StringTest getInstance() {
        return Holder.INSTANCE;
    }

    private StringTest() {
    }

    private static class Holder {
        private static final StringTest INSTANCE = new StringTest();
    }

}