package com.akkw.protobuf.utils.generate;

import java.lang.reflect.Field;

public class EnumTest {


    public static void main(String[] args) {
       test(C.as);
       test(C.be);
    }


    public static void test(Object o) {



//        System.out.println( o1.ordinal());
    }
    enum C{
        as,

        be
    }

}
