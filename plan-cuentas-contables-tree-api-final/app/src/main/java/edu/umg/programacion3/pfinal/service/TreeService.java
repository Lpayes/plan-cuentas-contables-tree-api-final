package edu.umg.programacion3.pfinal.service;

import org.springframework.stereotype.Service;

import edu.umg.programacion3.pfinal.api.generated.model.NodeListResponse;
import edu.umg.programacion3.pfinal.api.generated.model.NodeResponse;
import edu.umg.programacion3.pfinal.api.generated.model.NumberResponse;
import edu.umg.programacion3.pfinal.api.generated.model.TreeNodeResponse;
import edu.umg.programacion3.pfinal.api.generated.model.ValidationResponse;

@Service
public class TreeService {

    private final StorageSelectorService storageSelectorService;

    public TreeService(StorageSelectorService storageSelectorService) {
        this.storageSelectorService = storageSelectorService;
    }

    public NodeResponse createRoot(String name) {
        return storageSelectorService.getActiveService().createRoot(name);
    }

    public NodeResponse addChild(Long parentId, String name) {
        return storageSelectorService.getActiveService().addChild(parentId, name);
    }

    public TreeNodeResponse getTree() {
        return storageSelectorService.getActiveService().getTree();
    }

    public TreeNodeResponse getSubtree(Long nodeId) {
        return storageSelectorService.getActiveService().getSubtree(nodeId);
    }

    public NodeListResponse getPath(Long nodeId) {
        return storageSelectorService.getActiveService().getPath(nodeId);
    }

    public NodeListResponse traverse(String type) {
        return storageSelectorService.getActiveService().traverse(type);
    }

    public NumberResponse getHeight() {
        return storageSelectorService.getActiveService().getHeight();
    }

    public NumberResponse getDepth(Long nodeId) {
        return storageSelectorService.getActiveService().getDepth(nodeId);
    }

    public NodeListResponse getAncestors(Long nodeId) {
        return storageSelectorService.getActiveService().getAncestors(nodeId);
    }

    public ValidationResponse validateNoCycles() {
        return storageSelectorService.getActiveService().validateNoCycles();
    }
}