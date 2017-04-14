CREATE TABLE item (
   id integer NOT NULL PRIMARY KEY AUTOINCREMENT,
   descricao varchar(255) NOT NULL,
   valor double NOT NULL
);

CREATE TABLE usuarios (
   id integer NOT NULL PRIMARY KEY AUTOINCREMENT,
   nome varchar(255) NOT NULL,
   senha varchar(255) NOT NULL
);