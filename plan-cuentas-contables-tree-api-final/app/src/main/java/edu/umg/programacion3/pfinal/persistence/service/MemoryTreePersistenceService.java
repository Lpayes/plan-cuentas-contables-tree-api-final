package edu.umg.programacion3.pfinal.persistence.service;

import edu.umg.programacion3.pfinal.treeengine.custom.CustomTreeStrategy;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import edu.umg.programacion3.pfinal.api.generated.model.NodeListResponse;
import edu.umg.programacion3.pfinal.api.generated.model.NodeResponse;
import edu.umg.programacion3.pfinal.api.generated.model.NumberResponse;
import edu.umg.programacion3.pfinal.api.generated.model.TreeNodeResponse;
import edu.umg.programacion3.pfinal.api.generated.model.ValidationResponse;
import edu.umg.programacion3.pfinal.treeengine.custom.CustomTreeNode;

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
    public NodeResponse createRoot(String name) {
        customTreeStrategy.createRoot(name);
        return toNodeResponse((CustomTreeNode) customTreeStrategy.getTree());
    }
    
    @Override
    public NodeResponse addChild(Long parentId, String name) {
        customTreeStrategy.addChild(parentId, name);
        return toNodeResponse((CustomTreeNode) customTreeStrategy.getSubtree(parentId));
    }
    
    @Override
    public TreeNodeResponse getTree() {
        return toTreeNodeResponse((CustomTreeNode) customTreeStrategy.getTree());
    }
    
    @Override
    public TreeNodeResponse getSubtree(Long nodeId) {
        return toTreeNodeResponse((CustomTreeNode) customTreeStrategy.getSubtree(nodeId));
    }
    
    @Override
    public NodeListResponse getPath(Long nodeId) {
        return toNodeListResponse(customTreeStrategy.getPath(nodeId));
    }

    @Override
    public NodeListResponse traverse(String type) {
        if ("DFS".equalsIgnoreCase(type)) {
            return toNodeListResponse(customTreeStrategy.traverseDFS());
        }

        if ("BFS".equalsIgnoreCase(type)) {
            return toNodeListResponse(customTreeStrategy.traverseBFS());
        }

        return new NodeListResponse();
    }

    @Override
    public NumberResponse getHeight() {
        return new NumberResponse()
                .value(customTreeStrategy.getHeight());
    }

    @Override
    public NumberResponse getDepth(Long nodeId) {
        return new NumberResponse()
                .value(customTreeStrategy.getDepth(nodeId));
    }

    @Override
    public NodeListResponse getAncestors(Long nodeId) {
        return toNodeListResponse(customTreeStrategy.getAncestors(nodeId));
    }

    @Override
    public ValidationResponse validateNoCycles() {
        boolean valid = customTreeStrategy.validateNoCycles();

        return new ValidationResponse()
                .valid(valid)
                .message(valid ? "El árbol no contiene ciclos" : "El árbol contiene ciclos");
    }

    private NodeListResponse toNodeListResponse(Object[] nodes) {
        NodeListResponse response = new NodeListResponse();

        if (nodes == null) {
            return response;
        }

        for (Object node : nodes) {
            response.addNodesItem(toNodeResponse((CustomTreeNode) node));
        }

        return response;
    }

    private NodeResponse toNodeResponse(CustomTreeNode node) {
        if (node == null) {
            return null;
        }

        return new NodeResponse()
                .id(node.getId())
                .name(node.getName())
                .parentId(node.getParent() == null ? null : node.getParent().getId());
    }

    private TreeNodeResponse toTreeNodeResponse(CustomTreeNode node) {
        if (node == null) {
            return null;
        }

        TreeNodeResponse response = new TreeNodeResponse()
                .id(node.getId())
                .name(node.getName());

        CustomTreeNode[] children = node.getChildren();

        for (int i = 0; i < node.getChildCount(); i++) {
            response.addChildrenItem(toTreeNodeResponse(children[i]));
        }

        return response;
    }
    
}