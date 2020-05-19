CREATE TABLE planner.plano_ferias (
  id SERIAL,
  id_usuario INT NOT NULL,
  data_inicio DATE NOT NULL,
  data_fim DATE NOT NULL,
  destino VARCHAR(100) NOT NULL,
  status VARCHAR(32) NOT NULL,
  anotacoes TEXT,
  excluded BOOLEAN NOT NULL DEFAULT FALSE,
  excluded_at DATE,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  created_by VARCHAR(128) NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_by VARCHAR(128) NOT NULL,
  version INTEGER NOT NULL DEFAULT 0,
  CONSTRAINT pk_plano_ferias PRIMARY KEY(id),
  CONSTRAINT fk_plano_ferias_id_usuario FOREIGN KEY (id_usuario) REFERENCES auth.usuario(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE INDEX idx_plano_ferias_id_usuario ON planner.plano_ferias(id_usuario);
CREATE INDEX idx_plano_ferias_excluded ON planner.plano_ferias(excluded);
