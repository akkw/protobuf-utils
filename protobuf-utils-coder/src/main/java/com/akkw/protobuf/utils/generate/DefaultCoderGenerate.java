package com.akkw.protobuf.utils.generate;

import com.akkw.protobuf.utils.coder.*;
import javassist.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class DefaultCoderGenerate implements GenerateCoder {

    private static Map<Type, ProtobufCoder> types = new HashMap<>();

    public final static Set<Type> basicType = new HashSet<>();

    private final Class<?> sourceType;

    private Class<?> tragetType;

    private CtClass ctClass;

    private final ClassPool classPool = ClassPool.getDefault();

    private final Map<Type, ProtobufCoder> coderCache = new ConcurrentHashMap<>();


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

    public Map<Type, ProtobufCoder> getCoderCache() {
        return coderCache;
    }

    @Override
    public void generate() throws Exception {
        ctClass = classPool.makeClass(className(sourceType));
        addCoderInterface();
        addCoderFields();
        paresFields();
        addConstructor();
        addSerializedSizeBody();
        addEncoderMethodBody();
        addDecoderMethodBody();
        ctClass.writeFile("/Users/qiangzhiwei/code/java/protobuf-utils/protobuf-utils-coder/src/main/resources");
        tragetType = ctClass.toClass();
    }

    private void paresFields() throws Exception {
        Field[] fields = sourceType.getDeclaredFields();
        for (Field field : fields) {
            paresFieldType(field);
        }
    }


    private void addConstructor() throws Exception {
        CtClass[] param = {classPool.get("java.util.Map")};
        CtClass[] exception = {classPool.get("java.lang.InstantiationException"), classPool.get("java.lang.IllegalAccessException")};
        CtConstructor ctConstructor = CtNewConstructor.make(param, exception, ctClass);
        ctConstructor.setBody(generateConstructorNameBody());
        ctConstructor.setModifiers(0x0001);
        ctClass.addConstructor(ctConstructor);
    }

    private String generateConstructorNameBody() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append("this.coderCache = $1;\n");
        builder.append("}");
        return builder.toString();
    }

    private void addDecoderMethodBody() throws CannotCompileException {
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
        builder.append("int serializedSize = 0; \n");
//        builder.append("java.lang.reflect.Field[] fields = o.getClass().getDeclaredFields();\n");
//        builder.append("Object value;\n");
//        builder.append("java.lang.reflect.Field field;\n");
//        builder.append("for (int i = 0; i < fields.length; i++) { \n");
//        builder.append("field = fields[i];\n");
//        builder.append("field.setAccessible(true); \n");
//        builder.append("value = field.get(o);\n");
//        builder.append("serializedSize += coder[i].getSerializedSize(i + 1, value);\n");
//        builder.append("}");
//        builder.append("if (fieldNumber != 0) { \n");
//        builder.append("serializedSize += com.google.protobuf.CodedOutputStream.computeTagSize(fieldNumber) + " +
//                "com.google.protobuf.CodedOutputStream.computeUInt32SizeNoTag(serializedSize);\n");
//        builder.append("}\n");
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
//        builder.append("\n");
//        builder.append("java.lang.reflect.Field[] fields = o.getClass().getDeclaredFields();\n");
//        builder.append("Object value;\n");
//        builder.append("java.lang.reflect.Field field;\n");
//        builder.append("for (int i = 0; i < fields.length; i++) { \n");
//        builder.append("field = fields[i]; \n");
//        builder.append("field.setAccessible(true);");
//        builder.append("value = fields[i].get(o);\n");
//        builder.append("coder[i].encoder(i + 1, output, value, true);\n");
//        builder.append("}");
        return builder.toString();
    }


    private void generateProtobufCoderField() throws Exception {
        addProtobufCoderTypeArray();
    }
    private void addProtobufCoderTypeArray() throws Exception {
        CtField cacheField = CtField.make("java.util.Map coderCache;", ctClass);
        ctClass.addField(cacheField);
    }

    void paresFieldType(Field field) throws Exception {
        Class<?> type = field.getType();
        if (basicType.contains(type)) {
            paresBasicType(type);
        } else {
            paresRecombinationType(type);
        }
    }

    private void paresRecombinationType(Class<?> type) throws Exception {
        if (!coderCache.containsKey(type)) {
            DefaultCoderGenerate generate = new DefaultCoderGenerate(type);
            generate.generate();
            Class<?> recombinationCtClass = generate.getTargetType();
            Constructor<?> constructor = recombinationCtClass.getDeclaredConstructors()[0];
            coderCache.put(recombinationCtClass, (ProtobufCoder) constructor.newInstance(coderCache));
        }
    }

    private void paresBasicType(Class<?> type) throws Exception {
        if (type.isAssignableFrom(String.class) || type.isAssignableFrom(byte[].class)) {
            coderCache.put(String.class, new StringCoder());
            coderCache.put(byte[].class, new StringCoder());
        } else if (type.isAssignableFrom(Long.class) || type.isAssignableFrom(long.class)) {
            coderCache.put(Long.class, new LongCoder());
            coderCache.put(long.class, new LongCoder());
        } else if (type.isAssignableFrom(Integer.class) || type.isAssignableFrom(int.class)) {
            coderCache.put(Integer.class, new IntegerCoder());
            coderCache.put(int.class, new IntegerCoder());
        } else if (type.isAssignableFrom(Boolean.class) || type.isAssignableFrom(boolean.class)) {
            coderCache.put(Boolean.class, new BooleanCoder());
            coderCache.put(boolean.class, new BooleanCoder());
        } else if (type.isAssignableFrom(List.class)) {
            coderCache.put(List.class, new ListCoder(coderCache));
        }
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

    public Class<?> getTargetType() {
        return tragetType;
    }

}
