CREATE DATABASE supermarket;

USE supermarket;

CREATE TABLE producto(
id int PRIMARY KEY AUTO_INCREMENT,
nombre varchar(40) NOT NULL,
precio decimal(10,2) NOT NULL,
descripcion varchar(100) NOT NULL, 
cantidad int NOT NULL);


INSERT INTO producto(nombre, precio, descripcion, cantidad) VALUES('Gigabyte', 5000, 'Gigabyte RTX 5050', 5);
INSERT INTO producto(nombre, precio, descripcion, cantidad) VALUES('Mouse', 3500, 'Logitec Inalambrico', 4);
INSERT INTO producto(nombre, precio, descripcion, cantidad) VALUES('PC Monitor', 100000, 'LG 24 Pulgadas', 6);
INSERT INTO producto(nombre, precio, descripcion, cantidad) VALUES('Perfume Natura', 30000, 'Paz e Humor', 7);
INSERT INTO producto(nombre, precio, descripcion, cantidad) VALUES('Headset', 148000, 'Headset JBL', 4);
INSERT INTO producto(nombre, precio, descripcion, cantidad) VALUES('Notebook DELL', 1000000, 'DELL Latitude 7420', 1);
INSERT INTO producto(nombre, precio, descripcion, cantidad) VALUES('RTX 4070', 5000, 'Nvidia RTX 4070 8GB', 2);


SELECT * FROM producto;