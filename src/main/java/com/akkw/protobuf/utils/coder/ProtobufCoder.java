package com.akkw.protobuf.utils.coder;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;

import java.io.IOException;

public interface ProtobufCoder {
    Object decoder(Class<?> type, CodedInputStream input, ExtensionRegistryLite extensionRegistry);

    void encoder(int fieldNumber, CodedOutputStream output, Object t) throws IOException;

    int getSerializedSize(final int fieldNumber, final Object o);
}
