package simple.xfj.framework.proxy;

/**
 * Created by asus on 2017/4/20.
 */
public abstract class  Proxy {

     private String regex;

    public abstract Object doProxy(ProxyChain proxyChain) throws Throwable;

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
}
