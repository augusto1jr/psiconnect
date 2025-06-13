-- ########## Tabelas Relacionadas aos Psicólogos ##########

CREATE TABLE psicologos (
    id_psicologo INT AUTO_INCREMENT PRIMARY KEY,
    crp VARCHAR(10) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    foto VARCHAR(255) NOT NULL,
    bio VARCHAR(300),
    formacao VARCHAR(300),
    contato VARCHAR(50) NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    valor_consulta DECIMAL(10,2) NOT NULL,
    aceita_beneficio TINYINT(1) DEFAULT 0,
    modalidade_atendimento ENUM('REMOTO', 'PRESENCIAL', 'HIBRIDO') NOT NULL DEFAULT 'REMOTO'
);

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

CREATE TABLE especialidades (
    id_especialidade INT AUTO_INCREMENT PRIMARY KEY,
    nome_especialidade VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE abordagens (
    id_abordagem INT AUTO_INCREMENT PRIMARY KEY,
    nome_abordagem VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE psicologos_especialidades (
    id_psicologo INT NOT NULL,
    id_especialidade INT NOT NULL,
    PRIMARY KEY (id_psicologo, id_especialidade),
    FOREIGN KEY (id_psicologo) REFERENCES psicologos(id_psicologo) ON DELETE CASCADE,
    FOREIGN KEY (id_especialidade) REFERENCES especialidades(id_especialidade) ON DELETE CASCADE
);

CREATE TABLE psicologos_abordagens (
    id_psicologo INT NOT NULL,
    id_abordagem INT NOT NULL,
    PRIMARY KEY (id_psicologo, id_abordagem),
    FOREIGN KEY (id_psicologo) REFERENCES psicologos(id_psicologo) ON DELETE CASCADE,
    FOREIGN KEY (id_abordagem) REFERENCES abordagens(id_abordagem) ON DELETE CASCADE
);

-- ########## Tabelas Relacionadas aos Pacientes ##########

CREATE TABLE pacientes (
    id_paciente INT AUTO_INCREMENT PRIMARY KEY,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    foto VARCHAR(255) NOT NULL UNIQUE,
    bio VARCHAR(300),
    contato VARCHAR(50) NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    beneficio_social ENUM('NENHUM', 'ESTUDANTE', 'CADUNICO') NOT NULL DEFAULT 'NENHUM'
);

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

CREATE TABLE preferencia_especialidades (
    id_paciente INT NOT NULL,
    id_especialidade INT NOT NULL,
    PRIMARY KEY (id_paciente, id_especialidade),
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente) ON DELETE CASCADE,
    FOREIGN KEY (id_especialidade) REFERENCES especialidades(id_especialidade) ON DELETE CASCADE
);

CREATE TABLE preferencia_abordagens (
    id_paciente INT NOT NULL,
    id_abordagem INT NOT NULL,
    PRIMARY KEY (id_paciente, id_abordagem),
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente) ON DELETE CASCADE,
    FOREIGN KEY (id_abordagem) REFERENCES abordagens(id_abordagem) ON DELETE CASCADE
);

-- ########## Tabelas de Relacionamento entre Psicólogos e Pacientes ##########

CREATE TABLE consultas (
    id_consulta INT AUTO_INCREMENT PRIMARY KEY,
    id_psicologo INT NOT NULL,
    id_paciente INT NOT NULL,
    data_consulta DATETIME NOT NULL,
    status ENUM('AGENDADA', 'CONCLUIDA', 'CANCELADA') NOT NULL DEFAULT 'AGENDADA',
    modalidade ENUM('REMOTA', 'PRESENCIAL') NOT NULL,
    tipo ENUM('COMUM', 'SOCIAL') NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_psicologo) REFERENCES psicologos(id_psicologo) ON DELETE CASCADE,
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente) ON DELETE CASCADE
);

CREATE TABLE avaliacoes (
    id_avaliacao INT AUTO_INCREMENT PRIMARY KEY,
    id_consulta INT UNIQUE NOT NULL,
    id_paciente INT NOT NULL,
    id_psicologo INT NOT NULL,
    nota INT CHECK (nota BETWEEN 1 AND 5),
    comentario TEXT,
    data_avaliacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_consulta) REFERENCES consultas(id_consulta) ON DELETE CASCADE,
    FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente) ON DELETE CASCADE,
    FOREIGN KEY (id_psicologo) REFERENCES psicologos(id_psicologo) ON DELETE CASCADE
);

-- ########## Trigger para Valor Social ##########

DELIMITER $$

CREATE TRIGGER aplicar_valor_social
BEFORE INSERT ON consultas
FOR EACH ROW
BEGIN
    DECLARE beneficio TEXT;
    DECLARE aceita BOOLEAN;
    DECLARE valor_base DECIMAL(10,2);

    SELECT beneficio_social INTO beneficio FROM pacientes WHERE id_paciente = NEW.id_paciente;
    SELECT aceita_beneficio, valor_consulta INTO aceita, valor_base FROM psicologos WHERE id_psicologo = NEW.id_psicologo;

    IF NEW.tipo = 'SOCIAL' THEN
        IF (beneficio IN ('ESTUDANTE', 'CADUNICO')) AND aceita THEN
            SET NEW.valor = 50.00;
        ELSE
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Consulta social inválida: paciente não tem direito ou psicólogo não aceita benefício.';
        END IF;
    ELSE
        SET NEW.valor = IFNULL(valor_base, NEW.valor);
    END IF;
END$$

DELIMITER ;
