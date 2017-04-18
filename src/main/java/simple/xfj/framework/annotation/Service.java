package simple.xfj.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by asus on 2017/4/18.
 */
@Target(ElementType.TYPE) //标明该注解用在类上
@Retention(RetentionPolicy.RUNTIME) //注解作用范围运行时
public @interface Service {
}
