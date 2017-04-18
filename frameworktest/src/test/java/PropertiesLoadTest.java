import org.junit.Before;
import org.junit.Test;
import simple.xfj.framework.constant.ConfigConstant;
import simple.xfj.framework.helper.ClassHelper;
import simple.xfj.framework.util.ClassUtil;
import simple.xfj.framework.util.PropsUtil;

import java.io.IOException;
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

}
