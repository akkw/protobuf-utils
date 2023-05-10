package com.akkw.protobuf.utils.coder;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;

import java.io.IOException;

public class FloatCoder implements ProtobufCoder {
    @Override
    public Object decoder(Class<?> type, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return input.readFloat();
    }

    @Override
    public void encoder(int fieldNumber, CodedOutputStream output, Object o, boolean isList) throws IOException {
        if ((float) o != 0) {
            output.writeFloat(fieldNumber, (float) o);
        }
    }

    @Override
    public int getSerializedSize(int fieldNumber, Object o, boolean writeTag, boolean isList) {
        return (float) o != 0 ? com.google.protobuf.CodedOutputStream
                .computeFloatSize(fieldNumber, (float) o) : 0;
    }
}
