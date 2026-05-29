package edu.umg.programacion3.pfinal.persistence.service;

import edu.umg.programacion3.pfinal.api.generated.model.NodeListResponse;
import edu.umg.programacion3.pfinal.api.generated.model.NodeResponse;
import edu.umg.programacion3.pfinal.api.generated.model.NumberResponse;
import edu.umg.programacion3.pfinal.api.generated.model.TreeNodeResponse;
import edu.umg.programacion3.pfinal.api.generated.model.ValidationResponse;


public interface TreePersistenceService {
	
    NodeResponse createRoot(String name);

    NodeResponse addChild(Long parentId, String name);

    TreeNodeResponse getTree();

    TreeNodeResponse getSubtree(Long nodeId);

    NodeListResponse getPath(Long nodeId);

    NodeListResponse traverse(String type);

    NumberResponse getHeight();

    NumberResponse getDepth(Long nodeId);

    NodeListResponse getAncestors(Long nodeId);

    ValidationResponse validateNoCycles();
	
}
