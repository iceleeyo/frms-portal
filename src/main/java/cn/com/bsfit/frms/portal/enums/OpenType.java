package cn.com.bsfit.frms.portal.enums;

/**
 * 菜单打开方式枚举
 * 
 * @author hjp
 * 
 * @since 1.2.0
 *
 */
public enum OpenType {
	
	OLDWINOPEN((short) 0, "原窗口打开"),
	NEWWINOPEN((short) 1, "新窗口打开");
	
	private Short index;
	private String name;
	
	private OpenType(Short index, String name) {
		this.index = index;
		this.name = name;
	}

	public Short getIndex() {
		return index;
	}

	public void setIndex(Short index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static String getNameByIndex(Short index) {
		for(OpenType type : OpenType.values()) {
			if (type.getIndex().equals(index)) {
				return type.getName();
			}
		}
		return null;
	}
}
