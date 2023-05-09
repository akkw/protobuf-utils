package com.akkw.protobuf.utils.coder;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;

import java.io.IOException;

public class LongCoder implements ProtobufCoder {
    @Override
    public Object decoder(Class type, CodedInputStream input, ExtensionRegistryLite extensionRegistry) {
        return null;
    }

    @Override
    public void encoder(int fieldNumber, CodedOutputStream output, Object t, boolean isList) throws IOException {
        output.writeInt64(fieldNumber, (long) t);
    }

    @Override
    public int getSerializedSize(int fieldNumber, Object o, boolean writeTag, boolean isList) {
        return com.google.protobuf.CodedOutputStream
                .computeInt64Size(fieldNumber, (long) o);
    }
}
