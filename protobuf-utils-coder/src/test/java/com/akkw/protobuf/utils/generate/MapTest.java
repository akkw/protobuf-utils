package com.akkw.protobuf.utils.generate;

import com.akkw.protobuf.test.Coupon;
import com.akkw.protobuf.test.CouponDetail;
import com.akkw.protobuf.test.CouponWriteParam;
import com.akkw.protobuf.test.MapExampleJava;
import com.akkw.protobuf.test.ptoto.CouponProto;
import com.akkw.protobuf.test.ptoto.CouponWriteParamProto;
import com.akkw.protobuf.test.ptoto.MapExampleProto;
import com.akkw.protobuf.utils.coder.ProtobufCoder;
import com.akkw.protobuf.utils.coder.ProtobufCoderUtils;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapTest {
    @Test
    public void test() throws Exception {
        MapExampleProto.MapExample mapExample = MapExampleProto.MapExample.newBuilder().
                putStringMap("map-key1", "map-value1")
                .putStringMap("map-key2", "map-value2")
                .putStringMap("map-key3", "map-value3")
                .putStringMap("map-key4", "map-value4")
                .putStringMap("map-key5", "map-value5")
                .putCouponMap("coupon1", createCouponProtoDate())
                .putCouponMap("coupon2", createCouponProtoDate())
                .putCouponMap("coupon3", createCouponProtoDate())
                .putCouponDetail("couponDetail1", createCouponDetailProtoDate())
                .putCouponDetail("couponDetail2", createCouponDetailProtoDate())
                .putCouponDetail("couponDetail3", createCouponDetailProtoDate())
                .putCouponDetail("couponDetail4", createCouponDetailProtoDate())
                .build();

        byte[] byteArray = mapExample.toByteArray();
//        ProtobufCoderUtils.parseJavaClass(MapExampleJava.class);
        Assert.assertNotNull(ProtobufCoderUtils.decoder(byteArray, MapExampleJava.class));

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
//                .setCouponDetail(createCouponDetailProtoDate())
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
