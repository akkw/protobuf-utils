package com.akkw.protobuf.test.example;

import com.akkw.protobuf.test.ptoto.BasicType;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

public class BasicTypeExample {
    private static final byte[] bytes = "basicTypeTest".getBytes();
    public static void main(String[] args) throws InvalidProtocolBufferException {
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

        byte[] byteArray = basicTypeProto.toByteArray();


        BasicType.BasicTypeProto basicTypeProto1 = BasicType.BasicTypeProto.parseFrom(byteArray);
    }
}
