package com.akkw.protobuf.utils.generate;

import com.google.protobuf.WireFormat;


public class TagUtils {

    public static void main(String[] args) {
        System.out.println(WireFormat.getTagFieldNumber(10));
        System.out.println(WireFormat.getTagFieldNumber(18));
        System.out.println(WireFormat.getTagFieldNumber(26));
        System.out.println(WireFormat.getTagFieldNumber(34));
    }
}
