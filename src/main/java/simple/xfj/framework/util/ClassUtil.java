package simple.xfj.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by asus on 2017/4/18.
 */
public class ClassUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(Class.class);

    /**
     * 获取类加载器
     * @return
     */
    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载某个类
     * @param className
     * @param isInitialized
     * @return
     */
    public static Class<?> loadClass(String className,boolean isInitialized){
        Class<?> clazz;
        try{
            clazz = Class.forName(className,isInitialized,getClassLoader());
        }catch (Exception e){
            LOGGER.error("load class failed",e);
            throw new RuntimeException(e);
        }
        return clazz;
    }

    /**
     * 加载某个包下的所有类
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName) throws IOException {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
        while (urls.hasMoreElements()){
            URL url = urls.nextElement();
            if(null != url){
                String protocol = url.getProtocol();
                if(protocol.equals("file")){
                    String packagePath = url.getPath().replace("%20", " ");
                    addClass(classSet,packagePath,packageName);
                }else if(protocol.equals("jar")){
                    JarURLConnection connection = (JarURLConnection)url.openConnection();
                    url.openConnection();
                    if(connection != null){
                        JarFile jarFile = connection.getJarFile();
                        if(jarFile != null){
                            Enumeration<JarEntry> entries = jarFile.entries();
                            while (entries.hasMoreElements()){
                                JarEntry entry = entries.nextElement();
                                String name = entry.getName();
                                if(name.endsWith(".class")){
                                    String className = name.substring(0,name.lastIndexOf(".")).replaceAll("/",".");
                                    doAddClass(classSet,className);
                                    System.out.println(className);
                                }
                            }
                        }
                    }
                }
            }
        }
        return classSet;
    }

    //加载路径下的所有class文件
    private static void addClass(Set<Class<?>> set,String packagePath,String packageName){
        File file = new File(packagePath);
        File[] files = file.listFiles(new FileFilter(){
            public boolean accept(File f) {
                return f.isFile() && f.getName().endsWith(".class") || f.isDirectory();
            }});
        //如果有空文件夹 需要跳过考虑
        if(files == null || files.length == 0){
            return;
        }
        for(File f: files){
            String fileName = f.getName();
            if(f.isFile()){
                String className = fileName.substring(0,fileName.lastIndexOf("."));
                if(packageName != null && packageName.length() >0){
                    className = packageName + "." + className;
                }
                doAddClass(set,className);
            }else {
                //如果存在子文件夹，则递归读取子文件夹下的所有class文件，并加载
                String subPackageName = null;
                String subPackagePath = null;
                if(packageName != null && packageName.length() >0){
                    subPackageName = packageName + "." + fileName;
                    subPackagePath = packagePath + "/" + fileName;
                }
                addClass(set,subPackagePath,subPackageName);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> set,String clazz){
        Class<?> cls = loadClass(clazz, false);
        set.add(cls);
    }

}
