package mutants.fastjson_1_2_32.com.alibaba.fastjson.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONPOJOBuilder;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;

public class JavaBeanInfo {

    public final Class<?>       clazz;
    public final Class<?>       builderClass;
    public final Constructor<?> defaultConstructor;
    public final Constructor<?> creatorConstructor;
    public final Method         factoryMethod;
    public final Method         buildMethod;

    public final int            defaultConstructorParameterSize;

    public final com.alibaba.fastjson.util.FieldInfo[]    fields;
    public final com.alibaba.fastjson.util.FieldInfo[]    sortedFields;

    public final int            parserFeatures;

    public final JSONType       jsonType;
    
    public final String         typeName;

    public JavaBeanInfo(Class<?> clazz, //
                        Class<?> builderClass, //
                        Constructor<?> defaultConstructor, //
                        Constructor<?> creatorConstructor, //
                        Method factoryMethod, //
                        Method buildMethod, //
                        JSONType jsonType, //
                        List<com.alibaba.fastjson.util.FieldInfo> fieldList){
        this.clazz = clazz;
        this.builderClass = builderClass;
        this.defaultConstructor = defaultConstructor;
        this.creatorConstructor = creatorConstructor;
        this.factoryMethod = factoryMethod;
        this.parserFeatures = com.alibaba.fastjson.util.TypeUtils.getParserFeatures(clazz);
        this.buildMethod = buildMethod;

        this.jsonType = jsonType;
        if (jsonType != null) {
            String typeName = jsonType.typeName();
            if (typeName.length() != 0) {
                this.typeName = typeName;
            } else {
                this.typeName = clazz.getName();
            }
        } else {
            this.typeName = clazz.getName();
        }

        fields = new com.alibaba.fastjson.util.FieldInfo[fieldList.size()];
        fieldList.toArray(fields);

        com.alibaba.fastjson.util.FieldInfo[] sortedFields = new com.alibaba.fastjson.util.FieldInfo[fields.length];
        System.arraycopy(fields, 0, sortedFields, 0, fields.length);
        Arrays.sort(sortedFields);

        if (Arrays.equals(fields, sortedFields)) {
            sortedFields = fields;
        }
        this.sortedFields = sortedFields;

        if (defaultConstructor != null) {
            defaultConstructorParameterSize = defaultConstructor.getParameterTypes().length;
        } else if (factoryMethod != null) {
            defaultConstructorParameterSize = factoryMethod.getParameterTypes().length;
        } else {
            defaultConstructorParameterSize = 0;
        }
    }

    private static com.alibaba.fastjson.util.FieldInfo getField(List<com.alibaba.fastjson.util.FieldInfo> fieldList, String propertyName) {
        for (com.alibaba.fastjson.util.FieldInfo item : fieldList) {
            if (item.name.equals(propertyName)) {
                return item;
            }

            Field field = item.field;
            if (field != null && item.getAnnotation() != null && field.getName().equals(propertyName)) {
                return item;
            }
        }
        return null;
    }

    static boolean add(List<com.alibaba.fastjson.util.FieldInfo> fieldList, com.alibaba.fastjson.util.FieldInfo field) {
        for (int i = fieldList.size() - 1; i >= 0; --i) {
            com.alibaba.fastjson.util.FieldInfo item = fieldList.get(i);

            if (item.name.equals(field.name)) {
                if (item.getOnly && !field.getOnly) {
                    continue;
                }

                if (item.fieldClass.isAssignableFrom(field.fieldClass)) {
                    fieldList.remove(i);
                    break;
                }

                int result = item.compareTo(field);

                if (result < 0) {
                    fieldList.remove(i);
                    break;
                } else {
                    return false;
                }
            }
        }
        fieldList.add(field);

        return true;
    }

    public static JavaBeanInfo build(Class<?> clazz, Type type, PropertyNamingStrategy propertyNamingStrategy) {
        return build(clazz, type, propertyNamingStrategy, false, com.alibaba.fastjson.util.TypeUtils.compatibleWithJavaBean);
    }

    public static JavaBeanInfo build(Class<?> clazz //
            , Type type //
            , PropertyNamingStrategy propertyNamingStrategy //
            , boolean fieldBased //
            , boolean compatibleWithJavaBean
    ) {
        JSONType jsonType = clazz.getAnnotation(JSONType.class);

        Class<?> builderClass = getBuilderClass(jsonType);

        Field[] declaredFields = clazz.getDeclaredFields();
        Method[] methods = clazz.getMethods();

        Constructor<?> defaultConstructor = getDefaultConstructor(builderClass == null ? clazz : builderClass);
        Constructor<?> creatorConstructor = null;
        Method buildMethod = null;
        Method factoryMethod = null;

        List<com.alibaba.fastjson.util.FieldInfo> fieldList = new ArrayList<com.alibaba.fastjson.util.FieldInfo>();

        if (fieldBased) {
            for (Class<?> currentClass = clazz; currentClass != null; currentClass = currentClass.getSuperclass()) {
                Field[] fields = currentClass.getDeclaredFields();

                computeFields(clazz, type, propertyNamingStrategy, fieldList, fields);
            }
            return new JavaBeanInfo(clazz, builderClass, defaultConstructor, null, factoryMethod, buildMethod, jsonType, fieldList);
        }

        boolean isInterfaceOrAbstract = clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers());
        if (defaultConstructor == null || isInterfaceOrAbstract) {
            creatorConstructor = getCreatorConstructor(clazz);
            if (creatorConstructor != null && !isInterfaceOrAbstract) { // 基于标记 JSONCreator 注解的构造方法
                com.alibaba.fastjson.util.TypeUtils.setAccessible(creatorConstructor);

                Class<?>[] types = creatorConstructor.getParameterTypes();
                if (types.length > 0) {
                    Annotation[][] paramAnnotationArrays = creatorConstructor.getParameterAnnotations();
                    for (int i = 0; i < types.length; ++i) {
                        Annotation[] paramAnnotations = paramAnnotationArrays[i];
                        JSONField fieldAnnotation = null;
                        for (Annotation paramAnnotation : paramAnnotations) {
                            if (paramAnnotation instanceof JSONField) {
                                fieldAnnotation = (JSONField) paramAnnotation;
                                break;
                            }
                        }
                        if (fieldAnnotation == null) {
                            throw new JSONException("illegal json creator");
                        }
                        Class<?> fieldClass = types[i];
                        Type fieldType = creatorConstructor.getGenericParameterTypes()[i];
                        Field field = com.alibaba.fastjson.util.TypeUtils.getField(clazz, fieldAnnotation.name(), declaredFields);
                        final int ordinal = fieldAnnotation.ordinal();
                        final int serialzeFeatures = SerializerFeature.of(fieldAnnotation.serialzeFeatures());
                        final int parserFeatures = Feature.of(fieldAnnotation.parseFeatures());
                        com.alibaba.fastjson.util.FieldInfo fieldInfo = new com.alibaba.fastjson.util.FieldInfo(fieldAnnotation.name(), clazz, fieldClass, fieldType, field,
                                                            ordinal, serialzeFeatures, parserFeatures);
                        add(fieldList, fieldInfo);
                    }
                }

                return new JavaBeanInfo(clazz, builderClass, null, creatorConstructor, null, null, jsonType, fieldList);
            }

            factoryMethod = getFactoryMethod(clazz, methods); // 基于标记 JSONCreator 注解的工厂方法
            if (factoryMethod != null) {
                com.alibaba.fastjson.util.TypeUtils.setAccessible(factoryMethod);

                Class<?>[] types = factoryMethod.getParameterTypes();
                if (types.length > 0) {
                    Annotation[][] paramAnnotationArrays = factoryMethod.getParameterAnnotations();
                    for (int i = 0; i < types.length; ++i) {
                        Annotation[] paramAnnotations = paramAnnotationArrays[i];
                        JSONField fieldAnnotation = null;
                        for (Annotation paramAnnotation : paramAnnotations) {
                            if (paramAnnotation instanceof JSONField) {
                                fieldAnnotation = (JSONField) paramAnnotation;
                                break;
                            }
                        }
                        if (fieldAnnotation == null) {
                            throw new JSONException("illegal json creator");
                        }

                        Class<?> fieldClass = types[i];
                        Type fieldType = factoryMethod.getGenericParameterTypes()[i];
                        Field field = com.alibaba.fastjson.util.TypeUtils.getField(clazz, fieldAnnotation.name(), declaredFields);
                        final int ordinal = fieldAnnotation.ordinal();
                        final int serialzeFeatures = SerializerFeature.of(fieldAnnotation.serialzeFeatures());
                        final int parserFeatures = Feature.of(fieldAnnotation.parseFeatures());
                        com.alibaba.fastjson.util.FieldInfo fieldInfo = new com.alibaba.fastjson.util.FieldInfo(fieldAnnotation.name(), clazz, fieldClass, fieldType, field,
                                                            ordinal, serialzeFeatures, parserFeatures);
                        add(fieldList, fieldInfo);
                    }

                    return new JavaBeanInfo(clazz, builderClass, null, null, factoryMethod, null, jsonType, fieldList);
                }
            } else if (!isInterfaceOrAbstract){
                throw new JSONException("default constructor not found. " + clazz);
            }
        }

        if (defaultConstructor != null) {
            com.alibaba.fastjson.util.TypeUtils.setAccessible(defaultConstructor);
        }

        if (builderClass != null) {
            String withPrefix = null;

            JSONPOJOBuilder builderAnno = builderClass.getAnnotation(JSONPOJOBuilder.class);
            if (builderAnno != null) {
                withPrefix = builderAnno.withPrefix();
            }

            if (withPrefix == null || withPrefix.length() == 0) {
                withPrefix = "with";
            }

            for (Method method : builderClass.getMethods()) {
                if (Modifier.isStatic(method.getModifiers())) {
                    continue;
                }

                if (!(method.getReturnType().equals(builderClass))) {
                    continue;
                }

                int ordinal = 0, serialzeFeatures = 0, parserFeatures = 0;

                JSONField annotation = method.getAnnotation(JSONField.class);

                if (annotation == null) {
                    annotation = com.alibaba.fastjson.util.TypeUtils.getSuperMethodAnnotation(clazz, method);
                }

                if (annotation != null) {
                    if (!annotation.deserialize()) {
                        continue;
                    }

                    ordinal = annotation.ordinal();
                    serialzeFeatures = SerializerFeature.of(annotation.serialzeFeatures());
                    parserFeatures = Feature.of(annotation.parseFeatures());

                    if (annotation.name().length() != 0) {
                        String propertyName = annotation.name();
                        add(fieldList, new com.alibaba.fastjson.util.FieldInfo(propertyName, method, null, clazz, type, ordinal, serialzeFeatures, parserFeatures,
                                                     annotation, null, null));
                        continue;
                    }
                }

                String methodName = method.getName();
                if (!methodName.startsWith(withPrefix)) {
                    continue;
                }

                if (methodName.length() <= withPrefix.length()) {
                    continue;
                }

                char c0 = methodName.charAt(withPrefix.length());

                if (!Character.isUpperCase(c0)) {
                    continue;
                }

                StringBuilder properNameBuilder = new StringBuilder(methodName.substring(withPrefix.length()));
                properNameBuilder.setCharAt(0, Character.toLowerCase(c0));

                String propertyName = properNameBuilder.toString();
                
                add(fieldList, new com.alibaba.fastjson.util.FieldInfo(propertyName, method, null, clazz, type, ordinal, serialzeFeatures, parserFeatures,
                                             annotation, null, null));
            }

            if (builderClass != null) {
                JSONPOJOBuilder builderAnnotation = builderClass.getAnnotation(JSONPOJOBuilder.class);

                String buildMethodName = null;
                if (builderAnnotation != null) {
                    buildMethodName = builderAnnotation.buildMethod();
                }

                if (buildMethodName == null || buildMethodName.length() == 0) {
                    buildMethodName = "build";
                }

                try {
                    buildMethod = builderClass.getMethod(buildMethodName);
                } catch (NoSuchMethodException e) {
                    // skip
                } catch (SecurityException e) {
                    // skip
                }

                if (buildMethod == null) {
                    try {
                        buildMethod = builderClass.getMethod("create");
                    } catch (NoSuchMethodException e) {
                        // skip
                    } catch (SecurityException e) {
                        // skip
                    }
                }

                if (buildMethod == null) {
                    throw new JSONException("buildMethod not found.");
                }

                com.alibaba.fastjson.util.TypeUtils.setAccessible(buildMethod);
            }
        }

        for (Method method : methods) { //
            int ordinal = 0, serialzeFeatures = 0, parserFeatures = 0;
            String methodName = method.getName();

            if (Modifier.isStatic(method.getModifiers())) {
                continue;
            }

            // support builder set
            Class<?> returnType = method.getReturnType();
            if (!(returnType.equals(Void.TYPE) || returnType.equals(method.getDeclaringClass()))) {
                continue;
            }

            if (method.getDeclaringClass() == Object.class) {
                continue;
            }

            Class<?>[] types = method.getParameterTypes();

            if (types.length == 0 || types.length > 2) {
                continue;
            }

            JSONField annotation = method.getAnnotation(JSONField.class);
            if (annotation != null
                    && types.length == 2
                    && types[0] == String.class
                    && types[1] == Object.class) {
                add(fieldList, new com.alibaba.fastjson.util.FieldInfo("", method, null, clazz, type, ordinal,
                        serialzeFeatures, parserFeatures, annotation, null, null));
                 continue;
            }

            if (types.length != 1) {
                continue;
            }

            if (annotation == null) {
                annotation = com.alibaba.fastjson.util.TypeUtils.getSuperMethodAnnotation(clazz, method);
            }

            if (annotation == null && methodName.length() < 4) {
                continue;
            }

            if (annotation != null) {
                if (!annotation.deserialize()) {
                    continue;
                }

                ordinal = annotation.ordinal();
                serialzeFeatures = SerializerFeature.of(annotation.serialzeFeatures());
                parserFeatures = Feature.of(annotation.parseFeatures());

                if (annotation.name().length() != 0) {
                    String propertyName = annotation.name();
                    add(fieldList, new com.alibaba.fastjson.util.FieldInfo(propertyName, method, null, clazz, type, ordinal, serialzeFeatures, parserFeatures,
                                                 annotation, null, null));
                    continue;
                }
            }

            if (annotation == null && !methodName.startsWith("set")) { // TODO "set"的判断放在 JSONField 注解后面，意思是允许非 setter 方法标记 JSONField 注解？
                continue;
            }

            char c3 = methodName.charAt(3);

            String propertyName;
            if (Character.isUpperCase(c3) //
                || c3 > 512 // for unicode method name
            ) {
                if (com.alibaba.fastjson.util.TypeUtils.compatibleWithJavaBean) {
                    propertyName = com.alibaba.fastjson.util.TypeUtils.decapitalize(methodName.substring(3));
                } else {
                    propertyName = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
                }
            } else if (c3 == '_') {
                propertyName = methodName.substring(4);
            } else if (c3 == 'f') {
                propertyName = methodName.substring(3);
            } else if (methodName.length() >= 5 && Character.isUpperCase(methodName.charAt(4))) {
                propertyName = com.alibaba.fastjson.util.TypeUtils.decapitalize(methodName.substring(3));
            } else {
                continue;
            }

            Field field = com.alibaba.fastjson.util.TypeUtils.getField(clazz, propertyName, declaredFields);
            if (field == null && types[0] == boolean.class) {
                String isFieldName = "is" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
                field = com.alibaba.fastjson.util.TypeUtils.getField(clazz, isFieldName, declaredFields);
            }

            JSONField fieldAnnotation = null;
            if (field != null) {
                fieldAnnotation = field.getAnnotation(JSONField.class);

                if (fieldAnnotation != null) {
                    if (!fieldAnnotation.deserialize()) {
                        continue;
                    }
                    
                    ordinal = fieldAnnotation.ordinal();
                    serialzeFeatures = SerializerFeature.of(fieldAnnotation.serialzeFeatures());
                    parserFeatures = Feature.of(fieldAnnotation.parseFeatures());

                    if (fieldAnnotation.name().length() != 0) {
                        propertyName = fieldAnnotation.name();
                        add(fieldList, new com.alibaba.fastjson.util.FieldInfo(propertyName, method, field, clazz, type, ordinal,
                                                     serialzeFeatures, parserFeatures, annotation, fieldAnnotation, null));
                        continue;
                    }
                }

            }
            
            if (propertyNamingStrategy != null) {
                propertyName = propertyNamingStrategy.translate(propertyName);
            }

            add(fieldList, new com.alibaba.fastjson.util.FieldInfo(propertyName, method, field, clazz, type, ordinal, serialzeFeatures, parserFeatures,
                                         annotation, fieldAnnotation, null));
        }

        Field[] fields = clazz.getFields();
        computeFields(clazz, type, propertyNamingStrategy, fieldList, fields);

        for (Method method : clazz.getMethods()) { // getter methods
            String methodName = method.getName();
            if (methodName.length() < 4) {
                continue;
            }

            if (Modifier.isStatic(method.getModifiers())) {
                continue;
            }

            if (methodName.startsWith("get") && Character.isUpperCase(methodName.charAt(3))) {
                if (method.getParameterTypes().length != 0) {
                    continue;
                }

                if (Collection.class.isAssignableFrom(method.getReturnType()) //
                    || Map.class.isAssignableFrom(method.getReturnType()) //
                    || AtomicBoolean.class == method.getReturnType() //
                    || AtomicInteger.class == method.getReturnType() //
                    || AtomicLong.class == method.getReturnType() //
                ) {
                    String propertyName;

                    JSONField annotation = method.getAnnotation(JSONField.class);
                    if (annotation != null && annotation.deserialize()) {
                        continue;
                    }
                    
                    if (annotation != null && annotation.name().length() > 0) {
                        propertyName = annotation.name();
                    } else {
                        propertyName = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);

                        Field field = TypeUtils.getField(clazz, propertyName, declaredFields);
                        if (field != null) {
                            JSONField fieldAnnotation = field.getAnnotation(JSONField.class);
                            if (fieldAnnotation != null && !fieldAnnotation.deserialize()) {
                                continue;
                            }
                        }
                    }
                    
                    com.alibaba.fastjson.util.FieldInfo fieldInfo = getField(fieldList, propertyName);
                    if (fieldInfo != null) {
                        continue;
                    }

                    if (propertyNamingStrategy != null) {
                        propertyName = propertyNamingStrategy.translate(propertyName);
                    }
                    
                    add(fieldList, new com.alibaba.fastjson.util.FieldInfo(propertyName, method, null, clazz, type, 0, 0, 0, annotation, null, null));
                }
            }
        }

        return new JavaBeanInfo(clazz, builderClass, defaultConstructor, null, factoryMethod, buildMethod, jsonType, fieldList);
    }

    private static void computeFields(Class<?> clazz, Type type, PropertyNamingStrategy propertyNamingStrategy, List<com.alibaba.fastjson.util.FieldInfo> fieldList, Field[] fields) {
        for (Field field : fields) { // public static fields
            int modifiers = field.getModifiers();
            if ((modifiers & Modifier.STATIC) != 0) {
                continue;
            }

            if((modifiers & Modifier.FINAL) != 0) {
                Class<?> fieldType = field.getType();
                boolean supportReadOnly = Map.class.isAssignableFrom(fieldType)
                        || Collection.class.isAssignableFrom(fieldType)
                        || AtomicLong.class.equals(fieldType) //
                        || AtomicInteger.class.equals(fieldType) //
                        || AtomicBoolean.class.equals(fieldType);
                if (!supportReadOnly) {
                    continue;
                }
            }

            boolean contains = false;
            for (com.alibaba.fastjson.util.FieldInfo item : fieldList) {
                if (item.name.equals(field.getName())) {
                    contains = true;
                    break; // 已经是 contains = true，无需继续遍历
                }
            }

            if (contains) {
                continue;
            }

            int ordinal = 0, serialzeFeatures = 0, parserFeatures = 0;
            String propertyName = field.getName();

            JSONField fieldAnnotation = field.getAnnotation(JSONField.class);

            if (fieldAnnotation != null) {
                if (!fieldAnnotation.deserialize()) {
                    continue;
                }

                ordinal = fieldAnnotation.ordinal();
                serialzeFeatures = SerializerFeature.of(fieldAnnotation.serialzeFeatures());
                parserFeatures = Feature.of(fieldAnnotation.parseFeatures());

                if (fieldAnnotation.name().length() != 0) {
                    propertyName = fieldAnnotation.name();
                }
            }

            if (propertyNamingStrategy != null) {
                propertyName = propertyNamingStrategy.translate(propertyName);
            }

            add(fieldList, new FieldInfo(propertyName, null, field, clazz, type, ordinal, serialzeFeatures, parserFeatures, null,
                                         fieldAnnotation, null));
        }
    }

    static Constructor<?> getDefaultConstructor(Class<?> clazz) {
        if (Modifier.isAbstract(clazz.getModifiers())) {
            return null;
        }

        Constructor<?> defaultConstructor = null;
        final Constructor<?>[] constructors = clazz.getDeclaredConstructors();

        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterTypes().length == 0) {
                defaultConstructor = constructor;
                break;
            }
        }

        if (defaultConstructor == null) {
            if (clazz.isMemberClass() && !Modifier.isStatic(clazz.getModifiers())) {
                Class<?>[] types;
                for (Constructor<?> constructor : constructors) {
                    if ((types = constructor.getParameterTypes()).length == 1
                        && types[0].equals(clazz.getDeclaringClass())) {
                        defaultConstructor = constructor;
                        break;
                    }
                }
            }
        }

        return defaultConstructor;
    }

    public static Constructor<?> getCreatorConstructor(Class<?> clazz) {
        Constructor<?> creatorConstructor = null;

        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            JSONCreator annotation = constructor.getAnnotation(JSONCreator.class);
            if (annotation != null) {
                if (creatorConstructor != null) {
                    throw new JSONException("multi-JSONCreator");
                }

                creatorConstructor = constructor;
                // 不应该break，否则多个构造方法上存在 JSONCreator 注解时，并不会触发上述异常抛出
            }
        }
        return creatorConstructor;
    }

    private static Method getFactoryMethod(Class<?> clazz, Method[] methods) {
        Method factoryMethod = null;

        for (Method method : methods) {
            if (!Modifier.isStatic(method.getModifiers())) {
                continue;
            }

            if (!clazz.isAssignableFrom(method.getReturnType())) {
                continue;
            }

            JSONCreator annotation = method.getAnnotation(JSONCreator.class);
            if (annotation != null) {
                if (factoryMethod != null) {
                    throw new JSONException("multi-JSONCreator");
                }

                factoryMethod = method;
                // 不应该break，否则多个静态工厂方法上存在 JSONCreator 注解时，并不会触发上述异常抛出
            }
        }
        return factoryMethod;
    }

    public static Class<?> getBuilderClass(JSONType type) {
        if (type == null) {
            return null;
        }

        Class<?> builderClass = type.builder();

        if (builderClass == Void.class) {
            return null;
        }

        return builderClass;
    }
}
