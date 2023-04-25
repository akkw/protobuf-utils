package com.akkw.protobuf.utils.generate;

import com.akkw.protobuf.utils.coder.*;
import javassist.*;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;


public class DefaultCoderGenerate implements GenerateCoder {

    private static Map<Type, ProtobufCoder> types = new HashMap<>();

    private final static Set<Type> basicType = new HashSet<>();

    private final Class<?> sourceType;

    private Class<?> tragetType;

    private CtClass ctClass;


    private boolean writeTag;

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
        basicType.add(byte[].class);
    }

    public DefaultCoderGenerate(Class<?> sourceType, boolean writeTag) throws Exception {
        this.sourceType = sourceType;
        this.writeTag = writeTag;
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

    private void addSerializedSizeBody() throws CannotCompileException {
        String serializedSizeCode = generateSerializedSizeMethodDefinition() +
                generateSerializedSizeMethodBody() +
                methodClose();
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
        for (int i = 0; i < fields.length; i++) {
            builder.append(String.format("field = fields[%d];\n", i));
            builder.append("field.setAccessible(true);");
            builder.append(String.format("value = fields[%d].get(o);\n", i));
            builder.append(String.format("serializedSize += %s.getSerializedSize(%d, value);\n", fields[i].getName() + "Coder", i + 1));
//            builder.append("System.out.println(\"filed: \" +serializedSize);");
        }
        builder.append("if (fieldNumber != 0) { \n");
//        builder.append("System.out.println(\"fieldNumber: \" +fieldNumber);");
        builder.append("serializedSize += com.google.protobuf.CodedOutputStream.computeTagSize(fieldNumber) + " +
                "com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(serializedSize);\n");
        builder.append("}");
//        builder.append("System.out.println(serializedSize);");
        builder.append("return serializedSize; \n");
        return builder.toString();
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

    private String encoderMethodDefinition() {
        return "public void encoder(int fieldNumber, com.google.protobuf.CodedOutputStream output, Object o) { \n";
    }

    private String invokeEncoderMethod() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        if (writeTag) {
            builder.append("output.writeTag(fieldNumber, com.google.protobuf.WireFormat.WIRETYPE_LENGTH_DELIMITED);");
            builder.append("int size = getSerializedSize(0, o);");
            builder.append("System.out.println(\"getSerializedSize: \" + size);");

            builder.append("output.writeUInt32NoTag(size);");

        }
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

    CtField paresFieldType(Field field) throws Exception {
        Class<?> type = field.getType();
        if (basicType.contains(type)) {
            return paresBasicType(field);
        } else {
            return paresRecombinationType(field);
        }
    }

    private CtField paresRecombinationType(Field field) throws Exception {
        DefaultCoderGenerate generate = new DefaultCoderGenerate(field.getType(), true);
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
        } else if (type.isAssignableFrom(Boolean.class) || type.isAssignableFrom(boolean.class)) {
            return CtField.make(String.format("private %s %s;", BooleanCoder.class.getTypeName(), field.getName()  + "Coder"), ctClass);
        }
        return null;
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
