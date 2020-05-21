CREATE TABLE planner.evento (
  id SERIAL,
  id_usuario INT NOT NULL,
  data_inicio DATE NOT NULL,
  data_fim DATE NOT NULL,
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

CREATE TABLE planner.notificacao_evento (
  id_notificacao BIGINT NOT NULL,
  id_evento BIGINT NOT NULL,
  CONSTRAINT pk_notificacao_evento PRIMARY KEY(id_notificacao),
  CONSTRAINT fk_notificacao_evento_id_not FOREIGN KEY(id_notificacao) REFERENCES planner.notificacao(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_notificacao_evento_id_eve FOREIGN KEY(id_evento) REFERENCES planner.evento(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);
