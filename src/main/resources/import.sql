INSERT INTO categories (is_active, id, name) VALUES (1,0, 'Hardware Issue');
INSERT INTO categories (is_active, id, name) VALUES (1,1, 'Software Issue');
INSERT INTO categories (is_active, id, name) VALUES (1,2, 'Skill Issue');
INSERT INTO operators (id, email, password, active, username) VALUES (1,'lucadebar33@gmail.com','{noop}password',1,'Luca');
INSERT INTO operators (id, email, password, active, username) VALUES (2,'gianmarcotocco@gmail.com','{noop}password',1,'Giovanni');
INSERT INTO operators (id, email, password, active, username) VALUES (3,'marzonebello@gmail.com','{noop}password',1,'Mattia');
INSERT INTO roles (id, name) VALUES (0, 'Admin');
INSERT INTO roles (id, name) VALUES (1,'Operatore');
INSERT INTO operators_roles (operator_id, roles_id) VALUES (1,0);
INSERT INTO operators_roles (operator_id, roles_id) VALUES (2,1);
INSERT INTO operators_roles (operator_id, roles_id) VALUES (3,1);
INSERT INTO customers (customer_details, customer_type, name) VALUES ('Via Gigino Firenze 7','Privato','Massimo Lolli');
INSERT INTO customers (customer_details, customer_type, name) VALUES ('Via Madonnina Pescatrice 2','Business','Falegname S.r.l');
INSERT INTO customers (customer_details, customer_type, name) VALUES ('Via OrcaMaiora 9','Business','Motociclista s.a.s');