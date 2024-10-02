INSERT INTO operators (email, password, stato_operatore, username) VALUES ('lucadebar33@gmail.com','{noop}password','attivo','luca');
INSERT INTO operators (email, password, stato_operatore, username) VALUES ('marzonebarese@gmail.com','{noop}password','attivo','blur');
INSERT INTO operators (email, password, stato_operatore, username) VALUES ('gianmarcotocco@siuu.it','{noop}password','attivo','marzone');
INSERT INTO roles (id, name) VALUES (0, 'admin');
INSERT INTO roles (id, name) VALUES (1,'user');
INSERT INTO operators_roles (operator_id, roles_id) VALUES (1,0);
INSERT INTO operators_roles (operator_id, roles_id) VALUES (2,1);
INSERT INTO operators_roles (operator_id, roles_id) VALUES (3,1);