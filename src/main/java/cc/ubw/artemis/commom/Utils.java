/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.ubw.artemis.commom;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Allen
 * 2019年11月5日 下午3:26:36
 * V1.0
 */
public class Utils {
//这就是一个实现了深拷贝的反射

    public static <T> void firstLevelDeepCopy(Class<T> clazz, T from, T to) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            //  System.out.println();
            String methodName = method.getName();
            System.out.println(methodName);
            String memberName;
            //如果是set方法并且有对应的get方法
            System.out.println("sub" + methodName.substring(0, 3));
            if (methodName.substring(0, 3).equals("set") && (method.getParameterTypes().length == 1)) {
                System.out.println("set" + methodName);
                memberName = methodName.substring(3);
                Method methodGet = null;
                try {
                    methodGet = clazz.getMethod("get" + memberName);
                } catch (NoSuchMethodException e) {

                    System.out.println("not found" + "get" + memberName);
                    try {
                        methodGet = clazz.getMethod("is" + memberName);
                    } catch (NoSuchMethodException e1) {
                        methodGet = null;
                        System.out.println("not found" + "is" + memberName);
                    }
                }
                if (methodGet != null) {
                    Object target = null;
                    try {
                        target = methodGet.invoke(from);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    try {
                        if (methodGet.getReturnType().equals(method.getParameterTypes()[0])) {
                            method.invoke(to, target);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
