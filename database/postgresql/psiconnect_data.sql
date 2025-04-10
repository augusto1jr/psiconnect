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
INSERT INTO psicologos (crp, nome, email, foto, bio, formacao, contato, senha_hash, valor_consulta, aceita_beneficio, modalidade_atendimento) VALUES
('06/123456', 'Dr. João Silva', 'joao.silva@email.com', 'https://drive.google.com/file/d/1foyZOhrjLyKZHW47GE5l76_sFfrQhkp_/view?usp=drive_link', 'Especialista em terapia cognitiva.', 'USP - Psicologia', '(11) 98765-4321', 'senha_hash1', 150.00, TRUE, 'hibrido'),
('08/654321', 'Dra. Maria Souza', 'maria.souza@email.com', 'https://drive.google.com/file/d/1yNgQ-BYuo9RFI7NosaMF_wyt6B7wJkO8/view?usp=drive_link', 'Experiência em tratamento de ansiedade.', 'PUC - Psicologia', '(21) 97654-3210', 'senha_hash2', 180.00, FALSE, 'remoto'),
('09/111222', 'Dr. Carlos Mendes', 'carlos.mendes@email.com', 'https://drive.google.com/file/d/1Jp2zzlEA5ieZyRyesL9pPapFF4G32u0G/view?usp=drive_link', 'Atua com terapia sistêmica.', 'UFRJ - Psicologia', '(31) 99999-8888', 'senha_hash3', 200.00, TRUE, 'presencial');

-- Inserindo Endereços dos Psicólogos
INSERT INTO enderecos_psicologos (id_psicologo, rua, numero, complemento, bairro, cidade, estado, cep, latitude, longitude) VALUES
(1, 'Rua das Flores', '123', 'Sala 5', 'Centro', 'São Paulo', 'SP', '01000-000', -23.5505, -46.6333),
(2, 'Rua do Sol', '789', 'Conjunto 10', 'Jardins', 'Belo Horizonte', 'MG', '30100-000', -19.9208, -43.9378),
(3, 'Avenida Principal', '456', NULL, 'Bela Vista', 'Rio de Janeiro', 'RJ', '22000-000', -22.9068, -43.1729);

-- Inserindo Relacionamento entre Psicólogos e Especialidades
INSERT INTO psicologos_especialidades (id_psicologo, id_especialidade) VALUES
(1, 1), (1, 2), (2, 3), (2, 4), (3, 5), (3, 6);

-- Inserindo Relacionamento entre Psicólogos e Abordagens
INSERT INTO psicologos_abordagens (id_psicologo, id_abordagem) VALUES
(1, 1), (1, 2), (2, 3), (2, 4), (3, 5), (3, 6);


-- Inserindo Pacientes
INSERT INTO pacientes (cpf, nome, email, foto, bio, contato, senha_hash, beneficio_social) VALUES
('123.456.789-00', 'Ana Oliveira', 'ana.oliveira@email.com', 'https://drive.google.com/file/d/1aQejZLGgqP6nTt7cqrUOyyBLGWTFwHqV/view?usp=drive_link', 'Paciente com histórico de ansiedade.', '(11) 99999-5555', 'senha_hash4', 'estudante'),
('987.654.321-00', 'Bruno Santos', 'bruno.santos@email.com', 'https://drive.google.com/file/d/1NfxUoWBVSLBkSaFQeOVa4MMXT96n5M6t/view?usp=drive_link', 'Busca tratamento para traumas.', '(21) 98888-7777', 'senha_hash5', 'nenhum'),
('456.123.789-00', 'Carlos Ferreira', 'carlos.ferreira@email.com', 'https://drive.google.com/file/d/19AgA_vai7_nCVgeX3n4sxhuGTA9sMYIq/view?usp=drive_link', 'Precisa de ajuda com autoestima.', '(31) 97777-6666', 'senha_hash6', 'cadunico');

-- Inserindo Endereços dos Pacientes
INSERT INTO enderecos_pacientes (id_paciente, rua, numero, complemento, bairro, cidade, estado, cep, latitude, longitude) VALUES
(1, 'Rua Esperança', '100', 'Ap 22', 'Centro', 'São Paulo', 'SP', '01100-000', -23.5505, -46.6333),
(2, 'Avenida Brasil', '200', NULL, 'Copacabana', 'Rio de Janeiro', 'RJ', '22200-000', -22.9707, -43.1823),
(3, 'Rua das Palmeiras', '300', 'Casa 5', 'Savassi', 'Belo Horizonte', 'MG', '30140-000', -19.937, -43.9392);

-- Inserindo Relacionamento entre Pacientes e Especialidades
INSERT INTO preferencia_especialidades (id_paciente, id_especialidade) VALUES
(1, 1), (1, 3), (2, 2), (2, 4), (3, 5), (3, 6);

-- Inserindo Relacionamento entre Pacientes e Abordagens
INSERT INTO preferencia_abordagens (id_paciente, id_abordagem) VALUES
(1, 1), (1, 4), (2, 2), (2, 3), (3, 5), (3, 6);


-- Inserindo Consultas
INSERT INTO consultas (id_psicologo, id_paciente, data_consulta, status, modalidade, tipo, valor) VALUES
(1, 1, '2025-03-20 14:00:00', 'agendada', 'remota', 'social', 50.00),
(2, 2, '2025-03-21 15:30:00', 'agendada', 'remota', 'comum', 180.00),
(3, 3, '2025-03-22 16:00:00', 'agendada', 'presencial', 'social', 50.00);


-- Inserindo Avaliações
INSERT INTO avaliacoes (id_consulta, id_paciente, id_psicologo, nota, comentario, data_avaliacao) VALUES
(1, 1, 1, 5, 'Ótima consulta, muito esclarecedor!', '2025-03-21 14:30:00'),
(2, 2, 2, 4, 'Gostei muito, profissional excelente.', '2025-03-22 16:00:00'),
(3, 3, 3, 3, 'Foi bom, mas poderia ser melhor.', '2025-03-23 17:00:00');
