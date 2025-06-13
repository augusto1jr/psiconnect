---------------------------------------------------------------
-- Inserindo Especialidades
---------------------------------------------------------------
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


---------------------------------------------------------------
-- Inserindo Abordagens
---------------------------------------------------------------
INSERT INTO abordagens (nome_abordagem) VALUES
('Terapia Cognitivo Comportamental (TCC)'),
('Psicologia Comportamental'),
('Behaviorismo'),
('Psicanálise'),
('Gestalt-terapia'),
('Humanismo'),
('Terapia Sistêmica'),
('Terapia Junguiana');


---------------------------------------------------------------
-- Inserindo Psicólogos
---------------------------------------------------------------
INSERT INTO psicologos (crp, nome, email, foto, bio, formacao, contato, senha_hash, valor_consulta, aceita_beneficio, modalidade_atendimento) VALUES
('06/123456', 'João Silva', 'joao.silva@email.com', 'https://i.pravatar.cc/150?u=1', 'Descomplicando o processo de terapia para quem busca autoconhecimento, crescimento pessoal, autoestima e comunicação assertiva. Tratamento para ansiedade, conflitos nos relacionamentos, estresse, entre outros.', 'USP - Psicologia', '(11) 98765-4321', 'senha_hash1', 150.00, TRUE, 'HIBRIDO'),
('08/654321', 'Maria Souza', 'maria.souza@email.com', 'https://i.pravatar.cc/150?u=2', 'Apoio psicológico para desenvolvimento emocional, enfrentamento da ansiedade e fortalecimento da autoestima. Trabalhando sua autonomia e qualidade de vida.', 'PUC - Psicologia', '(21) 97654-3210', 'senha_hash2', 180.00, FALSE, 'REMOTO'),
('09/111222', 'Carlos Mendes', 'carlos.mendes@email.com', 'https://i.pravatar.cc/150?u=3', 'Auxiliando no desenvolvimento pessoal, controle da ansiedade, enfrentamento de traumas e melhora da comunicação interpessoal. Vamos construir juntos esse caminho.', 'UFRJ - Psicologia', '(31) 99999-8888', 'senha_hash3', 200.00, TRUE, 'PRESENCIAL'),
('07/333444', 'Fernanda Lima', 'fernanda.lima@email.com', 'https://i.pravatar.cc/150?u=4', 'Minha missão é te ajudar a construir uma jornada de autoconhecimento, equilíbrio emocional e superação de desafios como ansiedade, insegurança e relacionamentos.', 'USP - Psicologia', '(11) 91234-5678', 'senha_hash4', 170.00, TRUE, 'REMOTO'),
('05/555666', 'Rafael Costa', 'rafael.costa@email.com', 'https://i.pravatar.cc/150?u=5', 'Atendimento focado no desenvolvimento da autoestima, enfrentamento da ansiedade e resolução de conflitos familiares e afetivos.', 'PUC - Psicologia', '(21) 92345-6789', 'senha_hash5', 160.00, FALSE, 'HIBRIDO'),
('04/777888', 'Aline Pereira', 'aline.pereira@email.com', 'https://i.pravatar.cc/150?u=6', 'Trabalho com acolhimento, empatia e escuta ativa. Auxílio no enfrentamento de traumas, ansiedade, autoestima e desenvolvimento pessoal.', 'UFRJ - Psicologia', '(31) 93456-7890', 'senha_hash6', 155.00, TRUE, 'PRESENCIAL'),
('03/999000', 'Bruno Almeida', 'bruno.almeida@email.com', 'https://i.pravatar.cc/150?u=7', 'Ofereço suporte psicológico para quem busca compreender suas emoções, superar desafios, fortalecer vínculos e melhorar sua qualidade de vida.', 'USP - Psicologia', '(11) 94567-8901', 'senha_hash7', 180.00, TRUE, 'REMOTO'),
('02/111333', 'Camila Rocha', 'camila.rocha@email.com', 'https://i.pravatar.cc/150?u=8', 'Ajudo pessoas no desenvolvimento da autoconfiança, enfrentamento da ansiedade, traumas e dificuldades emocionais, sempre com acolhimento e ética.', 'PUC - Psicologia', '(21) 95678-9012', 'senha_hash8', 175.00, FALSE, 'HIBRIDO'),
('01/222444', 'Diego Martins', 'diego.martins@email.com', 'https://i.pravatar.cc/150?u=9', 'Meu objetivo é promover saúde mental, bem-estar emocional e fortalecer sua autonomia na construção de uma vida mais leve e significativa.', 'UFRJ - Psicologia', '(31) 96789-0123', 'senha_hash9', 190.00, TRUE, 'REMOTO'),
('06/555777', 'Elisa Ramos', 'elisa.ramos@email.com', 'https://i.pravatar.cc/150?u=10', 'Atendimento focado na escuta empática, no desenvolvimento pessoal, autoestima e superação de dificuldades emocionais, como ansiedade e estresse.', 'USP - Psicologia', '(11) 97890-1234', 'senha_hash10', 165.00, FALSE, 'PRESENCIAL');


---------------------------------------------------------------
-- Inserindo Endereços dos Psicólogos
---------------------------------------------------------------
INSERT INTO enderecos_psicologos (id_psicologo, rua, numero, complemento, bairro, cidade, estado, cep, latitude, longitude) VALUES
(1, 'Rua das Flores', '123', 'Sala 5', 'Centro', 'São Paulo', 'SP', '01000-000', -23.5505, -46.6333),
(2, 'Rua do Sol', '789', 'Conjunto 10', 'Jardins', 'Belo Horizonte', 'MG', '30100-000', -19.9208, -43.9378),
(3, 'Avenida Principal', '456', NULL, 'Bela Vista', 'Rio de Janeiro', 'RJ', '22000-000', -22.9068, -43.1729),
(4, 'Rua das Acácias', '55', 'Sala 2', 'Pinheiros', 'São Paulo', 'SP', '05422-000', -23.5617, -46.6822),
(5, 'Av. Paulista', '1000', 'Conj. 1502', 'Bela Vista', 'São Paulo', 'SP', '01310-100', -23.5614, -46.6554),
(6, 'Rua Amazonas', '321', NULL, 'Savassi', 'Belo Horizonte', 'MG', '30140-130', -19.937, -43.9392),
(7, 'Rua das Laranjeiras', '77', 'Sala 8', 'Laranjeiras', 'Rio de Janeiro', 'RJ', '22240-003', -22.9319, -43.1785),
(8, 'Rua Oscar Freire', '500', 'Ap 101', 'Jardins', 'São Paulo', 'SP', '01426-001', -23.564, -46.6601),
(9, 'Av. Atlântica', '1500', NULL, 'Copacabana', 'Rio de Janeiro', 'RJ', '22021-001', -22.9711, -43.1822),
(10, 'Av. Afonso Pena', '400', 'Sala 12', 'Centro', 'Belo Horizonte', 'MG', '30130-003', -19.9191, -43.9386);


---------------------------------------------------------------
-- Inserindo Pacientes
---------------------------------------------------------------
INSERT INTO pacientes (cpf, nome, email, foto, bio, contato, senha_hash, beneficio_social) VALUES
('123.456.789-00', 'Ana Oliveira', 'ana.oliveira@email.com', 'https://i.pravatar.cc/150?u=11', 'Em busca de desenvolvimento pessoal e controle da ansiedade.', '(11) 99999-5555', 'senha_hash11', 'ESTUDANTE'),
('987.654.321-00', 'Bruno Santos', 'bruno.santos@email.com', 'https://i.pravatar.cc/150?u=12', 'Buscando ajuda para superar traumas e melhorar sua autoestima.', '(21) 98888-7777', 'senha_hash12', 'NENHUM'),
('456.123.789-00', 'Carlos Ferreira', 'carlos.ferreira@email.com', 'https://i.pravatar.cc/150?u=13', 'Em processo de autoconhecimento e enfrentamento de inseguranças.', '(31) 97777-6666', 'senha_hash13', 'CADUNICO'),
('321.654.987-00', 'Daniela Lima', 'daniela.lima@email.com', 'https://i.pravatar.cc/150?u=14', 'Desejo aprender a lidar com ansiedade e melhorar seus relacionamentos.', '(11) 98888-2222', 'senha_hash14', 'ESTUDANTE'),
('654.987.321-00', 'Eduardo Souza', 'eduardo.souza@email.com', 'https://i.pravatar.cc/150?u=15', 'Procuro apoio para enfrentar estresse e insegurança profissional.', '(21) 97777-3333', 'senha_hash15', 'NENHUM'),
('789.123.456-00', 'Fernanda Costa', 'fernanda.costa@email.com', 'https://i.pravatar.cc/150?u=16', 'Buscando equilíbrio emocional e desenvolvimento pessoal.', '(31) 96666-4444', 'senha_hash16', 'CADUNICO'),
('159.357.456-00', 'Gustavo Pereira', 'gustavo.pereira@email.com', 'https://i.pravatar.cc/150?u=17', 'Desejo enfrentar traumas passados e melhorar sua autoestima.', '(11) 95555-6666', 'senha_hash17', 'ESTUDANTE'),
('258.456.789-00', 'Helena Martins', 'helena.martins@email.com', 'https://i.pravatar.cc/150?u=18', 'Em busca de autoconhecimento, superação de medos e ansiedade.', '(21) 94444-7777', 'senha_hash18', 'NENHUM'),
('369.258.147-00', 'Igor Rocha', 'igor.rocha@email.com', 'https://i.pravatar.cc/150?u=19', 'Preciso de apoio psicológico para lidar com estresse e inseguranças.', '(31) 93333-8888', 'senha_hash19', 'CADUNICO'),
('741.852.963-00', 'Juliana Alves', 'juliana.alves@email.com', 'https://i.pravatar.cc/150?u=20', 'Buscando ajuda para desenvolver autoconfiança e melhorar relações interpessoais.', '(11) 92222-9999', 'senha_hash20', 'NENHUM');


---------------------------------------------------------------
-- Inserindo Endereços dos Pacientes
---------------------------------------------------------------
INSERT INTO enderecos_pacientes (id_paciente, rua, numero, complemento, bairro, cidade, estado, cep, latitude, longitude) VALUES
(1, 'Rua das Rosas', '321', 'Ap 101', 'Centro', 'São Paulo', 'SP', '01001-000', -23.5505, -46.6333),
(2, 'Rua da Liberdade', '456', NULL, 'Savassi', 'Belo Horizonte', 'MG', '30150-000', -19.9208, -43.9378),
(3, 'Av. dos Lagos', '789', 'Bloco B', 'Copacabana', 'Rio de Janeiro', 'RJ', '22030-000', -22.9068, -43.1729),
(4, 'Rua das Palmeiras', '100', 'Casa 5', 'Pinheiros', 'São Paulo', 'SP', '05424-000', -23.5617, -46.6822),
(5, 'Av. Brasil', '1500', 'Ap 502', 'Jardins', 'São Paulo', 'SP', '01430-000', -23.5614, -46.6554),
(6, 'Rua Minas Gerais', '200', NULL, 'Savassi', 'Belo Horizonte', 'MG', '30160-130', -19.937, -43.9392),
(7, 'Rua das Amendoeiras', '75', 'Casa 2', 'Laranjeiras', 'Rio de Janeiro', 'RJ', '22250-003', -22.9319, -43.1785),
(8, 'Rua Augusta', '880', 'Ap 303', 'Consolação', 'São Paulo', 'SP', '01305-100', -23.564, -46.6601),
(9, 'Av. Vieira Souto', '1700', NULL, 'Ipanema', 'Rio de Janeiro', 'RJ', '22420-002', -22.9711, -43.1822),
(10, 'Av. Amazonas', '550', 'Sala 10', 'Centro', 'Belo Horizonte', 'MG', '30180-003', -19.9191, -43.9386);


---------------------------------------------------------------
-- Relacionamento Psicólogos com Especialidades (8 cada)
---------------------------------------------------------------
INSERT INTO psicologos_especialidades (id_psicologo, id_especialidade) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8),
(2, 2), (2, 3), (2, 4), (2, 5), (2, 6), (2, 7), (2, 8), (2, 9),
(3, 3), (3, 4), (3, 5), (3, 6), (3, 7), (3, 8), (3, 9), (3, 10),
(4, 1), (4, 2), (4, 3), (4, 4), (4, 5), (4, 6), (4, 7), (4, 9),
(5, 2), (5, 3), (5, 4), (5, 5), (5, 6), (5, 7), (5, 8), (5, 10),
(6, 1), (6, 3), (6, 4), (6, 5), (6, 6), (6, 7), (6, 9), (6, 10),
(7, 1), (7, 2), (7, 4), (7, 5), (7, 6), (7, 8), (7, 9), (7, 10),
(8, 1), (8, 2), (8, 3), (8, 4), (8, 6), (8, 7), (8, 9), (8, 10),
(9, 2), (9, 3), (9, 4), (9, 5), (9, 7), (9, 8), (9, 9), (9, 10),
(10, 1), (10, 3), (10, 5), (10, 6), (10, 7), (10, 8), (10, 9), (10, 10);

---------------------------------------------------------------
-- Relacionamento Psicólogos com Abordagens (2 cada)
---------------------------------------------------------------
INSERT INTO psicologos_abordagens (id_psicologo, id_abordagem) VALUES
(1, 1), (1, 4),
(2, 2), (2, 5),
(3, 3), (3, 6),
(4, 1), (4, 7),
(5, 2), (5, 8),
(6, 3), (6, 4),
(7, 5), (7, 6),
(8, 7), (8, 8),
(9, 1), (9, 5),
(10, 2), (10, 6);

---------------------------------------------------------------
-- Relacionamento Pacientes com Preferências de Especialidade (4 cada)
---------------------------------------------------------------
INSERT INTO preferencia_especialidades (id_paciente, id_especialidade) VALUES
(1, 1), (1, 2), (1, 3), (1, 4),
(2, 2), (2, 3), (2, 5), (2, 6),
(3, 3), (3, 4), (3, 7), (3, 8),
(4, 1), (4, 5), (4, 6), (4, 7),
(5, 2), (5, 3), (5, 8), (5, 9),
(6, 1), (6, 4), (6, 5), (6, 10),
(7, 3), (7, 5), (7, 6), (7, 7),
(8, 2), (8, 4), (8, 7), (8, 9),
(9, 1), (9, 5), (9, 8), (9, 10),
(10, 3), (10, 4), (10, 6), (10, 7);

---------------------------------------------------------------
-- Relacionamento Pacientes com Preferências de Abordagem (1 cada)
---------------------------------------------------------------
INSERT INTO preferencia_abordagens (id_paciente, id_abordagem) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 1),
(10, 2);


---------------------------------------------------------------
-- Inserindo Consultas (30 consultas)
---------------------------------------------------------------
INSERT INTO consultas (id_paciente, id_psicologo, data_consulta, status, modalidade, tipo, valor) VALUES
(1, 1, '2025-05-01 10:00:00', 'CONCLUIDA', 'REMOTA', 'COMUM', 150.00),
(2, 2, '2025-05-01 10:00:00', 'CONCLUIDA', 'REMOTA', 'COMUM', 150.00),
(6, 2, '2025-05-01 10:00:00', 'CANCELADA', 'REMOTA', 'COMUM', 150.00),
(1, 2, '2025-05-01 10:00:00', 'AGENDADA', 'REMOTA', 'COMUM', 150.00),
(2, 2, '2025-05-11 11:00:00', 'CONCLUIDA', 'REMOTA', 'COMUM', 180.00),
(3, 3, '2025-05-01 12:00:00', 'CONCLUIDA', 'PRESENCIAL', 'SOCIAL', 100.00),
(4, 4, '2025-05-02 09:00:00', 'CANCELADA', 'REMOTA', 'COMUM', 170.00),
(5, 5, '2025-05-02 10:00:00', 'CONCLUIDA', 'REMOTA', 'COMUM', 160.00),
(6, 6, '2025-05-02 11:00:00', 'AGENDADA', 'PRESENCIAL', 'SOCIAL', 80.00),
(7, 7, '2025-05-02 12:00:00', 'CONCLUIDA', 'REMOTA', 'COMUM', 180.00),
(8, 8, '2025-05-03 09:00:00', 'AGENDADA', 'REMOTA', 'SOCIAL', 90.00),
(9, 9, '2025-05-03 10:00:00', 'CONCLUIDA', 'REMOTA', 'COMUM', 190.00),
(10, 10, '2025-05-03 11:00:00', 'CONCLUIDA', 'PRESENCIAL', 'COMUM', 165.00),
(1, 2, '2025-05-04 10:00:00', 'CONCLUIDA', 'REMOTA', 'COMUM', 180.00),
(2, 3, '2025-05-04 11:00:00', 'CONCLUIDA', 'PRESENCIAL', 'COMUM', 200.00),
(3, 4, '2025-05-04 12:00:00', 'CONCLUIDA', 'REMOTA', 'SOCIAL', 100.00),
(4, 5, '2025-05-05 09:00:00', 'CANCELADA', 'REMOTA', 'COMUM', 160.00),
(5, 6, '2025-05-05 10:00:00', 'CONCLUIDA', 'PRESENCIAL', 'SOCIAL', 80.00),
(6, 7, '2025-05-05 11:00:00', 'AGENDADA', 'REMOTA', 'COMUM', 180.00),
(7, 8, '2025-05-05 12:00:00', 'AGENDADA', 'REMOTA', 'SOCIAL', 90.00),
(8, 9, '2025-05-06 09:00:00', 'CONCLUIDA', 'REMOTA', 'COMUM', 190.00),
(9, 10, '2025-05-06 10:00:00', 'CONCLUIDA', 'PRESENCIAL', 'COMUM', 165.00),
(10, 1, '2025-05-06 11:00:00', 'CONCLUIDA', 'REMOTA', 'COMUM', 150.00),
(1, 3, '2025-05-07 10:00:00', 'CONCLUIDA', 'PRESENCIAL', 'COMUM', 200.00),
(2, 4, '2025-05-07 11:00:00', 'CONCLUIDA', 'REMOTA', 'SOCIAL', 100.00),
(3, 5, '2025-05-07 12:00:00', 'CONCLUIDA', 'REMOTA', 'COMUM', 160.00),
(4, 6, '2025-05-08 09:00:00', 'CANCELADA', 'PRESENCIAL', 'COMUM', 155.00),
(5, 7, '2025-05-08 10:00:00', 'CONCLUIDA', 'REMOTA', 'SOCIAL', 90.00),
(6, 8, '2025-05-08 11:00:00', 'AGENDADA', 'REMOTA', 'COMUM', 175.00),
(7, 9, '2025-05-08 12:00:00', 'CONCLUIDA', 'REMOTA', 'COMUM', 190.00),
(8, 10, '2025-05-09 09:00:00', 'CONCLUIDA', 'PRESENCIAL', 'SOCIAL', 85.00),
(9, 1, '2025-05-09 10:00:00', 'CONCLUIDA', 'REMOTA', 'COMUM', 150.00),
(10, 2, '2025-05-09 11:00:00', 'CONCLUIDA', 'REMOTA', 'COMUM', 180.00);


---------------------------------------------------------------
-- Inserindo Avaliações (10 avaliações)
---------------------------------------------------------------
INSERT INTO avaliacoes (id_consulta, id_paciente, id_psicologo, nota, comentario) VALUES
(1, 1, 1, 5, 'Excelente atendimento, muito empático e profissional.'),
(3, 3, 3, 4, 'Gostei bastante, me senti acolhido.'),
(5, 5, 5, 5, 'Ótima profissional, muito atenciosa.'),
(7, 7, 7, 4, 'Foi bom, mas achei a plataforma um pouco confusa.'),
(9, 9, 9, 5, 'Atendimento maravilhoso, super recomendo.'),
(11, 1, 2, 5, 'Me senti muito bem na consulta.'),
(13, 3, 4, 4, 'Psicólogo muito profissional, gostei.'),
(15, 5, 6, 5, 'Ajudou bastante a entender minhas questões.'),
(17, 7, 8, 5, 'Atendimento excelente, recomendo fortemente.'),
(19, 9, 10, 4, 'Ótima profissional, só atrasou um pouco.');

