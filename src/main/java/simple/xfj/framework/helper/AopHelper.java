package simple.xfj.framework.helper;

import simple.xfj.framework.annotation.Aspect;
import simple.xfj.framework.proxy.AspectProxy;
import simple.xfj.framework.proxy.Proxy;
import simple.xfj.framework.proxy.ProxyManager;
import simple.xfj.framework.util.ReflectionUtil;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Created by asus on 2017/4/20.
 */
public class AopHelper {

    static {
        //类加载时初始化AOP，并将其设置到Bean中
        Map<Class<?>, List<Proxy>> target2ProxyList = getTarget2ProxyList();
        if(null != target2ProxyList && target2ProxyList.size() > 0){
            Set<Map.Entry<Class<?>, List<Proxy>>> entries = target2ProxyList.entrySet();
            for(Map.Entry<Class<?>, List<Proxy>> entry: entries){
                Class clazz = entry.getKey();
                List<Proxy> proxyList = entry.getValue();
                Object bean = ProxyManager.getProxy(clazz, proxyList);
                BeanHelper.setBean(clazz,bean);
            }
        }
    }

    /**
     * 获取切面要去增强的所有类
     * @param aspect
     * @return
     */
    private static Set<Class<?>> getTargetClassSet(Aspect aspect){
        Set<Class<?>> targetClass = new HashSet<Class<?>>();
        if(aspect != null){
            Class<? extends Annotation> annotation = aspect.value();
            if(annotation != null && !annotation.equals(Aspect.class)){
                Set<Class<?>> classSetByAnnoation = ClassHelper.getClassSetByAnnoation(annotation);
                targetClass.addAll(classSetByAnnoation);
            }
        }
        return  targetClass;
    }

    /**
     * 获取所有的切面类aspest与目标类的映射关系
     */

    private static Map<Class<? extends Proxy>, Set<Class<?>>> getAspect2Target(){
        Map<Class<? extends Proxy>, Set<Class<?>>> proxyMap = new HashMap<Class<? extends Proxy>, Set<Class<?>>>();
        Set<Class<?>> aspectClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for(Class clazz : aspectClassSet){
            if(clazz.isAnnotationPresent(Aspect.class)){
                Aspect aspect = (Aspect)clazz.getAnnotation(Aspect.class);
                Set<Class<?>> classSet = getTargetClassSet(aspect);
                proxyMap.put(clazz,classSet);
            }
        }
        return proxyMap;
    }

    /**
     * 将每个目标类所需要增强的增强类整理成1对多的map集合以便在beanHelper中用去取代原来的原始bean
     * @return
     */
    private static Map<Class<?>,List<Proxy>> getTarget2ProxyList(){
        Map<Class<?>,List<Proxy>> clazz4Proxy = new HashMap<Class<?>, List<Proxy>>();
        Map<Class<? extends Proxy>, Set<Class<?>>> aspect2Target = getAspect2Target();
        Set<Map.Entry<Class<? extends Proxy>, Set<Class<?>>>> entries = aspect2Target.entrySet();
        for(Map.Entry<Class<? extends Proxy>, Set<Class<?>>> entry : entries){
            Class<? extends Proxy> proxyClass = entry.getKey();
            Set<Class<?>> targetSet = entry.getValue();
            for(Class cls : targetSet){
                List<Proxy> proxies = clazz4Proxy.get(cls);
                if(null == proxies){
                    proxies = new ArrayList<Proxy>();
                    proxies.add(ReflectionUtil.newInstance(proxyClass));
                    clazz4Proxy.put(cls,proxies);
                }else{
                    proxies.add(ReflectionUtil.newInstance(proxyClass));
                }
            }
       }
       return clazz4Proxy;
    }

}
