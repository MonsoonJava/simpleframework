package simple.xfj.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simple.xfj.framework.annotation.Action;
import simple.xfj.framework.bean.Handler;
import simple.xfj.framework.bean.Param;
import simple.xfj.framework.bean.Request;
import simple.xfj.framework.bootstarp.HelperInitiler;
import simple.xfj.framework.helper.BeanHelper;
import simple.xfj.framework.helper.ClassHelper;
import simple.xfj.framework.helper.ConfigHelper;
import simple.xfj.framework.helper.ControllerHelper;
import simple.xfj.framework.util.StreamUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by asus on 2017/4/18.
 */
@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet{

    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("servlet start");
        //容器初始化
        HelperInitiler.init();
        ServletContext context = config.getServletContext();
        ServletRegistration jspServlet = context.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAPPJspPath() + "*");
        ServletRegistration defaultServlet = context.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAPPAsset() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        System.out.println(requestPath);
        Request request = new Request(method,requestPath);
        Handler handler = ControllerHelper.getActionMap().get(request);
        if(null != handler){
            Class<?> handlerClass = handler.getControllerClass();
            Object handlerBean = BeanHelper.getClassBean(handlerClass);
            //创建param
            System.out.println("yes");
            Param param = getParam(req);
        }
    }

    private Param getParam(HttpServletRequest req){
        Map<String,Object> paramMap = new HashMap<String,Object>();
        //URL中的参数收集
        Enumeration<String> paramNames = req.getParameterNames();
        while (paramNames.hasMoreElements()){
            String name = paramNames.nextElement();
            String value = req.getParameter(name);
            paramMap.put(name,value);
        }
        //form表单中的参数收集
        try {
            String StringParam = StreamUtil.getStringFromStream(req.getInputStream());
            System.out.println(StringParam);
        } catch (IOException e) {
            LOGGER.error("read param from Stream failure");
            throw new RuntimeException(e);
        }
        return new Param(paramMap);
    }


}
