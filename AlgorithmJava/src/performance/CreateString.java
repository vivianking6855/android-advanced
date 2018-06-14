package performance;

public class CreateString {
    public static void start() {
        normalCreate();
        noteOne();
        noteTwo();
        noteThree();
    }

    private static void normalCreate() {
        // new String, will create three String objects
        String s1 = new String("s1");
        String s2 = new String("s1");
        System.out.println("s1 == s2 " + (s1 == s2));

        // directly assign, will create two String objects
        String s3 = "s3";
        String s4 = "s3";
        System.out.println("s3 == s4 " + (s3 == s4));

        System.out.println();
    }

    private static void noteOne() {
        String s1 = new String("1");
        s1.intern(); // 常量池中已经有"1", 什么都不做
        String s2 = "1";
        System.out.println("s1 == s2 " + (s1 == s2)); // false

        String s3 = new String("1") + new String("1");
        s3.intern(); // 常量池中新增"11",并且它的ref = s3
        String s4 = "11";
        System.out.println("s3 == s4 " + (s3 == s4)); // true; JDK1.6及以下是false，因为它的常量池中会再存储一份对象

        System.out.println();
    }

    private static void noteTwo() {
        String s1 = new String("1");
        String s2 = "1";
        s1.intern(); // 常量池中已经有"1", 什么都不做
        System.out.println("s1 == s2 " + (s1 == s2)); // false

        String s3 = new String("1") + new String("1");
        String s4 = "11";
        s3.intern(); // 常量池中已经有"11", 什么都不做
        System.out.println("s3 == s4 " + (s3 == s4)); // false

        System.out.println();
    }

    private static void noteThree() {
        String s1 = "HelloWorld";
        String s2 = new String("HelloWorld");
        String s3 = "Hello";
        String s4 = "World";
        String s5 = "Hello" + "World";
        String s6 = s3 + s4;

        System.out.println("s1 == s2 " + (s1 == s2)); // false
        System.out.println("s1 == s5 " + (s1 == s5)); // true
        System.out.println("s1 == s6 " + (s1 == s6)); // false
        System.out.println("s1 == s6 " + (s1 == s6.intern())); // true
        System.out.println("s1 == s6 " + (s2 == s2.intern())); // false
    }

}
