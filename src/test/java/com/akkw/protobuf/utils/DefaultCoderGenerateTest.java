package com.akkw.protobuf.utils;


import com.akkw.protobuf.utils.coder.ProtobufCoder;
import com.akkw.protobuf.utils.generate.DefaultCoderGenerate;
import com.akkw.protobuf.utils.model.Test;
import com.akkw.protobuf.utils.model.TestSku;
import com.google.protobuf.CodedOutputStream;

import java.util.Arrays;

public class DefaultCoderGenerateTest {
    @org.junit.Test
    public void test() throws Exception {

        DefaultCoderGenerate defaultCoderGenerate = new DefaultCoderGenerate(Test.class);
        defaultCoderGenerate.generate();
        Class<?> targetType = defaultCoderGenerate.getTargetType();

        ProtobufCoder o = (ProtobufCoder)targetType.newInstance();
        TestSku testSku = new TestSku("testSku", 1);
        Test test = new Test(null, true, 32, 1000, testSku, "test");

        int serializedSize = o.getSerializedSize(0, test);
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
        int serializedSize = o.getSerializedSize(0, testSku);
        byte[] bytes = new byte[serializedSize];
        CodedOutputStream codedOutputStream = CodedOutputStream.newInstance(bytes);
        o.encoder(0, codedOutputStream, testSku);
//        codedOutputStream.checkNoSpaceLeft();
        System.out.println(Arrays.toString(bytes));
    }
}