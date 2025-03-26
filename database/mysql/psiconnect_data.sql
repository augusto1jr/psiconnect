-- ########## Inserindo Especialidades ##########
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
('Autoestima'),
('Estresse'),
('Transtornos Alimentares');

-- ########## Inserindo Abordagens ##########
INSERT INTO abordagens (nome_abordagem) VALUES
('TCC (Terapia Cognitivo Comportamental)'),
('Psicologia Comportamental'),
('Behaviorismo'),
('Psicanálise'),
('Gestalt-terapia'),
('Humanismo'),
('Terapia Sistêmica'),
('Neuropsicologia');

-- ########## Inserindo Psicólogos ##########
INSERT INTO psicologos (crp, nome_psicologo, email_psicologo, bio_psicologo, formacao, contato, senha_hash, valor_padrao_consulta, aceita_valor_social, modalidade_atendimento) VALUES
('06/123456', 'Dr. João Silva', 'joao.silva@email.com', 'Especialista em TCC.', 'USP', '(11) 91234-5678', 'senha_hash1', 150.00, TRUE, 'remoto'),
('07/654321', 'Dra. Maria Oliveira', 'maria.oliveira@email.com', 'Foco em traumas e luto.', 'PUC', '(21) 92345-6789', 'senha_hash2', 180.00, FALSE, 'presencial'),
('08/112233', 'Dr. Ricardo Mendes', 'ricardo.mendes@email.com', 'Atendimento humanizado.', 'Unicamp', '(31) 93456-7890', 'senha_hash3', 200.00, TRUE, 'hibrido');

-- ########## Inserindo Endereços dos Psicólogos ##########
INSERT INTO enderecos_psicologos (id_psicologo, rua, numero, complemento, bairro, cidade, estado, cep, latitude, longitude) VALUES
(2, 'Rua das Flores', '123', 'Sala 4', 'Centro', 'São Paulo', 'SP', '01000-000', -23.5505, -46.6333),
(3, 'Av. Paulista', '987', NULL, 'Bela Vista', 'São Paulo', 'SP', '01311-000', -23.5631, -46.6544);

-- ########## Inserindo Pacientes ##########
INSERT INTO pacientes (cpf, nome_paciente, email_paciente, bio_paciente, contato, senha_hash, beneficio_social) VALUES
('123.456.789-00', 'Carlos Souza', 'carlos.souza@email.com', 'Buscando melhorar sua autoestima.', '(41) 99876-5432', 'senha_hash4', 'estudante'),
('987.654.321-00', 'Ana Pereira', 'ana.pereira@email.com', 'Sofre de ansiedade.', '(42) 98765-4321', 'senha_hash5', 'cadunico'),
('456.789.123-00', 'Fernando Lima', 'fernando.lima@email.com', 'Quer aprimorar sua carreira.', '(43) 97654-3210', 'senha_hash6', 'nenhum');

-- ########## Inserindo Endereços dos Pacientes ##########
INSERT INTO enderecos_pacientes (id_paciente, rua, numero, complemento, bairro, cidade, estado, cep, latitude, longitude) VALUES
(1, 'Rua das Acácias', '22', 'Apto 304', 'Centro', 'Curitiba', 'PR', '80000-000', -25.4284, -49.2733),
(2, 'Av. Brasil', '1000', NULL, 'Centro', 'Rio de Janeiro', 'RJ', '20000-000', -22.9068, -43.1729);

-- ########## Inserindo Consultas ##########
INSERT INTO consultas (id_psicologo, id_paciente, data_consulta, status_consulta, tipo_consulta, plataforma_link, valor_consulta) VALUES
(1, 1, '2025-03-10 10:00:00', 'agendada', 'remota', 'https://meet.example.com/joaosilva', 50.00),
(2, 2, '2025-03-11 15:00:00', 'agendada', 'presencial', NULL, 180.00),
(3, 3, '2025-03-12 18:30:00', 'concluída', 'remota', 'https://meet.example.com/ricardomendes', 200.00);

-- ########## Inserindo Avaliações ##########
INSERT INTO avaliacoes (id_consulta, id_paciente, id_psicologo, nota, comentario, data_avaliacao) VALUES
(3, 3, 3, 5, 'Ótimo atendimento, me senti acolhido.', '2025-03-13 09:00:00');

-- ########## Relacionando Psicólogos com Especialidades ##########
INSERT INTO psicologo_especialidade (id_psicologo, id_especialidade) VALUES
(1, 1), (1, 2), (1, 3),
(2, 4), (2, 5),
(3, 6), (3, 7), (3, 8);

-- ########## Relacionando Psicólogos com Abordagens ##########
INSERT INTO psicologo_abordagem (id_psicologo, id_abordagem) VALUES
(1, 1), (1, 2),
(2, 3), (2, 4),
(3, 5), (3, 6);

-- ########## Relacionando Pacientes com Especialidades de Interesse ##########
INSERT INTO preferencia_especialidades (id_paciente, id_especialidade) VALUES
(1, 1), (1, 9),
(2, 2), (2, 10),
(3, 8);

-- ########## Relacionando Pacientes com Abordagens de Interesse ##########
INSERT INTO preferencia_abordagem (id_paciente, id_abordagem) VALUES
(1, 1),
(2, 3),
(3, 6);
