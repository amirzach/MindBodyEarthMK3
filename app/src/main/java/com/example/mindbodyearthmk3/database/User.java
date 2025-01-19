package com.example.mindbodyearthmk3.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table") // Optional: Specify a table name
public class User {

    @PrimaryKey(autoGenerate = true) // Auto-generate unique IDs
    private int id;

    private String username;

    private String email;

    private String password;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
