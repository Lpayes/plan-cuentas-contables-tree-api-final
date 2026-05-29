package edu.umg.programacion3.pfinal.api.controller;

import edu.umg.programacion3.pfinal.api.generated.TreeApi;
import edu.umg.programacion3.pfinal.api.generated.model.*;
import org.springframework.web.bind.annotation.RestController;
import edu.umg.programacion3.pfinal.service.TreeService;

@RestController
public class TreeController implements TreeApi {

	private final TreeService treeService;
	
	public TreeController(TreeService treeService) {
	    this.treeService = treeService;
	}

	@Override
	public NodeResponse nodesRootPost(NodeRequest nodeRequest) {
		return treeService.createRoot(nodeRequest.getName());
	}

	@Override
	public NodeResponse nodesParentIdChildrenPost(Long parentId, NodeRequest nodeRequest) {
		return treeService.addChild(parentId, nodeRequest.getName());
	}

	@Override
	public TreeNodeResponse treeGet() {
	    return treeService.getTree();
	}

	@Override
	public TreeNodeResponse treeNodeIdGet(Long nodeId) {
	    return treeService.getSubtree(nodeId);
	}

	@Override
	public NodeListResponse nodesNodeIdPathGet(Long nodeId) {
	    return treeService.getPath(nodeId);
	}

	@Override
	public NodeListResponse treeTraversalGet(String type) {
	    return treeService.traverse(type);
	}

	@Override
	public NumberResponse treeHeightGet() {
	    return treeService.getHeight();
	}

	@Override
	public NumberResponse nodesNodeIdDepthGet(Long nodeId) {
	    return treeService.getDepth(nodeId);
	}

	@Override
	public NodeListResponse nodesNodeIdAncestorsGet(Long nodeId) {
	    return treeService.getAncestors(nodeId);
	}

	@Override
	public ValidationResponse treeValidateGet() {
	    return treeService.validateNoCycles();
	}
    
}