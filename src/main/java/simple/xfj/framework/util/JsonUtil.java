package simple.xfj.framework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by asus on 2017/4/19.
 */
public class JsonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    public static final String ObjToStr(Object object){
        String res = null;
        try {
            res = MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOGGER.error("transform object to json string failure");
            throw new RuntimeException(e);
        }
        return res;
    }

    public static final <T> T strToObj(String str,Class<T> clazz){
        T t = null;
        try {
            t = MAPPER.readValue(str,clazz);
        } catch (IOException e) {
            LOGGER.error("parse json to obj string failure");
            throw new RuntimeException(e);
        }
        return t;
    }

}
