package edu.umg.programacion3.pfinal.api.controller;

import edu.umg.programacion3.pfinal.api.dto.NodeRequest;
import edu.umg.programacion3.pfinal.api.dto.TreeNodeResponse;
import edu.umg.programacion3.pfinal.treeengine.custom.CustomTreeNode;
import edu.umg.programacion3.pfinal.treeengine.strategy.TreeAlgorithmStrategy;
import org.springframework.web.bind.annotation.*;

@RestController
public class TreeController {

    private final TreeAlgorithmStrategy strategy;

    public TreeController(TreeAlgorithmStrategy strategy) {
        this.strategy = strategy;
    }

    @PostMapping("/nodes/root")
    public String createRoot(@RequestBody NodeRequest request) {

        strategy.createRoot(request.getName());

        return "Raíz creada correctamente";
    }

    @PostMapping("/nodes/{parentId}/children")
    public String addChild(
            @PathVariable("parentId") Long parentId,
            @RequestBody NodeRequest request) {

        strategy.addChild(parentId, request.getName());

        return "Hijo agregado correctamente";
    }

    @GetMapping("/tree")
    public TreeNodeResponse getTree() {

        CustomTreeNode root =
                (CustomTreeNode) strategy.getTree();

        return toResponse(root);
    }

    private TreeNodeResponse toResponse(CustomTreeNode node) {

        if (node == null) {
            return null;
        }

        TreeNodeResponse response =
                new TreeNodeResponse(
                        node.getId(),
                        node.getName()
                );

        CustomTreeNode[] children =
                node.getChildren();

        for (int i = 0; i < node.getChildCount(); i++) {

            response.getChildren()
                    .add(toResponse(children[i]));
        }

        return response;
    }
}