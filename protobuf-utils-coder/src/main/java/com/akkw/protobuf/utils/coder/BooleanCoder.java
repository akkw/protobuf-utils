package com.akkw.protobuf.utils.coder;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BooleanCoder implements ProtobufCoder {

    private static final Map<Type, ProtobufCoder> coderCache = new ConcurrentHashMap<>();

    @Override
    public Object decoder(Class<?> aClass, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return input.readBool();
    }

    @Override
    public Object decoder(int fieldNumber, Field field, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
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
