CREATE TABLE planner.projeto (
  id SERIAL,
  id_usuario INT NOT NULL,
  data_inicio DATE NOT NULL,
  data_fim DATE NOT NULL,
  descricao VARCHAR(64) NOT NULL,
  prioridade VARCHAR(16) NOT NULL,
  status VARCHAR(32) NOT NULL,
  anotacoes TEXT,
  excluded BOOLEAN NOT NULL DEFAULT FALSE,
  excluded_at DATE,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  created_by VARCHAR(128) NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_by VARCHAR(128) NOT NULL,
  version INTEGER NOT NULL DEFAULT 0,
  CONSTRAINT pk_projeto PRIMARY KEY (id),
  CONSTRAINT fk_projeto_id_usuario FOREIGN KEY (id_usuario) REFERENCES auth.usuario(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE INDEX idx_projeto_id_usuario ON planner.projeto(id_usuario);
CREATE INDEX idx_projeto_excluded ON planner.projeto(excluded);

CREATE TABLE planner.notificacao_projeto (
  id_notificacao BIGINT NOT NULL,
  id_projeto BIGINT NOT NULL,
  CONSTRAINT pk_notificacao_projeto PRIMARY KEY(id_notificacao),
  CONSTRAINT fk_notificacao_projeto_id_not FOREIGN KEY(id_notificacao) REFERENCES planner.notificacao(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_notificacao_projeto_id_pro FOREIGN KEY(id_projeto) REFERENCES planner.projeto(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE planner.tag_projeto (
  id_tag BIGINT NOT NULL,
  id_projeto BIGINT NOT NULL,
  CONSTRAINT pk_tag_projeto PRIMARY KEY(id_tag, id_projeto),
  CONSTRAINT fk_tag_projeto_id_tag FOREIGN KEY(id_tag) REFERENCES planner.tag(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_tag_projeto_id_pro FOREIGN KEY(id_projeto) REFERENCES planner.projeto(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);
