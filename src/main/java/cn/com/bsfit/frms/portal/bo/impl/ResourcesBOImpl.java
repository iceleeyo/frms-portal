package cn.com.bsfit.frms.portal.bo.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.bsfit.frms.portal.base.mapper.ResourcesMapper;
import cn.com.bsfit.frms.portal.base.pojo.Resources;
import cn.com.bsfit.frms.portal.base.pojo.ResourcesExample;
import cn.com.bsfit.frms.portal.base.pojo.RolesResources;
import cn.com.bsfit.frms.portal.base.util.TreeMenuNode;
import cn.com.bsfit.frms.portal.base.util.TreeNode;
import cn.com.bsfit.frms.portal.bo.ResourcesBO;
import cn.com.bsfit.frms.portal.enums.Status;
import cn.com.bsfit.frms.portal.util.Node;

@Service
public class ResourcesBOImpl implements ResourcesBO {
	
	@Autowired
	private ResourcesMapper resourcesMapper;

	private ResourcesBOImpl() {
		
	}
	
	@Override
	public Resources buildResource(TreeMenuNode treeMenuNode) {
		if (treeMenuNode == null) {
			return null;
		}
		Resources resources = new Resources();
		resources.setId(treeMenuNode.getId());
		resources.setParentId(treeMenuNode.getParentId());
		resources.setResourceName(treeMenuNode.getText());
		resources.setResourceLevel(treeMenuNode.getLevel());
		resources.setResourceCode(treeMenuNode.getCode());
		resources.setResourceUrl(treeMenuNode.getQtitle());
		resources.setResourceIcon(treeMenuNode.getIconCls());
		resources.setResourceType(treeMenuNode.getType());
		resources.setOrderField(treeMenuNode.getOrderField());
		resources.setResourceDesc(String.valueOf(treeMenuNode.getOpenType()));
		return resources;
	}

	@Override
	public List<TreeNode> buildTreeNodeList(List<Resources> resourcesList) {
		if (resourcesList == null || resourcesList.isEmpty()) {
			return null;
		}
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>(resourcesList.size());
		for (Resources resources : resourcesList) {
			treeNodeList.add(buildTreeNode(resources));
		}
		return treeNodeList;
	}

	@Override
	public TreeNode buildTreeNode(Resources resources) {
		if (resources == null) {
			return null;
		}
		// 没有子节点
		if (resources.getChildrenList() == null) {
			return new TreeNode(resources.getId(), resources.getResourceName(),
					resources.getParentId(), resources.getResourceLevel(),
					true, false, false, resources.getResourceIcon(), null);
		}
		List<TreeNode> childrenList = buildTreeNodeList(resources.getChildrenList());
		return new TreeNode(resources.getId(), resources.getResourceName(),
				resources.getParentId(), resources.getResourceLevel(), false,
				true, false, resources.getResourceIcon(), childrenList);
	}

	@Override
	public List<Resources> getTree(Integer resourceId) {
		if (resourceId == null) {
			return null;
		}
		ResourcesExample resourcesExample = new ResourcesExample();
		resourcesExample.createCriteria().andEnabledEqualTo(Status.NORMAL.getIndex()).andParentIdEqualTo(resourceId);
		resourcesExample.setOrderByClause("ORDER_FIELD ASC");
		return resourcesMapper.selectByExample(resourcesExample);
	}

	@Override
	public List<TreeNode> getCheckedTree(Integer resourceId) {
		if (resourceId == null) {
			return null;
		}
		ResourcesExample resourcesExample = new ResourcesExample();
		resourcesExample.createCriteria().andEnabledEqualTo(Status.NORMAL.getIndex()).andParentIdEqualTo(resourceId);
		resourcesExample.setOrderByClause("ORDER_FIELD ASC");
		List<Resources> resourceList = resourcesMapper.selectByExample(resourcesExample);
		List<Resources> oldResourceList = getResourceList(resourceList);
		return buildTreeNodeList(oldResourceList);
	}

	@Override
	public List<Resources> getResourceList(List<Resources> resourcesList) {
		if (resourcesList == null || resourcesList.isEmpty()) {
			return null;
		}
		List<Resources> newResourceList = new ArrayList<Resources>(resourcesList.size());
		for (Resources resources : resourcesList) {
			newResourceList.add(getResources(resources));
		}
		return newResourceList;
	}

	@Override
	public Resources getResources(Resources resources) {
		ResourcesExample resourcesExample = new ResourcesExample();
		resourcesExample.createCriteria().andEnabledEqualTo(Status.NORMAL.getIndex()).andParentIdEqualTo(resources.getId());
		resourcesExample.setOrderByClause("ORDER_FIELD ASC");
		List<Resources> resourcesList = resourcesMapper.selectByExample(resourcesExample);
		if (resourcesList == null || resourcesList.isEmpty()) {
			return new Resources(resources.getId(), resources.getEnabled(),
					resources.getResourceName(), resources.getResourceLevel(),
					resources.getResourceType(), resources.getResourceDesc(),
					resources.getResourceCode(), resources.getResourceUrl(),
					resources.getResourceIcon(), resources.getOrderField(), null);
		}
		List<Resources> children = getResourceList(resourcesList);
		return new Resources(resources.getId(), resources.getEnabled(),
				resources.getResourceName(), resources.getResourceLevel(),
				resources.getResourceType(), resources.getResourceDesc(),
				resources.getResourceCode(), resources.getResourceUrl(),
				resources.getResourceIcon(), resources.getOrderField(), children);
	}
	
	@Override
	public List<TreeMenuNode> buildTreeMenuNodeList(List<Resources> resourcesList) {
		if (resourcesList == null || resourcesList.isEmpty()) {
			return null;
		}
		List<TreeMenuNode> treeMenuNodeList = new ArrayList<TreeMenuNode>(resourcesList.size());
		for (Resources resources : resourcesList) {
			treeMenuNodeList.add(buildTreeMenuNode(resources));
		}
		return treeMenuNodeList;
	}

	@Override
	public TreeMenuNode buildTreeMenuNode(Resources resources) {
		if (resources == null) {
			return null;
		}
		if (resources.getChildrenList() == null) {
			return new TreeMenuNode(resources.getId(), resources.getResourceName(), 
					resources.getParentId(), resources.getResourceLevel(), true, false,
					resources.getResourceIcon(), resources.getResourceUrl(),
					resources.getResourceCode(), resources.getResourceType(), resources.getOrderField(), 
					((resources.getResourceDesc() == null || "".equals(resources.getResourceDesc())) ? (short) 0 : (short) 1));
		}
		List<TreeMenuNode> children = buildTreeMenuNodeList(resources.getChildrenList());
		return new TreeMenuNode(resources.getId(), resources.getResourceName(),
				resources.getParentId(), resources.getResourceLevel(), false,
				true, resources.getResourceIcon(), resources.getResourceUrl(),
				resources.getResourceCode(), resources.getResourceType(), resources.getOrderField(),
				((resources.getResourceDesc() == null || "".equals(resources.getResourceDesc())) ? (short) 0 : (short) 1),
				children);
	}

	@Override
	public List<Node> buildMenuNode(List<Resources> resourcesList) {
		List<Node> nodeList = new ArrayList<Node>(resourcesList.size());
		if(resourcesList == null || resourcesList.isEmpty()) {
			return null;
		}
		for (Resources resources : resourcesList) {
			Node node = new Node(resources.getId(), resources.getParentId(), 
					resources.getResourceName(), resources.getResourceType().toString(), 
					resources.getResourceIcon(), resources.getResourceUrl(), resources.getResourceCode());
			nodeList.add(node);
		}
		return nodeList;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Node> buildListToTree(List<Node> dirs) {
		List<Node> roots = findRoots(dirs);
		List<Node> notRoots = (List<Node>) CollectionUtils.subtract(dirs, roots);
		for (Node root : roots) {
			root.setChildren(findChildren(root, notRoots));
		}
		return roots;
	}

	@Override
	public List<Node> findRoots(List<Node> allNodes) {
		List<Node> results = new ArrayList<Node>();
		for (Node node : allNodes) {
			 boolean isRoot = true;
			 for (Node comparedOne : allNodes) {
				if (node.getParentId() == comparedOne.getId()) {
					isRoot = false;
					break;
				}
			}
			if (isRoot) {
				node.setLevel(0);
				results.add(node);
				node.setRootId(node.getId());
			}
		}
		return results;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Node> findChildren(Node root, List<Node> allNodes) {
		List<Node> children = new ArrayList<Node>();
		for (Node comparedOne : allNodes) {
			if (comparedOne.getParentId() == root.getId()) {
				comparedOne.setParent(root);
				comparedOne.setLevel(root.getLevel() + 1);
				children.add(comparedOne);
			}
		}
		List<Node> notChildren = (List<Node>) CollectionUtils.subtract(allNodes, children);
		for (Node child : children) {
			List<Node> tmpChildren = findChildren(child, notChildren);
			if (tmpChildren == null || tmpChildren.size() < 1) {
				child.setLeaf(true);
				child.setExpanded(false);
			} else {
				child.setLeaf(false);
				child.setExpanded(true);
			}
			child.setChildren(tmpChildren);
		}
		return children;
	}

	@Override
	public void roleResourcesChecked(List<RolesResources> rolesResourcesList, List<TreeNode> treeNodeList) {
		if (treeNodeList == null || treeNodeList.size() == 0) {
			return;
		}
		for (TreeNode treeNode : treeNodeList) {
			for (RolesResources rolesResources : rolesResourcesList) {
				if (treeNode.getId().equals(rolesResources.getResourcesId())) {
					treeNode.setChecked(true);
				}
			}
			roleResourcesChecked(rolesResourcesList, treeNode.getChildren());
		}
	}
}