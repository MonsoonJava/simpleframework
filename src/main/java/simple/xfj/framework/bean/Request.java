package simple.xfj.framework.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by asus on 2017/4/18.
 */
public class Request {

    private String requestMethod;

    private String requestPath;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (!requestMethod.equals(request.requestMethod)) return false;
        return requestPath.equals(request.requestPath);
    }

    @Override
    public int hashCode() {
        int result = requestMethod.hashCode();
        result = 31 * result + requestPath.hashCode();
        return result;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public Request(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }
}
