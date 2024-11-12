SET FOREIGN_KEY_CHECKS = 0;

-- Inserção de Usuários
INSERT INTO usuario (
    id, nome, email, senha, tipo, data_cadastro, status, ultimo_login, 
    cadastro_pessoal_completo, cadastro_historico_completo, portaria_aprovada, 
    email_confirmado, confirmacao_email_token, confirmacao_email_expiracao
) VALUES
(1, 'Admin User 1', 'admin1@example.com', '$2b$12$MbbPyTNri4UlKr9Jy9cofuAbaDku839gexrTJPJBOEypfG0p8BVEO', 'ADMINISTRADOR', '2023-09-01', 'ATIVO', '2023-09-10 10:00:00', false, false, false, true, NULL, '2023-10-30 23:59:59'),
(2, 'Admin User 2', 'admin2@example.com', '$2b$12$MbbPyTNri4UlKr9Jy9cofuAbaDku839gexrTJPJBOEypfG0p8BVEO', 'ADMINISTRADOR', '2023-09-01', 'ATIVO', '2023-09-10 11:00:00', false, false, false, true, NULL, '2023-10-31 23:59:59'),
(3, 'John Doe', 'john.doe@example.com', '$2b$12$MbbPyTNri4UlKr9Jy9cofuAbaDku839gexrTJPJBOEypfG0p8BVEO', 'ALUNO', '2023-09-01', 'ATIVO', '2023-09-10 10:00:00', true, true, false, true, NULL, '2023-10-30 23:59:59'),
(4, 'Jane Doe', 'jane.doe@example.com', '$2b$12$MbbPyTNri4UlKr9Jy9cofuAbaDku839gexrTJPJBOEypfG0p8BVEO', 'COORDENADOR', '2023-09-01', 'ATIVO', '2023-09-10 10:00:00', false, false, true, true, NULL, '2023-10-31 23:59:59'),
(5, 'Mark Smith', 'mark.smith@example.com', '$2b$12$MbbPyTNri4UlKr9Jy9cofuAbaDku839gexrTJPJBOEypfG0p8BVEO', 'ALUNO', '2023-09-01', 'ATIVO', '2023-09-10 10:00:00', true, false, false, true, NULL, '2023-10-31 23:59:59'),
(6, 'Eduardo de A', 'eduardo@example.com', '$2b$12$MbbPyTNri4UlKr9Jy9cofuAbaDku839gexrTJPJBOEypfG0p8BVEO', 'ALUNO', '2023-09-01', 'ATIVO', '2023-09-10 10:00:00', false, false, false, true, NULL, '2023-10-31 23:59:59'),
(7, 'Lucas Silva', 'lucas.silva@example.com', '$2b$12$XyNfuFnueK3fQ5rhysLhxYPR7PjqW5RQwfCnsdJ7Pt8KLgzVBND.', 'ALUNO', '2023-09-02', 'ATIVO', '2023-09-11 12:00:00', true, false, false, true, NULL, '2023-10-31 23:59:59'),
(8, 'Maria Clara', 'maria.clara@example.com', '$2b$12$XyNfuFnueK3fQ5rhysLhxYPR7PjqW5RQwfCnsdJ7Pt8KLgzVBND.', 'COORDENADOR', '2023-09-03', 'ATIVO', '2023-09-12 13:00:00', false, false, true, true, NULL, '2023-10-31 23:59:59'),
(9, 'Luck', 'luck@gmail.com', '$2a$10$v7W6WQOPcysvjizmIlSjE.R3noA3JtgxcKt4p1yrN8ij8lXHi5E8u', 'ADMINISTRADOR', '2023-09-03', 'ATIVO', '2023-09-12 13:00:00', false, false, false, true, NULL, '2023-10-31 23:59:59'),
(10, 'Carlos', 'Carlos@gmail.com', '$2a$10$v7W6WQOPcysvjizmIlSjE.R3noA3JtgxcKt4p1yrN8ij8lXHi5E8u', 'ADMINISTRADOR', '2023-09-03', 'ATIVO', '2023-09-12 13:00:00', false, false, false, true, NULL, '2023-10-31 23:59:59');


-- Inserção de Cursos
INSERT INTO curso (id, curso_nome, curso_modalidade, curso_turno, cod_curso) VALUES
(1, 'Engenharia de Software', 'Presencial', 'Diurno', 'ES001'),
(2, 'Ciência da Computação', 'Presencial', 'Noturno', 'CC002'),
(3, 'Administração', 'EAD', 'Integral', 'ADM003'),
(4, 'Engenharia Civil', 'Presencial', 'Diurno', 'EC004'),
(5, 'Direito', 'Presencial', 'Noturno', 'DIR005');

-- Inserção de Alunos
INSERT INTO aluno (id, usuario_id, curso_id, sobrenome, possui_deficiencia, deficiencias, cep, rua, bairro, cidade, estado, estado_civil, genero, dt_nascimento, naturalidade, etnia, bolsas, perido_bolsainicio, perido_bolsafim) VALUES
(1, 3, 1, 'Doe', false, '', '69900-000', 'Rua A', 'Centro', 'Rio Branco', 'AC', 'Solteiro', 'Masculino', '2000-01-01', 'Rio Branco', 'Branco', 'Nenhuma', NULL, NULL),
(2, 5, 2, 'Smith', true, 'Visual', '69000-000', 'Rua B', 'Bairro 2', 'Manaus', 'AM', 'Casado', 'Masculino', '1999-02-02', 'Manaus', 'Pardo', 'Bolsa Integral', '2019-02-01', '2022-11-01'),
(3, 6, 3, 'Santos', false, '', '01000-000', 'Rua C', 'Vila Maria', 'São Paulo', 'SP', 'Solteira', 'Feminino', '2000-03-03', 'São Paulo', 'Branca', 'Bolsa Parcial', '2021-02-01', '2023-11-01'),
(4, 7, 1, 'Silva', false, '', '04000-000', 'Rua D', 'Jardim', 'São Paulo', 'SP', 'Solteiro', 'Masculino', '2001-04-04', 'São Paulo', 'Pardo', 'Nenhuma', NULL, NULL);


-- Inserção de Coordenadores
INSERT IGNORE INTO coordenador (id, usuario_id, curso_id, portaria, status) VALUES
(1, 4, 1, 'Portaria ABC123', 'ATIVO'),
(2, 8, 2, 'Portaria XYZ456', 'ATIVO');

-- Inserção de Administradores
INSERT INTO administrador (id, usuario_id, nome, centro) VALUES
(1, 1, 'Admin User 1', 'Centro Acadêmico de Tecnologia'),
(2, 2, 'Admin User 2', 'Centro Acadêmico de Gestão');

-- Inserção de Disciplinas
INSERT INTO disciplina (id, curso_id, disciplina_nome, disciplina_codigo, disciplina_periodo, disciplina_creditos) VALUES
(1, 1, 'Programação Orientada a Objetos', 101, 2, 4),
(2, 1, 'Banco de Dados', 102, 3, 4),
(3, 2, 'Algoritmos', 201, 1, 3),
(4, 2, 'Estruturas de Dados', 202, 2, 4),
(5, 3, 'Administração de Empresas', 301, 3, 5);


-- Inserção de Logs
INSERT INTO logs (id, usuario_id, acao, data_acao) VALUES
(1, 1, 'Usuário logado', '2023-09-01 10:00:00'),
(2, 2, 'Atualizou informações pessoais', '2023-09-02 11:00:00'),
(3, 3, 'Alterou senha', '2023-09-03 12:00:00'),
(4, 4, 'Realizou logout', '2023-09-04 13:00:00'),
(5, 5, 'Logou novamente', '2023-09-05 14:00:00');


SET FOREIGN_KEY_CHECKS = 1;
