package com.akkw.protobuf.utils.model;

public class TestSkuA {
    private String a;
    private int b;

    public TestSkuA(String a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return "TestSkuA{" +
                "a='" + a + '\'' +
                ", b=" + b +
                '}';
    }
}