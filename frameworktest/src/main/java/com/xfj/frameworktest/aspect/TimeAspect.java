package com.xfj.frameworktest.aspect;

import simple.xfj.framework.annotation.Aspect;
import simple.xfj.framework.annotation.Controller;
import simple.xfj.framework.proxy.AspectProxy;

import java.lang.reflect.Method;

/**
 * Created by asus on 2017/4/20.
 */
@Aspect(value = Controller.class)//声明这个类是增强类，并且去拦截被所有被controller标注的类的方法
public class TimeAspect extends AspectProxy{
    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        super.before(cls, method, params);
        System.out.println("前置增强");
        System.out.println(System.currentTimeMillis());
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
        super.after(cls, method, params, result);
        System.out.println("后置增强");
        System.out.println(System.currentTimeMillis());
    }

    public static void main(String[] agrs){
        System.out.println(AspectProxy.class.isAssignableFrom(TimeAspect.class));
    }
}
