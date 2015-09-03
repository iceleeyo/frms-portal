package cn.com.bsfit.frms.portal.util;

import java.io.Serializable;
import java.util.List;

/**
 * Portal登录树形结构构造
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
public class Node implements Serializable {
	
	private int id;
	private int parentId;
	private Node parent;
	private List<Node> children;
	private String text;
	private Integer level;
	private int sort;
	private int rootId;
	private String type;
	private boolean leaf;
	private boolean expanded;
	private String iconCls;
	private String qtitle;
	private String code;// 资源编号,用于判断权限
	private long orderField;// 排序字段
	private String description;
	private static final long serialVersionUID = -2721191232926604726L;
	
	public Node() {
		super();
	}
	public Node(int id, int parentId, String text) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.text = text;
	}
	public Node(int id, int parentId, String text, String type,
			String iconCls, String qtitle, String code) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.text = text;
		this.type = type;
		this.iconCls = iconCls;
		this.qtitle = qtitle;
		this.code = code;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public List<Node> getChildren() {
		return children;
	}
	public void setChildren(List<Node> children) {
		this.children = children;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getRootId() {
		return rootId;
	}
	public void setRootId(int rootId) {
		this.rootId = rootId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getQtitle() {
		return qtitle;
	}
	public void setQtitle(String qtitle) {
		this.qtitle = qtitle;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public long getOrderField() {
		return orderField;
	}
	public void setOrderField(long orderField) {
		this.orderField = orderField;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}