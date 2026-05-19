package edu.umg.programacion3.pfinal.treeengine.strategy;

public interface TreeAlgorithmStrategy {
	 void createRoot(String name);
	 void addChild(Long parentId, String name);
	 Object getTree();
	 
	 Object getSubtree(Long nodeId);

	 Object[] getPath(Long nodeId);

	 Object[] traverseDFS();

	 Object[] traverseBFS();

	 int getHeight();

	 int getDepth(Long nodeId);

	 Object[] getAncestors(Long nodeId);

	 boolean validateNoCycles();
	 
}
