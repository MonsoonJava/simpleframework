package simple.xfj.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by asus on 2017/4/20.
 */
public class AspectProxy implements Proxy{


    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object res = null;
        Class<?> targetClass = proxyChain.getTargetClass();
        Object[] methodParams = proxyChain.getMethodParams();
        Method targetMethod = proxyChain.getTargetMethod();
        begin();
        try{
            if(intercept(targetClass,targetMethod,methodParams)){
                before(targetClass,targetMethod,methodParams);
                res = proxyChain.doProxyChain();
                after(targetClass,targetMethod,methodParams,res);
            }else{
                res = proxyChain.doProxyChain();
            }
        }catch (Exception e){
            LOGGER.error("invoke method chain proxy error" + targetMethod.getName());
            throw e;
        }finally {
            end();
        }
        return res;
    }
    public void begin(){

    }

    public boolean intercept(Class<?> cls,Method method,Object[] params) throws Throwable{
        return true;
    }
    public void before(Class<?> cls,Method method,Object[] params) throws Throwable{
        System.out.println("ni hao");

    }

    public void after(Class<?> cls,Method method,Object[] params,Object result) throws Throwable{
        System.out.println("ni huai");
    }
    public void end(){

    }


}
