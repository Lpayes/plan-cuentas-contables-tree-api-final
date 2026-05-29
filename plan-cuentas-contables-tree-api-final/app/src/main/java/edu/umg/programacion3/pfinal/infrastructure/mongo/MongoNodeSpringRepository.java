package edu.umg.programacion3.pfinal.infrastructure.mongo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoNodeSpringRepository extends MongoRepository<MongoNodeDocument, String> {

	Optional<MongoNodeDocument> findById(Long id);

	List<MongoNodeDocument> findByParentId(Long parentId);

    Optional<MongoNodeDocument> findByParentIdIsNull();
}