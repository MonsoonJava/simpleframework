package simple.xfj.framework.util;

import com.sun.org.apache.xml.internal.utils.StringToStringTableVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by asus on 2017/4/19.
 */
public class DEcodeUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DEcodeUtil.class);

    public static final String Decode(String str){
        String res = null;
        try {
           /* byte[] bytes = str.getBytes("ISO8859-1");
            res = new String(bytes, "GBK");*/
            res = URLDecoder.decode(str,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("decode String error");
            throw new RuntimeException(e);
        }
        return res;
    }

    public static final String Encode(String str){
        try {
            str = URLEncoder.encode(str,"ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("encode String error");
            throw new RuntimeException(e);
        }
        return str;
    }


}
