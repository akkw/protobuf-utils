package com.akkw.protobuf.utils.coder;

import com.akkw.protobuf.utils.generate.DefaultCoderGenerate;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;

import java.io.IOException;
import java.lang.reflect.Field;

public interface ProtobufCoder {
    Object decoder(Class<?> type, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException;

    Object decoder(int fieldNumber, Field field, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException;

    default void encoder(int fieldNumber, CodedOutputStream output, Object o, boolean writeTag, boolean isList) throws IOException {
        if (writeTag &&  o != null && !DefaultCoderGenerate.basicType.contains(o.getClass())) {
            output.writeTag(fieldNumber, com.google.protobuf.WireFormat.WIRETYPE_LENGTH_DELIMITED);
            int size = getSerializedSize(0, o, false, isList);
            output.writeUInt32NoTag(size);
        }
        encoder(fieldNumber, output, o, isList);
    }

    void encoder(int fieldNumber, CodedOutputStream output, Object o, boolean isList) throws IOException;

    int getSerializedSize(int fieldNumber, Object o, boolean writeTag, boolean isList);
}
