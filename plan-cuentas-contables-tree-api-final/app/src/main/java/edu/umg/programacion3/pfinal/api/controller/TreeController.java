package edu.umg.programacion3.pfinal.api.controller;

import edu.umg.programacion3.pfinal.api.generated.TreeApi;
import edu.umg.programacion3.pfinal.api.generated.model.*;
import edu.umg.programacion3.pfinal.treeengine.custom.CustomTreeNode;
import edu.umg.programacion3.pfinal.treeengine.strategy.TreeAlgorithmStrategy;
import org.springframework.web.bind.annotation.RestController;
import edu.umg.programacion3.pfinal.persistence.service.TreePersistenceService;

@RestController
public class TreeController implements TreeApi {

	private final TreeAlgorithmStrategy strategy;
	private final TreePersistenceService persistenceService;

	public TreeController(
	        TreeAlgorithmStrategy strategy,
	        TreePersistenceService persistenceService) {
	    this.strategy = strategy;
	    this.persistenceService = persistenceService;
	}

    @Override
    public NodeResponse nodesRootPost(NodeRequest nodeRequest) {
    	Object root = persistenceService.createRoot(nodeRequest.getName());
    	return toNodeResponse((CustomTreeNode) root);
    }

    @Override
    public NodeResponse nodesParentIdChildrenPost(Long parentId, NodeRequest nodeRequest) {
    	persistenceService.addChild(parentId, nodeRequest.getName());

    	return new NodeResponse()
    	        .name(nodeRequest.getName())
    	        .parentId(parentId);
    }

    @Override
    public TreeNodeResponse treeGet() {
    	return toResponse((CustomTreeNode) persistenceService.getTree());
    }

    @Override
    public TreeNodeResponse treeNodeIdGet(Long nodeId) {
        return toResponse((CustomTreeNode) strategy.getSubtree(nodeId));
    }

    @Override
    public NodeListResponse nodesNodeIdPathGet(Long nodeId) {
        return toNodeListResponse(strategy.getPath(nodeId));
    }

    @Override
    public NodeListResponse treeTraversalGet(String type) {
        if ("DFS".equalsIgnoreCase(type)) {
            return toNodeListResponse(strategy.traverseDFS());
        }

        if ("BFS".equalsIgnoreCase(type)) {
            return toNodeListResponse(strategy.traverseBFS());
        }

        return new NodeListResponse();
    }

    @Override
    public NumberResponse treeHeightGet() {
        return new NumberResponse()
                .value(strategy.getHeight());
    }

    @Override
    public NumberResponse nodesNodeIdDepthGet(Long nodeId) {
        return new NumberResponse()
                .value(strategy.getDepth(nodeId));
    }

    @Override
    public NodeListResponse nodesNodeIdAncestorsGet(Long nodeId) {
        return toNodeListResponse(strategy.getAncestors(nodeId));
    }

    @Override
    public ValidationResponse treeValidateGet() {
        boolean valid = strategy.validateNoCycles();

        return new ValidationResponse()
                .valid(valid)
                .message(valid ? "El árbol no contiene ciclos" : "El árbol contiene ciclos");
    }

    private NodeListResponse toNodeListResponse(Object[] nodes) {
        NodeListResponse response = new NodeListResponse();

        for (int i = 0; i < nodes.length; i++) {
            response.addNodesItem(toNodeResponse((CustomTreeNode) nodes[i]));
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

    private TreeNodeResponse toResponse(CustomTreeNode node) {
        if (node == null) {
            return null;
        }

        TreeNodeResponse response = new TreeNodeResponse()
                .id(node.getId())
                .name(node.getName());

        CustomTreeNode[] children = node.getChildren();

        for (int i = 0; i < node.getChildCount(); i++) {
            response.addChildrenItem(toResponse(children[i]));
        }

        return response;
    }
}