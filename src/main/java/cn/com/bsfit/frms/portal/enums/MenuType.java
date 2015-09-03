package cn.com.bsfit.frms.portal.enums;

/**
 * 菜单类型
 * 
 * @author hjp
 * 
 * @since 1.3.0
 *
 */
public enum MenuType {
	
	NOTMENU(0, "非菜单"), ISMENU(1, "是菜单");
	private Integer index;
	private String name;
	
	private MenuType(Integer index, String name) {
		this.index = index;
		this.name = name;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static String getNameByIndex(Short index) {
		for(MenuType type : MenuType.values()) {
			if (type.getIndex().equals(index)) {
				return type.getName();
			}
		}
		return null;
	}
}
