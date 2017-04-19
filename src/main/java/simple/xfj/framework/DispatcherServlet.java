package simple.xfj.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simple.xfj.framework.bean.*;
import simple.xfj.framework.bootstarp.HelperInitiler;
import simple.xfj.framework.helper.BeanHelper;
import simple.xfj.framework.helper.ConfigHelper;
import simple.xfj.framework.helper.ControllerHelper;
import simple.xfj.framework.util.DEcodeUtil;
import simple.xfj.framework.util.JsonUtil;
import simple.xfj.framework.util.ReflectionUtil;
import simple.xfj.framework.util.StreamUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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
            Param param = getParam(req);
            System.out.println(handler.getActionMethod().getName());
            Object res = ReflectionUtil.methodInvoke(handler.getActionMethod(), handlerBean, param);
            try {
                doResult(req,resp,res);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            String StringParam = DEcodeUtil.Decode(StreamUtil.getStringFromStream(req.getInputStream()));
            if(StringParam.length() == 0)
                return new Param(paramMap);
            String[] nameValue = StringParam.split("&");
            if(nameValue != null && nameValue.length > 0){
                for(int i = 0; i < nameValue.length;i++){
                    System.out.println(nameValue[i]);
                    String[] pair = nameValue[i].split("=");
                    paramMap.put(pair[0],pair[1]);
                }
            }
        } catch (IOException e) {
            LOGGER.error("read param from Stream failure");
            throw new RuntimeException(e);
        }
        return new Param(paramMap);
    }


    private void doResult(HttpServletRequest req,HttpServletResponse pon,Object res) throws Exception{
        if(res == null){
            req.getRequestDispatcher(ConfigHelper.getAPPJspPath() + "error.jsp").forward(req,pon);
        }
        if(res instanceof View){
            View view = (View) res;
            String path = view.getPath();
            if(path != null && path.length() > 0){
                if(path.startsWith("/")){
                    pon.sendRedirect(req.getContextPath() + path);
                }else{
                    Map<String, Object> model = view.getModel();
                    for(Map.Entry<String,Object> ele : model.entrySet()){
                        req.setAttribute(ele.getKey(),ele.getValue());
                    }
                    req.getRequestDispatcher(ConfigHelper.getAPPJspPath()).forward(req,pon);
                }
            }
        }else if(res instanceof Data){
            //返回json数据
            Data data = (Data) res;
            Object model = data.getModel();
            if(null  != model){
                pon.setContentType("application/json");
                pon.setCharacterEncoding("UTF-8");
                PrintWriter writer = pon.getWriter();
                String json = JsonUtil.ObjToStr(model);
                writer.write(json);
                writer.flush();
                writer.close();
            }
        }
    }
}
