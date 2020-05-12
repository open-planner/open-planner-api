CREATE SCHEMA planner;

CREATE TABLE planner.roda_vida (
  id SERIAL,
  id_usuario INT NOT NULL,
  espiritualidade  NUMERIC(3, 2) DEFAULT 0 NOT NULL,
  entretenimento NUMERIC(3, 2) DEFAULT 0 NOT NULL,
  dinheiro NUMERIC(3, 2) DEFAULT 0 NOT NULL,
  carreira NUMERIC(3, 2) DEFAULT 0 NOT NULL,
  desenvolvimento_pessoal NUMERIC(3, 2) DEFAULT 0 NOT NULL,
  relacionamento NUMERIC(3, 2) DEFAULT 0 NOT NULL,
  saude NUMERIC(3, 2) DEFAULT 0 NOT NULL,
  ambiente NUMERIC(3, 2) DEFAULT 0 NOT NULL,
  ativo BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  created_by VARCHAR(32) NOT NULL,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  updated_by VARCHAR(32) NOT NULL,
  version INTEGER NOT NULL DEFAULT 0,
  CONSTRAINT pk_roda_vida PRIMARY KEY(id),
  CONSTRAINT fk_roda_vida_id_usuario FOREIGN KEY (id_usuario) REFERENCES auth.usuario(id)
    MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT uk_roda_vida_id_usuario UNIQUE(id_usuario)
);
