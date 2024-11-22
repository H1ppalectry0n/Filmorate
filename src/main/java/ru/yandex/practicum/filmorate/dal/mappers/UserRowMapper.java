package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));             // Установка ID пользователя
        user.setEmail(rs.getString("email"));    // Установка Email
        user.setLogin(rs.getString("login"));    // Установка Логина
        user.setName(rs.getString("name"));      // Установка Имени
        user.setBirthday(rs.getDate("birthday").toLocalDate()); // Установка Дня рождения
        return user;
    }
}
