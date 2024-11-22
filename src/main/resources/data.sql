-- Тестовые данные для ratings
INSERT INTO ratings (name) VALUES
('G'),
('PG'),
('PG-13'),
('R'),
('NC-17');

-- Тестовые данные для genres
INSERT INTO genres (name) VALUES
('Комедия'),
('Драма'),
('Мультфильм'),
('Триллер'),
('Документальный'),
('Боевик');

-- Добавление данных в таблицу пользователей
INSERT INTO users (email, login, name, birthday) VALUES
('user1@example.com', 'user1', 'Alice', '1990-01-01'),
('user2@example.com', 'user2', 'Bob', '1992-05-15'),
('user3@example.com', 'user3', 'Charlie', '1988-10-20'),
('user4@example.com', 'user4', 'Diana', '1995-03-30');

-- Добавление данных в таблицу фильмов
INSERT INTO films (name, description, releaseDate, duration, rating_id) VALUES
('Inception', 'Dream within a dream', '2010-07-16', 148, 1),
('The Matrix', 'Reality or simulation?', '1999-03-31', 136, 2);

-- Связь фильмов и жанров
INSERT INTO films_genre (film_id, genre_id) VALUES
(1, 1),
(1, 2),
(2, 1),
(2, 2);

-- Добавление избранных фильмов пользователей
INSERT INTO films_favorites (film_id, user_id) VALUES
(1, 1),
(2, 2);

-- Добавление данных в таблицу дружбы (односторонние связи)
INSERT INTO friends_relations (user_id, friend_id) VALUES
(1, 2), -- Alice добавила Bob в друзья
(3, 1); -- Charlie добавил Alice в друзья
--(2, 3); -- Bob добавил Charlie в друзья
