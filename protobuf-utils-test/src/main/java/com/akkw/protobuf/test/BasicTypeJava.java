package com.akkw.protobuf.test;

import java.util.Arrays;

public class BasicTypeJava {
    byte a;

    short b;

    int c;

    long d;
    float e;

    double f;

    boolean g;

    byte[] bytes;

    public void setA(byte a) {
        this.a = a;
    }

    public void setB(short b) {
        this.b = b;
    }

    public void setC(int c) {
        this.c = c;
    }

    public void setD(long d) {
        this.d = d;
    }

    public void setE(float e) {
        this.e = e;
    }

    public void setF(double f) {
        this.f = f;
    }

    public void setG(boolean g) {
        this.g = g;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public String toString() {
        return "BasicTypeJava{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", d=" + d +
                ", e=" + e +
                ", f=" + f +
                ", g=" + g +
                ", bytes=" + Arrays.toString(bytes) +
                '}';
    }
}
