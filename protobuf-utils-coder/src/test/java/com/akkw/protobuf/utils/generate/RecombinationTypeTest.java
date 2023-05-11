package com.akkw.protobuf.utils.generate;

import com.akkw.protobuf.test.BasicTypeJava;
import com.akkw.protobuf.test.Coupon;
import com.akkw.protobuf.test.CouponDetail;
import com.akkw.protobuf.test.CouponWriteParam;
import com.akkw.protobuf.test.ptoto.CouponProto;
import com.akkw.protobuf.test.ptoto.CouponWriteParamProto;
import com.akkw.protobuf.utils.coder.ProtobufCoder;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.*;

public class RecombinationTypeTest {
    @Test
    public void recombinationTypeTest() throws Exception {
        CouponWriteParam couponWriteParam = createJavaData();
        CouponWriteParamProto.CouponWriteParam couponWriteParamProto = createProtoDate();

        DefaultCoderGenerate defaultCoderGenerate = new DefaultCoderGenerate(CouponWriteParam.class);
        defaultCoderGenerate.generate();

        Class<?> targetType = defaultCoderGenerate.getTargetType();
        Constructor<?> declaredConstructor = targetType.getDeclaredConstructor(Map.class, Set.class);
        declaredConstructor.setAccessible(true);
        ProtobufCoder coder = (ProtobufCoder)declaredConstructor.newInstance(defaultCoderGenerate.getCoderCache(), DefaultCoderGenerate.basicType);
        byte[] bytesValue = new byte[coder.getSerializedSize(0, couponWriteParam, true, false)];
        byte[] byteArray = couponWriteParamProto.toByteArray();
        Assert.assertEquals(byteArray.length, bytesValue.length);
        CodedOutputStream codedOutputStream = CodedOutputStream.newInstance(bytesValue);
        coder.encoder(0, codedOutputStream, couponWriteParam, false);
        Assert.assertArrayEquals(byteArray, bytesValue);
    }


    @Test
    public void recombinationTypeDecoderTest() throws Exception {

        CouponWriteParamProto.CouponWriteParam couponWriteParamProto = createProtoDate();

        DefaultCoderGenerate defaultCoderGenerate = new DefaultCoderGenerate(CouponWriteParam.class);
        defaultCoderGenerate.generate();

        Class<?> targetType = defaultCoderGenerate.getTargetType();
        Constructor<?> declaredConstructor = targetType.getDeclaredConstructor(Map.class, Set.class);
        declaredConstructor.setAccessible(true);
        ProtobufCoder coder = (ProtobufCoder)declaredConstructor.newInstance(defaultCoderGenerate.getCoderCache(), DefaultCoderGenerate.basicType);
        byte[] byteArray = couponWriteParamProto.toByteArray();

        CodedInputStream codedOutputStream = CodedInputStream.newInstance(byteArray);
        Object decoder = coder.decoder(CouponWriteParam.class, codedOutputStream, ExtensionRegistryLite.getEmptyRegistry());
        System.out.println(decoder);

    }

    private CouponWriteParam createJavaData() {
        CouponWriteParam couponWriteParam = new CouponWriteParam();


        Coupon coupon1 = new Coupon();
        coupon1.setCouponId("couponId1");
        coupon1.setPwdKey("pwdKey1");
        coupon1.setPin("zxcasdqweasdz");
        coupon1.setBatchId(1234567123123L);
        coupon1.setCouponType(1);
        coupon1.setCouponStyle(2);
        coupon1.setCouponState(2);
        coupon1.setOrderId(987654321L);
        coupon1.setBizId("123");
        coupon1.setVisible(1);
        coupon1.setOrgType("test");
        coupon1.setBatchOrgType("345");
        coupon1.setExt("{}");
        coupon1.setBuID(2);
        coupon1.setTenantId(3);
        coupon1.setBizExt("bizExt");
        coupon1.setOpCaller("caller");
        coupon1.setSourceBuID(123);

        CouponDetail couponDetail1 = new CouponDetail();
        couponDetail1.setCouponId("couponDetailId1");
        couponDetail1.setPwdKey("pwdKey1");
        couponDetail1.setPin("zxcasdqweasdz");
        couponDetail1.setBatchId(1234567123123L);
        couponDetail1.setCouponType(1);
        couponDetail1.setCouponStyle(2);
        couponDetail1.setCouponState(1);
        couponDetail1.setOrderId(987654321L);
        couponDetail1.setBizId("123");
        couponDetail1.setVisible(1);
        couponDetail1.setOrgType("test");
        couponDetail1.setExt("{}");
        couponDetail1.setBuID(2);
        couponDetail1.setTenantId(3);
        couponDetail1.setBizExt("bizExt");
        couponDetail1.setOpCaller("caller");
        couponDetail1.setSourceBuID(123);

//        coupon1.setCouponDetail(couponDetail1);

        List<CouponDetail> couponDetails = new ArrayList<>();
        couponDetails.add(couponDetail1);
        couponDetails.add(couponDetail1);
        ArrayList<Coupon> coupons = new ArrayList<>();
        coupons.add(coupon1);
        coupons.add(coupon1);
        coupons.add(coupon1);

        couponWriteParam.setCouponDetailInfoList(couponDetails);
        couponWriteParam.setCouponInfoList(coupons);
        couponWriteParam.setInvokeSource("1coupon");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("12345", "adasd");
        map.put("123456", "qwezxc");
        map.put("1234567", "asdsadf");
        couponWriteParam.setExtMap(map);
        return couponWriteParam;
    }


    private CouponWriteParamProto.CouponWriteParam createProtoDate() {
        CouponWriteParamProto.CouponWriteParam.Builder couponWriteParam = CouponWriteParamProto.CouponWriteParam.newBuilder();
        CouponProto.CouponDetail couponDetail = CouponProto.CouponDetail.newBuilder()
                .setCouponId("couponDetailId1")
                .setPwdKey("pwdKey1")
                .setPin("zxcasdqweasdz")
                .setBatchId(1234567123123L)
                .setCouponType(1)
                .setCouponStyle(2)
                .setCouponState(1)
                .setOrderId(987654321L)
                .setBizId("123")
                .setVisible(1)
                .setOrgType("test")
                .setExt("{}")
                .setBuID(2)
                .setTenantId(3)
                .setBizExt("bizExt")
                .setOpCaller("caller")
                .setSourceBuID(123).build();

        CouponProto.Coupon coupon = CouponProto.Coupon.newBuilder()
                .setCouponId("couponId1")
                .setPwdKey("pwdKey1")
                .setPin("zxcasdqweasdz")
                .setBatchId(1234567123123L)
                .setCouponType(1)
                .setCouponStyle(2)
                .setCouponState(2)
                .setOrderId(987654321L)
                .setBizId("123")
                .setVisible(1)
                .setOrgType("test")
                .setBatchOrgType("345")
                .setExt("{}")
                .setBuID(2)
                .setTenantId(3)
                .setBizExt("bizExt")
                .setOpCaller("caller")
                .setSourceBuId(123)
//                .setCouponDetail(couponDetail)
                .build();

        couponWriteParam.addCouponDetailInfoList(couponDetail);
        couponWriteParam.addCouponDetailInfoList(couponDetail);
        couponWriteParam.addCouponInfoList(coupon);
        couponWriteParam.addCouponInfoList(coupon);
        couponWriteParam.addCouponInfoList(coupon);
        couponWriteParam.setInvokeSource("1coupon");
        couponWriteParam.putExtMap("12345", "adasd");
        couponWriteParam.putExtMap("123456", "qwezxc");
        couponWriteParam.putExtMap("1234567", "asdsadf");
        return couponWriteParam.build();
    }
}
