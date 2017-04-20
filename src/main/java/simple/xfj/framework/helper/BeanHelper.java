package simple.xfj.framework.helper;

import simple.xfj.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by asus on 2017/4/18.
 */
public class BeanHelper {

    private static final Map<Class<?>,Object> beanMap = new HashMap<Class<?>, Object>();

    static {
        Set<Class<?>> beanSet = ClassHelper.getBeanSet();
        if(null != beanSet && beanSet.size()>0){
            for(Class clazz : beanSet){
                Object obj = ReflectionUtil.newInstance(clazz);
                beanMap.put(clazz,obj);
            }
        }
    }

    public static Map<Class<?>,Object> getBeanMap(){
        return beanMap;
    }

    public static <T> T getClassBean(Class<T> clazz){
        if(null != clazz){
            if(!beanMap.containsKey(clazz)){
                throw new RuntimeException("can not get bean by class:"+clazz.getName());
            }
            return (T)beanMap.get(clazz);
        }
        return null;
    }

    public static void setBean(Class<?> clazz,Object object){
        //用于在aop生成代理类后，替换bean容器里的原有目标类，
        beanMap.put(clazz,object);
    }
}
