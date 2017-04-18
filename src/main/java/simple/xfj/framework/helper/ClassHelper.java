package simple.xfj.framework.helper;

/**
 * Created by asus on 2017/4/18.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simple.xfj.framework.annotation.Controller;
import simple.xfj.framework.annotation.Service;
import simple.xfj.framework.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * 获取指定包名下的各种注解类的helper类
 */
public class ClassHelper {

    private static Logger LOGGER = LoggerFactory.getLogger(ClassHelper.class);
    private static Set<Class<?>> CLAZZ_SET;

    static {
        try{
            CLAZZ_SET = ClassUtil.getClassSet(ConfigHelper.getAPPBasePackage());
        }catch (Exception e){
            LOGGER.error("inited class set failure");
            throw  new RuntimeException(e);
        }
    }

    public static Set<Class<?>> getClazzSet() {
        return CLAZZ_SET;
    }

    /**
     * 获得所有带有@controller注解的类
     * @return
     */
    public static Set<Class<?>> getControllerSet(){
        Set<Class<?>> controllerSet = new HashSet<Class<?>>();
        if(null != CLAZZ_SET && CLAZZ_SET.size() > 0){
            for(Class clazz:CLAZZ_SET){
                if(clazz.isAnnotationPresent(Controller.class)){
                    controllerSet.add(clazz);
                }
            }
        }
        return controllerSet;
    }

    /**
     * 获得所有带有@Service注解的类
     * @return
     */
    public static Set<Class<?>> getServiceSet(){
        Set<Class<?>> serviceSet = new HashSet<Class<?>>();
        if(null != CLAZZ_SET && CLAZZ_SET.size() > 0){
            for(Class clazz:CLAZZ_SET){
                if(clazz.isAnnotationPresent(Service.class)){
                    serviceSet.add(clazz);
                }
            }
        }
        return serviceSet;
    }

    /*
        获得所有bean实列
     */
    public static Set<Class<?>> getBeanSet(){
        Set<Class<?>> beanSet = new HashSet<Class<?>>();
        beanSet.addAll(getServiceSet());
        beanSet.addAll(getControllerSet());
        return beanSet;
    }

}
