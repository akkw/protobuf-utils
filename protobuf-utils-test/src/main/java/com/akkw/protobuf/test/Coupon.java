package com.akkw.protobuf.test;

import java.math.BigDecimal;
import java.util.Date;

public class Coupon {
    private String couponId;

    private String pwdKey;

    private String pin;

    private Long batchId;

    private Integer couponType;

    private Integer couponStyle;

//    private BigDecimal discount;
//
//    private BigDecimal quota;
//
//    private Date beginTime;
//
//    private Date endTime;
    private Integer couponState;

//    private Date createTime;
//
//    private Date updateTime;

    private Long orderId;

//    private Date useTime;

    private String bizId;

    private Integer visible;

    private String orgType;

    private String batchOrgType;

    private String ext;

    private Integer buID;

    private Integer tenantId;

    private String bizExt;

    private String opCaller;

    private Integer sourceBuID;

    private CouponDetail couponDetail;


    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public void setPwdKey(String pwdKey) {
        this.pwdKey = pwdKey;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public void setCouponStyle(Integer couponStyle) {
        this.couponStyle = couponStyle;
    }

//    public void setDiscount(BigDecimal discount) {
//        this.discount = discount;
//    }
//
//    public void setQuota(BigDecimal quota) {
//        this.quota = quota;
//    }
//
//    public void setBeginTime(Date beginTime) {
//        this.beginTime = beginTime;
//    }
//
//    public void setEndTime(Date endTime) {
//        this.endTime = endTime;
//    }

    public void setCouponState(Integer couponState) {
        this.couponState = couponState;
    }

//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }

//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

//    public void setUseTime(Date useTime) {
//        this.useTime = useTime;
//    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public void setBatchOrgType(String batchOrgType) {
        this.batchOrgType = batchOrgType;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public void setBuID(Integer buID) {
        this.buID = buID;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public void setBizExt(String bizExt) {
        this.bizExt = bizExt;
    }

    public void setOpCaller(String opCaller) {
        this.opCaller = opCaller;
    }

    public void setSourceBuID(Integer sourceBuID) {
        this.sourceBuID = sourceBuID;
    }

    public void setCouponDetail(CouponDetail couponDetail) {
        this.couponDetail = couponDetail;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "couponId='" + couponId + '\'' +
                ", pwdKey='" + pwdKey + '\'' +
                ", pin='" + pin + '\'' +
                ", batchId=" + batchId +
                ", couponType=" + couponType +
                ", couponStyle=" + couponStyle +
                ", couponState=" + couponState +
                ", orderId=" + orderId +
                ", bizId='" + bizId + '\'' +
                ", visible=" + visible +
                ", orgType='" + orgType + '\'' +
                ", batchOrgType='" + batchOrgType + '\'' +
                ", ext='" + ext + '\'' +
                ", buID=" + buID +
                ", tenantId=" + tenantId +
                ", bizExt='" + bizExt + '\'' +
                ", opCaller='" + opCaller + '\'' +
                ", sourceBuID=" + sourceBuID +
                ", couponDetail=" + couponDetail +
                '}';
    }
}
