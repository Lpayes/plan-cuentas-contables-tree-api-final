package edu.umg.programacion3.pfinal.api.dto;

import java.util.ArrayList;
import java.util.List;

public class TreeNodeResponse {

    private Long id;
    private String name;
    private List<TreeNodeResponse> children = new ArrayList<>();

    public TreeNodeResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<TreeNodeResponse> getChildren() {
        return children;
    }
}