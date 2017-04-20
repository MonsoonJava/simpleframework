package simple.xfj.framework.proxy;

/**
 * Created by asus on 2017/4/20.
 */
public interface Proxy {

    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
