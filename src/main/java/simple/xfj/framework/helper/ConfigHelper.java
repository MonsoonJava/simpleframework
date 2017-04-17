package simple.xfj.framework.helper;

import simple.xfj.framework.constant.ConfigConstant;
import simple.xfj.framework.util.PropsUtil;

import java.util.Properties;

/**
 * Created by asus on 2017/4/18.
 */
public final class ConfigHelper {

    private static Properties properties;

    static {
        try {
            properties = PropsUtil.loadProperties(ConfigConstant.CONFIG_FILENAME);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getJdbcDriver(){
        return properties.getProperty(ConfigConstant.JDBC_DRIVER);
    }

    public static String getJdbcUrl(){
        return properties.getProperty(ConfigConstant.JDBC_URL);
    }

    public static String getJdbcUsername(){
        return properties.getProperty(ConfigConstant.JDBC_USERNAME);
    }

    public static String getJdbcPassword(){
        return properties.getProperty(ConfigConstant.JDBC_PASSWORD);
    }

    public static String getAPPBasePackage(){
        return properties.getProperty(ConfigConstant.APP_BASE_PACKAGE);
    }

    public static String getAPPJspPath(){
        return properties.getProperty(ConfigConstant.APP_JSP_PATH,"/WEB-INF/view/");
    }

    public static String getAPPAsset(){
        return properties.getProperty(ConfigConstant.APP_ASSET_PATH,"/asset/");
    }

}
