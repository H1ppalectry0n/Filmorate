package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class UserRepository extends BaseRepository<User> {
    private static final String FIND_ALL_QUERY = "SELECT * FROM users";
    private static final String FIND_ALL_FRIENDS_BY_USER_ID = "WITH cte AS (SELECT friend_id FROM friends_relations WHERE user_id = ? ) SELECT * FROM users WHERE id IN (SELECT friend_id FROM cte);";
    private static final String INSERT_FRIEND = "INSERT INTO friends_relations (user_id, friend_id) VALUES (?, ?)";
    private static final String REMOVE_FRIEND = "DELETE FROM friends_relations WHERE user_id = ? AND friend_id = ?";
    private static final String FIND_COMMON_FRIENDS = "WITH cte AS (SELECT fr1.friend_id AS common_friend_id FROM friends_relations fr1 " +
            "JOIN friends_relations fr2 ON fr1.friend_id = fr2.friend_id WHERE fr1.user_id = ? AND fr2.user_id = ?) SELECT * FROM users WHERE id IN (SELECT common_friend_id FROM cte);";
    private static final String UPDATE_QUERY = "UPDATE users SET email = ?, login = ?, name = ?, birthday = ? WHERE id = ?";

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String INSERT_QUERY = "INSERT INTO users (email, login, name, birthday) VALUES (?, ?, ?, ?);";
    private static final String FIND_FAVORITE_BY_FILM_ID = "WITH cte AS (SELECT user_id FROM films_favorites WHERE film_id = ?) SELECT * FROM users WHERE id IN (SELECT user_id FROM cte);";


    public UserRepository(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    public List<User> findAll() {
        List<User> users = findMany(FIND_ALL_QUERY);

        for (User user : users) {
            user.setFriends(findFriends(user.getId()).stream().map(User::getId).collect(Collectors.toSet()));
        }

        return users;
    }

    public void addFriend(Long userId, Long friendId) {
        insertMtM(INSERT_FRIEND, userId, friendId);
    }

    public void removeFriend(Long userId, Long friendId) {
        delete(REMOVE_FRIEND, userId, friendId);
    }

    public List<User> getCommonFriends(Long userId, Long otherUserId) {
        return findMany(FIND_COMMON_FRIENDS, userId, otherUserId);
    }

    public Optional<User> findById(Long userId) {
        return findOne(FIND_BY_ID_QUERY, userId);
    }

    public User save(User user) {
        Long id = insert(
                INSERT_QUERY,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday()
        );
        user.setId(id);
        return user;
    }

    public User update(User user) {
        update(
                UPDATE_QUERY,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()
        );
        return user;
    }

    public List<User> findFriends(Long id) {
        return findMany(FIND_ALL_FRIENDS_BY_USER_ID, id);
    }

    public Set<Long> getUserFavoriteFilmByFilmId(Long filmId) {
        return findMany(FIND_FAVORITE_BY_FILM_ID, filmId).stream().map(User::getId).collect(Collectors.toSet());
    }
}
