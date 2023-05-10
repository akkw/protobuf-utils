package com.akkw.protobuf.test;

import java.util.List;
import java.util.Map;

public class CouponWriteParam {

    /**
     * 券实体信息列表
     */
    private List<Coupon> couponInfoList;

    private List<CouponDetail> couponDetailInfoList;

    /**
     * 调用来源
     * eg：jdos应用名 或 定义的场景id
     */
    private String invokeSource;


    /**
     * 备用扩展对象
     */
    private Map<String,String> extMap;

    public void setCouponInfoList(List<Coupon> couponInfoList) {
        this.couponInfoList = couponInfoList;
    }

    public void setCouponDetailInfoList(List<CouponDetail> couponDetailInfoList) {
        this.couponDetailInfoList = couponDetailInfoList;
    }

    public void setInvokeSource(String invokeSource) {
        this.invokeSource = invokeSource;
    }

    public void setExtMap(Map<String, String> extMap) {
        this.extMap = extMap;
    }
}