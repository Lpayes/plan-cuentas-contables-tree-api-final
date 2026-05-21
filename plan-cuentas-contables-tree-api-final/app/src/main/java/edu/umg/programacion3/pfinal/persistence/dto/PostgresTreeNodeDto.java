package edu.umg.programacion3.pfinal.persistence.dto;

import java.util.ArrayList;
import java.util.List;

public class PostgresTreeNodeDto {

    private Long id;
    private String name;
    private Long parentId;
    private List<PostgresTreeNodeDto> children = new ArrayList<>();

    public PostgresTreeNodeDto() {
    }

    public PostgresTreeNodeDto(Long id, String name, Long parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getParentId() {
        return parentId;
    }

    public List<PostgresTreeNodeDto> getChildren() {
        return children;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public void setChildren(List<PostgresTreeNodeDto> children) {
        this.children = children;
    }
}
