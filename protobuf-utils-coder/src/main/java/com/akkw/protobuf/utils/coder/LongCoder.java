package com.akkw.protobuf.utils.coder;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;

import java.io.IOException;

public class LongCoder implements ProtobufCoder {
    @Override
    public Object decoder(Class<?> type, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return input.readInt64();
    }

    @Override
    public void encoder(int fieldNumber, CodedOutputStream output, Object o, boolean isList) throws IOException {
        if ((long) o != 0) {
            output.writeInt64(fieldNumber, (long) o);
        }
    }

    @Override
    public int getSerializedSize(int fieldNumber, Object o, boolean writeTag, boolean isList) {
        return (long) o != 0 ? com.google.protobuf.CodedOutputStream
                .computeInt64Size(fieldNumber, (long) o) : 0;
    }
}
