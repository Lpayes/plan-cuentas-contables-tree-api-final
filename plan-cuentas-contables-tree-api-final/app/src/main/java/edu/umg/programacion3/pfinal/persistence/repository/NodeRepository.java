package edu.umg.programacion3.pfinal.persistence.repository;
import edu.umg.programacion3.pfinal.persistence.entity.NodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeRepository extends JpaRepository<NodeEntity, Long> {
	
}
