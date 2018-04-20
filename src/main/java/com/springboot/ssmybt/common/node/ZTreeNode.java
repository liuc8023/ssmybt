
/**
* 文件名：ZTreeNode.java
*
* 版本信息：
* 日期：2018年2月7日
* Copyright 足下 Corporation 2018
* 版权所有
*
*/

package com.springboot.ssmybt.common.node;

/**
*
* 项目名称：ssmybt
* 类名称：ZTreeNode
* 类描述：jquery ztree 插件的节点
* 创建人：liuc
* 创建时间：2018年2月7日 下午3:55:07
* 修改人：liuc
* 修改时间：2018年2月7日 下午3:55:07
* 修改备注：
* @version
*
*/
public class ZTreeNode {
	private Long id; // 节点id

	private Long pId;// 父节点id

	private String name;// 节点名称

	private Boolean open;// 是否打开节点

	private Boolean checked;// 是否被选中

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Boolean getIsOpen() {
		return open;
	}

	public void setIsOpen(Boolean open) {
		this.open = open;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public static ZTreeNode createParent() {
		ZTreeNode zTreeNode = new ZTreeNode();
		zTreeNode.setChecked(true);
		zTreeNode.setId(0L);
		zTreeNode.setName("顶级");
		zTreeNode.setOpen(true);
		zTreeNode.setpId(0L);
		return zTreeNode;
	}
}
