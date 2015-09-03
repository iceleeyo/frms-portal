package cn.com.bsfit.frms.portal.util;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cn.com.bsfit.frms.portal.base.log.LogDBUtil;
import cn.com.bsfit.frms.portal.base.util.ExtJSResponse;
import cn.com.bsfit.frms.portal.exception.AuthException;

/**
 * 异常处理类
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
@ControllerAdvice(annotations = { RestController.class })
public class ExceptionUtil implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception) {
		LogDBUtil.error(this.getClass(), "exception:", exception);
		return Response.status(Status.BAD_REQUEST).entity(ExtJSResponse.errorRes(exception.getMessage())).type(MediaType.APPLICATION_JSON).build();
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody ExtJSResponse exception(HttpServletRequest request, Exception exception) {
		String url = request.getRequestURL().toString();
		LogDBUtil.error(this.getClass(), "the request is : " + url + " and exception is :", exception);
		if(exception instanceof AuthException) {
			return ExtJSResponse.errorRes("no permission");
		}
		return ExtJSResponse.errorRes("出错");
	}
}