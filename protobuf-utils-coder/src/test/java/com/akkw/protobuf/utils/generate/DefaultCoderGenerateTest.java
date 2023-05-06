package com.akkw.protobuf.utils.generate;


import com.akkw.protobuf.utils.coder.ProtobufCoder;
import com.akkw.protobuf.utils.model.TestSku;
import com.akkw.protobuf.utils.model.TestSkuA;
import com.akkw.test.model.NickNameJava;
import com.akkw.test.model.UserJavaObject;
import com.google.protobuf.CodedOutputStream;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class DefaultCoderGenerateTest {
    @Test
    public void test() throws Exception {

        DefaultCoderGenerate defaultCoderGenerate = new DefaultCoderGenerate(com.akkw.protobuf.utils.model.Test.class);
        defaultCoderGenerate.generate();
        Class<?> targetType = defaultCoderGenerate.getTargetType();
        Constructor<?> declaredConstructor = targetType.getDeclaredConstructor(Map.class);
        declaredConstructor.setAccessible(true);
        ProtobufCoder o = (ProtobufCoder)declaredConstructor.newInstance(defaultCoderGenerate.getCoderCache());
        TestSku testSku = new TestSku("testSku", 1);
        TestSkuA testSkuA = new TestSkuA("testSkuA", 2);
        com.akkw.protobuf.utils.model.Test test = new com.akkw.protobuf.utils.model.Test(null, true, 32, testSkuA, 1000, testSku, "test");

        int serializedSize = o.getSerializedSize(0, test, true);
        System.out.println(serializedSize);
        byte[] bytes = new byte[serializedSize];
        CodedOutputStream codedOutputStream = CodedOutputStream.newInstance(bytes);
        o.encoder(0, codedOutputStream, test);
//        codedOutputStream.checkNoSpaceLeft();
        System.out.println(Arrays.toString(bytes));
    }

    @org.junit.Test
    public void testSku() throws Exception {

        DefaultCoderGenerate defaultCoderGenerate = new DefaultCoderGenerate(TestSku.class);
        defaultCoderGenerate.generate();
        Class<?> targetType = defaultCoderGenerate.getTargetType();

        ProtobufCoder o = (ProtobufCoder)targetType.newInstance();
        TestSku testSku = new TestSku("testSku", 1);
        int serializedSize = o.getSerializedSize(0, testSku, true);
        byte[] bytes = new byte[serializedSize];
        CodedOutputStream codedOutputStream = CodedOutputStream.newInstance(bytes);
        o.encoder(0, codedOutputStream, testSku);
//        codedOutputStream.checkNoSpaceLeft();
        System.out.println(Arrays.toString(bytes));
    }


    @Test
    public void skuTestEncoder() throws Exception {
        ArrayList<String> strList = new ArrayList<>();
        strList.add("strList");
        ArrayList<NickNameJava> nickNameJavas = new ArrayList<>();
        nickNameJavas.add(new NickNameJava("asd"));
        UserJavaObject userJavaObject = new UserJavaObject();
        userJavaObject.setId(1);
        userJavaObject.setCode("200");
        userJavaObject.setName("qzw");
        userJavaObject.setStrList(strList);
        userJavaObject.setNickNameJavas(nickNameJavas);

        DefaultCoderGenerate defaultCoderGenerate = new DefaultCoderGenerate(UserJavaObject.class);
        defaultCoderGenerate.generate();
        Class<?> targetType = defaultCoderGenerate.getTargetType();
        Constructor<?> declaredConstructor = targetType.getDeclaredConstructor(Map.class);
        declaredConstructor.setAccessible(true);
        ProtobufCoder o = (ProtobufCoder)declaredConstructor.newInstance(defaultCoderGenerate.getCoderCache());
        int serializedSize = o.getSerializedSize(0, userJavaObject, true);
        System.out.println(serializedSize);
        byte[] bytes = new byte[serializedSize];
        CodedOutputStream codedOutputStream = CodedOutputStream.newInstance(bytes);
        o.encoder(0, codedOutputStream, userJavaObject);
        System.out.println(Arrays.toString(bytes));
    }
}