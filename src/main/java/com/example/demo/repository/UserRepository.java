package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcClient jdbcClient;
    private final int _maxRows;
    public UserRepository(JdbcClient jdbcClient, @Value("${database.maxRows:10}") int maxRows){
        this.jdbcClient = jdbcClient;
        this._maxRows = maxRows;
    }
    public List<User> GetUsers(){
        var users = jdbcClient.sql("select top (?) id, name, email, phone from dbo.[User]")
                .param(_maxRows)
                .query(User.class)
                .list();
        return users;
    }

    public Optional<User> GetUserById(int id){
        var user = jdbcClient.sql("select id, name, email, phone from dbo.[User] where id = :id")
                .param("id", id)
                .query(User.class).optional();
        return user;
    }

    public int CreateUser(User user){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql("insert into dbo.[User](name, email, phone) output inserted.id values (:name, :email, :phone)")
                .param("name", user.getName())
                .param("email", user.getEmail())
                .param("phone", user.getPhone())
                .update(keyHolder, "id");
        return keyHolder.getKeyAs(Integer.class);
    }

    public void UpdateUser(int userId, User user){
        jdbcClient.sql("update dbo.[User] set name = :name, email = :email, phone = :phone where id = :id")
                .param("name", user.getName())
                .param("email", user.getEmail())
                .param("phone", user.getPhone())
                .param("id", userId)
                .update();
    }
}
