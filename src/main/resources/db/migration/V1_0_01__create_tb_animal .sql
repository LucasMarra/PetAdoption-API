CREATE TABLE IF NOT EXISTS tb_animal
(
    id                   VARCHAR(255) NOT NULL PRIMARY KEY,
    external_id          VARCHAR(255) NOT NULL,
    name                 VARCHAR(255) ,
    description          VARCHAR(500) ,
    image_url            VARCHAR(255) ,
    category             VARCHAR(255) NOT NULL,
    status               VARCHAR(255) NOT NULL,
    created_at           TIMESTAMP(6) NOT NULL,
    updated_at           TIMESTAMP(6) NOT NULL
);

CREATE UNIQUE INDEX idx_tb_tb_animal_external_id ON tb_animal(external_id);
