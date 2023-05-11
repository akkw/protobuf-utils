package com.akkw.protobuf.test;

import java.util.Map;

public class MapExampleJava {
    Map<String, Coupon> couponMap;
    Map<String, CouponDetail> couponDetailMap;

    Map<String, String> stringMap;

    public void setCouponMap(Map<String, Coupon> couponMap) {
        this.couponMap = couponMap;
    }

    public void setCouponDetailMap(Map<String, CouponDetail> couponDetailMap) {
        this.couponDetailMap = couponDetailMap;
    }

    public void setStringMap(Map<String, String> stringMap) {
        this.stringMap = stringMap;
    }
}
