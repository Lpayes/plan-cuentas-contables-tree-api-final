package edu.umg.programacion3.pfinal.treeengine.custom;

import edu.umg.programacion3.pfinal.treeengine.strategy.TreeAlgorithmStrategy;
import edu.umg.programacion3.pfinal.treeengine.memory.CustomMemoryTreeRepository;

public class CustomTreeStrategy implements TreeAlgorithmStrategy {
	// Uso el repositorio en memoria para guardar el arbol
	
	private final CustomMemoryTreeRepository repository =
            new CustomMemoryTreeRepository();

	@Override
	public void createRoot(String name) {
		repository.createRoot(name);
	}

	@Override
	public void addChild(Long parentId, String name) {
		repository.addChild(parentId, name);
	}

	@Override
	public void printTree() {
		printNode(repository.getTree(), 0);
	}
	
	// Recorre e imprime el arbol de forma recursiva
	private void printNode(
	            CustomTreeNode node,
	            int level) {

	        if (node == null) {
	            return;
	        }

	        for (int i = 0; i < level; i++) {
	            System.out.print("  ");
	        }

	        System.out.println(node.getName());

	        CustomTreeNode[] children = node.getChildren();

	        for (int i = 0; i < node.getChildCount(); i++) {

	            printNode(children[i], level + 1);
	        }
	    }
}
