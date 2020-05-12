DO $$
DECLARE
  _id_root INT := -1;
  _id_system INT := -2;
  _id_admin INT := 1;
BEGIN
  -- Usuários Padrão
  INSERT INTO auth.usuario (id, nome, data_nascimento, email, valor_senha, tentativas_erro_senha, data_atualizacao_senha, pendente, bloqueado, created_at, created_by, updated_at, updated_by) VALUES
    (_id_root, 'Super User', '1991-01-01', 'root@email.com', '$2a$10$ruzUEX3kN/b0URwAioSWXOQ5FPeK8LttYNveZywgiFQfq0wI0EVK6', 0, NOW(), FALSE, FALSE, NOW(), 'root', NOW(), 'root'),
    (_id_system, 'System User', '1991-01-01', 'system@email.com', '$2a$10$2hCHsFoN3kyZNNnDwb4e9.5tGGs2tmCh46YKiCRVn18ghDMDgIMZm', 0, NOW(), FALSE, FALSE, NOW(), 'root', NOW(), 'root'),
    (_id_admin, 'Administrador', '1991-01-01', 'admin@email.com', '$2a$10$9.vsponXOmbP6L9RqeZPBeVhojwg4bhjLdVTP/EkLaZGht9juv5fq', 0, NOW(), FALSE, FALSE, NOW(), 'root', NOW(), 'root');

  ALTER SEQUENCE auth.usuario_id_seq RESTART WITH 2;

  -- Permissões
  INSERT INTO auth.permissao (id, papel, descricao) VALUES
    (_id_root, 'ROOT', 'Root'),
    (_id_system, 'SYSTEM', 'System'),
    (_id_admin, 'ADMIN', 'Administrador');

  ALTER SEQUENCE auth.permissao_id_seq RESTART WITH 2;

  INSERT INTO auth.usuario_permissao (id_usuario, id_permissao) VALUES
    (_id_root, _id_root),
    (_id_system, _id_system),
    (_id_admin, _id_admin);
END $$;
