package edu.umg.programacion3.pfinal.treeengine.strategy;

public interface TreeAlgorithmStrategy {
	 void createRoot(String name);
	 void addChild(Long parentId, String name);
	 Object getTree();
	 void printTree();
	 
}
