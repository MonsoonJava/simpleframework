package simple.xfj.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * Created by asus on 2017/4/20.
 */
public class AspectProxy extends Proxy{


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
        if(super.getRegex() == null) {
            //对@Aspect注解的类的所有方法进行增强，其他的如@Execution的注解进行拦截判断
            Method[] declaredMethods = cls.getDeclaredMethods();
            if(null != declaredMethods && declaredMethods.length > 0){
                for(int i = 0;i< declaredMethods.length;i++){
                    if(method.equals(declaredMethods[i]))
                        //只对自己声明的方法进行增强
                        return true;
                }
                return false;
            }
        }else {
            String regex = super.getRegex();
            Pattern pattern = Pattern.compile(regex);
            String compileMethodName = cls.getName() + "." + method.getName().toLowerCase();
            if(pattern.matcher(compileMethodName).matches()){
                return true;
            }else
                return false;
        }
        return  false;
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
