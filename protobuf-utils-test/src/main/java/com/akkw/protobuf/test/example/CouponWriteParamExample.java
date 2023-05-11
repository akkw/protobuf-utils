package com.akkw.protobuf.test.example;

import com.akkw.protobuf.test.CouponWriteParam;
import com.akkw.protobuf.test.ptoto.CouponProto;
import com.akkw.protobuf.test.ptoto.CouponWriteParamProto;
import com.google.protobuf.InvalidProtocolBufferException;

public class CouponWriteParamExample {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        CouponWriteParamProto.CouponWriteParam protoDate = createProtoDate();
        byte[] byteArray = protoDate.toByteArray();
        CouponWriteParamProto.CouponWriteParam couponWriteParam = CouponWriteParamProto.CouponWriteParam.parseFrom(byteArray);
    }


    private static CouponWriteParamProto.CouponWriteParam createProtoDate() {
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
                .setCouponDetail(couponDetail).build();

        couponWriteParam.addCouponDetailInfoList(couponDetail);
        couponWriteParam.addCouponDetailInfoList(couponDetail);
        couponWriteParam.addCouponInfoList(coupon);
        couponWriteParam.addCouponInfoList(coupon);
        couponWriteParam.putExtMap("12345", "adasd");
        couponWriteParam.putExtMap("123456", "qwezxc");
        couponWriteParam.putExtMap("1234567", "asdsadf");
        return couponWriteParam.build();
    }
}
