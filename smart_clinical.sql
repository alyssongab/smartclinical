show databases;

use smart_clinical;
show tables;

create table admins(
                       id int auto_increment primary key,
                       nome varchar(255) not null,
                       email varchar(255) unique not null,
                       telefone varchar(20) unique not null,
                       senha varchar(40) unique not null,
                       tipoUser ENUM("ADMIN") not null
);

create table medicos(
                        id int auto_increment primary key,
                        nome varchar(255) not null,
                        email varchar(255) unique not null,
                        telefone varchar(20) unique not null,
                        senha varchar(40) unique not null,
                        CRM varchar(20) unique not null,
                        especialidade varchar(40) not null,
                        tipoUser ENUM("MEDICO") not null
);

create table recepcionistas(
                               id int auto_increment primary key,
                               nome varchar(255) not null,
                               email varchar(255) unique not null,
                               telefone varchar(20) unique not null,
                               senha varchar(40) unique not null,
                               turno varchar(20) not null,
                               tipoUser ENUM("RECEPCIONISTA") not null
);

create table pacientes(
                          id int auto_increment primary key,
                          nome varchar(255) not null,
                          email varchar(255) unique not null,
                          telefone varchar(20) unique not null,
                          cpf varchar(20) unique not null,
                          dataNascimento varchar(20) not null
);

/*Remover  atributo unique da senha*/
ALTER TABLE admins DROP INDEX senha;

ALTER TABLE admins DROP INDEX senha;
ALTER TABLE admins MODIFY COLUMN email VARCHAR(255) NULL;
/*Modificar o Enum para mais opções*/
ALTER TABLE admins ADD COLUMN tipoUser ENUM('ADMIN', 'MEDICO', 'RECEPCIONISTA') NOT NULL DEFAULT 'ADMIN';

alter table medicos drop index senha;


ALTER TABLE recepcionistas MODIFY COLUMN tipoUser ENUM('ADMIN', 'MEDICO', 'RECEPCIONISTA') NOT NULL DEFAULT 'MEDICO';
alter table recepcionistas drop index senha;
ALTER TABLE recepcionistas MODIFY COLUMN tipoUser ENUM('ADMIN', 'MEDICO', 'RECEPCIONISTA') NOT NULL DEFAULT 'RECEPCIONISTA';

ALTER TABLE pacientes MODIFY COLUMN email VARCHAR(255) NULL;
ALTER TABLE pacientes MODIFY COLUMN telefone VARCHAR(255) NULL;


describe admins;
describe medicos;
describe recepcionistas;
describe pacientes;

select * from admins;
insert into admins(nome, email, telefone, senha, tipoUser)
values
    ("Bob", "bob@gmail.com", "92998877", "admin123", "ADMIN");

insert into usuario(nome, email, telefone, senha, tipoUser)
values
    ("Bob", "bob@gmail.com", "92998877", "admin123", "ADMIN");

select * from medicos;
select * from usuario;
select * from recepcionistas;
select * from pacientes;
SELECT * FROM admins WHERE tipoUser IS NULL;
SELECT nome, CRM FROM medicos;
