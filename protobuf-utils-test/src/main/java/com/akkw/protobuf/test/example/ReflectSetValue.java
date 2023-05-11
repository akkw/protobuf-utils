package com.akkw.protobuf.test.example;

import com.akkw.protobuf.test.BasicTypeJava;
import com.akkw.protobuf.test.ptoto.BasicType;

import java.lang.reflect.Field;

public class ReflectSetValue {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Class<BasicTypeJava> basicTypeJavaClass = BasicTypeJava.class;
        Field[] declaredFields = basicTypeJavaClass.getDeclaredFields();
        Object basicTypeJava = basicTypeJavaClass.newInstance();

        for (Field field : declaredFields) {
            field.setAccessible(true);
            field.set(basicTypeJava, (Integer) 1 );

        }
        byte b = 1;
        Object a = b;

        Class<?> aClass = a.getClass();

        if (aClass.isAssignableFrom(Byte.class)) {
            Byte.valueOf((Byte)a);
        }
    }
}
