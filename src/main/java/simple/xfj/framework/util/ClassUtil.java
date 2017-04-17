package simple.xfj.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by asus on 2017/4/18.
 */
public class ClassUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(Class.class);

    /**
     * 获取类加载器
     * @return
     */
    public static ClassLoader getClassLoader(){
        return ClassUtil.getClassLoader();
    }

    /**
     * 加载某个类
     * @param className
     * @param isInitialized
     * @return
     */
    public static Class<?> loadClass(String className,boolean isInitialized){
        Class<?> clazz;
        try{
            clazz = Class.forName(className,isInitialized,getClassLoader());
        }catch (Exception e){
            LOGGER.error("load class failed",e);
            throw new RuntimeException(e);
        }
        return clazz;
    }

    /**
     * 加载某个包下的所有类
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName){

        return null;
    }






}
