package com.akkw.protobuf.utils.coder;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;

import java.io.IOException;
import java.lang.reflect.Field;

public class EnumCoder implements ProtobufCoder {
    @Override
    public Object decoder(Class<?> type, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return type.getEnumConstants()[input.readEnum()];
    }

    @Override
    public Object decoder(int fieldNumber, Field field, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return null;
    }

    @Override
    public void encoder(int fieldNumber, CodedOutputStream output, Object o, boolean isList) throws IOException {
        @SuppressWarnings("rawtypes")
        Enum anEnum = (Enum) o;
        if (anEnum.ordinal() != 0) {
            output.writeEnum(fieldNumber, anEnum.ordinal());
        }
    }

    @Override
    public int getSerializedSize(int fieldNumber, Object o, boolean writeTag, boolean isList) {
        @SuppressWarnings("rawtypes")
        Enum anEnum = (Enum) o;
        return anEnum.ordinal() != 0 ? CodedOutputStream.computeTagSize(fieldNumber) + CodedOutputStream.computeEnumSizeNoTag(anEnum.ordinal()) : 0;
    }
}
