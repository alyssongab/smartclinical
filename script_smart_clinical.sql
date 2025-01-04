create database smart_clinical;

use smart_clinical;

create table usuario(
	id int primary key auto_increment,
    nome varchar(255),
    email varchar(100) unique,
    senha varchar(32),
    telefone varchar(15),
    tipoUser ENUM("ADMIN", "MEDICO", "RECEPCIONISTA") not null
);

create table admins(
	id int primary key, -- referencia o id de usuario
    crm varchar(20),
    especialidade varchar(200),
    FOREIGN KEY (id) REFERENCES usuario	(id)
);

create table recepcionistas(
	id int primary key, -- referencia o id de usuario
    turno varchar(20),
    FOREIGN KEY (id) REFERENCES usuario (id)
);

insert into usuario(nome, email, senha, telefone, tipoUser)
values
("Bob", "bob@gmail.com", "admin123", "92998877", "ADMIN");

show tables;

select * from usuario;

RENAME TABLE admins TO medicos;

create table pacientes(
	id_paciente int primary key,
    nome varchar(255),
    cpf varchar(16),
    data_nasc varchar(11)
);

ALTER TABLE pacientes
MODIFY id_paciente INT AUTO_INCREMENT;

insert into pacientes(nome, cpf, data_nasc)
values("Neymar", "123.987.543-11", "20/02/1992");

select * from pacientes;	