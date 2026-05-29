package edu.umg.programacion3.pfinal.infrastructure.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "nodes")
public class MongoNodeDocument {

	@Id
	private String mongoId;

	private Long id;

	private String value;

	private Long parentId;
    public MongoNodeDocument() {
    }

    public MongoNodeDocument(Long id, String value, Long parentId) {
        this.id = id;
        this.value = value;
        this.parentId = parentId;
    }
    
    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
