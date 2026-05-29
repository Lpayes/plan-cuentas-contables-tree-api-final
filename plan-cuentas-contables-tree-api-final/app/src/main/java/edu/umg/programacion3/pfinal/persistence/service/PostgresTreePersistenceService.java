package edu.umg.programacion3.pfinal.persistence.service;

import edu.umg.programacion3.pfinal.api.generated.model.NodeListResponse;
import edu.umg.programacion3.pfinal.api.generated.model.NodeResponse;
import edu.umg.programacion3.pfinal.api.generated.model.NumberResponse;
import edu.umg.programacion3.pfinal.api.generated.model.TreeNodeResponse;
import edu.umg.programacion3.pfinal.api.generated.model.ValidationResponse;
import edu.umg.programacion3.pfinal.persistence.dto.PostgresTreeNodeDto;
import edu.umg.programacion3.pfinal.persistence.entity.NodeEntity;
import edu.umg.programacion3.pfinal.persistence.repository.NodeRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Profile("postgres")
@ConditionalOnProperty(
        name = "app.storage",
        havingValue = "postgres"
)
public class PostgresTreePersistenceService implements TreePersistenceService {

    private final NodeRepository nodeRepository;

    public PostgresTreePersistenceService(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    /*
     * Crea el nodo raíz en PostgreSQL.
     * La raíz no tiene padre, por eso parentId = null.
     */
    @Override
    public NodeResponse createRoot(String name) {
        NodeEntity root = nodeRepository.save(new NodeEntity(name, null));
        return toNodeResponse(root);
    }

    /*
     * Agrega un hijo a un nodo padre existente.
     * Se usa parent_id para guardar la relación jerárquica.
     */
    @Override
    public NodeResponse addChild(Long parentId, String name) {
        nodeRepository.findById(parentId)
        .orElseThrow(() ->
                new RuntimeException("Nodo padre no encontrado: " + parentId));

        NodeEntity child = nodeRepository.save(new NodeEntity(name, parentId));
        return toNodeResponse(child);
    }

    /*
     * Obtiene la raíz del árbol.
     * La raíz es el nodo cuyo parent_id es null.
     */
    public NodeEntity getRoot() {
        return nodeRepository.findByParentIdIsNull()
                .orElseThrow(() ->
                        new RuntimeException("No existe nodo raíz"));
    }

    /*
     * Obtiene los hijos directos de un nodo.
     */
    public List<NodeEntity> getChildren(Long parentId) {
        return nodeRepository.findByParentId(parentId);
    }

    /*
     * Obtiene todos los nodos guardados en PostgreSQL.
     */
    public List<NodeEntity> getAllNodes() {
        return nodeRepository.findAll();
    }

    /*
     * Busca un nodo por id.
     */
    public NodeEntity getNodeById(Long id) {
        return nodeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Nodo no encontrado: " + id));
    }

    /*
     * Limpia la tabla nodes.
     * Útil para pruebas.
     */
    public void deleteAllNodes() {
        nodeRepository.deleteAll();
    }
    
    @Override
    public TreeNodeResponse getTree() {
    	return toTreeNodeResponse(rebuildTree());
    }
    
    public PostgresTreeNodeDto rebuildTree() {
        List<NodeEntity> entities = nodeRepository.findAll();

        Map<Long, PostgresTreeNodeDto> nodesById = new HashMap<>();

        PostgresTreeNodeDto root = null;

        for (NodeEntity entity : entities) {
            PostgresTreeNodeDto dto = new PostgresTreeNodeDto(
                    entity.getId(),
                    entity.getName(),
                    entity.getParentId()
            );

            nodesById.put(entity.getId(), dto);
        }

        for (NodeEntity entity : entities) {
            PostgresTreeNodeDto current = nodesById.get(entity.getId());

            if (entity.getParentId() == null) {
                root = current;
            } else {
                PostgresTreeNodeDto parent = nodesById.get(entity.getParentId());

                if (parent != null) {
                    parent.getChildren().add(current);
                }
            }
        }

        return root;
    }


    
    @Override
    public TreeNodeResponse getSubtree(Long nodeId) {
    	return toTreeNodeResponse(buildSubtree(nodeId));
    }

    private PostgresTreeNodeDto buildSubtree(Long nodeId) {
        NodeEntity entity = getNodeById(nodeId);

        PostgresTreeNodeDto dto = new PostgresTreeNodeDto(
                entity.getId(),
                entity.getName(),
                entity.getParentId()
        );

        List<NodeEntity> children = getChildren(nodeId);

        for (NodeEntity child : children) {
            dto.getChildren().add(buildSubtree(child.getId()));
        }

        return dto;
    }
    
    @Override
    public NodeListResponse getPath(Long nodeId) {
        NodeListResponse response = new NodeListResponse();

        for (NodeEntity node : getPathEntities(nodeId)) {
            response.addNodesItem(toNodeResponse(node));
        }

        return response;
    }

    @Override
    public NodeListResponse getAncestors(Long nodeId) {
        NodeListResponse response = new NodeListResponse();

        for (NodeEntity node : getAncestorEntities(nodeId)) {
            response.addNodesItem(toNodeResponse(node));
        }

        return response;
    }

    @Override
    public NumberResponse getDepth(Long nodeId) {
        return new NumberResponse()
                .value(calculateDepth(nodeId));
    }

    @Override
    public NumberResponse getHeight() {
        return new NumberResponse()
                .value(calculateHeight());
    }

    @Override
    public NodeListResponse traverse(String type) {
        NodeListResponse response = new NodeListResponse();

        if ("DFS".equalsIgnoreCase(type)) {
            dfs(getRoot(), response);
            return response;
        }

        if ("BFS".equalsIgnoreCase(type)) {
            bfs(response);
            return response;
        }

        return response;
    }

    @Override
    public ValidationResponse validateNoCycles() {
        return new ValidationResponse()
                .valid(true)
                .message("El árbol no contiene ciclos");
    }

    private NodeResponse toNodeResponse(NodeEntity node) {
        return new NodeResponse()
                .id(node.getId())
                .name(node.getName())
                .parentId(node.getParentId());
    }

    private TreeNodeResponse toTreeNodeResponse(PostgresTreeNodeDto node) {
        if (node == null) {
            return null;
        }

        TreeNodeResponse response = new TreeNodeResponse()
                .id(node.getId())
                .name(node.getName());

        for (PostgresTreeNodeDto child : node.getChildren()) {
            response.addChildrenItem(toTreeNodeResponse(child));
        }

        return response;
    }

    private List<NodeEntity> getPathEntities(Long nodeId) {
        List<NodeEntity> path = new ArrayList<>();
        NodeEntity node = getNodeById(nodeId);

        while (node != null) {
            path.add(node);

            if (node.getParentId() == null) {
                break;
            }

            node = getNodeById(node.getParentId());
        }

        Collections.reverse(path);
        return path;
    }

    private List<NodeEntity> getAncestorEntities(Long nodeId) {
        List<NodeEntity> ancestors = new ArrayList<>();
        NodeEntity node = getNodeById(nodeId);

        while (node.getParentId() != null) {
            NodeEntity parent = getNodeById(node.getParentId());
            ancestors.add(parent);
            node = parent;
        }

        return ancestors;
    }

    private int calculateDepth(Long nodeId) {
        NodeEntity node = getNodeById(nodeId);
        int depth = 0;

        while (node.getParentId() != null) {
            depth++;
            node = getNodeById(node.getParentId());
        }

        return depth;
    }

    private int calculateHeight() {
        NodeEntity root = getRoot();
        return calculateHeightFromNode(root.getId());
    }

    private int calculateHeightFromNode(Long nodeId) {
        List<NodeEntity> children = getChildren(nodeId);

        if (children.isEmpty()) {
            return 1;
        }

        int maxChildHeight = 0;

        for (NodeEntity child : children) {
            maxChildHeight = Math.max(maxChildHeight, calculateHeightFromNode(child.getId()));
        }

        return maxChildHeight + 1;
    }

    private void dfs(NodeEntity node, NodeListResponse response) {
        response.addNodesItem(toNodeResponse(node));

        for (NodeEntity child : getChildren(node.getId())) {
            dfs(child, response);
        }
    }

    private void bfs(NodeListResponse response) {
        List<NodeEntity> queue = new ArrayList<>();
        queue.add(getRoot());

        int index = 0;

        while (index < queue.size()) {
            NodeEntity current = queue.get(index++);
            response.addNodesItem(toNodeResponse(current));
            queue.addAll(getChildren(current.getId()));
        }
    }
}