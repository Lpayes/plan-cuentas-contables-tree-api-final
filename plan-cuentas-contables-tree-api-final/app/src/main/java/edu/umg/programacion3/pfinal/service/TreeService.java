package edu.umg.programacion3.pfinal.service;

import org.springframework.stereotype.Service;

import edu.umg.programacion3.pfinal.api.generated.model.NodeListResponse;
import edu.umg.programacion3.pfinal.api.generated.model.NodeResponse;
import edu.umg.programacion3.pfinal.api.generated.model.NumberResponse;
import edu.umg.programacion3.pfinal.api.generated.model.TreeNodeResponse;
import edu.umg.programacion3.pfinal.api.generated.model.ValidationResponse;
import edu.umg.programacion3.pfinal.persistence.service.TreePersistenceService;

@Service
public class TreeService {

    private final TreePersistenceService persistenceService;

    public TreeService(TreePersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    public NodeResponse createRoot(String name) {
        return persistenceService.createRoot(name);
    }

    public NodeResponse addChild(Long parentId, String name) {
        return persistenceService.addChild(parentId, name);
    }

    public TreeNodeResponse getTree() {
        return persistenceService.getTree();
    }

    public TreeNodeResponse getSubtree(Long nodeId) {
        return persistenceService.getSubtree(nodeId);
    }

    public NodeListResponse getPath(Long nodeId) {
        return persistenceService.getPath(nodeId);
    }

    public NodeListResponse traverse(String type) {
        return persistenceService.traverse(type);
    }

    public NumberResponse getHeight() {
        return persistenceService.getHeight();
    }

    public NumberResponse getDepth(Long nodeId) {
        return persistenceService.getDepth(nodeId);
    }

    public NodeListResponse getAncestors(Long nodeId) {
        return persistenceService.getAncestors(nodeId);
    }

    public ValidationResponse validateNoCycles() {
        return persistenceService.validateNoCycles();
    }
}