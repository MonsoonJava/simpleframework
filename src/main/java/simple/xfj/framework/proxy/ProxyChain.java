package simple.xfj.framework.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2017/4/20.
 */
public class ProxyChain {

    private final Class<?> targetClass;
    private final Object target;
    private final Method targetMethod;
    private final MethodProxy methodProxy;
    private final Object[] methodParams;

    private List<Proxy> proxyList = new ArrayList<Proxy>();

    private int proxyIndex = 0;

    public ProxyChain(Class<?> targetClass, Object target, Method targetMethod, MethodProxy methodProxy, Object[] methodParams) {
        this.targetClass = targetClass;
        this.target = target;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public ProxyChain setProxyList(List proxyList){
        this.proxyList = proxyList;
        return  this;
    }

    public Object doProxyChain() throws Throwable{
        Object res = null;
        if(null != proxyList && proxyIndex < proxyList.size()){
            //将链条中的代理一个一个的添加到代理方法中
            res = proxyList.get(proxyIndex++).doProxy(this);
        }else{
            res = methodProxy.invokeSuper(target,methodParams);
        }
        return res;
    }

}
