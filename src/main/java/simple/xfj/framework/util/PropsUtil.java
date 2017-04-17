package simple.xfj.framework.util;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by asus on 2017/4/17.
 */
public class PropsUtil {

    public static Properties loadProperties(String propertiesFileName) throws IOException {
        Properties prop = new Properties();
        prop.load(PropsUtil.class.getClassLoader().getResourceAsStream(propertiesFileName));
        return prop;
    }


}
