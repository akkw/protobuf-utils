package com.akkw.protobuf.utils.generate;

import com.akkw.protobuf.test.BasicTypeJava;
import com.akkw.protobuf.test.ptoto.BasicType;
import com.akkw.protobuf.utils.coder.ProtobufCoderUtils;
import com.google.protobuf.ByteString;
import org.junit.Assert;
import org.junit.Test;

public class BasicTypeTest {

    private static final byte[] bytes = "basicTypeTest".getBytes();


    @Test
    public void basicTypeEncoderTest() throws Exception {
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

        byte[] protoBytes = basicTypeProto.toByteArray();
        ProtobufCoderUtils.parseJavaClass(BasicTypeJava.class);
        byte[] encoder = ProtobufCoderUtils.encoder(basicTypeJava);
        Assert.assertEquals(protoBytes.length, encoder.length);

        Assert.assertNotNull(BasicType.BasicTypeProto.parseFrom(encoder));
        Assert.assertNotNull(ProtobufCoderUtils.decoder(protoBytes, BasicTypeJava.class));
    }


    @Test
    public void basicTypeEncoderTestDefault() throws Exception {
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

        byte[] protoBytes = basicTypeProto.toByteArray();
        ProtobufCoderUtils.parseJavaClass(BasicTypeJava.class);
        byte[] encoder = ProtobufCoderUtils.encoder(basicTypeJava);
        Assert.assertEquals(protoBytes.length, encoder.length);

        Assert.assertNotNull(BasicType.BasicTypeProto.parseFrom(encoder));
        Assert.assertNotNull(ProtobufCoderUtils.decoder(protoBytes, BasicTypeJava.class));
    }


    @Test
    public void basicTypeEncoderTestEmpty() throws Exception {
        BasicType.BasicTypeProto basicTypeProto = BasicType.BasicTypeProto.newBuilder()
                .build();

        byte[] protoBytes = basicTypeProto.toByteArray();
        ProtobufCoderUtils.parseJavaClass(BasicTypeJava.class);
        Assert.assertNotNull(ProtobufCoderUtils.decoder(protoBytes, BasicTypeJava.class));

    }


    @Test
    public void basicTypeDecoderTest() throws Exception {
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

        byte[] protoBytes = basicTypeProto.toByteArray();
        ProtobufCoderUtils.parseJavaClass(BasicTypeJava.class);
        Assert.assertNotNull(ProtobufCoderUtils.decoder(protoBytes, BasicTypeJava.class));
    }
}
