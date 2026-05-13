package edu.umg.programacion3.pfinal.treeengine.custom;

public class CustomTreeNode {
	   private Long id;
	    private String name;
	    private CustomTreeNode parent;
	    
		public Long getId() {
			return id;
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
