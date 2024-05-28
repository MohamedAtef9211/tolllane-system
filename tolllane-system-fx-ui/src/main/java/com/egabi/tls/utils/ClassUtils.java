package com.egabi.tls.utils;

import java.lang.annotation.Annotation;

public class ClassUtils {

    public static boolean isAnnotationPresent(Class<?> targetClass,Class<? extends Annotation> annotationClass){
       return targetClass.isAnnotationPresent(annotationClass);
    }
}
