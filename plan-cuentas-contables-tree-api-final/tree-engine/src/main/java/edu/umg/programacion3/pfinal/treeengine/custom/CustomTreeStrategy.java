package edu.umg.programacion3.pfinal.treeengine.custom;

import edu.umg.programacion3.pfinal.treeengine.memory.CustomMemoryTreeRepository;
import edu.umg.programacion3.pfinal.treeengine.strategy.TreeAlgorithmStrategy;
import org.springframework.stereotype.Service;

@Service
public class CustomTreeStrategy implements TreeAlgorithmStrategy {

    // Uso el repositorio en memoria para guardar el árbol
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
    public Object getTree() {
        return repository.getTree();
    }
    
    @Override
    public Object getSubtree(Long nodeId) {
        return repository.getSubtree(nodeId);
    }

    @Override
    public Object[] getPath(Long nodeId) {
        return repository.getPath(nodeId);
    }

    @Override
    public Object[] traverseDFS() {
        return repository.traverseDFS();
    }

    @Override
    public Object[] traverseBFS() {
        return repository.traverseBFS();
    }

    @Override
    public int getHeight() {
        return repository.getHeight();
    }

    @Override
    public int getDepth(Long nodeId) {
        return repository.getDepth(nodeId);
    }

    @Override
    public Object[] getAncestors(Long nodeId) {
        return repository.getAncestors(nodeId);
    }

    @Override
    public boolean validateNoCycles() {
        return repository.validateNoCycles();
    }

  
}