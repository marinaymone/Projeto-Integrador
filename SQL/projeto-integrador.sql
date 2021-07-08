create database projetointegrador;
use projetointegrador;

CREATE TABLE Favoritos
(CodEmpresa integer AUTO_INCREMENT,
 nome varchar(40),
 link varchar(40),
 PRIMARY KEY(CodEmpresa)
 );

CREATE TABLE Empresa
(IdEmpresa integer AUTO_INCREMENT,
CNPJ varchar(40),
nome varchar(40),
endereco varchar(200),
descricao varchar(100),
categoria varchar(10),
link varchar(40),
PRIMARY KEY(IdEmpresa)
);

CREATE TABLE Avaliacao
(CodAvaliacao integer AUTO_INCREMENT,
nota integer,
sugestao varchar(100),
Empresa_IdEmpresa integer,
FOREIGN KEY(Empresa_IdEmpresa) REFERENCES Empresa(IdEmpresa) ON UPDATE CASCADE,
PRIMARY KEY(CodAvaliacao)
 );

CREATE TABLE Cliente
(CodCliente integer AUTO_INCREMENT,
nome varchar(40),
DataNascimento date,
email varchar(40),
Avaliacao_CodAvaliacao integer,
FOREIGN KEY(Avaliacao_CodAvaliacao) REFERENCES Avaliacao(CodAvaliacao) ON UPDATE CASCADE,
PRIMARY KEY(CodCliente)
);

CREATE TABLE Doacao
(CodDoacao integer AUTO_INCREMENT,
nome varchar(40),
valor integer,
email varchar(80),
Cliente_CodCliente integer,
FOREIGN KEY(Cliente_CodCliente) REFERENCES Cliente(CodCliente) ON UPDATE CASCADE,
PRIMARY KEY(CodDoacao)
 );


INSERT INTO Favoritos(nome, link) VALUES ('Lojas Renner','www.lojasrenner.com.br'),('Burger King','www.burgerking.com.br');

INSERT INTO Empresa(CNPJ, nome, endereco, descricao, categoria, link) VALUES ('8877417487', 'Lojas Renner', 'Rua Primeiro de Março, 88', 'A Lojas Renner...', 'Moda', 'www.lojasrenner.com.br'),('4458541741','Burger King', 'Rua Primeiro de Março', 'Burger King, é uma rede de restaurantes especializada em fast-food...', 'Alimentos', 'www.burgerking.com.br');

INSERT INTO Avaliacao(nota, sugestao, empresa_IdEmpresa) VALUES ('5', 'Minha sugestão é...', 01),('5', 'Quero sugerir...', 02);

INSERT INTO Cliente(nome, DataNascimento, email, Avaliacao_CodAvaliacao) VALUES ('Maria', '1998-06-18', 'maria@mail.com', 01),('João','1997-05-09', 'joao@mail.com', 02);

INSERT INTO Doacao(nome, valor, email, Cliente_CodCliente) VALUES ('Maria', '150', 'maria@mail.com', 01),('João', '100', 'joao@mail.com', 02);