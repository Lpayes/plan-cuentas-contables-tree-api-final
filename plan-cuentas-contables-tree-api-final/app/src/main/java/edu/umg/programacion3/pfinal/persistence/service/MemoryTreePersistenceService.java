package edu.umg.programacion3.pfinal.persistence.service;

import edu.umg.programacion3.pfinal.treeengine.custom.CustomTreeStrategy;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(
        name = "app.storage",
        havingValue = "memory"
)
public class MemoryTreePersistenceService implements TreePersistenceService {

    private final CustomTreeStrategy customTreeStrategy;

    public MemoryTreePersistenceService(CustomTreeStrategy customTreeStrategy) {
        this.customTreeStrategy = customTreeStrategy;
    }

    @Override
    public Object createRoot(String name) {
        customTreeStrategy.createRoot(name);
        return customTreeStrategy.getTree();
    }

    @Override
    public Object addChild(Long parentId, String name) {
        customTreeStrategy.addChild(parentId, name);
        return customTreeStrategy.getTree();
    }

    @Override
    public Object getTree() {
        return customTreeStrategy.getTree();
    }
}