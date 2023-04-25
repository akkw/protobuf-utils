package com.akkw.protobuf.utils;


import com.akkw.protobuf.utils.coder.ProtobufCoder;
import com.akkw.protobuf.utils.generate.DefaultCoderGenerate;
import com.akkw.protobuf.utils.model.Test;
import com.akkw.protobuf.utils.model.TestSku;
import com.akkw.protobuf.utils.model.TestSkuA;
import com.google.protobuf.CodedOutputStream;

import java.util.Arrays;

public class DefaultCoderGenerateTest {
    public static void main(String[] args) throws Exception {

        DefaultCoderGenerate defaultCoderGenerate = new DefaultCoderGenerate(Test.class, false);
        defaultCoderGenerate.generate();
        Class<?> targetType = defaultCoderGenerate.getTargetType();

        ProtobufCoder o = (ProtobufCoder)targetType.newInstance();
        TestSku testSku = new TestSku("testSku", 1);
        TestSkuA testSkuA = new TestSkuA("testSkuasdad", 123123);
        Test test = new Test(null, true, 32, testSkuA, 1000, testSku, "test");

        int serializedSize = o.getSerializedSize(0, test);
        byte[] bytes = new byte[serializedSize];
        CodedOutputStream codedOutputStream = CodedOutputStream.newInstance(bytes);
        o.encoder(0, codedOutputStream, test);
//        codedOutputStream.checkNoSpaceLeft();
        System.out.println(Arrays.toString(bytes));
    }
}