CREATE TABLE planner.tarefa (
  id SERIAL,
  id_usuario INT NOT NULL,
  data_hora TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  descricao VARCHAR(64) NOT NULL,
  status VARCHAR(32) NOT NULL,
  anotacoes TEXT,
  id_relacao BIGINT,
  unidade_recorrencia VARCHAR(10),
  data_limite_recorrencia DATE,
  excluded BOOLEAN NOT NULL DEFAULT FALSE,
  excluded_at DATE,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  created_by VARCHAR(128) NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_by VARCHAR(128) NOT NULL,
  version INTEGER NOT NULL DEFAULT 0,
  CONSTRAINT pk_tarefa PRIMARY KEY (id),
  CONSTRAINT fk_tarefa_id_usuario FOREIGN KEY (id_usuario) REFERENCES auth.usuario(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_tarefa_id_relacao FOREIGN KEY (id_relacao) REFERENCES planner.tarefa(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE INDEX idx_tarefa_id_usuario ON planner.tarefa(id_usuario);
CREATE INDEX idx_tarefa_excluded ON planner.tarefa(excluded);

CREATE TABLE planner.notificacao_tarefa (
  id_notificacao BIGINT NOT NULL,
  id_tarefa BIGINT NOT NULL,
  CONSTRAINT pk_notificacao_tarefa PRIMARY KEY(id_notificacao),
  CONSTRAINT fk_notificacao_tarefa_id_not FOREIGN KEY(id_notificacao) REFERENCES planner.notificacao(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_notificacao_tarefa_id_tar FOREIGN KEY(id_tarefa) REFERENCES planner.tarefa(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE planner.tag_tarefa (
  id_tag BIGINT NOT NULL,
  id_tarefa BIGINT NOT NULL,
  CONSTRAINT pk_tag_tarefa PRIMARY KEY(id_tag, id_tarefa),
  CONSTRAINT fk_tag_tarefa_id_tag FOREIGN KEY(id_tag) REFERENCES planner.tag(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_tag_tarefa_id_tar FOREIGN KEY(id_tarefa) REFERENCES planner.tarefa(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);
