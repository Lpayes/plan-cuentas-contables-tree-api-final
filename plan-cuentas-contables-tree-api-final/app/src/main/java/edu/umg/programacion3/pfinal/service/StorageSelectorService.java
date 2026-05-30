package edu.umg.programacion3.pfinal.service;

import edu.umg.programacion3.pfinal.persistence.service.MemoryTreePersistenceService;
import edu.umg.programacion3.pfinal.persistence.service.MongoTreePersistenceService;
import edu.umg.programacion3.pfinal.persistence.service.PostgresTreePersistenceService;
import edu.umg.programacion3.pfinal.persistence.service.TreePersistenceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StorageSelectorService {

    private final MemoryTreePersistenceService memoryService;
    private final MongoTreePersistenceService mongoService;
    private final PostgresTreePersistenceService postgresService;

    private String activeStorage;

    public StorageSelectorService(
            MemoryTreePersistenceService memoryService,
            MongoTreePersistenceService mongoService,
            PostgresTreePersistenceService postgresService,
            @Value("${app.storage:memory}") String activeStorage) {
        this.memoryService = memoryService;
        this.mongoService = mongoService;
        this.postgresService = postgresService;
        this.activeStorage = activeStorage;
    }

    public TreePersistenceService getActiveService() {
        if ("mongo".equalsIgnoreCase(activeStorage)) {
            return mongoService;
        }

        if ("postgres".equalsIgnoreCase(activeStorage)) {
            return postgresService;
        }

        return memoryService;
    }

    public String getActiveStorage() {
        return activeStorage;
    }

    public void changeStorage(String storage) {
        if (!"memory".equalsIgnoreCase(storage)
                && !"mongo".equalsIgnoreCase(storage)
                && !"postgres".equalsIgnoreCase(storage)) {
            throw new IllegalArgumentException("Storage no soportado: " + storage);
        }

        this.activeStorage = storage.toLowerCase();
    }
}