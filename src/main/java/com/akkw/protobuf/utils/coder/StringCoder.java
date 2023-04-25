package com.akkw.protobuf.utils.coder;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;

import java.io.IOException;

public class StringCoder implements ProtobufCoder {

    @Override
    public Object decoder(Class type, CodedInputStream input, ExtensionRegistryLite extensionRegistry) {
        return null;
    }

    @Override
    public void encoder(int fieldNumber, CodedOutputStream output, Object o) throws IOException {
        if (o instanceof String) {
            output.writeString(fieldNumber, (String) o);
        } else if (o instanceof ByteString) {
            output.writeBytes(fieldNumber, (ByteString) o);
        }
    }

    @Override
    public int getSerializedSize(int fieldNumber, Object o) {
        if (o == null) {
            return 0;
        }

        if (o instanceof String) {
            return com.google.protobuf.CodedOutputStream
                    .computeStringSize(fieldNumber, (String) o);
        } else if (o instanceof ByteString) {
            return com.google.protobuf.CodedOutputStream
                    .computeBytesSize(fieldNumber, ByteString.copyFrom((byte[]) o));
        }
        return -1;
    }

}
