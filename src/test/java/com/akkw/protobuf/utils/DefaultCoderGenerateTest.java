package com.akkw.protobuf.utils;


import com.akkw.protobuf.utils.coder.ProtobufCoder;
import com.akkw.protobuf.utils.model.SkuInfo;
import com.google.protobuf.CodedOutputStream;
import org.junit.Test;

import java.lang.reflect.Field;

public class DefaultCoderGenerateTest {
    public static void main(String[] args) throws Exception {

        DefaultCoderGenerate defaultCoderGenerate = new DefaultCoderGenerate(SkuInfo.class);
        defaultCoderGenerate.generate();
        Class<?> targetType = defaultCoderGenerate.getTargetType();

        ProtobufCoder o = (ProtobufCoder)targetType.newInstance();
        CodedOutputStream codedOutputStream = CodedOutputStream.newInstance(new byte[1024]);
        SkuInfo.Sku sku = new SkuInfo.Sku();
        sku.setUuid("uuid");
        sku.setName("name");
        sku.setNum(3);
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setB(1);
        skuInfo.setA("a");
        skuInfo.setSku(sku);
        o.encoder(1, codedOutputStream, skuInfo);
        System.out.println();
    }
}