package edu.umg.programacion3.pfinal.infrastructure.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "nodes")
public class MongoNodeDocument {

    @Id
    private String id;

    private String value;

    private String parentId;

    public MongoNodeDocument() {
    }

    public MongoNodeDocument(String id, String value, String parentId) {
        this.id = id;
        this.value = value;
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public String getParentId() {
        return parentId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
