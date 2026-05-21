package edu.umg.programacion3.pfinal.persistence.service;

import edu.umg.programacion3.pfinal.persistence.dto.PostgresTreeNodeDto;
import edu.umg.programacion3.pfinal.persistence.entity.NodeEntity;
import edu.umg.programacion3.pfinal.persistence.repository.NodeRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
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
     * Crea nodo raíz en PostgreSQL.
     */
    public NodeEntity createRoot(String name) {
        NodeEntity root =
                new NodeEntity(name, null);
        return nodeRepository.save(root);
    }
    /*
     * Agrega hijo usando parent_id.
     */
    public NodeEntity addChild(Long parentId, String name) {
        nodeRepository.findById(parentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Nodo padre no encontrado: " + parentId
                        ));
        NodeEntity child =
                new NodeEntity(name, parentId);
        return nodeRepository.save(child);
    }
    /*
     * Obtiene nodo raíz.
     */
    public NodeEntity getRoot() {
        return nodeRepository.findByParentIdIsNull()
                .orElseThrow(() ->
                        new RuntimeException(
                                "No existe nodo raíz"
                        ));
    }
    /*
     * Obtiene hijos directos.
     */
    public List<NodeEntity> getChildren(Long parentId) {
        return nodeRepository.findByParentId(parentId);
    }
    /*
     * Obtiene todos los nodos.
     */
    public List<NodeEntity> getAllNodes() {
        return nodeRepository.findAll();
    }
    /*
     * Busca nodo por id.
     */
    public NodeEntity getNodeById(Long id) {
        return nodeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Nodo no encontrado: " + id
                        ));
    }
    /*
     * Elimina todos los nodos.
     */
    public void deleteAllNodes() {
        nodeRepository.deleteAll();
    }
    /*
     * Reconstruye árbol jerárquico usando parent_id.
     *
     * Convierte datos planos SQL:
     *
     * id | name | parent_id
     *
     * en árbol real con children.
     */
    public PostgresTreeNodeDto rebuildTree() {
        List<NodeEntity> entities =
                nodeRepository.findAll();
        /*
         * Mapa auxiliar:
         *
         * id -> nodo DTO
         */
        Map<Long, PostgresTreeNodeDto> nodesById =
                new HashMap<>();
        PostgresTreeNodeDto root = null;
        /*
         * Primero crear todos los nodos DTO.
         */
        for (NodeEntity entity : entities) {
            PostgresTreeNodeDto dto =
                    new PostgresTreeNodeDto(
                            entity.getId(),
                            entity.getName(),
                            entity.getParentId()
                    );
            nodesById.put(entity.getId(), dto);
        }
        /*
         * Luego conectar hijos con padres.
         */
        for (NodeEntity entity : entities) {
            PostgresTreeNodeDto current =
                    nodesById.get(entity.getId());
            /*
             * Si no tiene parent_id -> es raíz.
             */
            if (entity.getParentId() == null) {
                root = current;

            } else {
                /*
                 * Buscar padre.
                 */
                PostgresTreeNodeDto parent =
                        nodesById.get(entity.getParentId());
                /*
                 * Agregar hijo al padre.
                 */
                if (parent != null) {
                    parent.getChildren()
                            .add(current);
                }
            }
        }

        return root;
    }
}