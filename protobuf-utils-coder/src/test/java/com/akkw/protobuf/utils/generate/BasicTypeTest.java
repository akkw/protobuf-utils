package com.akkw.protobuf.utils.generate;

import com.akkw.protobuf.test.BasicTypeJava;
import com.akkw.protobuf.test.ptoto.BasicType;
import com.akkw.protobuf.utils.coder.ProtobufCoder;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedOutputStream;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Map;

public class BasicTypeTest {

    private static final byte[] bytes = "basicTypeTest".getBytes();


    @Test
    public void basicTypeTest() throws Exception {
        BasicType.BasicTypeProto basicTypeProto = BasicType.BasicTypeProto.newBuilder()
                .setA(1)
                .setB(2)
                .setC(3)
                .setD(4)
                .setE(5)
                .setF(6.0)
                .setG(true)
                .setBytes(ByteString.copyFrom(bytes))
                .build();

        BasicTypeJava basicTypeJava = new BasicTypeJava();
        basicTypeJava.setA((byte) 1);
        basicTypeJava.setB((short) 2);
        basicTypeJava.setC(3);
        basicTypeJava.setD(4);
        basicTypeJava.setE(5);
        basicTypeJava.setF(6.0);
        basicTypeJava.setG(true);
        basicTypeJava.setBytes(bytes);

        DefaultCoderGenerate defaultCoderGenerate = new DefaultCoderGenerate(BasicTypeJava.class);
        defaultCoderGenerate.generate();

        Class<?> targetType = defaultCoderGenerate.getTargetType();
        Constructor<?> declaredConstructor = targetType.getDeclaredConstructor(Map.class);
        declaredConstructor.setAccessible(true);
        ProtobufCoder coder = (ProtobufCoder)declaredConstructor.newInstance(defaultCoderGenerate.getCoderCache());
        byte[] bytesValue = new byte[coder.getSerializedSize(0, basicTypeJava, true, false)];
        byte[] byteArray = basicTypeProto.toByteArray();
        Assert.assertEquals(byteArray.length, bytesValue.length);
        CodedOutputStream codedOutputStream = CodedOutputStream.newInstance(bytesValue);
        coder.encoder(0, codedOutputStream, basicTypeJava, false);
        Assert.assertArrayEquals(basicTypeProto.toByteArray(), bytesValue);
    }



    @Test
    public void basicTypeTestDefault() throws Exception {
        BasicType.BasicTypeProto basicTypeProto = BasicType.BasicTypeProto.newBuilder()
                .setA(0)
                .setB(0)
                .setC(0)
                .setD(0)
                .setE(0)
                .setF(0)
                .setG(false)
                .build();

        BasicTypeJava basicTypeJava = new BasicTypeJava();
        basicTypeJava.setA((byte) 0);
        basicTypeJava.setB((short) 0);
        basicTypeJava.setC(0);
        basicTypeJava.setD(0);
        basicTypeJava.setE(0);
        basicTypeJava.setF(0);
        basicTypeJava.setG(false);

        DefaultCoderGenerate defaultCoderGenerate = new DefaultCoderGenerate(BasicTypeJava.class);
        defaultCoderGenerate.generate();

        Class<?> targetType = defaultCoderGenerate.getTargetType();
        Constructor<?> declaredConstructor = targetType.getDeclaredConstructor(Map.class);
        declaredConstructor.setAccessible(true);
        ProtobufCoder coder = (ProtobufCoder)declaredConstructor.newInstance(defaultCoderGenerate.getCoderCache());
        byte[] bytesValue = new byte[coder.getSerializedSize(0, basicTypeJava, true, false)];
        byte[] byteArray = basicTypeProto.toByteArray();
        Assert.assertEquals(byteArray.length, bytesValue.length);
        CodedOutputStream codedOutputStream = CodedOutputStream.newInstance(bytesValue);
        coder.encoder(0, codedOutputStream, basicTypeJava, false);
        Assert.assertArrayEquals(basicTypeProto.toByteArray(), bytesValue);
    }


    @Test
    public void basicTypeTestEmpty() throws Exception {
        BasicType.BasicTypeProto basicTypeProto = BasicType.BasicTypeProto.newBuilder()
                .build();

        BasicTypeJava basicTypeJava = new BasicTypeJava();
        DefaultCoderGenerate defaultCoderGenerate = new DefaultCoderGenerate(BasicTypeJava.class);
        defaultCoderGenerate.generate();

        Class<?> targetType = defaultCoderGenerate.getTargetType();
        Constructor<?> declaredConstructor = targetType.getDeclaredConstructor(Map.class);
        declaredConstructor.setAccessible(true);
        ProtobufCoder coder = (ProtobufCoder)declaredConstructor.newInstance(defaultCoderGenerate.getCoderCache());
        byte[] bytesValue = new byte[coder.getSerializedSize(0, basicTypeJava, true, false)];
        byte[] byteArray = basicTypeProto.toByteArray();
        Assert.assertEquals(byteArray.length, bytesValue.length);
        CodedOutputStream codedOutputStream = CodedOutputStream.newInstance(bytesValue);
        coder.encoder(0, codedOutputStream, basicTypeJava, false);
        Assert.assertArrayEquals(basicTypeProto.toByteArray(), bytesValue);
    }
}
