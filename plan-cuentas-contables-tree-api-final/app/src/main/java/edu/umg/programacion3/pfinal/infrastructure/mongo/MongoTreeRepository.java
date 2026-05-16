package edu.umg.programacion3.pfinal.infrastructure.mongo;

public class MongoTreeRepository {

    private final MongoNodeSpringRepository mongoRepository;

    public MongoTreeRepository(MongoNodeSpringRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }
}
