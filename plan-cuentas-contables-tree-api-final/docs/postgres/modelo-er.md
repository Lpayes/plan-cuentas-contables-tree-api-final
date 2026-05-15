# Modelo ER - PostgreSQL

## Tabla: nodes

La tabla `nodes` representa cada cuenta contable como un nodo dentro de un árbol jerárquico.

| Campo | Tipo | Descripción |
|---|---|---|
| id | BIGSERIAL | Identificador único del nodo |
| name | VARCHAR(255) | Nombre de la cuenta o nodo |
| parent_id | BIGINT | Referencia al nodo padre |

## Relación

La tabla `nodes` tiene una relación autorreferenciada:

```text
nodes.parent_id -> nodes.id