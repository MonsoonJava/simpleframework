package simple.xfj.framework.bootstarp;

import simple.xfj.framework.annotation.Controller;
import simple.xfj.framework.helper.AopHelper;
import simple.xfj.framework.helper.BeanHelper;
import simple.xfj.framework.helper.ClassHelper;
import simple.xfj.framework.helper.IocHelper;
import simple.xfj.framework.util.ClassUtil;

/**
 * Created by asus on 2017/4/18.
 */
public class HelperInitiler {

    public static void init(){
        Class[] clazzs = new Class[]{
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,// AopHelper要在beanHelper之后，IocHelper之前初始化
                IocHelper.class,
                Controller.class
        };

        for(int i = 0;i < clazzs.length;i++){
            ClassUtil.loadClass(clazzs[i].getName(),true);
        }
    }

}
