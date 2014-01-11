/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wuspba.ctams.ui.client;

import com.smartgwt.client.widgets.tree.TreeNode;

public class ExplorerTreeNode extends TreeNode {

    public ExplorerTreeNode(String name, String tabName, String nodeID, String parentNodeID, 
            String icon, PanelFactory factory, boolean enabled, boolean topLevel) {
        init(name, tabName, nodeID, parentNodeID, icon, factory, enabled, topLevel);
    }

    private void init(String name, String tabName, String nodeID, String parentNodeID, 
            String icon, PanelFactory factory, boolean enabled, boolean topLevel) {
        if (enabled) {
            if(topLevel) {
                setName("<span style='font-weight: bold;'>" + name + "</span>");
            } else {
                setName(name);
            }
        } else {
            if(topLevel) {
                setName("<span style='font-weight: bold; color:808080'>" + name + "</span>");
            } else {
                setName("<span style='color:808080'>" + name + "</span>");
            }
        }
        setTabName(tabName);
        setNodeID(nodeID.replace("-", "_"));
        setThumbnail("thumbnails/" + nodeID.replace("-", "_") + ".gif");
        setParentNodeID(parentNodeID.replace("-", "_"));
        setIcon(icon);

        setFactory(factory);

        setIsOpen(true);
    }
    
    public void setSampleClassName(String name) {
        setAttribute("sampleClassName",name);
    }
    public String getSampleClassName() {
        return getAttribute("sampleClassName");
    }

    public void setNodeID(String value) {
        setAttribute("nodeID", value);
    }

    public String getNodeID() {
        return getAttribute("nodeID");
    }

    public void setParentNodeID(String value) {
        setAttribute("parentNodeID", value);
    }

    public void setTabName(String name) {
        setAttribute("tabName", name);
    }

    public String getTabName() {
        return getAttributeAsString("tabName");
    }

    @Override
    public void setName(String name) {
        setAttribute("nodeTitle", name);
    }

    @Override
    public String getName() {
        return getAttributeAsString("nodeTitle");
    }

    @Override
    public void setIcon(String icon) {
        setAttribute("icon", icon);
    }

    @Override
    public String getIcon() {
        return getAttributeAsString("icon");
    }

    public void setThumbnail(String thumbnail) {
        setAttribute("thumbnail", thumbnail);
    }

    public String getThumbnail() {
        return getAttributeAsString("thumbnail");
    }

    public void setIsOpen(boolean isOpen) {
        setAttribute("isOpen", isOpen);
    }

    public void setIconSrc(String iconSrc) {
        setAttribute("iconSrc", iconSrc);
    }

    public String getIconSrc() {
        return getAttributeAsString("iconSrc");
    }

    public void setFactory(PanelFactory factory) {
        setAttribute("factory", factory);
    }
    
    public PanelFactory getFactory() {
        return (PanelFactory) getAttributeAsObject("factory");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExplorerTreeNode that = (ExplorerTreeNode) o;

        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
