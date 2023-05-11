package com.akkw.protobuf.utils.generate;

import javassist.CannotCompileException;

import java.lang.reflect.Field;

public interface GenerateCoder {
    Class<?> generate() throws Exception;
}
