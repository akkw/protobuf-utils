package com.akkw.protobuf.utils.generate;

import com.akkw.protobuf.utils.coder.*;
import javassist.*;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class DefaultCoderGenerate implements GenerateCoder {

    private final Set<Type> collectionType;

    private final  Set<Type> basicType;

    private final Map<Type, ProtobufCoder> coderCache;

    private final Class<?> sourceType;

    private Class<?> tragetType;

    private CtClass ctClass;

    private final ClassPool classPool = ClassPool.getDefault();





    public DefaultCoderGenerate(Set<Type> collectionType, Set<Type> basicType, Map<Type, ProtobufCoder> coderCache,
                                Class<?> sourceType) {
        this.collectionType = collectionType;
        this.basicType = basicType;
        this.coderCache = coderCache;
        this.sourceType = sourceType;
    }

    @Override
    public Class<?> generate() throws Exception {
        ctClass = classPool.makeClass(className(sourceType));
        addCoderInterface();
        addCoderCache();
        paresFields();
        generateConstructor();
        addSerializedSizeBody();
        addEncoderMethodBody();
        addDecoderMethodBody();
        return toClass();
    }

    private Class<?> toClass() throws CannotCompileException, NotFoundException, IOException {
        synchronized (coderCache) {
            if (!coderCache.containsKey(sourceType)) {
                tragetType = ctClass.toClass();
                ctClass.writeFile();
            }
        }
        return tragetType;
    }

    private void addDecoderMethodBody() throws CannotCompileException {
        String decoderCode = generateDecoderBody();
        CtMethod encoder = CtMethod.make(decoderCode, ctClass);
        ctClass.addMethod(encoder);
    }

    private void paresFields() throws Exception {
        Field[] fields = sourceType.getDeclaredFields();
        for (Field field : fields) {
            paresFieldType(field);
        }
    }


    private void generateConstructor() throws Exception {
        CtClass[] param = {classPool.get("java.util.Map"), classPool.get("java.util.Set")};
        CtClass[] exception = {classPool.get("java.lang.InstantiationException"), classPool.get("java.lang.IllegalAccessException")};
        CtConstructor ctConstructor = CtNewConstructor.make(param, exception, ctClass);
        ctConstructor.setBody(generateConstructorNameBody());
        ctConstructor.setModifiers(0x0001);
        ctClass.addConstructor(ctConstructor);
    }

    private String generateConstructorNameBody() {
        return "{\n" +
                "this.coderCache = $1;\n" +
                "this.basicType = $2;\n" +
                "}";
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
        return "public Object decoder(Class aClass, com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) { \n";
    }

    private String invokeDecoderMethod() {
        StringBuilder builder = new StringBuilder();
        builder.append("Object object = aClass.newInstance();\n");
        builder.append("boolean done = false;\n");
        builder.append("while (true) {\n");
        builder.append("int tag = input.readTag();\n");
        builder.append("if (tag == 0) {\n");
        builder.append("break;\n");
        builder.append("}\n");
        builder.append("java.lang.reflect.Field[] declaredFields = aClass.getDeclaredFields();\n");
        builder.append("int fieldNumber = com.google.protobuf.WireFormat.getTagFieldNumber(tag) ;\n");
        builder.append("System.out.println(fieldNumber);\n");
        builder.append("java.lang.reflect.Field field= declaredFields[fieldNumber - 1];\n");
        builder.append("Class type = field.getType();\n");
        builder.append("Object result = null;\n");
        builder.append("if (type.isAssignableFrom(java.util.List.class) || type.isAssignableFrom(java.util.ArrayList.class) || type.isAssignableFrom(java.util.LinkedList.class)) {\n");
        builder.append("System.out.println(111111);");
        builder.append("java.lang.reflect.Type actualTypeArguments = ((java.lang.reflect.ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];\n");
        builder.append("field.setAccessible(true);\n");
        builder.append("if (field.get(object) == null) {\n");
        builder.append("field.set(object, new java.util.ArrayList() );\n");
        builder.append("}\n");
        builder.append("java.util.List list = (java.util.List)field.get(object);");
        builder.append("final int length = input.readRawVarint32();\n");
        builder.append("int oldLimit = input.pushLimit(length);\n");
        builder.append("result = ((com.akkw.protobuf.utils.coder.ProtobufCoder)coderCache.get(actualTypeArguments)).decoder((Class)actualTypeArguments, input, extensionRegistry);\n");
        builder.append("list.add(result);\n");
        builder.append("input.popLimit(oldLimit);\n");
        builder.append("} else if (type.isAssignableFrom(java.util.Map.class) || type.isAssignableFrom(java.util.HashMap.class) || type.isAssignableFrom(java.util.LinkedHashMap.class)) {\n");
        builder.append("System.out.println(22222);");
        builder.append("field.setAccessible(true);\n");
        builder.append("if (field.get(object) == null) {\n");
        builder.append("field.set(object, new java.util.LinkedHashMap() );\n");
        builder.append("}\n");
        builder.append("final int length = input.readRawVarint32();\n");
        builder.append("int oldLimit = input.pushLimit(length);\n");
        builder.append("result = ((com.akkw.protobuf.utils.coder.ProtobufCoder)coderCache.get(type)).decoder(fieldNumber, field, input, extensionRegistry);\n");
        builder.append("java.util.Map.Entry entry = (java.util.Map.Entry)result;\n");
        builder.append("java.util.Map map = (java.util.Map)field.get(object);\n");
        builder.append("map.put(entry.getKey(), entry.getValue());\n");
        builder.append("input.popLimit(oldLimit);\n");
        builder.append("} else {\n");
        builder.append("System.out.println(33333);");
        builder.append("boolean basic = basicType.contains(type);\n");
        builder.append("final int oldLimit = 0;");
        builder.append("System.out.println(!type.isEnum());");
        builder.append("if (!basic && !type.isEnum()) {");
        builder.append("final int length = input.readRawVarint32();\n");
        builder.append("oldLimit = input.pushLimit(length);\n");
        builder.append("}\n");
        builder.append("System.out.println(((com.akkw.protobuf.utils.coder.ProtobufCoder)coderCache.get(type)));");
        builder.append("result = ((com.akkw.protobuf.utils.coder.ProtobufCoder)coderCache.get(type)).decoder(type, input, extensionRegistry);\n");
        builder.append("field.setAccessible(true);\n");
        builder.append("field.set(object, result);\n");
        builder.append("if (!basic && !type.isEnum()) {\n");
        builder.append("input.popLimit(oldLimit);\n");
        builder.append("}\n");
        builder.append("}\n");
        builder.append("}\n");
        return builder.toString();
    }


    private String decoderReturnStatement() {
        return "return object;";
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
        CtField basicTypeField = CtField.make("java.util.Set basicType;", ctClass);
        ctClass.addField(cacheField);
        ctClass.addField(basicTypeField);
    }

    void paresFieldType(Field field) throws Exception {
        Class<?> aClass = field.getType();
        if (basicType.contains(aClass)) {
            paresBasicType(aClass);
        } else if (collectionType.contains(aClass)) {
            paresCollectionType(field);
        } else if (aClass.isEnum()) {
            paresEnumType(aClass);
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
            DefaultCoderGenerate generate = new DefaultCoderGenerate(collectionType, basicType, coderCache, aClass);
            generate.generate();
            Class<?> recombinationCtClass = generate.getTargetType();
            Constructor<?> constructor = recombinationCtClass.getDeclaredConstructors()[0];
            coderCache.put(aClass, (ProtobufCoder) constructor.newInstance(coderCache, basicType));
        }
    }

    private void paresBasicType(Class<?> aClass) {
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
        } else if (aClass.isAssignableFrom(Double.class) || aClass.isAssignableFrom(double.class)) {
            coderCache.put(Double.class, new DoubleCoder());
            coderCache.put(double.class, new DoubleCoder());
        } else if (aClass.isAssignableFrom(Float.class) || aClass.isAssignableFrom(float.class)) {
            coderCache.put(Float.class, new FloatCoder());
            coderCache.put(float.class, new FloatCoder());
        } else if (aClass.isAssignableFrom(Byte.class) || aClass.isAssignableFrom(byte.class)) {
            coderCache.put(Byte.class, new IntegerCoder());
            coderCache.put(byte.class, new IntegerCoder());
        } else if (aClass.isAssignableFrom(Short.class) || aClass.isAssignableFrom(short.class)) {
            coderCache.put(Short.class, new IntegerCoder());
            coderCache.put(short.class, new IntegerCoder());
        }
    }

    public void paresEnumType(Class<?> aClass) {
        coderCache.put(aClass, new EnumCoder());
    }

    private String methodClose() {
        return "}";
    }

    private void addCoderInterface() throws Exception {
        CtClass anInterface = classPool.makeInterface(ProtobufCoder.class.getName());
        anInterface.addMethod(CtMethod.make("int getSerializedSize(int fieldNumber, Object o, boolean writeTag, boolean isList); \n", anInterface));
        anInterface.addMethod(CtMethod.make("Object decoder(Class type, com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException;\n ", anInterface));
        anInterface.addMethod(CtMethod.make("Object decoder(int fieldNumber, java.lang.reflect.Field field, com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException;\n ", anInterface));
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
