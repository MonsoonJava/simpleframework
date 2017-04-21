package simple.xfj.framework.helper;

import simple.xfj.framework.annotation.Aspect;
import simple.xfj.framework.annotation.Execution;
import simple.xfj.framework.bean.RegexSet;
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
    private static RegexSet getTargetClassSet(Aspect aspect){
        Set<Class<?>> targetClass = new HashSet<Class<?>>();
        if(aspect != null){
            Class<? extends Annotation> annotation = aspect.value();
            if(annotation != null && !annotation.equals(Aspect.class)){
                Set<Class<?>> classSetByAnnoation = ClassHelper.getClassSetByAnnoation(annotation);
                targetClass.addAll(classSetByAnnoation);
            }
        }
        if(targetClass != null && targetClass.size() > 0){
            return  new RegexSet(null,targetClass);
        }
        return  null;
    }


    private static RegexSet getTargetClassSetByRegex(Execution execution){
        if(null != execution){
            String regex = execution.value();
            if(regex != null && regex.length() > 0){
                Set<Class<?>> classSet = ClassHelper.getClassSetByMatchMethodName(regex);
                if(classSet != null && classSet.size()>0){
                    return new RegexSet(regex,classSet);
                }
            }
        }
        return null;
    }

    /**
     * 获取所有的切面类aspest与目标类的映射关系
     */

    private static Map<Class<? extends Proxy>, RegexSet> getAspect2Target(){
        Map<Class<? extends Proxy>, RegexSet> proxyMap = new HashMap<Class<? extends Proxy>, RegexSet>();
        Set<Class<?>> aspectClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for(Class clazz : aspectClassSet){
            if(clazz.isAnnotationPresent(Aspect.class)){
                Aspect aspect = (Aspect)clazz.getAnnotation(Aspect.class);
                RegexSet regexAllSet = getTargetClassSet(aspect);
                proxyMap.put(clazz,regexAllSet);
            }
            if(clazz.isAnnotationPresent(Execution.class)){
                Execution execution = (Execution)clazz.getAnnotation(Execution.class);
                RegexSet regexStringSet = getTargetClassSetByRegex(execution);
                proxyMap.put(clazz,regexStringSet);  // classSet 与 regexClassSet可能会有重复
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
        Map<Class<? extends Proxy>, RegexSet> aspect2Target = getAspect2Target();
        Set<Map.Entry<Class<? extends Proxy>, RegexSet>> entries = aspect2Target.entrySet();
        for(Map.Entry<Class<? extends Proxy>, RegexSet> entry : entries){
            Class<? extends Proxy> proxyClass = entry.getKey();
            RegexSet targetSet = entry.getValue();
            if(targetSet != null){
                String regex = targetSet.getRegex();
                Set<Class<?>> classes = targetSet.getClassSet();
                if(classes != null && classes.size() >0){
                    for(Class cls : classes){
                        List<Proxy> proxies = clazz4Proxy.get(cls);
                        if(null == proxies){
                            proxies = new ArrayList<Proxy>();
                            Proxy reproxy = ReflectionUtil.newInstance(proxyClass);
                            reproxy.setRegex(regex);
                            proxies.add(reproxy);
                            clazz4Proxy.put(cls,proxies);
                        }else{
                            Proxy reproxy = ReflectionUtil.newInstance(proxyClass);
                            reproxy.setRegex(regex);
                            proxies.add(reproxy);
                        }
                    }
                }
            }
          /*  for(Class cls : targetSet){
                List<Proxy> proxies = clazz4Proxy.get(cls);
                if(null == proxies){
                    proxies = new ArrayList<Proxy>();
                    proxies.add(ReflectionUtil.newInstance(proxyClass));
                    clazz4Proxy.put(cls,proxies);
                }else{
                    proxies.add(ReflectionUtil.newInstance(proxyClass));
                }
            }*/
       }
       return clazz4Proxy;
    }

}
