package com.akkw.protobuf.utils.coder;

import com.google.protobuf.CodedOutputStream;

public interface Coder {
    void encoder(CodedOutputStream outputStream, Object o);

    Object decoder(Class<?> type);
}
