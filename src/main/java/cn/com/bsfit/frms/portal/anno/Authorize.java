package cn.com.bsfit.frms.portal.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for specifying a method access-control expression 
 * which will be evaluated to decide whether a
 * method invocation is allowed or not.
 * 
 * @author hjp
 * 
 * @since 1.2.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Authorize {
	public String value() default "";
}
