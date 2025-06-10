-- ########## Tabelas Relacionadas aos Psicólogos ##########

-- Tabela de Psicólogos
CREATE TABLE psicologos (
    id_psicologo SERIAL PRIMARY KEY,
    crp VARCHAR(10) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
	foto VARCHAR(255) NOT NULL,
    bio VARCHAR(300),
    formacao VARCHAR(300),
    contato VARCHAR(50) NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    valor_consulta DECIMAL(10,2) NOT NULL,
    aceita_beneficio BOOLEAN DEFAULT FALSE,
    modalidade_atendimento TEXT NOT NULL CHECK (modalidade_atendimento IN ('REMOTO', 'PRESENCIAL', 'HIBRIDO')) DEFAULT 'REMOTO'
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
    latitude DOUBLE PRECISION NOT NULL,  
    longitude DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (id_psicologo) REFERENCES psicologos(id_psicologo) ON DELETE CASCADE
);

-- Tabela de Especialidades
CREATE TABLE especialidades (
    id_especialidade SERIAL PRIMARY KEY,
    nome_especialidade VARCHAR(100) NOT NULL UNIQUE
);

-- Tabela de Abordagens Terapêuticas
CREATE TABLE abordagens (
    id_abordagem SERIAL PRIMARY KEY,
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
    id_paciente SERIAL PRIMARY KEY,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
	foto VARCHAR(255) NOT NULL UNIQUE,
    bio VARCHAR(300),
    contato VARCHAR(50) NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    beneficio_social TEXT NOT NULL CHECK (beneficio_social IN ('NENHUM', 'ESTUDANTE', 'CADUNICO')) DEFAULT 'NENHUM'
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
    latitude DOUBLE PRECISION NOT NULL,  
    longitude DOUBLE PRECISION NOT NULL,
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
    id_consulta SERIAL PRIMARY KEY,
    id_psicologo INT NOT NULL,
    id_paciente INT NOT NULL,
    data_consulta TIMESTAMP NOT NULL,
    status TEXT NOT NULL CHECK (status IN ('AGENDADA', 'CONCLUIDA', 'CANCELADA')) DEFAULT 'AGENDADA',
    modalidade TEXT NOT NULL CHECK (modalidade IN ('REMOTA', 'PRESENCIAL')),
    tipo TEXT NOT NULL CHECK (tipo IN ('COMUM', 'SOCIAL')),
    valor DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_psicologo) REFERENCES psicologos(id_psicologo) ON DELETE CASCADE,
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente) ON DELETE CASCADE
);


-- Tabela de Avaliações
CREATE TABLE avaliacoes (
    id_avaliacao SERIAL PRIMARY KEY,
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

-- Trigger para aplicar valor social automaticamente antes de inserir consulta
CREATE OR REPLACE FUNCTION aplicar_valor_social()
RETURNS TRIGGER AS $$
DECLARE
    beneficio TEXT;
    aceita_beneficio BOOLEAN;
    valor_base DECIMAL(10,2);
BEGIN
    -- Obtendo informações do paciente e psicólogo
    SELECT beneficio_social INTO beneficio FROM pacientes WHERE id_paciente = NEW.id_paciente;
    SELECT aceita_beneficio, valor_consulta INTO aceita_beneficio, valor_base FROM psicologos WHERE id_psicologo = NEW.id_psicologo;

    -- Definir o valor da consulta com base no tipo
    IF NEW.tipo = 'social' THEN
        IF beneficio IN ('ESTUDANTE', 'CADUNICO') AND aceita_beneficio = TRUE THEN
            NEW.valor := 50.00; 
        ELSE
            RAISE EXCEPTION 'A consulta foi marcada como social, mas o paciente não tem direito ou o psicólogo não aceita valor social';
        END IF;
    ELSE
        NEW.valor := COALESCE(valor_base, NEW.valor);
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
