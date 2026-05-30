# plan-cuentas-contables-tree-api-final
API REST de estructura de árboles para plan de cuentas contable
# Distribución de trabajo — Semana 1, 2 y 3
---

# Integrante A — Documentación de Trabajo Realizado

## Información General

**Proyecto:** Plan de Cuentas Contables Tree API

**Curso:** Programación III

**Rol:** Integrante A (Motor de Árbol y Estrategia Custom)

**Responsabilidades principales:**

- Diseño e implementación del motor de árbol en memoria.
- Implementación de la estrategia Custom.
- Definición y ampliación de la interfaz `TreeAlgorithmStrategy`.
- Implementación de los algoritmos del árbol.
- Integración del motor con OpenAPI.
- Documentación técnica de la estrategia Custom.
- Validaciones cruzadas entre estrategias.
- Soporte para selector dinámico de estrategia.

---

# Semana 1

## Objetivo

Construir el motor principal del árbol sin depender de bases de datos externas.

La idea era tener un árbol completamente funcional en memoria para validar la lógica del proyecto antes de conectar PostgreSQL o MongoDB.

---

## Diseño del modelo de árbol

Se diseñó la clase:

```java
CustomTreeNode
```

Responsabilidades:

- Representar cada nodo del árbol.
- Almacenar identificador.
- Almacenar nombre.
- Mantener referencia al padre.
- Mantener arreglo dinámico de hijos.

Estructura:

```text
CustomTreeNode
├── id
├── name
├── parent
├── children[]
└── childCount
```

---

## Implementación de arreglo dinámico

Para evitar depender de estructuras avanzadas y cumplir las restricciones del proyecto, se implementó crecimiento manual del arreglo.

```java
if (childCount == children.length) {

    CustomTreeNode[] newChildren =
            new CustomTreeNode[children.length * 2];

    ...

}
```

Beneficio:

- El árbol puede crecer dinámicamente.
- No se pierde información.
- No requiere reiniciar la estructura.

---

## Creación de la interfaz TreeAlgorithmStrategy

Se diseñó la interfaz principal del motor:

```java
public interface TreeAlgorithmStrategy
```

Su propósito fue definir un contrato común para cualquier implementación futura.

Inicialmente permitió que el proyecto pudiera ejecutar la misma lógica utilizando diferentes motores internos.

Ejemplo:

```text
CustomTreeStrategy

o

CollectionsTreeStrategy
```

Sin cambiar el resto del sistema.

---

## Operaciones iniciales implementadas

Durante la primera etapa se implementaron:

### Crear raíz

```java
createRoot(String name)
```

Permite crear el nodo principal del árbol.

---

### Agregar hijo

```java
addChild(Long parentId, String name)
```

Permite agregar nodos hijos a un nodo existente.

---

### Obtener árbol completo

```java
getTree()
```

Permite recuperar toda la estructura del árbol.

---

## Implementación de CustomTreeStrategy

Se creó:

```java
CustomTreeStrategy
```

Responsabilidad:

Conectar la interfaz del motor con el repositorio en memoria.

Flujo:

```text
CustomTreeStrategy
↓
CustomMemoryTreeRepository
↓
CustomTreeNode
```

---

## Repositorio en memoria

Se desarrolló:

```java
CustomMemoryTreeRepository
```

Responsabilidades:

- Guardar nodos.
- Buscar nodos.
- Crear raíces.
- Agregar hijos.
- Mantener el árbol completo en memoria.

---

## Resultado de Semana 1

Se logró:

- Crear raíz.
- Agregar hijos.
- Mantener estructura jerárquica.
- Recuperar árbol completo.
- Motor funcionando completamente en memoria.

---

# Semana 2

## Objetivo

Ampliar el motor para soportar todas las operaciones definidas por OpenAPI.

---

## Actualización de OpenAPI

Se amplió el contrato para soportar:

```text
11 operaciones
```

---

## Ampliación de TreeAlgorithmStrategy

La interfaz fue extendida para soportar:

```java
void createRoot(String name);

void addChild(Long parentId, String name);

Object getTree();

Object getSubtree(Long nodeId);

Object[] getPath(Long nodeId);

Object[] traverseDFS();

Object[] traverseBFS();

int getHeight();

int getDepth(Long nodeId);

Object[] getAncestors(Long nodeId);

boolean validateNoCycles();
```

---

## Implementación de Subárbol

```java
getSubtree(Long nodeId)
```

Permite devolver un nodo junto con todos sus descendientes.

---

## Implementación de Ruta

```java
getPath(Long nodeId)
```

Obtiene la ruta completa desde la raíz hasta el nodo solicitado.

Ejemplo:

```text
Activo
↓
Caja
↓
Caja General
```

---

## Implementación DFS

```java
traverseDFS()
```

Recorrido en profundidad.

Ejemplo:

```text
Activo
Caja
Caja General
Bancos
```

---

## Implementación BFS

```java
traverseBFS()
```

Recorrido por niveles.

Ejemplo:

```text
Activo
Caja
Bancos
Caja General
```

---

## Implementación de Altura

```java
getHeight()
```

Calcula la profundidad máxima del árbol.

---

## Implementación de Profundidad

```java
getDepth(Long nodeId)
```

Calcula la distancia entre la raíz y un nodo.

---

## Implementación de Ancestros

```java
getAncestors(Long nodeId)
```

Obtiene todos los padres de un nodo.

---

## Implementación de Validación de Ciclos

```java
validateNoCycles()
```

Permite detectar referencias circulares.

Ejemplo inválido:

```text
Activo
↓
Caja
↓
Caja General
↓
Activo
```

---

## Resultado de Semana 2

Se completaron las 11 operaciones definidas en OpenAPI para la estrategia Custom.

---

# Semana 3

## Objetivo

Integración final y documentación.

---

## Documentación Custom

Se documentó completamente:

- Arquitectura del motor.
- Estrategia Custom.
- Flujo de ejecución.
- Operaciones implementadas.
- Algoritmos utilizados.
- Integración con OpenAPI.

---

## Integración con OpenAPI

Se verificó que todas las operaciones implementadas coincidieran con el contrato definido en:

```text
openapi.yaml
```

Garantizando compatibilidad entre:

```text
Swagger
↓
Controller
↓
Service
↓
Motor Custom
```

---

## Integración con la arquitectura final

El motor Custom quedó integrado dentro del flujo definitivo:

```text
Frontend
↓
Swagger/OpenAPI
↓
TreeController
↓
TreeService
↓
StorageSelectorService
↓
MemoryTreePersistenceService
↓
CustomTreeStrategy
↓
CustomMemoryTreeRepository
↓
CustomTreeNode
```

---

# Documentación Custom

## Componentes principales

### CustomTreeNode

Representa cada nodo del árbol.

Contiene:

- id
- name
- parent
- children

---

### CustomMemoryTreeRepository

Repositorio principal en memoria.

Responsable de:

- Guardar nodos.
- Recuperar nodos.
- Mantener estructura del árbol.

---

### CustomTreeStrategy

Implementación de:

```java
TreeAlgorithmStrategy
```

Responsable de ejecutar toda la lógica del árbol.

---

### TreeAlgorithmStrategy

Contrato principal del motor.

Define todas las operaciones soportadas por cualquier estrategia.

---

# Validaciones Cruzadas

## Objetivo

Verificar que las diferentes estrategias produzcan exactamente los mismos resultados.

---

## Estrategias comparadas

```text
CustomTreeStrategy
CollectionsTreeStrategy
```

---

## Escenario utilizado

```text
1 Activo
├── 1.1 Caja
│   └── 1.1.1 Caja General
└── 1.2 Bancos
```

---

## DFS

Resultado esperado:

```text
Activo
Caja
Caja General
Bancos
```

Custom:

```text
Correcto
```

Collections:

```text
Correcto
```

---

## BFS

Resultado esperado:

```text
Activo
Caja
Bancos
Caja General
```

Custom:

```text
Correcto
```

Collections:

```text
Correcto
```

---

## Altura

Resultado esperado:

```text
3
```

Custom:

```text
Correcto
```

Collections:

```text
Correcto
```

---

## Profundidad

Nodo evaluado:

```text
Caja General
```

Resultado esperado:

```text
2
```

Custom:

```text
Correcto
```

Collections:

```text
Correcto
```

---

## Ancestros

Resultado esperado:

```text
Activo
Caja
```

Custom:

```text
Correcto
```

Collections:

```text
Correcto
```

---

## Validación de Ciclos

Resultado esperado:

```json
{
  "valid": true,
  "message": "El árbol no contiene ciclos"
}
```

Custom:

```text
Correcto
```

Collections:

```text
Correcto
```

---

# Conclusión

Como Integrante A fui responsable del diseño e implementación del motor del árbol, la estrategia Custom, la ampliación del contrato `TreeAlgorithmStrategy`, la implementación de los algoritmos principales, la documentación técnica de la estrategia y la realización de validaciones cruzadas para verificar la consistencia funcional entre las distintas estrategias soportadas por el proyecto.


# Integrante B – Persistencia PostgreSQL y Estrategia Collections

## Semana 1 – Persistencia PostgreSQL

### Objetivo

Implementar la persistencia relacional utilizando PostgreSQL para almacenar la estructura del árbol.

### Actividades Realizadas

- Configuración de PostgreSQL mediante Docker.
- Configuración de Spring Boot para conexión con PostgreSQL.
- Creación de la entidad `NodeEntity`.
- Creación del repositorio `NodeRepository`.
- Diseño de la estructura de la tabla `nodes`.
- Implementación de la relación padre-hijo mediante `parent_id`.
- Elaboración de scripts SQL para estructura y datos de prueba.
- Verificación de conexión entre la aplicación y PostgreSQL.

### Componentes Implementados

#### NodeEntity

Representa un nodo almacenado en PostgreSQL.

Campos principales:

```text
id
name
parentId
```

#### NodeRepository

Repositorio JPA encargado de realizar operaciones CRUD sobre la tabla `nodes`.

#### PostgreSQL

Motor de base de datos relacional utilizado para almacenar de forma persistente la estructura jerárquica del árbol.

#### Hibernate y JPA

Se utilizaron Hibernate y Spring Data JPA para simplificar la persistencia y el acceso a datos mediante entidades y repositorios.

### Resultado

Se logró almacenar y recuperar nodos desde PostgreSQL, dejando lista la base de persistencia del proyecto.

---

## Semana 2 – Estrategia Collections

### Objetivo

Implementar una estrategia alternativa utilizando las colecciones nativas de Java para el manejo del árbol.

### Actividades Realizadas

- Implementación de `CollectionsTreeStrategy`.
- Desarrollo de las operaciones requeridas por OpenAPI.
- Implementación de recorridos DFS y BFS.
- Implementación del cálculo de altura.
- Implementación de profundidad de nodos.
- Implementación de búsqueda de ancestros.
- Implementación de rutas desde la raíz.
- Implementación de validación de ciclos.
- Pruebas funcionales de las operaciones implementadas.

### Componentes Implementados

#### CollectionsTreeStrategy

Implementación basada en colecciones Java para gestionar la estructura del árbol.

### Operaciones Implementadas

- Crear raíz.
- Agregar hijo.
- Obtener árbol completo.
- Obtener subárbol.
- Obtener ruta.
- Recorrido DFS.
- Recorrido BFS.
- Altura del árbol.
- Profundidad de nodos.
- Ancestros.
- Validación de ciclos.

### Resultado

La estrategia Collections quedó preparada para soportar todas las operaciones definidas en el contrato OpenAPI y funcionar como alternativa al motor custom.

# Integrante C – Persistencia MongoDB y Configuración Dinámica

## Semana 1

### Objetivo

Implementar una alternativa de persistencia utilizando MongoDB.

### Actividades realizadas

- Instalación y configuración de MongoDB.
- Creación de MongoNodeDocument.
- Creación de MongoNodeSpringRepository.
- Implementación de MongoTreeRepository.
- Configuración de conexión MongoDB.
- Pruebas de almacenamiento y recuperación de nodos.

### Resultado

MongoDB quedó integrado como tercera alternativa de persistencia del proyecto.

---

## Semana 2

### Objetivo

Integrar MongoDB dentro de la arquitectura general.

### Actividades realizadas

- Implementación de MongoTreePersistenceService.
- Integración con TreeService.
- Integración con OpenAPI.
- Integración con los endpoints existentes.
- Pruebas funcionales de persistencia Mongo.

### Resultado

Todos los endpoints definidos por OpenAPI pudieron funcionar utilizando MongoDB como almacenamiento.

---

## Semana 3

### Objetivo

Implementar configuración dinámica de persistencia.

### Actividades realizadas

- Implementación de StorageSelectorService.
- Implementación de ConfigController.
- Implementación del cambio dinámico de persistencia.
- Integración con la interfaz web.
- Validación de cambio entre Memory, PostgreSQL y MongoDB sin reiniciar la aplicación.

### Componentes implementados

#### StorageSelectorService

Permite seleccionar dinámicamente:

- Memory
- PostgreSQL
- MongoDB

#### ConfigController

Endpoints:

GET /config/storage

POST /config/storage/{storage}

#### Integración Frontend

Permite cambiar la persistencia activa desde la interfaz web.

### Resultado

La aplicación puede cambiar dinámicamente entre Memory, PostgreSQL y MongoDB durante la ejecución.

---

# Conclusión

Como Integrante C fui responsable de la integración de MongoDB, la configuración dinámica de persistencia y el desarrollo de los componentes necesarios para permitir el cambio entre Memory, PostgreSQL y MongoDB sin modificar el código ni reiniciar la aplicación.
