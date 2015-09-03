package cn.com.bsfit.frms.portal.bo;

import java.util.List;

import cn.com.bsfit.frms.portal.base.pojo.Resources;
import cn.com.bsfit.frms.portal.base.pojo.RolesResources;
import cn.com.bsfit.frms.portal.base.util.TreeMenuNode;
import cn.com.bsfit.frms.portal.base.util.TreeNode;
import cn.com.bsfit.frms.portal.util.Node;

/**
 * 树形菜单操作接口
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
public interface ResourcesBO {

	/**
	 * 根据树形菜单构造资源对象
	 * 
	 * @param treeMenuNode
	 * @return
	 */
	public abstract Resources buildResource(TreeMenuNode treeMenuNode);
	
	/**
	 * 根据资源列表递归构造树形菜单
	 * 
	 * @param resourceList
	 * @return
	 */
	public abstract List<TreeNode> buildTreeNodeList(List<Resources> resourcesList);

	/**
	 * 根据资源对象递归构造树形菜单
	 * 
	 * @param resources
	 * @return
	 */
	public abstract TreeNode buildTreeNode(Resources resources);

	/**
	 * 根据父级资源ID查找子节点
	 * 
	 * @param resourceId
	 * @return
	 */
	public abstract List<Resources> getTree(Integer resourceId);

	/**
	 * 根据父级资源ID递归构造树形菜单
	 * 
	 * @param resourceId
	 * @return
	 */
	public abstract List<TreeNode> getCheckedTree(Integer resourceId);

	/**
	 * 递归资源列表
	 * 
	 * @param resourcesList
	 * @return
	 */
	public abstract List<Resources> getResourceList(List<Resources> resourcesList);
	
	/**
	 * 递归资源
	 * 
	 * @param resources
	 * @return
	 */
	public abstract Resources getResources(Resources resources);
	
	/**
	 * 根据资源列表递归构造树形菜单
	 * 
	 * @param resourcesList
	 * @return
	 */
	public abstract List<TreeMenuNode> buildTreeMenuNodeList(List<Resources> resourcesList);

	/**
	 * 根据资源对象递归构造树形菜单
	 * 
	 * @param resources
	 * @return
	 */
	public abstract TreeMenuNode buildTreeMenuNode(Resources resources);
	
	/**
	 * 将资源列表转换为节点
	 * 
	 * @param resourcesList
	 * @return
	 */
	public abstract List<Node> buildMenuNode(List<Resources> resourcesList);
	
	/**
	 * 将集合建立成树结构
	 * 
	 * @param dirs
	 * @return
	 */
	public abstract List<Node> buildListToTree(List<Node> dirs);
	
	/**
	 * 找出集合中的根元素
	 * 
	 * @param allNodes
	 * @return
	 */
	public abstract List<Node> findRoots(List<Node> allNodes);

	/**
	 * 递归找子目录
	 * 
	 * @param root
	 * @param allNodes
	 * @return
	 */
	public abstract List<Node> findChildren(Node root, List<Node> allNodes);
	
	/**
	 * 递归构造角色树选中
	 * 
	 * @param rolesResourcesList
	 * @param treeNodeList
	 */
	public void roleResourcesChecked(List<RolesResources> rolesResourcesList, List<TreeNode> treeNodeList);
}