package simple.xfj.framework.annotation;

import java.lang.annotation.*;

/**
 * Created by asus on 2017/4/20.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    Class<? extends Annotation> value();
}
