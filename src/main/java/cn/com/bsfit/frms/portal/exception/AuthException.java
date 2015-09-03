package cn.com.bsfit.frms.portal.exception;

/**
 * 用户权限错误时抛出此异常
 * 
 * @author hjp
 * 
 * @since 1.2.0
 *
 */
public class AuthException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	// ~ Constructors
	// ===================================================================================================

	/**
	 * Constructs an <code>AccessDeniedException</code> with the specified
	 * message.
	 *
	 * @param msg
	 *            the detail message
	 */
	public AuthException(String msg) {
		super(msg);
	}

	/**
	 * Constructs an <code>AccessDeniedException</code> with the specified
	 * message and root cause.
	 *
	 * @param msg
	 *            the detail message
	 * @param t
	 *            root cause
	 */
	public AuthException(String msg, Throwable t) {
		super(msg, t);
	}
}
