package simple.xfj.framework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by asus on 2017/4/18.
 */
public class View {

    //返回视图路径
    private String path;

    //返回时所携带的数据模型
    private Map<String,Object> model;

    public View(String path, Map<String, Object> model) {
        this.path = path;
        this.model = model;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void addMode(String key,Object obj){
        if(model == null){
            model = new HashMap<String,Object>();
            model.put(key,obj);
        }else {
            model.put(key,obj);
        }
    }
}
