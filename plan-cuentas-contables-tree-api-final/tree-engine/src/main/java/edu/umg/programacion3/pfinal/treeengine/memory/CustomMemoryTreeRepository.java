package edu.umg.programacion3.pfinal.treeengine.memory;

import edu.umg.programacion3.pfinal.treeengine.custom.CustomTreeNode;

public class CustomMemoryTreeRepository {

    // Guarda la raíz principal del arbol
    private CustomTreeNode root;

    // 	Esto es para simular ids automáticos
    private Long nextId = 1L;

    public CustomTreeNode createRoot(String name) {

        CustomTreeNode node = new CustomTreeNode();

        node.setId(nextId);
        node.setName(name);
        node.setParent(null);

        root = node;
        nextId++;

        return root;
    }

    public CustomTreeNode addChild(Long parentId, String name) {

        CustomTreeNode parent = findById(root, parentId);

        if (parent == null) {
            return null;
        }

        CustomTreeNode child = new CustomTreeNode();

        child.setId(nextId);
        child.setName(name);
        child.setParent(parent);

        // Aqui se agrega el hijo al nodo padre
        parent.addChild(child);

        nextId++;

        return child;
    }

    public CustomTreeNode getTree() {
        return root;
    }

    // Busqueda recursiva para encontrar nodos por id
    private CustomTreeNode findById(CustomTreeNode current, Long id) {

        if (current == null) {
            return null;
        }

        if (current.getId().equals(id)) {
            return current;
        }

        CustomTreeNode[] children = current.getChildren();

        for (int i = 0; i < current.getChildCount(); i++) {

            CustomTreeNode found = findById(children[i], id);

            if (found != null) {
                return found;
            }
        }

        return null;
    }
}