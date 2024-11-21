-- Таблица для жанров
CREATE TABLE IF NOT EXISTS genres (
    id BIGINT PRIMARY KEY AUTO_INCREMENT, -- Автогенерация ключа
    name VARCHAR(200)
);

-- Таблица для рейтингов
CREATE TABLE IF NOT EXISTS ratings (
    id BIGINT PRIMARY KEY AUTO_INCREMENT, -- Автогенерация ключа
    name VARCHAR(200)
);

-- Таблица для пользователей
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT, -- Автогенерация ключа
    email VARCHAR(200), -- Валидация email
    login VARCHAR(200), -- Логин без пробелов
    name VARCHAR(200), -- Имя может быть пустым
    birthday DATE -- Дата рождения не в будущем
);

-- Таблица для фильмов
CREATE TABLE IF NOT EXISTS films (
    id BIGINT PRIMARY KEY AUTO_INCREMENT, -- Автогенерация ключа
    name VARCHAR(200), -- Поле обязательно
    description VARCHAR(200), -- Ограничение длины
    releaseDate DATE, -- Проверка даты
    duration INTEGER, -- Положительное значение
    rating_id BIGINT,
    FOREIGN KEY (rating_id) REFERENCES ratings(id)
);


-- Таблица для связи фильмов и жанров (многие ко многим)
CREATE TABLE IF NOT EXISTS films_genre (
    film_id BIGINT,
    genre_id BIGINT,
    PRIMARY KEY (film_id, genre_id),
    FOREIGN KEY (film_id) REFERENCES films(id),
    FOREIGN KEY (genre_id) REFERENCES genres(id)
);

-- Таблица для избранных фильмов пользователей
CREATE TABLE IF NOT EXISTS films_favorites (
    film_id BIGINT,
    user_id BIGINT,
    PRIMARY KEY (film_id, user_id),
    FOREIGN KEY (film_id) REFERENCES films(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);


-- Таблица для связей друзей
CREATE TABLE IF NOT EXISTS friends_relations (
    user_id BIGINT,
    friend_id BIGINT,
    PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (friend_id) REFERENCES users(id)
);
