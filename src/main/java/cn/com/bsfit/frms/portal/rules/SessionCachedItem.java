package cn.com.bsfit.frms.portal.rules;

import java.util.Date;

public class SessionCachedItem {
	
	private Date cacheTime;
    private Object object;
    
	public SessionCachedItem() {
		super();
	}
	
	public SessionCachedItem(Date cacheTime, Object object) {
		super();
		this.cacheTime = cacheTime;
		this.object = object;
	}
	
	public Date getCacheTime() {
		return cacheTime;
	}
	public void setCacheTime(Date cacheTime) {
		this.cacheTime = cacheTime;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
}