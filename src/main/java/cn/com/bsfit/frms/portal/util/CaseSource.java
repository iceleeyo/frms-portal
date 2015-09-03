package cn.com.bsfit.frms.portal.util;

/**
 * 案件来源
 * 主要为丹东银行服务
 * 
 * @author hjp
 * 
 * @since 1.2.0
 *
 */
public class CaseSource {
	
	private Integer code;
	private String name;
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "CaseSource [code=" + code + ", name=" + name + "]";
	}
}