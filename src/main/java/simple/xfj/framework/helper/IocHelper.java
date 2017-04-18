package simple.xfj.framework.helper;

/**
 * Created by asus on 2017/4/18.
 */

import simple.xfj.framework.annotation.Autowired;
import simple.xfj.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 将带有@Autowired的filed字段通过反射注入
 */
public class IocHelper {
    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if(null != beanMap && beanMap.size() > 0){
            for(Map.Entry entry: beanMap.entrySet()){
                Class clazz = (Class) entry.getKey();
                Object instance = entry.getValue();
                Field[] fields = clazz.getDeclaredFields();
                if(null != fields && fields.length > 0){
                    for(Field field:fields){
                        if(field.isAnnotationPresent(Autowired.class)){
                            Class<?> clazzType = field.getType();
                            Object beanInject = BeanHelper.getClassBean(clazzType);
                            if(null != beanInject )
                                //通过反射设置beanfield的值
                                ReflectionUtil.setField(instance,field,beanInject);
                        }
                    }
                }
            }
        }
    }
}
