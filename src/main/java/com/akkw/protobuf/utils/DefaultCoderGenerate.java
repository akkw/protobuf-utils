package com.akkw.protobuf.utils;

import com.akkw.protobuf.utils.coder.*;
import javassist.*;
import javassist.bytecode.MethodInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;


public class DefaultCoderGenerate implements GenerateCoder {

    private static Map<Type, ProtobufCoder> types = new HashMap<>();

    private final static Set<Type> basicType = new HashSet<>();

    private final Class<?> sourceType;

    private Class<?> tragetType;

    private CtClass ctClass;

    ClassPool classPool = ClassPool.getDefault();

    static {
        basicType.add(Byte.TYPE);
        basicType.add(byte.class);
        basicType.add(Short.TYPE);
        basicType.add(short.class);
        basicType.add(Integer.TYPE);
        basicType.add(int.class);
        basicType.add(Long.TYPE);
        basicType.add(long.class);
        basicType.add(Float.TYPE);
        basicType.add(float.class);
        basicType.add(Double.TYPE);
        basicType.add(double.class);
        basicType.add(Boolean.TYPE);
        basicType.add(boolean.class);
        basicType.add(String.class);
        basicType.add(char.class);
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
        addEncoderMethodBody();
        addDecoderMethodBody();
        ctClass.writeFile("/Users/qiangzhiwei/code/java/protobuf-utils/src/main/resources");
        tragetType = ctClass.toClass();
    }

    private void addConstructor() throws Exception {
        CtConstructor ctConstructor = CtNewConstructor.defaultConstructor(ctClass);
        ctConstructor.setBody(generateConstructorNameBody());
        ctConstructor.setModifiers(0x0001);
        ctConstructor.setExceptionTypes(new CtClass[] {classPool.get("java.lang.InstantiationException"), classPool.get("java.lang.IllegalAccessException")});
        ctClass.addConstructor(ctConstructor);

        ctConstructor.isConstructor();
    }

    private String generateConstructorNameBody() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        CtField[] ctFields = ctClass.getDeclaredFields();
        for (CtField field : ctFields) {
            CtClass[] interfaces = field.getType().getInterfaces();
            for (CtClass inf : interfaces) {
                if (ProtobufCoder.class.getName().equals(inf.getName())) {
                    builder.append(String.format("%s = (%s)%s.class.newInstance();\n", field.getName(), field.getType().getName(), field.getType().getName()));
                }
            }
        }
        builder.append("}");
        return builder.toString();
    }

    private void addDecoderMethodBody() throws CannotCompileException {
        String decoderCode = generateDecoderBody();
        CtMethod decoder = CtMethod.make(decoderCode, ctClass);
        ctClass.addMethod(decoder);
    }

    private void addCoderFields() throws Exception {
        decoderProtobufCoderField();
    }

    private void addEncoderMethodBody() throws CannotCompileException {
        String encoderCode = generateEncoderBody();
        CtMethod encoder = CtMethod.make(encoderCode, ctClass);
        ctClass.addMethod(encoder);
    }

    private String generateDecoderBody() {
        return decoderMethodDefinition() +
                invokeDecoderMethod() +
                decoderReturnStatement() +
                methodClose();
    }

    private String decoderReturnStatement() {
        return "return null;";
    }

    private String generateEncoderBody() throws CannotCompileException {
        return encoderMethodDefinition() +
                invokeEncoderMethod() +
                methodClose();
    }

    private String invokeDecoderMethod() {
        StringBuilder builder = new StringBuilder();
        Field[] declaredFields = sourceType.getDeclaredFields();
        for (Field field : declaredFields) {
            builder.append(String.format("%s.decoder(type, input, extensionRegistry);\n", field.getName() + "Coder"));
        }
        return builder.toString();
    }


    private String invokeEncoderMethod() {
        StringBuilder builder = new StringBuilder();
        builder.append("java.lang.reflect.Field[] fields = o.getClass().getDeclaredFields();\n");
        builder.append("Object value;\n");
        builder.append("java.lang.reflect.Field field;\n");
        Field[] fields = sourceType.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            builder.append(String.format("field = fields[%d];\n", i));
            builder.append("field.setAccessible(true);");
            builder.append(String.format("value = fields[%d].get(o);\n", i));
            builder.append(String.format("%s.encoder(%d, output, value);\n", fields[i].getName() + "Coder", i + 1));
        }
        return builder.toString();
    }

    private void decoderProtobufCoderField() throws Exception {
        Field[] fields = sourceType.getDeclaredFields();
        for (Field field : fields) {
            ctClass.addField(paresFieldType(field));
//            ctClass.addField(addField(field));
        }
    }

    private CtField addField(Field field) throws CannotCompileException {
        return CtField.make(String.format("private %s %s;", field.getClass().getTypeName(), (field.getName())), ctClass);
    }

    CtField paresFieldType(Field field) throws Exception {
        Class<?> type = field.getType();
        if (basicType.contains(type)) {
            return paresBasicType(field);
        } else {
            return paresRecombinationType(field);
        }
    }

    private CtField paresRecombinationType(Field field) throws Exception {
        DefaultCoderGenerate generate = new DefaultCoderGenerate(field.getType());
        generate.generate();
        Class<?> recombinationCtClass = generate.getTargetType();
        return CtField.make(String.format("private %s %s;", recombinationCtClass.getTypeName(), field.getName()  + "Coder"), ctClass);
    }

    private CtField paresBasicType(Field field) throws CannotCompileException {
        Class<?> type = field.getType();
        if (type.isAssignableFrom(String.class) || type.isAssignableFrom(byte[].class)) {
            return CtField.make(String.format("private %s %s;", StringCoder.class.getTypeName(), field.getName()  + "Coder"), ctClass);
        } else if (type.isAssignableFrom(Long.class) || type.isAssignableFrom(long.class)) {
            return CtField.make(String.format("private %s %s;", LongCoder.class.getTypeName(), field.getName()  + "Coder"), ctClass);
        } else if (type.isAssignableFrom(Integer.class) || type.isAssignableFrom(int.class)) {
            return CtField.make(String.format("private %s %s;", IntegerCoder.class.getTypeName(), field.getName()  + "Coder"), ctClass);
        } else if (type.isAssignableFrom(Integer.class) || type.isAssignableFrom(int.class)) {
            return CtField.make(String.format("private %s %s;", BooleanCoder.class.getTypeName(), field.getName()  + "Coder"), ctClass);
        }
        return null;
    }

    private String encoderMethodDefinition() {
        return "public void encoder(int fieldNumber, com.google.protobuf.CodedOutputStream output, Object o) { \n";
    }

    private String decoderMethodDefinition() {
        return "public Object decoder(Class type, com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) { \n";
    }

    private String methodClose() {
        return "}";
    }

    private void addCoderInterface() {
        CtClass anInterface = classPool.makeInterface(ProtobufCoder .class.getName());
        ctClass.addInterface(anInterface);
    }

    private String className(Class<?> type) {
        return type.getName() + "$JavaToProtobufCoder";
    }

    public Class<?> getTargetType() {
        return tragetType;
    }

}
