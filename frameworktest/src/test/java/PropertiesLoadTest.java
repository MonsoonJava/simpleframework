import org.junit.Test;
import simple.xfj.framework.constant.ConfigConstant;
import simple.xfj.framework.util.PropsUtil;

import java.util.Properties;

/**
 * Created by asus on 2017/4/17.
 */
public class PropertiesLoadTest {

    @Test
    public void testConfig() throws Exception{
        Properties properties = PropsUtil.loadProperties(ConfigConstant.CONFIG_FILENAME);
        System.out.println(properties.getProperty(ConfigConstant.JDBC_DRIVER));

    }

}
