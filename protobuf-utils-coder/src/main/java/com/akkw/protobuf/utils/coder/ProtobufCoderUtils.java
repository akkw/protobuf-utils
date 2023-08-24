package com.akkw.protobuf.utils.coder;

import com.akkw.protobuf.utils.generate.DefaultCoderGenerate;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ProtobufCoderUtils {
    static final Set<Type> basicType = new HashSet<>();

    static final Set<Type> collectionType = new HashSet<>();


     static final Map<Type, ProtobufCoder> coderCache = new ConcurrentHashMap<>();


    static {
        basicType.add(Byte.class);
        basicType.add(byte.class);
        basicType.add(Short.class);
        basicType.add(short.class);
        basicType.add(Integer.class);
        basicType.add(int.class);
        basicType.add(Long.class);
        basicType.add(long.class);
        basicType.add(Float.class);
        basicType.add(float.class);
        basicType.add(Double.class);
        basicType.add(double.class);
        basicType.add(Boolean.class);
        basicType.add(boolean.class);
        basicType.add(String.class);
        basicType.add(char.class);
        basicType.add(byte[].class);
        collectionType.add(List.class);
        collectionType.add(ArrayList.class);
        collectionType.add(LinkedList.class);
        collectionType.add(Map.class);
        collectionType.add(HashMap.class);
        collectionType.add(LinkedHashMap.class);

    }

    public static void parseJavaClass(Class<?> aClass) throws Exception {
        if (aClass == null) {
            throw new IllegalArgumentException("class is null");
        }
        if (coderCache.containsKey(aClass)) {
            return;
        }
        parse(aClass);
    }

    private static void parse(Class<?> aClass) throws Exception {
        DefaultCoderGenerate defaultCoderGenerate = new DefaultCoderGenerate(collectionType, basicType, coderCache, aClass);
        Class<?> tragetClass = defaultCoderGenerate.generate();
        if (tragetClass != null) {
            createCoderInstance(aClass, tragetClass);
        }
    }

    private static void createCoderInstance(Class<?> aClass, Class<?> tragetClass) throws Exception {
        Constructor<?> declaredConstructor = tragetClass.getDeclaredConstructor(Map.class, Set.class);
        ProtobufCoder protobufCoder = (ProtobufCoder) declaredConstructor.newInstance(coderCache, basicType);
        addCoderToCache(aClass, protobufCoder);
    }

    private static synchronized void addCoderToCache(Class<?> tragetClass, ProtobufCoder protobufCoder) {
        if (!coderCache.containsKey(tragetClass)) {
            coderCache.put(tragetClass, protobufCoder);
        }
    }

    public static byte[] encoder(Object object) throws Exception {
        if (object == null) {
            throw new IllegalArgumentException("object is null");
        }
        return doEncoder(getCoder(object.getClass()), object);
    }

    private static byte[] doEncoder(ProtobufCoder coder, Object object) throws IOException {
        int objectSize = coder.getSerializedSize(0, object, true, false);
        if (objectSize < 0) {
            throw new IOException("object size less than 0");
        }
        byte[] bytes = new byte[objectSize];

        CodedOutputStream codedOutputStream = CodedOutputStream.newInstance(bytes);
        coder.encoder(0, codedOutputStream, object, false);
        return bytes;
    }


    public static <T> T  decoder(byte[] input, Class<T> aClass) throws Exception {
        if (input == null) {
            throw new NullPointerException("input is null");
        }
        return doDecoder(input, aClass, getCoder(aClass));
    }
    @SuppressWarnings("unchecked")
    private static <T> T doDecoder(byte[] input, Class<T> aClass, ProtobufCoder coder) throws IOException {
        CodedInputStream codedOutputStream = CodedInputStream.newInstance(input);
        return (T) coder.decoder(aClass, codedOutputStream, ExtensionRegistryLite.getEmptyRegistry());
    }

    private static ProtobufCoder getCoder(Class<?> aClass) throws Exception {
        ProtobufCoder coder = coderCache.get(aClass);
        if (coder != null) {
            return coder;
        }
        parseJavaClass(aClass);
        return coderCache.get(aClass);
    }
}
