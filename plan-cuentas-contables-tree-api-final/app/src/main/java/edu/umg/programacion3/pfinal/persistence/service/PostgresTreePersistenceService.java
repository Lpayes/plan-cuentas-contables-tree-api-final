package edu.umg.programacion3.pfinal.persistence.service;

import edu.umg.programacion3.pfinal.persistence.entity.NodeEntity;
import edu.umg.programacion3.pfinal.persistence.repository.NodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Service
@Profile("postgres")
public class PostgresTreePersistenceService {

    private final NodeRepository nodeRepository;

    public PostgresTreePersistenceService(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    public NodeEntity createRoot(String name) {
        NodeEntity root = new NodeEntity(name, null);
        return nodeRepository.save(root);
    }

    public NodeEntity addChild(Long parentId, String name) {
        nodeRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Nodo padre no encontrado: " + parentId));

        NodeEntity child = new NodeEntity(name, parentId);
        return nodeRepository.save(child);
    }

    public NodeEntity getRoot() {
        return nodeRepository.findByParentIdIsNull()
                .orElseThrow(() -> new RuntimeException("No existe nodo raiz"));
    }

    public List<NodeEntity> getChildren(Long parentId) {
        return nodeRepository.findByParentId(parentId);
    }

    public List<NodeEntity> getAllNodes() {
        return nodeRepository.findAll();
    }
}
