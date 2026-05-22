package edu.umg.programacion3.pfinal.treeengine.collections;
import edu.umg.programacion3.pfinal.treeengine.strategy.TreeAlgorithmStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

@Component
@ConditionalOnProperty(
        name = "app.tree-strategy",
        havingValue = "collections"
)
public class CollectionsTreeStrategy implements TreeAlgorithmStrategy {

    /*
     * Estructura principal del árbol usando Collections.
     *
     * Key   -> id del nodo
     * Value -> información del nodo
     */
    private final Map<Long, Map<String, Object>> nodes = new HashMap<>();

    // Guarda el id de la raíz del árbol
    private Long rootId = null;

    // Simula ids autoincrementales
    private Long currentId = 1L;

    @Override
    public void createRoot(String name) {

        // Nodo raíz representado con un Map
        Map<String, Object> root = new HashMap<>();
        root.put("id", currentId);
        root.put("name", name);
        root.put("parentId", null);

        /*
         * Lista de hijos.
         *
         * Aquí NO guardamos objetos nodo,
         * sino solamente ids de hijos.
         */
        root.put("children", new ArrayList<Long>());

        // Guardar raíz
        nodes.put(currentId, root);
        rootId = currentId;
        currentId++;
    }

    @Override
    public void addChild(Long parentId, String name) {

        // Buscar padre
        Map<String, Object> parent = nodes.get(parentId);
        if (parent == null) {
            return;
        }

        // Crear hijo
        Map<String, Object> child = new HashMap<>();
        child.put("id", currentId);
        child.put("name", name);
        child.put("parentId", parentId);
        child.put("children", new ArrayList<Long>());

        // Guardar hijo en el mapa principal
        nodes.put(currentId, child);

        /*
         * Obtener lista de hijos del padre
         * y agregar el nuevo hijo.
         */
        List<Long> children = getChildrenIds(parent);
        children.add(currentId);
        currentId++;
    }

    @Override
    public Object getTree() {

        // Devuelve el nodo raíz
        return nodes.get(rootId);
    }

    @Override
    public Object getSubtree(Long nodeId) {

        // Devuelve el nodo solicitado
        return nodes.get(nodeId);
    }

    @Override
    public Object[] getPath(Long nodeId) {
        List<Object> path = new ArrayList<>();
        Map<String, Object> current = nodes.get(nodeId);

        /*
         * Va subiendo desde el nodo actual
         * hasta llegar a la raíz.
         */
        while (current != null) {

            // Insertar al inicio de la lista
            path.add(0, current);
            Long parentId = (Long) current.get("parentId");
            if (parentId == null) {
                break;
            }

            current = nodes.get(parentId);
        }

        return path.toArray();
    }

    @Override
    public Object[] traverseDFS() {
        List<Object> result = new ArrayList<>();
        Map<String, Object> root = nodes.get(rootId);

        // DFS recursivo
        traverseDFSRecursive(root, result);

        return result.toArray();
    }

    /*
     * DFS = Depth First Search
     *
     * Recorre primero profundidad.
     */
    private void traverseDFSRecursive(
            Map<String, Object> node,
            List<Object> result) {

        if (node == null) {
            return;
        }

        result.add(node);

        List<Long> children = getChildrenIds(node);

        for (Long childId : children) {

            traverseDFSRecursive(
                    nodes.get(childId),
                    result
            );
        }
    }

    @Override
    public Object[] traverseBFS() {

        List<Object> result = new ArrayList<>();

        if (rootId == null) {
            return result.toArray();
        }

        /*
         * Queue para recorrido BFS
         * usando ArrayDeque.
         */
        Queue<Long> queue = new ArrayDeque<>();

        queue.add(rootId);

        while (!queue.isEmpty()) {

            Long currentNodeId = queue.poll();

            Map<String, Object> currentNode =
                    nodes.get(currentNodeId);

            if (currentNode == null) {
                continue;
            }

            result.add(currentNode);

            List<Long> children =
                    getChildrenIds(currentNode);

            for (Long childId : children) {
                queue.add(childId);
            }
        }

        return result.toArray();
    }

    @Override
    public int getHeight() {

        // Altura total desde raíz
        return calculateHeight(rootId);
    }

    /*
     * Calcula altura máxima del árbol.
     */
    private int calculateHeight(Long nodeId) {

        if (nodeId == null) {
            return 0;
        }

        Map<String, Object> node =
                nodes.get(nodeId);

        if (node == null) {
            return 0;
        }

        List<Long> children =
                getChildrenIds(node);

        // Nodo hoja
        if (children.isEmpty()) {
            return 1;
        }

        int maxHeight = 0;

        for (Long childId : children) {

            int childHeight =
                    calculateHeight(childId);

            if (childHeight > maxHeight) {
                maxHeight = childHeight;
            }
        }

        return maxHeight + 1;
    }

    @Override
    public int getDepth(Long nodeId) {

        Map<String, Object> node =
                nodes.get(nodeId);

        if (node == null) {
            return -1;
        }

        int depth = 0;

        Long parentId =
                (Long) node.get("parentId");

        /*
         * Cuenta cuántos niveles hay
         * desde el nodo hasta la raíz.
         */
        while (parentId != null) {

            depth++;

            Map<String, Object> parent =
                    nodes.get(parentId);

            if (parent == null) {
                break;
            }

            parentId =
                    (Long) parent.get("parentId");
        }

        return depth;
    }

    @Override
    public Object[] getAncestors(Long nodeId) {

        List<Object> ancestors =
                new ArrayList<>();

        Map<String, Object> node =
                nodes.get(nodeId);

        if (node == null) {
            return ancestors.toArray();
        }

        Long parentId =
                (Long) node.get("parentId");

        /*
         * Va subiendo desde el padre
         * hasta la raíz.
         */
        while (parentId != null) {

            Map<String, Object> parent =
                    nodes.get(parentId);

            if (parent == null) {
                break;
            }

            ancestors.add(parent);

            parentId =
                    (Long) parent.get("parentId");
        }

        return ancestors.toArray();
    }

    @Override
    public boolean validateNoCycles() {

        /*
         * Detecta ciclos usando:
         *
         * visited
         * recursionStack
         */

        if (rootId == null) {
            return true;
        }

        Set<Long> visited = new HashSet<>();
        Set<Long> recursionStack =
                new HashSet<>();
        return validateNoCyclesRecursive(
                rootId,
                visited,
                recursionStack
        );
    }

    private boolean validateNoCyclesRecursive(
            Long nodeId,
            Set<Long> visited,
            Set<Long> recursionStack) {

        // Si ya está en la pila -> ciclo
        if (recursionStack.contains(nodeId)) {
            return false;
        }

        // Si ya fue visitado -> continuar
        if (visited.contains(nodeId)) {
            return true;
        }

        visited.add(nodeId);
        recursionStack.add(nodeId);
        Map<String, Object> node =
                nodes.get(nodeId);
        if (node != null) {

            List<Long> children =
                    getChildrenIds(node);

            for (Long childId : children) {

                boolean valid =
                        validateNoCyclesRecursive(
                                childId,
                                visited,
                                recursionStack
                        );

                if (!valid) {
                    return false;
                }
            }
        }

        recursionStack.remove(nodeId);
        return true;
    }

    /*
     * Helper para obtener hijos
     * casteando correctamente.
     */
    @SuppressWarnings("unchecked")
    private List<Long> getChildrenIds(
            Map<String, Object> node) {
        return (List<Long>) node.get("children");
    }
}
