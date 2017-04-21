import com.xfj.frameworktest.controller.CartController;
import com.xfj.frameworktest.service.CartService;
import org.junit.Before;
import org.junit.Test;
import simple.xfj.framework.bean.Handler;
import simple.xfj.framework.bean.Request;
import simple.xfj.framework.constant.ConfigConstant;
import simple.xfj.framework.helper.BeanHelper;
import simple.xfj.framework.helper.ClassHelper;
import simple.xfj.framework.helper.ControllerHelper;
import simple.xfj.framework.helper.IocHelper;
import simple.xfj.framework.util.ClassUtil;
import simple.xfj.framework.util.PropsUtil;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by asus on 2017/4/17.
 */
public class PropertiesLoadTest {

    private Properties properties;

    @Before
    public void before()throws Exception{
        properties = PropsUtil.loadProperties(ConfigConstant.CONFIG_FILENAME);
    }

    @Test
    public void testConfig() throws Exception{
        System.out.println(properties.getProperty(ConfigConstant.JDBC_DRIVER));
    }

    @Test
    public void testGetClassSet() throws IOException {
        Set<Class<?>> clazzSet = ClassUtil.getClassSet(properties.getProperty(ConfigConstant.APP_BASE_PACKAGE));
        for(Class cls : clazzSet){
            System.out.println(cls.getPackage());
            System.out.println(cls.getName());
        }
    }

    @Test
    public void testGetControllerBean(){
        Set<Class<?>> controllerSet = ClassHelper.getControllerSet();
        for(Class cls : controllerSet){
            System.out.println(cls.getPackage());
            System.out.println(cls.getName());
        }
    }



    /**
     *      测试cartservice是否依赖注入到cartcontroller中
     */
    @Test
    public void testFrameWorkIOC()throws Exception{
        Set<Class<?>> beanSet = ClassHelper.getBeanSet();
        ClassUtil.loadClass(IocHelper.class.getName(),true);
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        Set<Class<?>> keys = BeanHelper.getBeanMap().keySet();
        for(Class clazz : keys){
            System.out.println(clazz.getName());
            System.out.println(beanMap.get(clazz));
        }
        CartController cartController = (CartController) beanMap.get(CartController.class);
        System.out.println(cartController.getCartService() == beanMap.get(CartService.class) );
    }

    @Test
    public void testControllerHelper(){
        ClassUtil.loadClass(ControllerHelper.class.getName(),true);
        Map<Request, Handler> actionMap = ControllerHelper.getActionMap();
        Set<Request> keys = actionMap.keySet();
        for(Request re : keys){
            System.out.println(re.getRequestPath());
            System.out.println(re.getRequestMethod());
            System.out.println(actionMap.get(re).getControllerClass().getName());
            System.out.println(actionMap.get(re).getActionMethod().getName());
        }
    }

    @Test
    public void testRegexMatchClass(){
        ClassUtil.loadClass(ClassHelper.class.getName(),true);
        Set<Class<?>> classSet = ClassHelper.getClassSetByMatchMethodName("com.xfj.frameworktest.*find.*");
        for(Class cls : classSet){
            System.out.println(cls.getName());
        }
    }

}
