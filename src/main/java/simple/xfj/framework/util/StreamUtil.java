package simple.xfj.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by asus on 2017/4/18.
 */
public class StreamUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class);
    //从输入流中获得字符串
    public static String getStringFromStream(InputStream in){
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine())!= null){
                sb.append(line);
            }
            reader.close();
            in.close();
        }catch (Exception e){
            LOGGER.error("read String from Stream failure");
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

}
