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
    public void encoder(int fieldNumber, CodedOutputStream output, Object o, boolean writeTag, boolean isList) throws IOException {
        List<?> list = (List<?>) o;
        for (Object item : list) {
            if (writeTag && !DefaultCoderGenerate.basicType.contains(item.getClass())) {
                output.writeTag(fieldNumber, com.google.protobuf.WireFormat.WIRETYPE_LENGTH_DELIMITED);
                int size = getSerializedSize(0, item, false, true);
                output.writeUInt32NoTag(size);
            }
            encoder(fieldNumber, output, item, true);
        }
    }

    @Override
    public void encoder(int fieldNumber, CodedOutputStream output, Object o, boolean isList) throws IOException {
        coderCache.get(o.getClass()).encoder(fieldNumber,output, o, isList);
    }

    @Override
    public int getSerializedSize(int fieldNumber, Object o, boolean writeTag, boolean isList) {
        int size = 0;
        if (o instanceof List) {
            List<?> list = (List<?>) o;

            for (Object item : list) {
                size += coderCache.get(item.getClass()).getSerializedSize(fieldNumber, item, false, true);
                if (DefaultCoderGenerate.basicType.contains(item.getClass())) {
                    size += 1;
                }
            }
        } else {
            size += coderCache.get(o.getClass()).getSerializedSize(fieldNumber, o, false, isList);
        }
        return size;
    }
}