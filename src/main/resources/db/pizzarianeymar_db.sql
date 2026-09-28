-- Script de criacao do banco de dados pizzarianeymar_db
-- Execute no SQL Server Management Studio (F5)
-- Deve bater com as entidades em model.entity, pois spring.jpa.hibernate.ddl-auto=none

USE master
IF EXISTS (SELECT * FROM sys.databases WHERE name = 'pizzarianeymar_db')
    DROP DATABASE pizzarianeymar_db
GO

CREATE DATABASE pizzarianeymar_db
GO

USE pizzarianeymar_db
GO

CREATE TABLE Categoria (
    id BIGINT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(45) NOT NULL,
    descricao VARCHAR(255) NULL,
    cod_status BIT NOT NULL
)

CREATE TABLE Produto (
    id BIGINT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(45) NOT NULL,
    tipo VARCHAR(45) NULL,
    descricao VARCHAR(255) NULL,
    valor_compra DECIMAL(5,2) NULL,
    valor_venda DECIMAL(5,2) NULL,
    quantidade_estoque INT NULL,
    categoria_id BIGINT NULL,
    cod_status BIT NOT NULL,
    CONSTRAINT fk_produto_categoria_id FOREIGN KEY (categoria_id) REFERENCES Categoria(id)
)

CREATE TABLE Usuario (
    id BIGINT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(15) NULL,
    email VARCHAR(45) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    tipo_usuario VARCHAR(45) NOT NULL,
    sexo VARCHAR(20) NULL,
    logradouro VARCHAR(100) NULL,
    cep VARCHAR(10) NULL,
    bairro VARCHAR(45) NULL,
    cidade VARCHAR(45) NULL,
    uf CHAR(2) NULL,
    cod_status BIT NOT NULL
)

CREATE TABLE Telefone (
    id BIGINT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    ddd VARCHAR(3) NOT NULL,
    numero VARCHAR(15) NOT NULL,
    cod_status BIT NOT NULL,
    usuario_id BIGINT NOT NULL,
    CONSTRAINT fk_telefone_usuario_id FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
)

CREATE TABLE Pedido (
    id BIGINT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    valor_pedido DECIMAL(5,2) NOT NULL,
    data_hora_pedido DATETIME2 NOT NULL,
    data_hora_entrega DATETIME2 NULL,
    status VARCHAR(20) NULL,
    cod_status BIT NOT NULL,
    cliente_id BIGINT NOT NULL,
    CONSTRAINT fk_pedido_usuario_id FOREIGN KEY (cliente_id) REFERENCES Usuario(id)
)

CREATE TABLE ItemPedido (
    id BIGINT NOT NULL IDENTITY(1,1) PRIMARY KEY,
    quantidade_item INT NOT NULL,
    valor_unitario DECIMAL(5,2) NOT NULL,
    cod_status BIT NOT NULL,
    produto_id BIGINT NOT NULL,
    pedido_id BIGINT NOT NULL,
    CONSTRAINT fk_itemPedido_produto_id FOREIGN KEY (produto_id) REFERENCES Produto(id),
    CONSTRAINT fk_itemPedido_pedido_id FOREIGN KEY (pedido_id) REFERENCES Pedido(id)
)
GO
