package edu.umg.programacion3.pfinal.persistence.service;

import edu.umg.programacion3.pfinal.persistence.dto.PostgresTreeNodeDto;
import edu.umg.programacion3.pfinal.persistence.entity.NodeEntity;
import edu.umg.programacion3.pfinal.persistence.repository.NodeRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Profile("postgres")
public class PostgresTreePersistenceService {

    private final NodeRepository nodeRepository;

    public PostgresTreePersistenceService(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    /*
     * Crea el nodo raíz en PostgreSQL.
     * La raíz no tiene padre, por eso parentId = null.
     */
    public NodeEntity createRoot(String name) {
        NodeEntity root = new NodeEntity(name, null);
        return nodeRepository.save(root);
    }

    /*
     * Agrega un hijo a un nodo padre existente.
     * Se usa parent_id para guardar la relación jerárquica.
     */
    public NodeEntity addChild(Long parentId, String name) {
        nodeRepository.findById(parentId)
                .orElseThrow(() ->
                        new RuntimeException("Nodo padre no encontrado: " + parentId));

        NodeEntity child = new NodeEntity(name, parentId);
        return nodeRepository.save(child);
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

    public int getDepth(Long nodeId) {
        NodeEntity node = getNodeById(nodeId);

        int depth = 0;

        while (node.getParentId() != null) {
            depth++;
            node = getNodeById(node.getParentId());
        }

        return depth;
    }

    public List<NodeEntity> getPath(Long nodeId) {
        List<NodeEntity> path = new ArrayList<>();

        NodeEntity node = getNodeById(nodeId);

        /*
         * Se empieza desde el nodo destino
         * y se sube usando parent_id hasta llegar a la raíz.
         */
        while (node != null) {
            path.add(node);

            if (node.getParentId() == null) {
                break;
            }

            node = getNodeById(node.getParentId());
        }

        /*
         * Como se construyó desde el nodo hacia la raíz,
         * se invierte para devolver raíz -> nodo.
         */
        Collections.reverse(path);

        return path;
    }

    /*
     * Obtiene los ancestros de un nodo.
     *
     * Ancestros = padres superiores del nodo.
     * No incluye al nodo actual.
     */
    public List<NodeEntity> getAncestors(Long nodeId) {
        List<NodeEntity> ancestors = new ArrayList<>();

        NodeEntity node = getNodeById(nodeId);

        /*
         * Mientras exista padre, se agrega a la lista
         * y se sigue subiendo hacia la raíz.
         */
        while (node.getParentId() != null) {
            NodeEntity parent = getNodeById(node.getParentId());
            ancestors.add(parent);
            node = parent;
        }

        return ancestors;
    }
}