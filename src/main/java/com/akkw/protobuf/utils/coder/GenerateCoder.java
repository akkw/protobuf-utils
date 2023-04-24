package com.akkw.protobuf.utils.coder;

import javassist.CannotCompileException;

import java.lang.reflect.Field;

public interface GenerateCoder {
    void generate() throws Exception;


    String getCode();


}
