package edu.umg.programacion3.pfinal.persistence.repository;
import edu.umg.programacion3.pfinal.persistence.entity.NodeEntity;

public interface NodeRepository {
	NodeEntity save(NodeEntity node);

    NodeEntity findById(Long id);
}
