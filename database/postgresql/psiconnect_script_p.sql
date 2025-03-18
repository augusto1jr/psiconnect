-- ########## Tabelas Relacionadas aos Psicólogos ##########

-- Tabela de Psicólogos
CREATE TABLE psicologos (
    id_psicologo SERIAL PRIMARY KEY,
    crp VARCHAR(10) NOT NULL UNIQUE,
    nome_psicologo VARCHAR(100) NOT NULL,
    email_psicologo VARCHAR(100) NOT NULL UNIQUE,
    bio_psicologo VARCHAR(300),
    formacao_psicologo VARCHAR(300),
    contato_psicologo VARCHAR(50) NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    valor_padrao_consulta DECIMAL(10,2) NOT NULL,
    aceita_valor_social BOOLEAN DEFAULT FALSE,
    modalidade_atendimento TEXT CHECK (modalidade_atendimento IN ('remoto', 'presencial', 'híbrido')) NOT NULL DEFAULT 'remoto'
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
CREATE TABLE psicologo_especialidade (
    id_psicologo INT NOT NULL,
    id_especialidade INT NOT NULL,
    PRIMARY KEY (id_psicologo, id_especialidade),
    FOREIGN KEY (id_psicologo) REFERENCES psicologos(id_psicologo) ON DELETE CASCADE,
    FOREIGN KEY (id_especialidade) REFERENCES especialidades(id_especialidade) ON DELETE CASCADE
);

-- Relacionamento entre Psicólogos e Abordagens (N:N)
CREATE TABLE psicologo_abordagem (
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
    nome_paciente VARCHAR(100) NOT NULL,
    email_paciente VARCHAR(100) NOT NULL UNIQUE,
    bio_paciente VARCHAR(300),
    contato_paciente VARCHAR(50) NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    beneficio_social TEXT CHECK (beneficio_social IN ('nenhum', 'estudante', 'cadunico')) DEFAULT 'nenhum'
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
CREATE TABLE preferencia_abordagem (
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
    status_consulta TEXT CHECK (status_consulta IN ('agendada', 'concluída', 'cancelada')) DEFAULT 'agendada',
    tipo_consulta TEXT CHECK (tipo_consulta IN ('remota', 'presencial')) NOT NULL,
    plataforma_link VARCHAR(255) DEFAULT NULL,
    valor_consulta DECIMAL(10,2) NOT NULL,
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

-- Trigger para aplicar valor social automaticamente antes de inserir consulta
CREATE OR REPLACE FUNCTION aplicar_valor_social()
RETURNS TRIGGER AS $$
DECLARE
    beneficio TEXT;
    aceita_social BOOLEAN;
    valor_base DECIMAL(10,2);
BEGIN
    SELECT beneficio_social INTO beneficio FROM pacientes WHERE id_paciente = NEW.id_paciente;
    SELECT aceita_valor_social, valor_padrao_consulta INTO aceita_social, valor_base
    FROM psicologos WHERE id_psicologo = NEW.id_psicologo;

    IF beneficio IN ('estudante', 'cadunico') AND aceita_social = TRUE THEN
        NEW.valor_consulta := 50.00;
    ELSE
        NEW.valor_consulta := valor_base;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_aplicar_valor_social
BEFORE INSERT ON consultas
FOR EACH ROW
EXECUTE FUNCTION aplicar_valor_social();