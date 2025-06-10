-- ########## CRIANDO O BANCO DE DADOS ##########

CREATE DATABASE IF NOT EXISTS psiconnect;

USE psiconnect;

-- ########## Tabelas Relacionadas aos Psicólogos ##########

-- Tabela de Psicólogos
CREATE TABLE psicologos (
    id_psicologo INT AUTO_INCREMENT PRIMARY KEY,
    crp VARCHAR(10) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    foto VARCHAR(200) NOT NULL,
    bio VARCHAR(300),
    formacao VARCHAR(300),
    contato VARCHAR(50) NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    valor_consulta DECIMAL(10,2) NOT NULL,
    aceita_beneficio BOOLEAN DEFAULT FALSE,
    modalidade_atendimento ENUM('remoto', 'presencial', 'hibrido') NOT NULL DEFAULT 'remoto'
);

-- Tabela de Endereços dos Psicólogos
CREATE TABLE enderecos_psicologos (
    id_psicologo INT PRIMARY KEY,
    rua VARCHAR(150) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    complemento VARCHAR(50),
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    cep VARCHAR(10) NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    FOREIGN KEY (id_psicologo) REFERENCES psicologos(id_psicologo) ON DELETE CASCADE
);

-- Tabela de Especialidades
CREATE TABLE especialidades (
    id_especialidade INT AUTO_INCREMENT PRIMARY KEY,
    nome_especialidade VARCHAR(100) NOT NULL UNIQUE
);

-- Tabela de Abordagens Terapêuticas
CREATE TABLE abordagens (
    id_abordagem INT AUTO_INCREMENT PRIMARY KEY,
    nome_abordagem VARCHAR(100) NOT NULL UNIQUE
);

-- Relacionamento entre Psicólogos e Especialidades (N:N)
CREATE TABLE psicologos_especialidades (
    id_psicologo INT NOT NULL,
    id_especialidade INT NOT NULL,
    PRIMARY KEY (id_psicologo, id_especialidade),
    FOREIGN KEY (id_psicologo) REFERENCES psicologos(id_psicologo) ON DELETE CASCADE,
    FOREIGN KEY (id_especialidade) REFERENCES especialidades(id_especialidade) ON DELETE CASCADE
);

-- Relacionamento entre Psicólogos e Abordagens (N:N)
CREATE TABLE psicologos_abordagens (
    id_psicologo INT NOT NULL,
    id_abordagem INT NOT NULL,
    PRIMARY KEY (id_psicologo, id_abordagem),
    FOREIGN KEY (id_psicologo) REFERENCES psicologos(id_psicologo) ON DELETE CASCADE,
    FOREIGN KEY (id_abordagem) REFERENCES abordagens(id_abordagem) ON DELETE CASCADE
);


-- ########## Tabelas Relacionadas aos Pacientes ##########

-- Tabela de Pacientes
CREATE TABLE pacientes (
    id_paciente INT AUTO_INCREMENT PRIMARY KEY,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    foto VARCHAR(200) NOT NULL UNIQUE,
    bio VARCHAR(300),
    contato VARCHAR(50) NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    beneficio_social ENUM('nenhum', 'estudante', 'cadunico') NOT NULL DEFAULT 'nenhum'
);

-- Tabela de Endereços dos Pacientes
CREATE TABLE enderecos_pacientes (
    id_paciente INT PRIMARY KEY,
    rua VARCHAR(150) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    complemento VARCHAR(50),
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    cep VARCHAR(10) NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente) ON DELETE CASCADE
);

-- Tabela de Preferências de Especialidades dos Pacientes
CREATE TABLE preferencia_especialidades (
    id_paciente INT NOT NULL,
    id_especialidade INT NOT NULL,
    PRIMARY KEY (id_paciente, id_especialidade),
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente) ON DELETE CASCADE,
    FOREIGN KEY (id_especialidade) REFERENCES especialidades(id_especialidade) ON DELETE CASCADE
);

-- Tabela de Preferências de Abordagem dos Pacientes
CREATE TABLE preferencia_abordagens (
    id_paciente INT NOT NULL,
    id_abordagem INT NOT NULL,
    PRIMARY KEY (id_paciente, id_abordagem),
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente) ON DELETE CASCADE,
    FOREIGN KEY (id_abordagem) REFERENCES abordagens(id_abordagem) ON DELETE CASCADE
);


-- ########## Tabelas de Relacionamento entre os Psicólogos e Pacientes ##########

-- Tabela de Consultas
CREATE TABLE consultas (
    id_consulta INT AUTO_INCREMENT PRIMARY KEY,
    id_psicologo INT NOT NULL,
    id_paciente INT NOT NULL,
    data_consulta DATETIME NOT NULL,
    status ENUM('agendada', 'concluida', 'cancelada') NOT NULL DEFAULT 'agendada',
    modalidade ENUM('remota', 'presencial') NOT NULL,
    tipo ENUM('comum', 'social') NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_psicologo) REFERENCES psicologos(id_psicologo) ON DELETE CASCADE,
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente) ON DELETE CASCADE
);

-- Tabela de Avaliações
CREATE TABLE avaliacoes (
    id_avaliacao INT AUTO_INCREMENT PRIMARY KEY,
    id_consulta INT UNIQUE NOT NULL,
    id_paciente INT NOT NULL,
    id_psicologo INT NOT NULL,
    nota INT CHECK (nota BETWEEN 1 AND 5),
    comentario TEXT,
    data_avaliacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_consulta) REFERENCES consultas(id_consulta) ON DELETE CASCADE,
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente) ON DELETE CASCADE,
    FOREIGN KEY (id_psicologo) REFERENCES psicologos(id_psicologo) ON DELETE CASCADE
);

-- ########## TRIGGER Valor Social ##########

DELIMITER //

CREATE TRIGGER trigger_aplicar_valor_social
BEFORE INSERT ON consultas
FOR EACH ROW
BEGIN
    DECLARE beneficio VARCHAR(20);
    DECLARE aceita_beneficio BOOLEAN;
    DECLARE valor_base DECIMAL(10,2);

    -- Obtendo informações do paciente e psicólogo
    SELECT beneficio_social INTO beneficio FROM pacientes WHERE id_paciente = NEW.id_paciente;
    SELECT aceita_beneficio, valor_consulta INTO aceita_beneficio, valor_base
    FROM psicologos WHERE id_psicologo = NEW.id_psicologo;

    -- Definir o valor da consulta com base no tipo
    IF NEW.tipo = 'social' THEN
        IF (beneficio = 'estudante' OR beneficio = 'cadunico') AND aceita_beneficio = TRUE THEN
            SET NEW.valor = 50.00;
        ELSE
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'A consulta foi marcada como social, mas o paciente não tem direito ou o psicólogo não aceita valor social';
        END IF;
    ELSE
        SET NEW.valor = IFNULL(valor_base, NEW.valor);
    END IF;
END//

DELIMITER ;