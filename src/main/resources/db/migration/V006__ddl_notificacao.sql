CREATE TABLE planner.notificacao (
  id SERIAL,
  id_usuario INT NOT NULL,
  data_hora TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  lida BOOLEAN NOT NULL DEFAULT FALSE,
  email BOOLEAN DEFAULT FALSE,
  excluded BOOLEAN NOT NULL DEFAULT FALSE,
  excluded_at DATE,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  created_by VARCHAR(128) NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_by VARCHAR(128) NOT NULL,
  version INTEGER NOT NULL DEFAULT 0,
  CONSTRAINT pk_notificacao PRIMARY KEY(id),
  CONSTRAINT fk_notificacao_id_usuario FOREIGN KEY (id_usuario) REFERENCES auth.usuario(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE INDEX idx_notificacao_id_usuario ON planner.notificacao(id_usuario);
CREATE INDEX idx_notificacao_excluded ON planner.notificacao(excluded);
