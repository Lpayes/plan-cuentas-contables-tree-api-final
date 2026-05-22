package edu.umg.programacion3.pfinal.persistence.service;

public interface TreePersistenceService {
	
	Object createRoot(String name);

    Object addChild(Long parentId, String name);

    Object getTree();

}
