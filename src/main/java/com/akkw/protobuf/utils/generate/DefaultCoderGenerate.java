package com.akkw.protobuf.utils.generate;

import com.akkw.protobuf.utils.coder.*;
import javassist.*;
import sun.tools.java.ClassFile;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;


public class DefaultCoderGenerate implements GenerateCoder {

    private static Map<Type, ProtobufCoder> types = new HashMap<>();

    public final static Set<Type> basicType = new HashSet<>();

    private final Class<?> sourceType;

    private Class<?> tragetType;

    private CtClass ctClass;

    ClassPool classPool = ClassPool.getDefault();

    static {
        basicType.add(Byte.class);
        basicType.add(byte.class);
        basicType.add(Short.class);
        basicType.add(short.class);
        basicType.add(Integer.class);
        basicType.add(int.class);
        basicType.add(Long.class);
        basicType.add(long.class);
        basicType.add(Float.class);
        basicType.add(float.class);
        basicType.add(Double.class);
        basicType.add(double.class);
        basicType.add(Boolean.class);
        basicType.add(boolean.class);
        basicType.add(String.class);
        basicType.add(char.class);
        basicType.add(byte[].class);
    }

    public DefaultCoderGenerate(Class<?> sourceType) throws Exception {
        this.sourceType = sourceType;
    }

    @Override
    public void generate() throws Exception {
        ctClass = classPool.makeClass(className(sourceType));
        addCoderInterface();
        addCoderFields();
        addConstructor();
        addSerializedSizeBody();
        addEncoderMethodBody();
        addDecoderMethodBody();
        ctClass.writeFile("/Users/qiangzhiwei/code/java/protobuf-utils/src/main/resources");
        tragetType = ctClass.toClass();
    }


    private void addConstructor() throws Exception {
        CtConstructor ctConstructor = CtNewConstructor.defaultConstructor(ctClass);
        ctConstructor.setBody(generateConstructorNameBody());
        ctConstructor.setModifiers(0x0001);
        ctConstructor.setExceptionTypes(new CtClass[]{classPool.get("java.lang.InstantiationException"), classPool.get("java.lang.IllegalAccessException")});
        ctClass.addConstructor(ctConstructor);
    }

    private String generateConstructorNameBody() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        Field[] fields = sourceType.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Class<?> type = fields[i].getType();
            if (basicType.contains(type)) {
                builder.append(paresBasicType(type, i));
            } else {
                builder.append(paresRecombinationType(type, i));
            }
        }
        builder.append("}");
        return builder.toString();
    }

    private void addDecoderMethodBody() throws CannotCompileException {
        String decoderCode = generateDecoderBody();
//        CtMethod decoder = CtMethod.make(decoderCode, ctClass);
//        ctClass.addMethod(decoder);
    }

    private void addCoderFields() throws Exception {
        generateProtobufCoderField();
    }

    private void addSerializedSizeBody() throws CannotCompileException {
        String serializedSizeCode = generateSerializedSizeMethodDefinition() + generateSerializedSizeMethodBody() + methodClose();
        CtMethod decoder = CtMethod.make(serializedSizeCode, ctClass);
        ctClass.addMethod(decoder);
    }

    private String generateSerializedSizeMethodDefinition() {
        return "public int getSerializedSize(int fieldNumber, Object o) { \n";
    }

    private String generateSerializedSizeMethodBody() {
        StringBuilder builder = new StringBuilder();
        Field[] fields = sourceType.getDeclaredFields();
        builder.append("int serializedSize = 0; \n");
        builder.append("java.lang.reflect.Field[] fields = o.getClass().getDeclaredFields();\n");
        builder.append("Object value;\n");
        builder.append("java.lang.reflect.Field field;\n");
        builder.append("for (int i = 0; i < fields.length; i++) { \n");
        builder.append("field = fields[i];\n");
        builder.append("field.setAccessible(true); \n");
        builder.append("value = field.get(o);\n");
        builder.append("serializedSize += coder[i].getSerializedSize(i + 1, value);\n");
        builder.append("}");
        builder.append("if (fieldNumber != 0) { \n");
        builder.append("serializedSize += com.google.protobuf.CodedOutputStream.computeTagSize(fieldNumber) + " +
                "com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(serializedSize);\n");
        builder.append("}\n");
        builder.append("return serializedSize; \n");
        return builder.toString();
    }


    private void addEncoderMethodBody() throws CannotCompileException {
        String encoderCode = generateEncoderBody();
        CtMethod encoder = CtMethod.make(encoderCode, ctClass);
        ctClass.addMethod(encoder);
    }

    private String generateDecoderBody() {
        return decoderMethodDefinition() + invokeDecoderMethod() + decoderReturnStatement() + methodClose();
    }

    private String decoderMethodDefinition() {
        return "public Object decoder(Class type, com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) { \n";
    }

    private String invokeDecoderMethod() {
        StringBuilder builder = new StringBuilder();
        Field[] declaredFields = sourceType.getDeclaredFields();
        for (Field field : declaredFields) {
            builder.append(String.format("%s.decoder(type, input, extensionRegistry);\n", field.getName() + "Coder"));
        }
        return builder.toString();
    }

    private String decoderReturnStatement() {
        return "return null;";
    }

    private String generateEncoderBody() throws CannotCompileException {
        return encoderMethodDefinition() + invokeEncoderMethod() + methodClose();
    }

    private String encoderMethodDefinition() {
        return "public void encoder(int fieldNumber, com.google.protobuf.CodedOutputStream output, Object o) { \n";
    }

    private String invokeEncoderMethod() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        builder.append("java.lang.reflect.Field[] fields = o.getClass().getDeclaredFields();\n");
        builder.append("Object value;\n");
        builder.append("java.lang.reflect.Field field;\n");
        builder.append("for (int i = 0; i < fields.length; i++) { \n");
        builder.append("field = fields[i]; \n");
        builder.append("field.setAccessible(true);");
        builder.append("value = fields[i].get(o);\n");
        builder.append("coder[i].encoder(i + 1, output, value, true);\n");
        builder.append("}");
        return builder.toString();
    }


    private void generateProtobufCoderField() throws Exception {
        addProtobufCoderTypeArray();
    }

    private void addProtobufCoderTypeArray() throws Exception {
        Field[] fields = sourceType.getDeclaredFields();
        CtField make = CtField.make(String.format("private com.akkw.protobuf.utils.coder.ProtobufCoder[] coder = new com.akkw.protobuf.utils.coder.ProtobufCoder[%d];", fields.length), ctClass);
        ctClass.addField(make);
    }

    private CtField addField(Field field) throws CannotCompileException {
        return CtField.make(String.format("private %s %s;", field.getClass().getTypeName(), (field.getName())), ctClass);
    }

    String paresFieldType(Field field, int fieldIndex) throws Exception {
        Class<?> type = field.getType();
        if (basicType.contains(type)) {
            return paresBasicType(type, fieldIndex);
        } else {
            return paresRecombinationType(type, fieldIndex);
        }
    }

    private String paresRecombinationType(Class<?> type, int fieldIndex) throws Exception {
        DefaultCoderGenerate generate = new DefaultCoderGenerate(type);
        generate.generate();
        Class<?> recombinationCtClass = generate.getTargetType();
        return String.format("coder[%d] = %s.class.newInstance();", fieldIndex, recombinationCtClass.getName());
    }

    private String paresBasicType(Class<?> type, int fieldIndex) throws Exception {
        if (type.isAssignableFrom(String.class) || type.isAssignableFrom(byte[].class)) {
            return String.format("coder[%d] = com.akkw.protobuf.utils.coder.StringCoder.class.newInstance();  \n", fieldIndex);
        } else if (type.isAssignableFrom(Long.class) || type.isAssignableFrom(long.class)) {
            return String.format("coder[%d] = com.akkw.protobuf.utils.coder.LongCoder.class.newInstance(); \n", fieldIndex);
        } else if (type.isAssignableFrom(Integer.class) || type.isAssignableFrom(int.class)) {
            return String.format("coder[%d] = com.akkw.protobuf.utils.coder.IntegerCoder.class.newInstance(); \n ", fieldIndex);
        } else if (type.isAssignableFrom(Boolean.class) || type.isAssignableFrom(boolean.class)) {
            return String.format("coder[%d] = com.akkw.protobuf.utils.coder.BooleanCoder.class.newInstance(); \n", fieldIndex);
        }
        return null;
    }


    private String methodClose() {
        return "}";
    }

    private void addCoderInterface() throws Exception {
        CtClass anInterface = classPool.makeInterface(ProtobufCoder.class.getName());
        anInterface.addMethod(CtMethod.make("int getSerializedSize(int fieldNumber, Object o); \n", anInterface));
        anInterface.addMethod(CtMethod.make("Object decoder(Class type, com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry);\n ", anInterface));
        anInterface.addMethod(CtMethod.make("void encoder(int fieldNumber, com.google.protobuf.CodedOutputStream output, Object t) throws java.io.IOException;\n ", anInterface));
        anInterface.addMethod(CtMethod.make("void encoder(int fieldNumber, com.google.protobuf.CodedOutputStream output, Object o, boolean writeTag) throws java.io.IOException; \n", anInterface));
        ctClass.addInterface(anInterface);
    }

    private String className(Class<?> type) {
        return type.getName() + "$JavaToProtobufCoder";
    }

    public CtClass getCtClass() {
        return ctClass;
    }

    public Class<?> getTargetType() {
        return tragetType;
    }

    @Override
    public String getCode() {
        return null;
    }
}
