package cn.com.bsfit.frms.portal.enums;

/**
 * 数据库表中ENABLED状态枚举
 * 
 * @author hjp
 * 
 * @since 1.3.0
 *
 */
public enum Status {
	
	DELETED((short) 0, "删除"),
    NORMAL((short) 1, "正常"),
    DISABLE((short) 2, "禁用");
	
	private Short index;
    private String text;
    
    private Status(Short index, String text) {
        this.index = index;
        this.text = text;
    }

	public Short getIndex() {
		return index;
	}

	public void setIndex(Short index) {
		this.index = index;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public static String getTextByIndex(Short index) {
		for(Status status : Status.values()) {
			if (status.getIndex().equals(index)) {
				return status.getText();
			}
		}
		return null;
	}
}
