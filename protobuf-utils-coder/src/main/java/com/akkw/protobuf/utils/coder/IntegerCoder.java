package com.akkw.protobuf.utils.coder;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;

import java.io.IOException;

public class IntegerCoder implements ProtobufCoder {

    @Override
    public Object decoder(Class type, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return input.readInt32();
    }

    @Override
    public void encoder(int fieldNumber, CodedOutputStream output, Object o, boolean isList) throws IOException {
        Number number = (Number) o;
        if (number.intValue() != 0) {
            output.writeInt32(fieldNumber, number.intValue());
        }
    }

    @Override
    public int getSerializedSize(int fieldNumber, Object o, boolean writeTag, boolean isList) {
        Number number = (Number) o;
        return number.intValue() != 0 ? com.google.protobuf.CodedOutputStream
                .computeInt32Size(fieldNumber, number.intValue()) : 0;
    }
}
