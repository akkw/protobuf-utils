package com.akkw.protobuf.utils;

import com.akkw.protobuf.utils.coder.Coder;
import com.akkw.protobuf.utils.coder.GenerateCoder;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;


public class DefaultCoderGenerate implements GenerateCoder {

    private static Map<Type, Coder> types = new HashMap<>();

    private final static Set<Type> basicType = new HashSet<>();

    private final Class<?> type;

    private CtClass ctClass;

    static {
        basicType.add(Byte.TYPE);
        basicType.add(byte.class);
        basicType.add(Short.TYPE);
        basicType.add(short.class);
        basicType.add(Integer.TYPE);
        basicType.add(int.class);
        basicType.add(Long.TYPE);
        basicType.add(long.class);
        basicType.add(Float.TYPE);
        basicType.add(float.class);
        basicType.add(Double.TYPE);
        basicType.add(double.class);
        basicType.add(Boolean.TYPE);
        basicType.add(boolean.class);
        basicType.add(String.class);
        basicType.add(char.class);
    }

    public DefaultCoderGenerate(Class<?> type) {
        this.type = type;
    }

    @Override
    public void generate() {
        ClassPool classPool = ClassPool.getDefault();
        ctClass = classPool.makeClass(className(type));
        generateFieldsAndMethod();
    }

    private void generateFieldsAndMethod() {
        Field[] declaredFields = type.getDeclaredFields();

    }

    private void generateField(Field field) {
        CtField ctField = new CtField();
        ctClass.addField();

    }

    private void generateMethod(Field field) {

    }

    private String className(Class<?> type) {
        String name = type.getName();
        return "JavaToProtobufCoder$" + name;
    }

    @Override
    public String getCode() {
        return null;
    }


    enum Separator {
        WIRETYPE_LENGTH_DELIMITED,
        WIRETYPE_LENGTH_DELIMITED_END
    }

    private static class Index {
        final int location;
        final Object value;

        public Index(int location, Object value) {
            this.location = location;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Index{" +
                    "location=" + location +
                    ", value=" + value +
                    '}';
        }
    }
}
