package com.akkw.protobuf.utils.generate;

import com.akkw.protobuf.test.BasicTypeJava;
import com.akkw.protobuf.test.Coupon;
import com.akkw.protobuf.test.CouponDetail;
import com.akkw.protobuf.test.CouponWriteParam;
import com.akkw.protobuf.test.ptoto.BasicType;
import com.akkw.protobuf.test.ptoto.CouponProto;
import com.akkw.protobuf.test.ptoto.CouponWriteParamProto;
import com.akkw.protobuf.utils.coder.ProtobufCoderUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RecombinationTypeTest {
    @Test
    public void recombinationTypeTest() throws Exception {
        CouponWriteParam couponWriteParam = createJavaData();
        CouponWriteParamProto.CouponWriteParam couponWriteParamProto = createProtoDate();

        byte[] protoBytes = couponWriteParamProto.toByteArray();
        byte[] encoder = ProtobufCoderUtils.encoder(couponWriteParam);


        Assert.assertEquals(protoBytes.length, encoder.length);
        Assert.assertNotNull(BasicType.BasicTypeProto.parseFrom(encoder));
        Assert.assertNotNull(ProtobufCoderUtils.decoder(protoBytes, CouponWriteParam.class));
    }


    @Test
    public void recombinationTypeDecoderTest() throws Exception {
        CouponWriteParamProto.CouponWriteParam couponWriteParamProto = createProtoDate();
        byte[] byteArray = couponWriteParamProto.toByteArray();
        Assert.assertNotNull(ProtobufCoderUtils.decoder(byteArray, CouponWriteParam.class));
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
