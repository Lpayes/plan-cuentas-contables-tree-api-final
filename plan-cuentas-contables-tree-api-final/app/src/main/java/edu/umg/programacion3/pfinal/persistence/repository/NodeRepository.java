package edu.umg.programacion3.pfinal.persistence.repository;

import edu.umg.programacion3.pfinal.persistence.entity.NodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NodeRepository extends JpaRepository<NodeEntity, Long> {

    List<NodeEntity> findByParentId(Long parentId);

    Optional<NodeEntity> findByParentIdIsNull();
}
