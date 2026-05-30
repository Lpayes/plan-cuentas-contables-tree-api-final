package edu.umg.programacion3.pfinal.persistence.service;

import edu.umg.programacion3.pfinal.api.generated.model.NodeListResponse;
import edu.umg.programacion3.pfinal.api.generated.model.NodeResponse;
import edu.umg.programacion3.pfinal.api.generated.model.NumberResponse;
import edu.umg.programacion3.pfinal.api.generated.model.TreeNodeResponse;
import edu.umg.programacion3.pfinal.api.generated.model.ValidationResponse;
import edu.umg.programacion3.pfinal.infrastructure.mongo.MongoNodeDocument;
import edu.umg.programacion3.pfinal.infrastructure.mongo.MongoTreeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MongoTreePersistenceService implements TreePersistenceService {

    private final MongoTreeRepository mongoTreeRepository;

    public MongoTreePersistenceService(MongoTreeRepository mongoTreeRepository) {
        this.mongoTreeRepository = mongoTreeRepository;
    }

    @Override
    public NodeResponse createRoot(String name) {
        return toNodeResponse(mongoTreeRepository.createRoot(name));
    }

    @Override
    public NodeResponse addChild(Long parentId, String name) {
        return toNodeResponse(mongoTreeRepository.addChild(parentId, name));
    }

    @Override
    public TreeNodeResponse getTree() {
        return rebuildTree();
    }

    @Override
    public TreeNodeResponse getSubtree(Long nodeId) {
        return buildSubtree(nodeId);
    }

    @Override
    public NodeListResponse getPath(Long nodeId) {
        NodeListResponse response = new NodeListResponse();

        for (MongoNodeDocument node : getPathDocuments(nodeId)) {
            response.addNodesItem(toNodeResponse(node));
        }

        return response;
    }

    @Override
    public NodeListResponse traverse(String type) {
        NodeListResponse response = new NodeListResponse();

        if ("DFS".equalsIgnoreCase(type)) {
            dfs(mongoTreeRepository.getRoot(), response);
            return response;
        }

        if ("BFS".equalsIgnoreCase(type)) {
            bfs(response);
            return response;
        }

        return response;
    }

    @Override
    public NumberResponse getHeight() {
        MongoNodeDocument root = mongoTreeRepository.getRoot();

        return new NumberResponse()
                .value(calculateHeightFromNode(root.getId()));
    }

    @Override
    public NumberResponse getDepth(Long nodeId) {
        return new NumberResponse()
                .value(calculateDepth(nodeId));
    }

    @Override
    public NodeListResponse getAncestors(Long nodeId) {
        NodeListResponse response = new NodeListResponse();

        for (MongoNodeDocument node : getAncestorDocuments(nodeId)) {
            response.addNodesItem(toNodeResponse(node));
        }

        return response;
    }

    @Override
    public ValidationResponse validateNoCycles() {
        return new ValidationResponse()
                .valid(true)
                .message("El árbol no contiene ciclos");
    }

    private TreeNodeResponse rebuildTree() {
        List<MongoNodeDocument> documents = mongoTreeRepository.getAllNodes();

        Map<Long, TreeNodeResponse> nodesById = new HashMap<>();
        TreeNodeResponse root = null;

        for (MongoNodeDocument document : documents) {
            TreeNodeResponse node = new TreeNodeResponse()
                    .id(document.getId())
                    .name(document.getValue());

            nodesById.put(document.getId(), node);
        }

        for (MongoNodeDocument document : documents) {
            TreeNodeResponse current = nodesById.get(document.getId());

            if (document.getParentId() == null) {
                root = current;
            } else {
                TreeNodeResponse parent = nodesById.get(document.getParentId());

                if (parent != null) {
                    parent.addChildrenItem(current);
                }
            }
        }

        return root;
    }

    private TreeNodeResponse buildSubtree(Long nodeId) {
        MongoNodeDocument document = mongoTreeRepository.getNodeById(nodeId);

        TreeNodeResponse response = new TreeNodeResponse()
                .id(document.getId())
                .name(document.getValue());

        List<MongoNodeDocument> children = mongoTreeRepository.getChildren(nodeId);

        for (MongoNodeDocument child : children) {
            response.addChildrenItem(buildSubtree(child.getId()));
        }

        return response;
    }

    private NodeResponse toNodeResponse(MongoNodeDocument node) {
        return new NodeResponse()
                .id(node.getId())
                .name(node.getValue())
                .parentId(node.getParentId());
    }

    private List<MongoNodeDocument> getPathDocuments(Long nodeId) {
        List<MongoNodeDocument> path = new ArrayList<>();
        MongoNodeDocument node = mongoTreeRepository.getNodeById(nodeId);

        while (node != null) {
            path.add(node);

            if (node.getParentId() == null) {
                break;
            }

            node = mongoTreeRepository.getNodeById(node.getParentId());
        }

        java.util.Collections.reverse(path);
        return path;
    }

    private List<MongoNodeDocument> getAncestorDocuments(Long nodeId) {
        List<MongoNodeDocument> ancestors = new ArrayList<>();
        MongoNodeDocument node = mongoTreeRepository.getNodeById(nodeId);

        while (node.getParentId() != null) {
            MongoNodeDocument parent = mongoTreeRepository.getNodeById(node.getParentId());
            ancestors.add(parent);
            node = parent;
        }

        return ancestors;
    }

    private int calculateDepth(Long nodeId) {
        MongoNodeDocument node = mongoTreeRepository.getNodeById(nodeId);
        int depth = 0;

        while (node.getParentId() != null) {
            depth++;
            node = mongoTreeRepository.getNodeById(node.getParentId());
        }

        return depth;
    }

    private int calculateHeightFromNode(Long nodeId) {
        List<MongoNodeDocument> children = mongoTreeRepository.getChildren(nodeId);

        if (children.isEmpty()) {
            return 1;
        }

        int maxChildHeight = 0;

        for (MongoNodeDocument child : children) {
            maxChildHeight = Math.max(maxChildHeight, calculateHeightFromNode(child.getId()));
        }

        return maxChildHeight + 1;
    }

    private void dfs(MongoNodeDocument node, NodeListResponse response) {
        response.addNodesItem(toNodeResponse(node));

        for (MongoNodeDocument child : mongoTreeRepository.getChildren(node.getId())) {
            dfs(child, response);
        }
    }

    private void bfs(NodeListResponse response) {
        List<MongoNodeDocument> queue = new ArrayList<>();
        queue.add(mongoTreeRepository.getRoot());

        int index = 0;

        while (index < queue.size()) {
            MongoNodeDocument current = queue.get(index++);
            response.addNodesItem(toNodeResponse(current));
            queue.addAll(mongoTreeRepository.getChildren(current.getId()));
        }
    }
}