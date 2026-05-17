# plan-cuentas-contables-tree-api-final
API REST de estructura de árboles para plan de cuentas contable
# Distribución de trabajo — Semana 1

---

# Integrante A — Motor custom + API REST inicial

Responsabilidades trabajadas:
- Configuración inicial del módulo tree-engine
- Creación de la interfaz TreeAlgorithmStrategy
- Implementación de CustomTreeStrategy
- Desarrollo de CustomTreeNode
- Desarrollo de CustomMemoryTreeRepository
- Implementación inicial TreeController
- Creación de DTOs
- Configuración OpenAPI/Swagger YAML
- Pruebas iniciales desde Swagger
- Persistencia temporal en RAM

Tecnologías utilizadas:
- Spring Boot
- Java 17
- Swagger/OpenAPI
- Maven multimódulo

Resultado:
- Árbol custom funcional en memoria
- API REST funcional
- Endpoints básicos operativos

---

# Integrante B — Persistencia PostgreSQL

Responsabilidades trabajadas:
- Configuración PostgreSQL
- Creación de entidades JPA/Hibernate
- Configuración application-postgres.properties
- Configuración Docker PostgreSQL
- Scripts SQL iniciales
- Integración Spring Data JPA
- Configuración de persistencia relacional

Tecnologías utilizadas:
- PostgreSQL
- Docker
- Spring Data JPA
- Hibernate

Resultado:
- Base PostgreSQL funcional
- Persistencia relacional inicial conectada con Spring Boot

---

# Integrante C — MongoDB + wiring condicional

Responsabilidades trabajadas:
- Configuración MongoDB
- Configuración application-mongo.properties
- Integración Spring Data MongoDB
- Creación MongoNodeDocument
- Creación MongoNodeSpringRepository
- Configuración Docker MongoDB
- Wiring condicional con @ConditionalOnProperty
- Selectores dinámicos de estrategia/persistencia

Tecnologías utilizadas:
- MongoDB
- Docker
- Spring Data MongoDB

  # Enlace de Trello

Tablero de organización y seguimiento del proyecto:

https://trello.com/invite/b/6a03d763f43568c7a236cec8/ATTI1308c680ad23957e10c5a2a7a3106e2b83F9C303/plan-de-cuentas-contable-tree-api

Resultado:
- Persistencia MongoDB funcional
- Selección dinámica de estrategias y storage
