CREATE TABLE planner.meta (
  id SERIAL,
  id_usuario INT NOT NULL,
  data DATE NOT NULL,
  descricao VARCHAR(64) NOT NULL,
  status VARCHAR(32) NOT NULL,
  anotacoes TEXT,
  excluded BOOLEAN NOT NULL DEFAULT FALSE,
  excluded_at DATE,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  created_by VARCHAR(128) NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_by VARCHAR(128) NOT NULL,
  version INTEGER NOT NULL DEFAULT 0,
  CONSTRAINT pk_meta PRIMARY KEY (id),
  CONSTRAINT fk_meta_id_usuario FOREIGN KEY (id_usuario) REFERENCES auth.usuario(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE INDEX idx_meta_id_usuario ON planner.meta(id_usuario);
CREATE INDEX idx_meta_excluded ON planner.meta(excluded);

CREATE TABLE planner.notificacao_meta (
  id_notificacao BIGINT NOT NULL,
  id_meta BIGINT NOT NULL,
  CONSTRAINT pk_notificacao_meta PRIMARY KEY(id_notificacao),
  CONSTRAINT fk_notificacao_meta_id_not FOREIGN KEY(id_notificacao) REFERENCES planner.notificacao(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_notificacao_meta_id_met FOREIGN KEY(id_meta) REFERENCES planner.meta(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE planner.tag_meta (
  id_tag BIGINT NOT NULL,
  id_meta BIGINT NOT NULL,
  CONSTRAINT pk_tag_meta PRIMARY KEY(id_tag, id_meta),
  CONSTRAINT fk_tag_meta_id_tag FOREIGN KEY(id_tag) REFERENCES planner.tag(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_tag_meta_id_met FOREIGN KEY(id_meta) REFERENCES planner.meta(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);
