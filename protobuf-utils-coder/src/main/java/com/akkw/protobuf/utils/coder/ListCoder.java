package com.akkw.protobuf.utils.coder;

import com.akkw.protobuf.utils.generate.DefaultCoderGenerate;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class ListCoder implements ProtobufCoder {

    private Map<Type, ProtobufCoder> coderCache;

    public ListCoder(Map<Type, ProtobufCoder> coderCache) {
        this.coderCache = coderCache;
    }

    @Override
    public Object decoder(Class<?> type, CodedInputStream input, ExtensionRegistryLite extensionRegistry) {
        return null;
    }

    @Override
    public void encoder(int fieldNumber, CodedOutputStream output, Object o) throws IOException {
        List<?> list = (List<?>) o;
        Type genericSuperclass = o.getClass().getGenericSuperclass();
//
//        for (Object o : list) {
//            o.
//        }
    }

    @Override
    public int getSerializedSize(int fieldNumber, Object o, boolean writeTag) {
        List<?> list = (List<?>) o;
        int size = 0;
        for (Object item : list) {
            size += coderCache.get(item.getClass()).getSerializedSize(fieldNumber, item, false);
            if (DefaultCoderGenerate.basicType.contains(item.getClass())) {
                size += 1;
            }
        }
        return size;
    }
}