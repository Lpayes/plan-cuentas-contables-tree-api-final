CREATE TABLE IF NOT EXISTS nodes (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    parent_id BIGINT,

    CONSTRAINT fk_nodes_parent
        FOREIGN KEY (parent_id)
        REFERENCES nodes(id)
        ON DELETE CASCADE
);