CREATE TABLE planner.evento (
  id SERIAL,
  id_usuario INT NOT NULL,
  data DATE NOT NULL,
  descricao VARCHAR(64) NOT NULL,
  excluded BOOLEAN NOT NULL DEFAULT FALSE,
  excluded_at DATE,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  created_by VARCHAR(128) NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_by VARCHAR(128) NOT NULL,
  version INTEGER NOT NULL DEFAULT 0,
  CONSTRAINT pk_evento PRIMARY KEY(id),
  CONSTRAINT fk_evento_id_usuario FOREIGN KEY (id_usuario) REFERENCES auth.usuario(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE INDEX idx_evento_id_usuario ON planner.evento(id_usuario);
CREATE INDEX idx_evento_excluded ON planner.evento(excluded);
