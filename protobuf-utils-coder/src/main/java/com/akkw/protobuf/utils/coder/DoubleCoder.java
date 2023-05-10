package com.akkw.protobuf.utils.coder;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;

import java.io.IOException;

public class DoubleCoder implements ProtobufCoder {
    @Override
    public Object decoder(Class<?> type, CodedInputStream input, ExtensionRegistryLite extensionRegistry) {
        return null;
    }

    @Override
    public void encoder(int fieldNumber, CodedOutputStream output, Object o, boolean isList) throws IOException {
        if ((double) o != 0) {
            output.writeDouble(fieldNumber, (double) o);
        }
    }

    @Override
    public int getSerializedSize(int fieldNumber, Object o, boolean writeTag, boolean isList) {
        return (double) o != 0 ? com.google.protobuf.CodedOutputStream
                .computeDoubleSize(fieldNumber, (double) o) : 0;
    }
}
