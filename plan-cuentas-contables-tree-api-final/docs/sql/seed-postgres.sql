DELETE FROM nodes;

INSERT INTO nodes (name, parent_id) VALUES ('1 Activo', NULL);
INSERT INTO nodes (name, parent_id) VALUES ('1.1 Caja', 1);
INSERT INTO nodes (name, parent_id) VALUES ('1.1.1 Caja General', 2);
INSERT INTO nodes (name, parent_id) VALUES ('1.2 Bancos', 1);
INSERT INTO nodes (name, parent_id) VALUES ('2 Pasivo', NULL);
INSERT INTO nodes (name, parent_id) VALUES ('2.1 Cuentas por pagar', 5);