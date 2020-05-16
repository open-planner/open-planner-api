CREATE EXTENSION unaccent;

CREATE SCHEMA auth;

CREATE TABLE auth.usuario (
  id SERIAL,
  nome VARCHAR(128) NOT NULL,
  email VARCHAR(128) NOT NULL,
  data_nascimento DATE NOT NULL,
  valor_senha VARCHAR(64),
  reset_token_senha VARCHAR(255),
  tentativas_erro_senha SMALLINT,
  data_atualizacao_senha DATE,
  ativacao_token VARCHAR(255),
  pendente BOOLEAN NOT NULL DEFAULT TRUE,
  bloqueado BOOLEAN NOT NULL DEFAULT FALSE,
  excluded BOOLEAN NOT NULL DEFAULT FALSE,
  excluded_at DATE,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  created_by VARCHAR(128) NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_by VARCHAR(128) NOT NULL,
  version INTEGER NOT NULL DEFAULT 0,
  CONSTRAINT pk_usuario PRIMARY KEY(id),
  CONSTRAINT uk_usuario_email UNIQUE(email)
);

CREATE INDEX idx_usuario_excluded ON auth.usuario(excluded);

CREATE TABLE auth.permissao (
  id SERIAL,
  papel VARCHAR(64) NOT NULL,
  descricao VARCHAR(128) NOT NULL,
  excluded BOOLEAN NOT NULL DEFAULT FALSE,
  excluded_at DATE,
  version INTEGER NOT NULL DEFAULT 0,
  CONSTRAINT pk_permissao PRIMARY KEY(id),
  CONSTRAINT uk_permissao_papel UNIQUE(papel)
);

CREATE TABLE auth.usuario_permissao (
  id_usuario INTEGER NOT NULL,
  id_permissao INTEGER NOT NULL,
  CONSTRAINT pk_usuario_permissao PRIMARY KEY(id_usuario, id_permissao),
  CONSTRAINT fk_usuario_permissao_id_usuario FOREIGN KEY(id_usuario) REFERENCES auth.usuario(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_usuario_permissao_id_permissao FOREIGN KEY(id_permissao) REFERENCES auth.permissao(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);
