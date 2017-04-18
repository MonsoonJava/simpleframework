package simple.xfj.framework.helper;

import simple.xfj.framework.annotation.Action;
import simple.xfj.framework.bean.Handler;
import simple.xfj.framework.bean.Request;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by asus on 2017/4/18.
 */
public final class ControllerHelper {

    /**
     * 存放请求路径与处理器之间的映射关系
     */
    private static Map<Request,Handler> ACTION_MAP = new HashMap<Request,Handler>();

    static {
        Set<Class<?>> controllerSet = ClassHelper.getControllerSet();
        if(null != controllerSet && controllerSet.size() > 0){
            for(Class clazz : controllerSet){
                Method[] methods = clazz.getMethods();
                if(null != methods && methods.length > 0){
                    for(Method method: methods){
                        if(method.isAnnotationPresent(Action.class)){
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            if(mapping.matches("^/.*") ){
                               Request request = new Request(action.method(),mapping);
                               Handler handler = new Handler(clazz,method);
                                //将请求路径与处理的类的方法一一对应，联系起来
                                ACTION_MAP.put(request,handler);
                            }
                        }
                    }
                }
            }
        }
    }

    public static Map<Request, Handler> getActionMap() {
        return ACTION_MAP;
    }
}
