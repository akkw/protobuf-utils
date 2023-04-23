package com.akkw.protobuf.utils;


import com.akkw.protobuf.utils.model.SkuInfo;
import org.junit.Test;

public class DefaultCoderGenerateTest {
    @Test
    public void test() throws IllegalAccessException {

        SkuInfo.Sku sku = new SkuInfo.Sku();
        sku.setName("testsku");
        sku.setNum(10);
        sku.setUuid("uuid");

        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setSku(sku);
        skuInfo.setA("a");
        skuInfo.setB(2);

        DefaultCoderGenerate.javaObjectToProtobufByte(skuInfo);
    }
}