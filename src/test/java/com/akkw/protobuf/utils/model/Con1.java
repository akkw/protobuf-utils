package com.akkw.protobuf.utils.model;

public class Con1 {

    private com.akkw.protobuf.utils.coder.StringCoder aCoder;
    private com.akkw.protobuf.utils.Con skuCoder;
    private com.akkw.protobuf.utils.coder.IntegerCoder bCoder;
    public Con1() throws InstantiationException, IllegalAccessException {
        this.aCoder = com.akkw.protobuf.utils.coder.StringCoder.class.newInstance();
        this.skuCoder = com.akkw.protobuf.utils.Con.class.newInstance();
        this.bCoder = com.akkw.protobuf.utils.coder.IntegerCoder.class.newInstance();
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Con1 con1 = new Con1();
    }
}
