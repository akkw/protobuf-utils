package com.akkw.protobuf.utils.model;

public class TestSku {
    private String a;
    private int b;

    public TestSku(String a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return "TestSku{" +
                "a='" + a + '\'' +
                ", b=" + b +
                '}';
    }
}
