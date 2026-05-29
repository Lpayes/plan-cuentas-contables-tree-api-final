package edu.umg.programacion3.pfinal.infrastructure.mongo;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(
        name = "app.storage",
        havingValue = "mongo"
)
public class MongoTreeRepository {

    private final MongoNodeSpringRepository mongoRepository;

    public MongoTreeRepository(MongoNodeSpringRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    public MongoNodeDocument createRoot(String value) {
        Long nextId = getNextId();

        MongoNodeDocument root = new MongoNodeDocument(nextId, value, null);
        return mongoRepository.save(root);
    }
    

    public MongoNodeDocument addChild(Long parentId, String value) {
        mongoRepository.findById(parentId)
                .orElseThrow(() ->
                        new RuntimeException("Nodo padre no encontrado: " + parentId));

        Long nextId = getNextId();

        MongoNodeDocument child = new MongoNodeDocument(nextId, value, parentId);
        return mongoRepository.save(child);
    }
    

    public MongoNodeDocument getRoot() {
        return mongoRepository.findByParentIdIsNull()
                .orElseThrow(() ->
                        new RuntimeException("No existe nodo raíz"));
    }

    public MongoNodeDocument getNodeById(Long id) {
        return mongoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Nodo no encontrado: " + id));
    }

    public List<MongoNodeDocument> getChildren(Long parentId) {
    	 return mongoRepository.findByParentId(parentId);
    }

    public List<MongoNodeDocument> getAllNodes() {
        return mongoRepository.findAll();
    }

    public void deleteAllNodes() {
        mongoRepository.deleteAll();
    }
    
    private Long getNextId() {
        return mongoRepository.findAll()
                .stream()
                .map(MongoNodeDocument::getId)
                .filter(id -> id != null)
                .max(Long::compareTo)
                .orElse(0L) + 1;
    }
    
}
