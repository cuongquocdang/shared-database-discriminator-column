-- SCHEMA
CREATE SCHEMA IF NOT EXISTS saas;

-- TABLES
CREATE TABLE saas.tenants
(
    id         BIGSERIAL,
    name       VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_at TIMESTAMP,
    updated_by VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE saas.posts
(
    id         BIGSERIAL,
    content    VARCHAR,
    published  BOOLEAN,
    tenant_id  BIGINT NOT NULL,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_at TIMESTAMP,
    updated_by VARCHAR(255),
    PRIMARY KEY (id)
);

-- CONSTRAINTS
ALTER TABLE saas.posts
    ADD CONSTRAINT FK_posts_tenant_id FOREIGN KEY (tenant_id) REFERENCES saas.tenants (id);