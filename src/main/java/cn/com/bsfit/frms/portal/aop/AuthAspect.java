package cn.com.bsfit.frms.portal.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.bsfit.frms.portal.anno.Authorize;
import cn.com.bsfit.frms.portal.base.pojo.Users;
import cn.com.bsfit.frms.portal.base.util.SysContent;
import cn.com.bsfit.frms.portal.bo.AuthBO;
import cn.com.bsfit.frms.portal.exception.AuthException;

/**
 * AOP切点类
 * 
 * @Aspect 实现spring AOP 切面（Aspect）: 一个关注点的模块化，这个关注点可能会横切多个对象。
 * 事务管理是J2EE应用中一个关于横切关注点的很好的例子。
 * 在Spring AOP中，切面可以使用通用类（基于模式的风格） 或者在普通类中以 @Aspect 注解（@AspectJ风格）来实现。
 * AOP代理（AOP Proxy）： AOP框架创建的对象，用来实现切面契约（aspect contract）（包括通知方法执行等功能）。
 * 在Spring中，AOP代理可以是JDK动态代理或者CGLIB代理。 
 * 注意：Spring 2.0最新引入的基于模式（schema-based）风格和@AspectJ注解风格的切面声明，对于使用这些风格的用户来说，代理的创建是透明的。
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@Aspect
@Component
public class AuthAspect {

	@Autowired
	private AuthBO authBO;

	/**
	 * 前置通知
	 * 
	 * @param proceedingJoinPoint
	 *            切点
	 */
	@Before(value = "@annotation(authorize)")
	public void doBefore(final JoinPoint joinPoint, Authorize authorize) {
		
	}

	@Around(value = "@annotation(authorize)")
	public Object doAuthorize(final ProceedingJoinPoint proceedingJoinPoint, Authorize authorize) throws Throwable {
		String value = authorize.value();
		if (value == null || "".equals(value)) {
			return proceedingJoinPoint.proceed();
		}
		Users users = SysContent.getUser();
		if (users == null) {
			return new Exception("no login");
		}
		boolean flag = false;
		if (value.contains("permitAll")) {
			// 允许所有
			flag = authBO.permitAll();
		} else if (value.contains("denyAll")) {
			// 阻止所有
			flag = authBO.denyAll();
		} else if (value.contains("hasAuthority")) {
			String resourcesCode = value.substring(value.indexOf("'") + 1, value.lastIndexOf("'"));
			flag = authBO.hasAuthority(resourcesCode);
		} else if (value.contains("hasAllAuthority")) {
			String code = value.substring(value.indexOf("'") + 1, value.lastIndexOf("'")).replaceAll("\'", "").replaceAll("\\s*", "");
			String[] resourcesCodeArry = code.split(",");
			flag = authBO.hasAllAuthority(resourcesCodeArry);
		} else if (value.contains("hasAnyAuthority")) {
			String code = value.substring(value.indexOf("'") + 1, value.lastIndexOf("'")).replaceAll("\'", "").replaceAll("\\s*", "");
			String[] resourcesCodeArry = code.split(",");
			flag = authBO.hasAnyAuthority(resourcesCodeArry);
		} else if (value.contains("hasAllOrAnyAuthority")) {
			String resourcesCode = value.substring(value.indexOf("'") + 1, value.lastIndexOf("'"));
			flag = authBO.hasAllOrAnyAuthority(resourcesCode);
		} else {
			throw new Exception("未知的表达式:" + value);
		}
		if (!flag) {
			throw new AuthException("权限错误!");
		}
		return proceedingJoinPoint.proceed();
	}

	/**
	 * 声明后置通知
	 * 
	 * @param result
	 */
	@AfterReturning(pointcut = "@annotation(authorize)", returning = "result")
	public void doAfterReturning(final JoinPoint joinPoint, Authorize authorize, String result) {
		
	}

	/**
	 * 异常通知
	 * 
	 * @param proceedingJoinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "@annotation(authorize)", throwing = "e")
	public void doAfterThrowing(final JoinPoint joinPoint, Authorize authorize, Throwable e) {
		
	}

	/**
	 * 声明最终通知
	 * 
	 * @param joinPoint
	 */
	@After(value = "@annotation(authorize)")
	public void doAfter(final JoinPoint joinPoint, Authorize authorize) {

	}

	@Pointcut("execution(* cn.com.bsfit.frms.*.*(..))")
	private void pointCutMethod() {

	}
}