-- Inserindo Especialidades
INSERT INTO especialidades (nome_especialidade) VALUES
('Depressão'),
('Ansiedade'),
('Luto'),
('Insegurança'),
('Sexualidade'),
('Traumas'),
('Relacionamento'),
('Carreira'),
('Fobias'),
('Autoestima');

-- Inserindo Abordagens
INSERT INTO abordagens (nome_abordagem) VALUES
('Terapia Cognitivo Comportamental (TCC)'),
('Psicologia Comportamental'),
('Behaviorismo'),
('Psicanálise'),
('Gestalt-terapia'),
('Humanismo'),
('Terapia Sistêmica'),
('Terapia Junguiana');

-- Inserindo Psicólogos
INSERT INTO psicologos (crp, nome_psicologo, email_psicologo, bio_psicologo, formacao_psicologo, contato_psicologo, senha_hash, valor_padrao_consulta, aceita_valor_social, modalidade_atendimento) VALUES
('06/123456', 'Dr. João Silva', 'joao.silva@email.com', 'Especialista em terapia cognitiva.', 'USP - Psicologia', '(11) 98765-4321', 'senha_hash1', 150.00, TRUE, 'hibrido'),
('08/654321', 'Dra. Maria Souza', 'maria.souza@email.com', 'Experiência em tratamento de ansiedade.', 'PUC - Psicologia', '(21) 97654-3210', 'senha_hash2', 180.00, FALSE, 'remoto'),
('09/111222', 'Dr. Carlos Mendes', 'carlos.mendes@email.com', 'Atua com terapia sistêmica.', 'UFRJ - Psicologia', '(31) 99999-8888', 'senha_hash3', 200.00, TRUE, 'presencial');

-- Inserindo Endereços dos Psicólogos (apenas para quem atende presencialmente)
INSERT INTO enderecos_psicologos (id_psicologo, rua, numero, complemento, bairro, cidade, estado, cep, latitude, longitude) VALUES
(1, 'Rua das Flores', '123', 'Sala 5', 'Centro', 'São Paulo', 'SP', '01000-000', -23.5505, -46.6333),
(3, 'Avenida Principal', '456', NULL, 'Bela Vista', 'Rio de Janeiro', 'RJ', '22000-000', -22.9068, -43.1729);

-- Inserindo Pacientes
INSERT INTO pacientes (cpf, nome_paciente, email_paciente, bio_paciente, contato_paciente, senha_hash, beneficio_social) VALUES
('123.456.789-00', 'Ana Oliveira', 'ana.oliveira@email.com', 'Paciente com histórico de ansiedade.', '(11) 99999-5555', 'senha_hash4', 'estudante'),
('987.654.321-00', 'Bruno Santos', 'bruno.santos@email.com', 'Busca tratamento para traumas.', '(21) 98888-7777', 'senha_hash5', 'nenhum'),
('456.123.789-00', 'Carla Ferreira', 'carla.ferreira@email.com', 'Precisa de ajuda com autoestima.', '(31) 97777-6666', 'senha_hash6', 'cadunico');

-- Inserindo Endereços dos Pacientes
INSERT INTO enderecos_pacientes (id_paciente, rua, numero, complemento, bairro, cidade, estado, cep, latitude, longitude) VALUES
(1, 'Rua Esperança', '100', 'Ap 22', 'Centro', 'São Paulo', 'SP', '01100-000', -23.5505, -46.6333),
(2, 'Avenida Brasil', '200', NULL, 'Copacabana', 'Rio de Janeiro', 'RJ', '22200-000', -22.9707, -43.1823);

-- Inserindo Relacionamento entre Psicólogos e Especialidades
INSERT INTO psicologo_especialidade (id_psicologo, id_especialidade) VALUES
(1, 1), (1, 2), (2, 3), (2, 4), (3, 5), (3, 6);

-- Inserindo Relacionamento entre Psicólogos e Abordagens
INSERT INTO psicologo_abordagem (id_psicologo, id_abordagem) VALUES
(1, 1), (1, 2), (2, 3), (2, 4), (3, 5), (3, 6);

-- Inserindo Consultas
INSERT INTO consultas (id_psicologo, id_paciente, data_consulta, status_consulta, tipo_consulta, plataforma_link, valor_consulta) VALUES
(1, 1, '2025-03-20 14:00:00', 'agendada', 'remota', 'https://meet.example.com/joao123', 50.00),
(2, 2, '2025-03-21 15:30:00', 'agendada', 'remota', 'https://meet.example.com/maria456', 180.00),
(3, 3, '2025-03-22 16:00:00', 'agendada', 'presencial', NULL, 50.00);

-- Inserindo Avaliações
INSERT INTO avaliacoes (id_consulta, id_paciente, id_psicologo, nota, comentario, data_avaliacao) VALUES
(1, 1, 1, 5, 'Ótima consulta, muito esclarecedor!', '2025-03-21 14:30:00'),
(2, 2, 2, 4, 'Gostei muito, profissional excelente.', '2025-03-22 16:00:00'),
(3, 3, 3, 3, 'Foi bom, mas poderia ser melhor.', '2025-03-23 17:00:00');
