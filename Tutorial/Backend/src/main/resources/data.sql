INSERT INTO category(name) VALUES ('Eurogames');
INSERT INTO category(name) VALUES ('Ameritrash');
INSERT INTO category(name) VALUES ('Familiar');

INSERT INTO client(name) VALUES ('Fernando');
INSERT INTO client(name) VALUES ('Lucia');
INSERT INTO client(name) VALUES ('Jack');
INSERT INTO client(name) VALUES ('Alba');

INSERT INTO author(name, nationality) VALUES ('Alan R. Moon', 'US');
INSERT INTO author(name, nationality) VALUES ('Vital Lacerda', 'PT');
INSERT INTO author(name, nationality) VALUES ('Simone Luciani', 'IT');
INSERT INTO author(name, nationality) VALUES ('Perepau Llistosella', 'ES');
INSERT INTO author(name, nationality) VALUES ('Michael Kiesling', 'DE');
INSERT INTO author(name, nationality) VALUES ('Phil Walker-Harding', 'US');

INSERT INTO game(title, age, category_id, author_id) VALUES ('On Mars', '14', 1, 2);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Aventureros al tren', '8', 3, 1);
INSERT INTO game(title, age, category_id, author_id) VALUES ('1920: Wall Street', '12', 1, 4);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Barrage', '14', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Los viajes de Marco Polo', '12', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Azul', '8', 3, 5);

INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (1, 1, '2024-11-25', '2024-11-28');
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (2, 2, '2024-11-26', '2024-11-30');
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (3, 3, '2024-11-30', '2024-12-03');
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (2, 1, '2024-12-25', '2024-12-28');
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (3, 2, '2024-12-23', '2024-12-29');
INSERT INTO loan(game_id, client_id, init_date, end_date) VALUES (1, 3, '2024-12-26', '2024-12-31');