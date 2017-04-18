package simple.xfj.framework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by asus on 2017/4/18.
 */
public class Param {

    private Map<String,Object> paramMap = new HashMap<String,Object>();

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}
