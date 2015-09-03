package cn.com.bsfit.frms.portal.pojo;

import java.io.Serializable;

/**
 * 用户的角色POJO
 * 
 * @author hjp
 * 
 * @since 1.3.0
 *
 */
public class UserRoles implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer rolesId;// 角色ID
	private String rolesName;// 角色名称
	private Boolean checked;// 是否选中true选中
	
	public Integer getRolesId() {
		return rolesId;
	}
	public void setRolesId(Integer rolesId) {
		this.rolesId = rolesId;
	}
	public String getRolesName() {
		return rolesName;
	}
	public void setRolesName(String rolesName) {
		this.rolesName = rolesName;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checked == null) ? 0 : checked.hashCode());
		result = prime * result + ((rolesId == null) ? 0 : rolesId.hashCode());
		result = prime * result + ((rolesName == null) ? 0 : rolesName.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRoles other = (UserRoles) obj;
		if (checked == null) {
			if (other.checked != null)
				return false;
		} else if (!checked.equals(other.checked))
			return false;
		if (rolesId == null) {
			if (other.rolesId != null)
				return false;
		} else if (!rolesId.equals(other.rolesId))
			return false;
		if (rolesName == null) {
			if (other.rolesName != null)
				return false;
		} else if (!rolesName.equals(other.rolesName))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "UserRoles [rolesId=" + rolesId + ", rolesName=" + rolesName + ", checked=" + checked + "]";
	}
	
}
