package com.akkw.protobuf.utils.coder;

import com.akkw.protobuf.utils.generate.DefaultCoderGenerate;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;

import java.io.IOException;
import java.util.List;

public interface ProtobufCoder {
    Object decoder(Class<?> type, CodedInputStream input, ExtensionRegistryLite extensionRegistry);

    default void encoder(int fieldNumber, CodedOutputStream output, Object o, boolean writeTag) throws IOException {
        if (writeTag &&  o != null && !DefaultCoderGenerate.basicType.contains(o.getClass())) {
            output.writeTag(fieldNumber, com.google.protobuf.WireFormat.WIRETYPE_LENGTH_DELIMITED);
            int size = getSerializedSize(0, o, false);
            output.writeUInt32NoTag(size);
        }
        encoder(fieldNumber, output, o);
    }

    void encoder(int fieldNumber, CodedOutputStream output, Object o) throws IOException;

    int getSerializedSize(int fieldNumber, Object o, boolean writeTag);
}
