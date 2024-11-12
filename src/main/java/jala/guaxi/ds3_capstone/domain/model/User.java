package jala.guaxi.ds3_capstone.domain.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")

public class User {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    @Column(name = "login", nullable = false, length = 20, unique = true)
    private String login;
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    public User(String name, String login, String password) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.login = login;
        this.password = password;
    }


    public User() {
    }


    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}