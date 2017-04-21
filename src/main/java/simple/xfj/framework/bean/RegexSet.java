package simple.xfj.framework.bean;

import java.util.Set;

/**
 * Created by asus on 2017/4/21.
 */
public class RegexSet{
    String regex;

    Set<Class<?>> classSet;

    public RegexSet(String regex, Set<Class<?>> classSet) {
        this.regex = regex;
        this.classSet = classSet;
    }


    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public Set<Class<?>> getClassSet() {
        return classSet;
    }

    public void setClassSet(Set<Class<?>> classSet) {
        this.classSet = classSet;
    }
}
