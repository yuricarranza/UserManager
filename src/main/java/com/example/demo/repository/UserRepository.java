package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.simple.JdbcClient;
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

    public void CreateUser(User user){
        jdbcClient.sql("insert into dbo.[User](id, name, email, phone) values (:id, :name, :email, :phone)")
                .param("id", user.getId())
                .param("name", user.getName())
                .param("email", user.getEmail())
                .param("phone", user.getPhone()).update();
    }
}
