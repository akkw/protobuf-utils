package com.akkw.protobuf.utils.coder;

import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.MapEntry;
import com.google.protobuf.MapEntryLite;
import com.google.protobuf.WireFormat;
import com.protobuf.test.UserProto;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapCoderTest {
    @Test
    public void mapMetadataTest() {
        MapEntry<String, String> stringStringMapEntry = MapEntry.newDefaultInstance(null, WireFormat.FieldType.STRING, "map-key", WireFormat.FieldType.STRING, "map-value");
        System.out.println(com.google.protobuf.CodedOutputStream.computeMessageSize(8, stringStringMapEntry));
        MapEntry<String, UserProto.MapVauleObject> mapVauleObjectMapEntry = MapEntry.newDefaultInstance(null, WireFormat.FieldType.STRING, "map-key", WireFormat.FieldType.MESSAGE, UserProto.MapVauleObject.newBuilder().setCode("code").setName("qzw").build());

        System.out.println(com.google.protobuf.CodedOutputStream
                .computeMessageSize(9, mapVauleObjectMapEntry));
       System.out.println(CodedOutputStream.computeTagSize(9) + CodedOutputStream.computeUInt32SizeNoTag(mapVauleObjectMapEntry.getSerializedSize()) + mapVauleObjectMapEntry.getSerializedSize());
    }

}