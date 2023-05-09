package com.akkw.protobuf.utils.coder;

import com.google.protobuf.*;

import java.io.IOException;
import java.lang.Enum;
import java.lang.reflect.Type;
import java.util.Map;

import static com.google.protobuf.CodedOutputStream.computeTagSize;
import static com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag;
import static com.google.protobuf.WireFormat.FieldType.*;

public class MapCoder implements ProtobufCoder {

    private final Map<Type, ProtobufCoder> coderCache;


    public MapCoder(Map<Type, ProtobufCoder> coderCache) {
        this.coderCache = coderCache;
    }

    @Override
    public Object decoder(Class<?> type, CodedInputStream input, ExtensionRegistryLite extensionRegistry) {
        return null;
    }

    @Override
    public void encoder(int fieldNumber, CodedOutputStream output, Object o, boolean isList) throws IOException {

    }

    @Override
    public int getSerializedSize(int fieldNumber, Object o, boolean writeTag, boolean isList) {
        Map<?, ?> map = (Map<?, ?>) o;
        int size = computeTagSize(fieldNumber);
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            int keySize = computeElementSize(getFieldType(entry.getKey()), 1, entry.getKey());
            int valueSize = computeElementSize(getFieldType(entry.getValue()), 2, entry.getValue());
            if (getFieldType(entry.getValue()) == MESSAGE) {
                valueSize += computeUInt32SizeNoTag(valueSize);
            }
            size += computeUInt32SizeNoTag(keySize + valueSize) +keySize + valueSize;
        }
        return size;
    }

     int computeElementSize(
            final WireFormat.FieldType type, final int number, final Object value) {
        int tagSize = computeTagSize(number);
        if (type == WireFormat.FieldType.GROUP) {
            // Only count the end group tag for proto2 messages as for proto1 the end
            // group tag will be counted as a part of getSerializedSize().
            tagSize *= 2;
        }
        return tagSize + coderCache.get(value.getClass()).getSerializedSize(0, value, false, false);
    }

    private WireFormat.FieldType getFieldType(Object o) {
        if (o instanceof Double) {
            return DOUBLE;
        } else if (o instanceof Float) {
            return FLOAT;
        } else if (o instanceof Long) {
            return INT64;
        } else if (o instanceof Integer) {
            return INT32;
        } else if (o instanceof Boolean) {
            return BOOL;
        } else if (o instanceof String) {
            return STRING;
        } else if (o instanceof Enum) {
            return ENUM;
        } else if (o instanceof byte[]){
            return BYTES;
        } else {
            return MESSAGE;
        }

    }
}
