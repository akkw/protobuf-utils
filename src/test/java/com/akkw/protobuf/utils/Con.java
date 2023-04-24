package com.akkw.protobuf.utils;

public class Con {
    private com.akkw.protobuf.utils.coder.StringCoder nameCoder = com.akkw.protobuf.utils.coder.StringCoder.class.newInstance();
    private com.akkw.protobuf.utils.coder.StringCoder uuidCoder = com.akkw.protobuf.utils.coder.StringCoder.class.newInstance();
    private com.akkw.protobuf.utils.coder.LongCoder numCoder = com.akkw.protobuf.utils.coder.LongCoder.class.newInstance();

    public Con() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    }


    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Con con = new Con();
    }
}
