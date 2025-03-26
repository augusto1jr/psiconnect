-- ########## Tabelas Relacionadas aos Psicólogos ##########

-- Tabela de Psicólogos
CREATE TABLE psicologos (
    id_psicologo INT PRIMARY KEY AUTO_INCREMENT,
    crp VARCHAR(10) NOT NULL UNIQUE,
    nome_psicologo VARCHAR(100) NOT NULL,
    email_psicologo VARCHAR(100) NOT NULL UNIQUE,
    bio_psicologo VARCHAR(300),
    formacao VARCHAR(300),
    contato VARCHAR(50) NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    valor_padrao_consulta DECIMAL(10,2) NOT NULL,
    aceita_valor_social BOOLEAN DEFAULT FALSE,
    modalidade_atendimento ENUM('remoto', 'presencial', 'hibrido') NOT NULL DEFAULT 'remoto'
) DEFAULT CHARACTER SET utf8mb4;

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
) DEFAULT CHARACTER SET utf8mb4;

-- Tabela de Especialidades
CREATE TABLE especialidades (
    id_especialidade INT PRIMARY KEY AUTO_INCREMENT,
    nome_especialidade VARCHAR(100) NOT NULL UNIQUE
) DEFAULT CHARACTER SET utf8mb4;

-- Tabela de Abordagens Terapêuticas
CREATE TABLE abordagens (
    id_abordagem INT PRIMARY KEY AUTO_INCREMENT,
    nome_abordagem VARCHAR(100) NOT NULL UNIQUE
) DEFAULT CHARACTER SET utf8mb4;

-- Relacionamento entre Psicólogos e Especialidades (N:N)
CREATE TABLE psicologo_especialidade (
    id_psicologo INT NOT NULL,
    id_especialidade INT NOT NULL,
    PRIMARY KEY (id_psicologo, id_especialidade),
    FOREIGN KEY (id_psicologo) REFERENCES psicologos(id_psicologo) ON DELETE CASCADE,
    FOREIGN KEY (id_especialidade) REFERENCES especialidades(id_especialidade) ON DELETE CASCADE
) DEFAULT CHARACTER SET utf8mb4;

-- Relacionamento entre Psicólogos e Abordagens (N:N)
CREATE TABLE psicologo_abordagem (
    id_psicologo INT NOT NULL,
    id_abordagem INT NOT NULL,
    PRIMARY KEY (id_psicologo, id_abordagem),
    FOREIGN KEY (id_psicologo) REFERENCES psicologos(id_psicologo) ON DELETE CASCADE,
    FOREIGN KEY (id_abordagem) REFERENCES abordagens(id_abordagem) ON DELETE CASCADE
) DEFAULT CHARACTER SET utf8mb4;



-- ########## Tabelas Relacionadas aos Pacientes ##########

-- Tabela de Pacientes
CREATE TABLE pacientes (
    id_paciente INT PRIMARY KEY AUTO_INCREMENT,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    nome_paciente VARCHAR(100) NOT NULL,
    email_paciente VARCHAR(100) NOT NULL UNIQUE,
    bio_paciente VARCHAR(300),
    contato VARCHAR(50) NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    beneficio_social ENUM('nenhum', 'estudante', 'cadunico') DEFAULT 'nenhum'
) DEFAULT CHARACTER SET utf8mb4;

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
) DEFAULT CHARACTER SET utf8mb4;

-- Tabela de Preferências de Especialidades dos Pacientes
CREATE TABLE preferencia_especialidades (
    id_paciente INT NOT NULL,
    id_especialidade INT NOT NULL,
    PRIMARY KEY (id_paciente, id_especialidade),
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente) ON DELETE CASCADE,
    FOREIGN KEY (id_especialidade) REFERENCES especialidades(id_especialidade) ON DELETE CASCADE
) DEFAULT CHARACTER SET utf8mb4;

-- Tabela de Preferências de Abordagem dos Pacientes
CREATE TABLE preferencia_abordagem (
    id_paciente INT NOT NULL,
    id_abordagem INT NOT NULL,
    PRIMARY KEY (id_paciente, id_abordagem),
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente) ON DELETE CASCADE,
    FOREIGN KEY (id_abordagem) REFERENCES abordagens(id_abordagem) ON DELETE CASCADE
) DEFAULT CHARACTER SET utf8mb4;


-- ########## Tabelas de Relacionamento entre os Psicólogos e Pacientes ##########

-- Tabela de Consultas
CREATE TABLE consultas (
    id_consulta INT PRIMARY KEY AUTO_INCREMENT,
    id_psicologo INT NOT NULL,
    id_paciente INT NOT NULL,
    data_consulta DATETIME NOT NULL,
    status_consulta ENUM('agendada', 'concluída', 'cancelada') DEFAULT 'agendada',
    tipo_consulta ENUM('remota', 'presencial') NOT NULL,
    plataforma_link VARCHAR(255) DEFAULT NULL,
    valor_consulta DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_psicologo) REFERENCES psicologos(id_psicologo) ON DELETE CASCADE,
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente) ON DELETE CASCADE
) DEFAULT CHARACTER SET utf8mb4;

-- Tabela de Avaliações
CREATE TABLE avaliacoes (
    id_avaliacao INT PRIMARY KEY AUTO_INCREMENT,
    id_consulta INT UNIQUE NOT NULL,
    id_paciente INT NOT NULL,
    id_psicologo INT NOT NULL,
    nota INT CHECK (nota BETWEEN 1 AND 5),
    comentario TEXT,
    data_avaliacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_consulta) REFERENCES consultas(id_consulta) ON DELETE CASCADE,
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente) ON DELETE CASCADE,
    FOREIGN KEY (id_psicologo) REFERENCES psicologos(id_psicologo) ON DELETE CASCADE
) DEFAULT CHARACTER SET utf8mb4;


-- Trigger para aplicar valor social automaticamente antes de inserir consulta
DELIMITER $$

CREATE TRIGGER aplicar_valor_social
BEFORE INSERT ON consultas
FOR EACH ROW
BEGIN
    DECLARE beneficio VARCHAR(10);
    DECLARE aceita_social BOOLEAN;
    DECLARE valor_base DECIMAL(10,2);

    -- Buscar se o paciente tem benefício social
    SELECT beneficio_social INTO beneficio FROM pacientes WHERE id_paciente = NEW.id_paciente;

    -- Buscar se o psicólogo aceita valor social e seu valor padrão
    SELECT aceita_valor_social, valor_padrao_consulta INTO aceita_social, valor_base
    FROM psicologos WHERE id_psicologo = NEW.id_psicologo;

    -- Aplicar valor social somente se o psicólogo aceitar e o paciente for elegível
    IF beneficio IN ('estudante', 'cadunico') AND aceita_social = TRUE THEN
        SET NEW.valor_consulta = 50.00;
    ELSE
        SET NEW.valor_consulta = valor_base;
    END IF;
END $$

DELIMITER ;
