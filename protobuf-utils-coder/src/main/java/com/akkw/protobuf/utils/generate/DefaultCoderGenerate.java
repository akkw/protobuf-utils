package com.akkw.protobuf.utils.generate;

import com.akkw.protobuf.utils.coder.*;
import javassist.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class DefaultCoderGenerate implements GenerateCoder {

    public final static Set<Type> basicType = new HashSet<>();

    public final static Set<Type> collectionType = new HashSet<>();

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
        collectionType.add(List.class);
        collectionType.add(ArrayList.class);
        collectionType.add(LinkedList.class);
        collectionType.add(Map.class);
        collectionType.add(HashMap.class);
        collectionType.add(LinkedHashMap.class);
    }

    public DefaultCoderGenerate(Class<?> sourceType) {
        this.sourceType = sourceType;
    }

    public Map<Type, ProtobufCoder> getCoderCache() {
        return coderCache;
    }

    @Override
    public void generate() throws Exception {
        ctClass = classPool.makeClass(className(sourceType));
        addCoderInterface();
        addCoderCache();
        paresFields();
        generateConstructor();
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


    private void generateConstructor() throws Exception {
        CtClass[] param = {classPool.get("java.util.Map")};
        CtClass[] exception = {classPool.get("java.lang.InstantiationException"), classPool.get("java.lang.IllegalAccessException")};
        CtConstructor ctConstructor = CtNewConstructor.make(param, exception, ctClass);
        ctConstructor.setBody(generateConstructorNameBody());
        ctConstructor.setModifiers(0x0001);
        ctClass.addConstructor(ctConstructor);
    }

    private String generateConstructorNameBody() throws Exception {
        return "{\n" +
                "this.coderCache = $1;\n" +
                "}";
    }

    private void addDecoderMethodBody() throws CannotCompileException {
    }

    private void addCoderCache() throws Exception {
        generateProtobufCoderField();
    }

    private void addSerializedSizeBody() throws Exception {
        String serializedSizeCode = generateSerializedSizeMethodDefinition() + generateSerializedSizeMethodBody() + methodClose();
        CtMethod serializedSizeCodeMe = CtMethod.make(serializedSizeCode, ctClass);
        ctClass.addMethod(serializedSizeCodeMe);
    }

    private String generateSerializedSizeMethodDefinition() {
        return "public int getSerializedSize(int fieldNumber, Object o, boolean writeTag, boolean isList) { \n";
    }

    private String generateSerializedSizeMethodBody() {
        StringBuilder builder = new StringBuilder();
        builder.append("int serializedSize = 0; \n");
        builder.append("java.lang.reflect.Field[] fields = o.getClass().getDeclaredFields();\n");
        builder.append("Object value;\n");
        builder.append("java.lang.reflect.Field field;\n");
        builder.append("for (int i = 0; i < fields.length; i++) { \n");
        builder.append("field = fields[i];\n");
        builder.append("field.setAccessible(true); \n");
        builder.append("value = field.get(o);\n");
        builder.append("value = field.get(o);\n");
        builder.append("if (value != null) {\n");
        builder.append("serializedSize += ((com.akkw.protobuf.utils.coder.ProtobufCoder)coderCache.get(field.getType())).getSerializedSize(i + 1, value, true, isList);\n");
//        builder.append("System.out.println(field.getName() +\": \"+ serializedSize);\n");
        builder.append("}\n");
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
        return "public void encoder(int fieldNumber, com.google.protobuf.CodedOutputStream output, Object o, boolean isList) { \n";
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
        builder.append("if(value!=null) {\n");
        builder.append("((com.akkw.protobuf.utils.coder.ProtobufCoder)coderCache.get(field.getType())).encoder(i + 1, output, value, true, isList);\n");
        builder.append("}");
        builder.append("}");
        return builder.toString();
    }


    private void generateProtobufCoderField() throws Exception {
        CtField cacheField = CtField.make("java.util.Map coderCache;", ctClass);
        ctClass.addField(cacheField);
    }

    void paresFieldType(Field field) throws Exception {
        Class<?> aClass = field.getType();
        if (basicType.contains(aClass)) {
            paresBasicType(aClass);
        } else if (collectionType.contains(aClass)) {
            paresCollectionType(field);
        } else {
            paresRecombinationType(aClass);
        }
    }

    private void paresCollectionType(Field field) throws Exception {
        Class<?> aClass = field.getType();
        if (aClass.isAssignableFrom(List.class) || aClass.isAssignableFrom(Map.class)) {
            Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
                for (Type type : actualTypeArguments) {
                    if (!coderCache.containsKey(type)) {
                        if (basicType.contains(type)) {
                            paresBasicType((Class<?>) type);
                        } else if (collectionType.contains(type)) {
                            paresCollectionType(field);
                        } else {
                            paresRecombinationType((Class<?>) type);
                        }
                    }
                }
            } else {
                throw new IllegalArgumentException(field.getName() + " not exist generic " + field.getGenericType() + "<vacancy>");
            }
            if (aClass.isAssignableFrom(List.class)) {
                coderCache.put(List.class, new ListCoder(coderCache));
            } else if (aClass.isAssignableFrom(Map.class)) {
                coderCache.put(Map.class, new MapCoder(coderCache));
            }
        }
    }

    private void paresRecombinationType(Class<?> aClass) throws Exception {
        if (!coderCache.containsKey(aClass)) {
            DefaultCoderGenerate generate = new DefaultCoderGenerate(aClass);
            generate.generate();
            Class<?> recombinationCtClass = generate.getTargetType();
            Constructor<?> constructor = recombinationCtClass.getDeclaredConstructors()[0];
            coderCache.put(aClass, (ProtobufCoder) constructor.newInstance(coderCache));
        }
    }

    private void paresBasicType(Class<?> aClass) throws Exception {
        if (aClass.isAssignableFrom(String.class) || aClass.isAssignableFrom(byte[].class)) {
            coderCache.put(String.class, new StringCoder());
            coderCache.put(byte[].class, new StringCoder());
        } else if (aClass.isAssignableFrom(Long.class) || aClass.isAssignableFrom(long.class)) {
            coderCache.put(Long.class, new LongCoder());
            coderCache.put(long.class, new LongCoder());
        } else if (aClass.isAssignableFrom(Integer.class) || aClass.isAssignableFrom(int.class)) {
            coderCache.put(Integer.class, new IntegerCoder());
            coderCache.put(int.class, new IntegerCoder());
        } else if (aClass.isAssignableFrom(Boolean.class) || aClass.isAssignableFrom(boolean.class)) {
            coderCache.put(Boolean.class, new BooleanCoder());
            coderCache.put(boolean.class, new BooleanCoder());
        }
    }


    private String methodClose() {
        return "}";
    }

    private void addCoderInterface() throws Exception {
        CtClass anInterface = classPool.makeInterface(ProtobufCoder.class.getName());
        anInterface.addMethod(CtMethod.make("int getSerializedSize(int fieldNumber, Object o, boolean writeTag, boolean isList); \n", anInterface));
        anInterface.addMethod(CtMethod.make("Object decoder(Class type, com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry);\n ", anInterface));
        anInterface.addMethod(CtMethod.make("void encoder(int fieldNumber, com.google.protobuf.CodedOutputStream output, Object o, boolean isList) throws java.io.IOException;\n ", anInterface));
        anInterface.addMethod(CtMethod.make("void encoder(int fieldNumber, com.google.protobuf.CodedOutputStream output, Object o, boolean writeTag, boolean isList) throws java.io.IOException; \n", anInterface));
        ctClass.addInterface(anInterface);
    }

    private String className(Class<?> type) {
        return type.getName() + "$JavaToProtobufCoder";
    }

    public Class<?> getTargetType() {
        return tragetType;
    }

}
