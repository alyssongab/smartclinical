use smart_clinical;

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
/*Modificar o Enum para mais opções*/
ALTER TABLE admins ADD COLUMN tipoUser ENUM('ADMIN', 'MEDICO', 'RECEPCIONISTA') NOT NULL DEFAULT 'ADMIN';

alter table medicos drop index senha;
ALTER TABLE recepcionistas MODIFY COLUMN tipoUser ENUM('ADMIN', 'MEDICO', 'RECEPCIONISTA') NOT NULL DEFAULT 'MEDICO';

alter table recepcionistas drop index senha;
ALTER TABLE recepcionistas MODIFY COLUMN tipoUser ENUM('ADMIN', 'MEDICO', 'RECEPCIONISTA') NOT NULL DEFAULT 'RECEPCIONISTA';

insert into admins(nome, email, telefone, senha, tipoUser)
values
    ("Bob", "bob@gmail.com", "92998877", "admin123", "ADMIN");
