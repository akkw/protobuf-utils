package com.akkw.protobuf.utils.coder;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;

import java.io.IOException;

public class BooleanCoder implements ProtobufCoder {
    @Override
    public Object decoder(Class type, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return input.readBool();
    }

    @Override
    public void encoder(int fieldNumber, CodedOutputStream output, Object o, boolean isList) throws IOException {
        if ((boolean) o)  {
            output.writeBool(fieldNumber, (boolean) o);
        }
    }

    @Override
    public int getSerializedSize(int fieldNumber, Object o, boolean writeTag, boolean isList) {
        return (boolean) o ? com.google.protobuf.CodedOutputStream
                .computeBoolSize(fieldNumber, true) : 0;
    }
}
