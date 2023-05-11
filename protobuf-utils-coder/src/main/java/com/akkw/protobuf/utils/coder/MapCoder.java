package com.akkw.protobuf.utils.coder;

import com.google.protobuf.*;

import java.io.IOException;
import java.lang.Enum;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
    public Object decoder(int fieldNumber, Field field, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        Type[] actualTypeArguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
        Object key = null;
        Object value = null;
        while (true) {
            int tag = input.readTag();
            if (tag == 0) {
                break;
            }
            if (tag == makeTag(1, getFieldTypeByType(actualTypeArguments[0].getTypeName()).getWireType())) {
                key = parseField(input, extensionRegistry, getFieldTypeByType(actualTypeArguments[0].getTypeName()), actualTypeArguments[0]);
            } else if (tag == makeTag(2, getFieldTypeByType(actualTypeArguments[1].getTypeName()).getWireType())){
                value = parseField(input, extensionRegistry, getFieldTypeByType(actualTypeArguments[1].getTypeName()), actualTypeArguments[1]);
            } else {
                if (!input.skipField(tag)){
                    break;
                }
            }
        }
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    private Object parseField(
            CodedInputStream input,
            ExtensionRegistryLite extensionRegistry,
            WireFormat.FieldType fieldtype,
            Type classType)
            throws IOException {
        switch (fieldtype) {
            case MESSAGE:
                int length = input.readRawVarint32();
                int oldLimit = input.pushLimit(length);
                Object object = coderCache.get(classType).decoder((Class<?>) classType, input, extensionRegistry);
                input.popLimit(oldLimit);
                return object;
            case ENUM:
                return  input.readEnum();
            case GROUP:
                throw new RuntimeException("Groups are not allowed in maps.");
            default:
                return readPrimitiveField(input, fieldtype);
        }
    }
    @Override
    public void encoder(int fieldNumber, CodedOutputStream output, Object o, boolean writeTag, boolean isList) throws IOException {
        encoder(fieldNumber, output, o, isList);
    }

    private void writeValue(Object o, int number, CodedOutputStream output) throws IOException {
        WireFormat.FieldType keyType = getFieldType(o);
        output.writeTag(number, keyType.getWireType());
        writeElementNoTag(output, keyType, o);

    }

    @Override
    public void encoder(int fieldNumber, CodedOutputStream output, Object o, boolean isList) throws IOException {
        Map<?, ?> map = (Map<?,?>) o;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            output.writeTag(fieldNumber, WireFormat.WIRETYPE_LENGTH_DELIMITED);
            output.writeUInt32NoTag(getSerializedSize(fieldNumber, entry));
            writeValue(entry.getKey(), 1, output);
            writeValue(entry.getValue(), 2, output);
        }
    }

    @Override
    public int getSerializedSize(int fieldNumber, Object o, boolean writeTag, boolean isList) {
        Map<?, ?> map = (Map<?, ?>) o;
        int size = 0;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            size += computeTagSize(fieldNumber);
            int entrySize = getSerializedSize(fieldNumber, entry);
            size += computeUInt32SizeNoTag(entrySize) + entrySize;
        }
        return size;
    }


    private int getSerializedSize(int fieldNumber, Map.Entry<?,?> entry) {
        int keySize = computeElementSize(getFieldType(entry.getKey()), 1, entry.getKey());
        int valueSize = computeElementSize(getFieldType(entry.getValue()), 2, entry.getValue());
        if (getFieldType(entry.getValue()) == MESSAGE) {
            valueSize += computeUInt32SizeNoTag(valueSize);
        }

        return keySize + valueSize;
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

    private WireFormat.FieldType getFieldTypeByType(String type) {
        if (type.equals(Double.TYPE.getTypeName()) ) {
            return DOUBLE;
        } else if (type.equals(Float.TYPE.getTypeName())) {
            return FLOAT;
        } else if (type.equals(Long.TYPE.getTypeName())) {
            return INT64;
        } else if (type.equals(Integer.TYPE.getTypeName())) {
            return INT32;
        } else if (type.equals(Boolean.TYPE.getTypeName())) {
            return BOOL;
        } else if (type.equals(String.class.getTypeName())) {
            return STRING;
        } else if (type.equals(Enum.class.getTypeName())) {
            return ENUM;
        } else if (type.equals(byte[].class.getTypeName())) {
            return BYTES;
        } else {
            return MESSAGE;
        }
    }

    static Object readPrimitiveField(
            CodedInputStream input, WireFormat.FieldType type) throws IOException {
        switch (type) {
            case DOUBLE:
                return input.readDouble();
            case FLOAT:
                return input.readFloat();
            case INT64:
                return input.readInt64();
            case UINT64:
                return input.readUInt64();
            case INT32:
                return input.readInt32();
            case FIXED64:
                return input.readFixed64();
            case FIXED32:
                return input.readFixed32();
            case BOOL:
                return input.readBool();
            case BYTES:
                return input.readBytes();
            case UINT32:
                return input.readUInt32();
            case SFIXED32:
                return input.readSFixed32();
            case SFIXED64:
                return input.readSFixed64();
            case SINT32:
                return input.readSInt32();
            case SINT64:
                return input.readSInt64();

            case STRING:
                return input.readStringRequireUtf8();
            case GROUP:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle nested groups.");
            case MESSAGE:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle embedded messages.");
            case ENUM:
                // We don't handle enums because we don't know what to do if the
                // value is not recognized.
                throw new IllegalArgumentException("readPrimitiveField() cannot handle enums.");
        }

        throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
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

    void writeElementNoTag(
            final CodedOutputStream output, final WireFormat.FieldType type, final Object value)
            throws IOException {
        switch (type) {
            case DOUBLE:
                output.writeDoubleNoTag((Double) value);
                break;
            case FLOAT:
                output.writeFloatNoTag((Float) value);
                break;
            case INT64:
                output.writeInt64NoTag((Long) value);
                break;
            case UINT64:
                output.writeUInt64NoTag((Long) value);
                break;
            case INT32:
                output.writeInt32NoTag((Integer) value);
                break;
            case FIXED64:
                output.writeFixed64NoTag((Long) value);
                break;
            case FIXED32:
                output.writeFixed32NoTag((Integer) value);
                break;
            case BOOL:
                output.writeBoolNoTag((Boolean) value);
                break;
            case GROUP:
                output.writeGroupNoTag((MessageLite) value);
                break;
            case MESSAGE:
                output.writeUInt32NoTag(coderCache.get(value.getClass()).getSerializedSize(0, value, false, false));
                coderCache.get(value.getClass()).encoder(0, output, value, false);
                break;
            case STRING:
                if (value instanceof ByteString) {
                    output.writeBytesNoTag((ByteString) value);
                } else {
                    output.writeStringNoTag((String) value);
                }
                break;
            case BYTES:
                if (value instanceof ByteString) {
                    output.writeBytesNoTag((ByteString) value);
                } else {
                    output.writeByteArrayNoTag((byte[]) value);
                }
                break;
            case UINT32:
                output.writeUInt32NoTag((Integer) value);
                break;
            case SFIXED32:
                output.writeSFixed32NoTag((Integer) value);
                break;
            case SFIXED64:
                output.writeSFixed64NoTag((Long) value);
                break;
            case SINT32:
                output.writeSInt32NoTag((Integer) value);
                break;
            case SINT64:
                output.writeSInt64NoTag((Long) value);
                break;

            case ENUM:
                if (value instanceof Internal.EnumLite) {
                    output.writeEnumNoTag(((Internal.EnumLite) value).getNumber());
                } else {
                    output.writeEnumNoTag(((Integer) value).intValue());
                }
                break;
        }
    }

    static int makeTag(final int fieldNumber, final int wireType) {
        return (fieldNumber << 3) | wireType;
    }
}
