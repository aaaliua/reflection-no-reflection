package org.reflection_no_reflection.sample;

import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import org.reflection_no_reflection.AnnotationDatabaseFinder;
import org.reflection_no_reflection.Field;
import org.reflection_no_reflection.FieldFinder;
import org.reflection_no_reflection.reflection.ReflectionFieldFinderImpl;

public class Main {

    public static final int ITERATIONS = 1000000;

    public static void main(String[] args) throws IllegalAccessException {
        A a = new A();

        long timeWithReflection;
        long timeWithoutReflection;

        System.out.println("--- Via Reflection");
        long startTime = System.currentTimeMillis();
        for (int i=0;i< ITERATIONS;i++)
            usingReflection(a);
        long endTime = System.currentTimeMillis();
        timeWithReflection = endTime - startTime;


        System.out.println("--- Via No Reflection");
        startTime = System.currentTimeMillis();
        for (int i=0;i<ITERATIONS;i++)
            usingNoReflection(a);
        endTime = System.currentTimeMillis();
        timeWithoutReflection = endTime - startTime;

        System.out.println("With Reflection   ---> " + timeWithReflection + " ms");
        System.out.println("With No Relection ---> " + timeWithoutReflection + " ms");
    }

    private static void usingNoReflection(A a) throws IllegalAccessException {
        AnnotationDatabaseFinder fieldFinder;
        Set<Field> allFields;
        fieldFinder = new AnnotationDatabaseFinder(new String[0]);
        allFields = fieldFinder.getMapAnnotationToMapClassContainingInjectionToInjectedFieldSet().get(Inject.class).get(A.class);
        for (Field field : allFields) {
            //System.out.println("Field: " + field.getName() + ":" + field.getType().getName() + " -- " + field.getAnnotation(Inject.class));
            //System.out.println("Field: " + field.getName() + ":" + field.getType().getName() + " -- " + field.get(a));
            field.get(a);
        }
    }

    private static void usingReflection(A a) throws IllegalAccessException {FieldFinder fieldFinder = new ReflectionFieldFinderImpl();
        List<Field> allFields = fieldFinder.getAllFields(Inject.class, A.class);
        for (Field field : allFields) {
            //System.out.println("Field: " + field.getName() + ":" + field.getType().getName() + " -- " + field.getAnnotation(Inject.class));
            //System.out.println("Field: " + field.getName() + ":" + field.getType().getName() + " -- " + field.get(a));
            field.get(a);
        }
    }
}