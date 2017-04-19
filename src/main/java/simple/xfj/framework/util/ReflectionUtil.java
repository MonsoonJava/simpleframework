package simple.xfj.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by asus on 2017/4/18.
 */
public class ReflectionUtil {


    private static Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    public static Object newInstance(Class<?> clazz){
        Object obj = null;
        try {
            obj = clazz.newInstance();
        } catch (Exception e) {
            LOGGER.error("can new " + clazz.getName());
            throw  new RuntimeException(e);
        }
        return obj;
    }

    public static Object methodInvoke(Method method,Object obj,Object... args){
        Object result = null;
        if(!method.isAccessible()){
            method.setAccessible(true);
        }
        try {
            result = method.invoke(obj,args);
        } catch (Exception e) {
            LOGGER.error("can not invoke method:" + method.getName());
            throw new RuntimeException("invoke method failure:" + method.getName());
        }
        return result;
    }

    public static void setField(Object obj, Field field, Object value){
        try {
            field.setAccessible(true);
            field.set(obj,value);
        } catch (IllegalAccessException e) {
            LOGGER.error("can not set filed:" + field.getName());
            throw new RuntimeException("set filed failure:" + field.getName());
        }
    }

}
