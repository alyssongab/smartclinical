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

create table admins( -- Medicos ***************
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

ALTER TABLE pacientes
MODIFY nome varchar(255) not null,
MODIFY cpf varchar(16) not null unique;

insert into pacientes(nome, cpf, data_nasc)
values("Neymar", "123.987.543-11", "20/02/1992");

select * from pacientes;

insert into usuario(nome, email, senha, telefone, tipoUser)
values
("Messi", "lm10@gmail.com", "barca", "22 151109", "RECEPCIONISTA");

describe usuario;

alter table recepcionistas
change id id_recepcionista int;

alter table medicos
change id_medicos id_medico int;

select last_insert_id();

insert into recepcionistas
values(last_insert_id(), "tarde");

select * from usuario;

insert into usuario(nome, email, senha, telefone, tipoUser)
values("Daniel", "dan@gmail.com", "dandan", "9298016", "MEDICO");

insert into medicos
values(last_insert_id(), "203040", "Ortopedista");

create table consultas(
	id_consulta int primary key auto_increment,
    data_hora varchar(45) not null,
    paciente_id int not null,
    medico_id int not null,
    observacao varchar(255),
    foreign key (paciente_id) references pacientes(id_paciente),
    foreign key (medico_id) references medicos(id_medico)
);

select * from consultas;

describe pacientes;

UPDATE pacientes
SET cpf = REPLACE(REPLACE(cpf, '.', ''), '-', '')
WHERE id_paciente > 0;

ALTER TABLE pacientes MODIFY COLUMN cpf BIGINT NOT NULL UNIQUE;

select * from pacientes;

SHOW INDEXES FROM pacientes;

describe pacientes;
describe medicos;
select * from medicos;
select * from recepcionistas;

ALTER TABLE medicos
DROP FOREIGN KEY medicos_ibfk_1;  -- Nome da chave estrangeira

ALTER TABLE medicos
ADD CONSTRAINT medicos_ibfk_1
    FOREIGN KEY (id_medico) REFERENCES usuario(id)
    ON DELETE CASCADE;
    
SHOW CREATE TABLE recepcionistas;

ALTER TABLE recepcionistas
DROP FOREIGN KEY recepcionistas_ibfk_1;

ALTER TABLE recepcionistas
ADD CONSTRAINT recepcionistas_ibfk_1  -- O mesmo nome da chave estrangeira original
    FOREIGN KEY (id_recepcionista) REFERENCES usuario(id)
    ON DELETE CASCADE;  -- Define exclusão em cascata