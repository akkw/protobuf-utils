package com.akkw.protobuf.utils.coder;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;

import java.io.IOException;

public class StringCoder implements ProtobufCoder {

    @Override
    public Object decoder(Class<?> type, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        if (type.isAssignableFrom(byte[].class)) {
            return input.readBytes().toByteArray();
        } else {
            return input.readStringRequireUtf8();
        }
    }

    @Override
    public void encoder(int fieldNumber, CodedOutputStream output, Object o, boolean isList) throws IOException {
        if (o instanceof String && (isList || ((String) o).length() != 0)) {
            output.writeString(fieldNumber, (String) o);
        } else if (o instanceof byte[] && (isList || ((byte[]) o).length != 0)) {
            output.writeBytes(fieldNumber, ByteString.copyFrom((byte[]) o));
        }
    }

    @Override
    public int getSerializedSize(int fieldNumber, Object o, boolean writeTag, boolean isList) {
        if (o instanceof String && (isList || ((String) o).length() != 0)) {
            return writeTag ?  com.google.protobuf.CodedOutputStream
                    .computeStringSize(fieldNumber, (String) o) : CodedOutputStream.computeStringSizeNoTag((String) o);
        }
        if (o instanceof byte[] && (isList || ((byte[]) o).length != 0)) {
            return writeTag ? com.google.protobuf.CodedOutputStream
                    .computeBytesSize(fieldNumber, ByteString.copyFrom((byte[]) o)) : CodedOutputStream.computeBytesSizeNoTag(ByteString.copyFrom((byte[]) o));
        }
        return 0;
    }

}
