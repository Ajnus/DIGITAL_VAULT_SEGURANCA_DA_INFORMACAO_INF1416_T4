-- Create the Usuarios table with additional columns
CREATE TABLE Usuarios (
    UID INTEGER PRIMARY KEY AUTOINCREMENT, -- Unique user ID
    Nome TEXT NOT NULL,
    Email TEXT NOT NULL UNIQUE,
    Senha TEXT NOT NULL, -- Stored password
    NumeroDeAcessos INTEGER DEFAULT 0, -- Number of accesses
    NumeroDeConsultas INTEGER DEFAULT 0 -- Number of queries
);

-- Create the Chaveiro table
CREATE TABLE Chaveiro (
    KID INTEGER PRIMARY KEY AUTOINCREMENT, -- Unique key ID
    UID INTEGER NOT NULL, -- Reference to the Usuarios table
    CertificadoDigital BLOB NOT NULL, -- Digital certificate
    ChavePrivada BLOB NOT NULL, -- Private key
    FOREIGN KEY (UID) REFERENCES Usuarios (UID)
);

-- Create the Grupos table
CREATE TABLE Grupos (
    GID INTEGER PRIMARY KEY AUTOINCREMENT, -- Unique group ID
    Nome TEXT NOT NULL
);

-- Create the Mensagens table
CREATE TABLE Mensagens (
    MID INTEGER PRIMARY KEY AUTOINCREMENT, -- Unique message ID
    Conteudo TEXT NOT NULL,
    DataEnvio TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Create the Registros table
CREATE TABLE Registros (
    RID INTEGER PRIMARY KEY AUTOINCREMENT, -- Unique record ID
    UID INTEGER, -- Reference to the Usuarios table
    MID INTEGER NOT NULL, -- Reference to the Mensagens table
    DataHora TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, -- Record date and time
    Descricao TEXT,
    FOREIGN KEY (UID) REFERENCES Usuarios (UID),
    FOREIGN KEY (MID) REFERENCES Mensagens (MID)
);
