package com.akkw.protobuf.utils.model;

public class SkuInfo {
    private String a;

    private Sku sku;


    private int b;


    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public static class Sku {
        String name;
        String uuid;

        long num;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public long getNum() {
            return num;
        }

        public void setNum(long num) {
            this.num = num;
        }
    }
}
