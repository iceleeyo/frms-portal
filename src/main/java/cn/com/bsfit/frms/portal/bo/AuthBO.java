package cn.com.bsfit.frms.portal.bo;

/**
 * 
 * Standard interface for expression root objects used with expression-based @Auth.
 * 
 * @author hjp
 * 
 * @since 1.2.0
 *
 */
public interface AuthBO {
	
	/**
	 * Always grants access.
	 * 
	 * @return true
	 */
	public abstract Boolean permitAll();

	/**
	 * Always denies access
	 * 
	 * @return false
	 */
	public abstract Boolean denyAll();

	/**
	 * This is a synonym for {@link #hasAuthority(String)}.
	 * 
	 * @param resourcesCode the authority to test (i.e. "ROLE_USER")
	 * @return true if the authority is found, else false
	 */
	public abstract Boolean hasAuthority(String resourcesCode);

	/**
	 * This is a synonym for {@link #hasAllAuthority(String...)}.
	 * 
	 * @param resourcesCodes the authorities to test (i.e. "ROLE_USER", "ROLE_ADMIN")
	 * @return true if the authority is found, else false
	 */
	public abstract Boolean hasAllAuthority(String... resourcesCodes);

	/**
	 * This is a synonym for {@link #hasAnyAuthority(String...)}.
	 * 
	 * @param resourcesCode the authorities to test (i.e. "ROLE_USER", "ROLE_ADMIN")
	 * @return true if the authority is found, else false
	 */
	public abstract Boolean hasAnyAuthority(String... resourcesCodes);
	
	/**
	 * This is a synonym for {@link #hasAuthority(String)}.
	 * 
	 * @param resourcesCode resourcesCode the authority to test (i.e. "12&&13||14")
	 * @return true if the authority is found, else false
	 */
	public abstract Boolean hasAllOrAnyAuthority(String resourcesCode);
}
