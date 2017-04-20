package simple.xfj.framework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by asus on 2017/4/20.
 */
public class ProxyManager {

    //产生代理的管理类
    public static <T> T getProxy(final Class<T> clazz , final List<Proxy> proxyList){
        return (T)Enhancer.create(clazz, new MethodInterceptor() {
            public Object intercept(Object target, Method method, Object[] agrs, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(clazz,target,method,methodProxy,agrs).setProxyList(proxyList).doProxyChain();
            }
        });
    }

}
