create table products(
	id_product int not null auto_increment primary key,
	name varchar(155) not null,
	price decimal(10, 2) not null,
	description text
);