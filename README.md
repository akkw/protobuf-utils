# Protobuf Utils

**本工具可以将Java对象转化为Protobuf协议bytes，同时也可以将Protobuf协议bytes转化为Java对象**

### 使用说明
+ `com.akkw.protobuf.utils.coder.ProtobufCoderUtils.parseJavaClass(Class<?>)`方法可以解析你的Java Class并生成对应的Coder
+ `com.akkw.protobuf.utils.coder.ProtobufCoderUtils.decoder(byte[], Class<T>)` 方法入参$1是Protobuf协议 bytes，此方法可以将bytes数组转为$2类型的入参，注意类型匹配
+ `com.akkw.protobuf.utils.coder.ProtobufCoderUtils.encoder(Object)` 方法可以将$1入参转为Protobuf协议 bytes

### 例子

```
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

```






