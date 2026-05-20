package edu.umg.programacion3.pfinal.treeengine.collections;

import edu.umg.programacion3.pfinal.treeengine.strategy.TreeAlgorithmStrategy;

public class CollectionsTreeStrategy implements TreeAlgorithmStrategy {

    @Override
    public void createRoot(String name) {
        // Pendiente de implementar con collections Java
    }

    @Override
    public void addChild(Long parentId, String name) {
        // Pendiente de implementar con collections Java
    }
    
    @Override
    public Object getTree() {
        return null;
    }

    @Override
    public Object getSubtree(Long nodeId) {
        return null;
    }

    @Override
    public Object[] getPath(Long nodeId) {
        return new Object[0];
    }

    @Override
    public Object[] traverseDFS() {
        return new Object[0];
    }

    @Override
    public Object[] traverseBFS() {
        return new Object[0];
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getDepth(Long nodeId) {
        return -1;
    }

    @Override
    public Object[] getAncestors(Long nodeId) {
        return new Object[0];
    }

    @Override
    public boolean validateNoCycles() {
        return true;
    }
}
