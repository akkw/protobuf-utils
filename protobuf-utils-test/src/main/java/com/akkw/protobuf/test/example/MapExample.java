package com.akkw.protobuf.test.example;

import com.akkw.protobuf.test.ptoto.CouponProto;
import com.akkw.protobuf.test.ptoto.CouponWriteParamProto;
import com.akkw.protobuf.test.ptoto.MapExampleProto;
import com.google.protobuf.InvalidProtocolBufferException;

public class MapExample {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        MapExampleProto.MapExample mapExample = MapExampleProto.MapExample.newBuilder().
        putStringMap("map-key1", "map-value1")
//        mapExample.putStringMap("map-key2", "map-value2");
//        mapExample.putStringMap("map-key3", "map-value3");
//        mapExample.putStringMap("map-key4", "map-value4");
//        mapExample.putStringMap("map-key5", "map-value5");
        .putCouponMap("coupon1",createCouponProtoDate())
//        mapExample.putCouponMap("coupon2",createCouponProtoDate());
//        mapExample.putCouponMap("coupon3",createCouponProtoDate());
        .putCouponDetail("couponDetail1",createCouponDetailProtoDate())
//        mapExample.putCouponDetail("couponDetail2",createCouponDetailProtoDate());
//        mapExample.putCouponDetail("couponDetail3",createCouponDetailProtoDate());
//        mapExample.putCouponDetail("couponDetail4",createCouponDetailProtoDate());
        .build();

        byte[] byteArray = mapExample.toByteArray();
        MapExampleProto.MapExample.parseFrom(byteArray);
    }




    private static CouponProto.Coupon createCouponProtoDate() {
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
                .build();
        return coupon;
    }


    private static CouponProto.CouponDetail createCouponDetailProtoDate() {
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
                .setSourceBuID(123)
                .build();
        return couponDetail;
    }
}
