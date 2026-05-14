package edu.umg.programacion3.pfinal.treeengine.custom;

public class CustomTreeNode {
	   private Long id;
	    private String name;
	    private CustomTreeNode parent;
	    private CustomTreeNode[] children;
	    private int childCount;
	    
	    public CustomTreeNode() {
	        this.children = new CustomTreeNode[10];
	        this.childCount = 0;
	    }
	    
	    public void addChild(CustomTreeNode child) {
	        if (childCount == children.length) {
	            CustomTreeNode[] newChildren = new CustomTreeNode[children.length * 2];

	            for (int i = 0; i < children.length; i++) {
	                newChildren[i] = children[i];
	            }

	            children = newChildren;
	        }

	        children[childCount] = child;
	        childCount++;
	    }

		public Long getId() {
			return id;
		}
		public CustomTreeNode[] getChildren() {
			return children;
		}

		public void setChildren(CustomTreeNode[] children) {
			this.children = children;
		}

		public int getChildCount() {
			return childCount;
		}

		public void setChildCount(int childCount) {
			this.childCount = childCount;
		}

		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public CustomTreeNode getParent() {
			return parent;
		}
		public void setParent(CustomTreeNode parent) {
			this.parent = parent;
		}

}
