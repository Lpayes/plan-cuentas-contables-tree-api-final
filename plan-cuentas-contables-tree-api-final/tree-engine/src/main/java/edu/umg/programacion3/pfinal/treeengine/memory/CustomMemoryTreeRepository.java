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
    
    public CustomTreeNode getSubtree(Long nodeId) {
        return findById(root, nodeId);
    }

    public Object[] getPath(Long nodeId) {

        CustomTreeNode node = findById(root, nodeId);

        if (node == null) {
            return new Object[0];
        }

        CustomTreeNode[] temp = new CustomTreeNode[10];
        int count = 0;

        while (node != null) {

            if (count >= temp.length) {
                temp = expandArray(temp);
            }

            temp[count] = node;
            count++;
            node = node.getParent();
        }

        CustomTreeNode[] path = new CustomTreeNode[count];

        for (int i = 0; i < count; i++) {
            path[i] = temp[count - 1 - i];
        }

        return path;
    }

    public Object[] traverseDFS() {

        CustomTreeNode[] result = new CustomTreeNode[100];
        int[] index = new int[1];

        traverseDFSRecursive(root, result, index);

        CustomTreeNode[] finalResult = new CustomTreeNode[index[0]];

        for (int i = 0; i < index[0]; i++) {
            finalResult[i] = result[i];
        }

        return finalResult;
    }

    private void traverseDFSRecursive(
            CustomTreeNode node,
            CustomTreeNode[] result,
            int[] index) {

        if (node == null) {
            return;
        }

        result[index[0]] = node;
        index[0]++;

        CustomTreeNode[] children = node.getChildren();

        for (int i = 0; i < node.getChildCount(); i++) {
            traverseDFSRecursive(children[i], result, index);
        }
    }

    public Object[] traverseBFS() {

        if (root == null) {
            return new Object[0];
        }

        CustomTreeNode[] queue = new CustomTreeNode[100];
        CustomTreeNode[] result = new CustomTreeNode[100];

        int front = 0;
        int rear = 0;
        int count = 0;

        queue[rear] = root;
        rear++;

        while (front < rear) {

            CustomTreeNode current = queue[front];
            front++;

            result[count] = current;
            count++;

            CustomTreeNode[] children = current.getChildren();

            for (int i = 0; i < current.getChildCount(); i++) {
                queue[rear] = children[i];
                rear++;
            }
        }

        CustomTreeNode[] finalResult = new CustomTreeNode[count];

        for (int i = 0; i < count; i++) {
            finalResult[i] = result[i];
        }

        return finalResult;
    }

    public int getHeight() {
        return calculateHeight(root);
    }

    private int calculateHeight(CustomTreeNode node) {

        if (node == null) {
            return 0;
        }

        int maxHeight = 0;
        CustomTreeNode[] children = node.getChildren();

        for (int i = 0; i < node.getChildCount(); i++) {

            int childHeight = calculateHeight(children[i]);

            if (childHeight > maxHeight) {
                maxHeight = childHeight;
            }
        }

        return maxHeight + 1;
    }

    public int getDepth(Long nodeId) {

        CustomTreeNode node = findById(root, nodeId);

        if (node == null) {
            return -1;
        }

        int depth = 0;

        while (node.getParent() != null) {
            depth++;
            node = node.getParent();
        }

        return depth;
    }

    public Object[] getAncestors(Long nodeId) {

        CustomTreeNode node = findById(root, nodeId);

        if (node == null || node.getParent() == null) {
            return new Object[0];
        }

        CustomTreeNode[] temp = new CustomTreeNode[10];
        int count = 0;

        node = node.getParent();

        while (node != null) {

            if (count >= temp.length) {
                temp = expandArray(temp);
            }

            temp[count] = node;
            count++;
            node = node.getParent();
        }

        CustomTreeNode[] ancestors = new CustomTreeNode[count];

        for (int i = 0; i < count; i++) {
            ancestors[i] = temp[i];
        }

        return ancestors;
    }

    public boolean validateNoCycles() {
        return validateNoCyclesRecursive(root);
    }

    private boolean validateNoCyclesRecursive(CustomTreeNode node) {

        if (node == null) {
            return true;
        }

        CustomTreeNode[] children = node.getChildren();

        for (int i = 0; i < node.getChildCount(); i++) {

            if (children[i] == node) {
                return false;
            }

            if (!validateNoCyclesRecursive(children[i])) {
                return false;
            }
        }

        return true;
    }

    private CustomTreeNode[] expandArray(CustomTreeNode[] original) {

        CustomTreeNode[] newArray =
                new CustomTreeNode[original.length * 2];

        for (int i = 0; i < original.length; i++) {
            newArray[i] = original[i];
        }

        return newArray;
    }
}