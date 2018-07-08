package com.ruxuanwo.utils.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 树节点,需要拓展属性时，请采用继承的方式
 *
 * @author 如漩涡
 */
public class TreeNode {
    private String name;
    private String id;
    private String pId;
    private String parentspath;

    private Boolean checked = false;
    private List<TreeNode> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }


    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public void addChild(TreeNode child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getParentspath() {
        return parentspath;
    }

    public void setParentspath(String parentspath) {
        this.parentspath = parentspath;
    }
}
