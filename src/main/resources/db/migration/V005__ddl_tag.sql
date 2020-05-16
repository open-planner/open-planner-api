CREATE TABLE planner.tag (
  id SERIAL,
  id_usuario INT NOT NULL,
  descricao VARCHAR(64) NOT NULL,
  cor VARCHAR(6),
  excluded BOOLEAN NOT NULL DEFAULT FALSE,
  excluded_at DATE,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  created_by VARCHAR(128) NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_by VARCHAR(128) NOT NULL,
  version INTEGER NOT NULL DEFAULT 0,
  CONSTRAINT pk_tag PRIMARY KEY(id),
  CONSTRAINT fk_tag_id_usuario FOREIGN KEY (id_usuario) REFERENCES auth.usuario(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE INDEX idx_tag_id_usuario ON planner.tag(id_usuario);
CREATE INDEX idx_tag_excluded ON planner.tag(excluded);
