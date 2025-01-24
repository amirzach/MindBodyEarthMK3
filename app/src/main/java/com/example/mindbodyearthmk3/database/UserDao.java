package com.example.mindbodyearthmk3.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    // Insert a new user into the database
    @Insert
    void insertUser(User user);

    // Query to validate user login using username and password
    @Query("SELECT * FROM user_table WHERE username = :username AND password = :password")
    User login(String username, String password);

    // Retrieve all users from the database
    @Query("SELECT * FROM user_table")
    List<User> getAllUsers();

    // Update the password for a user identified by their email
    @Query("UPDATE user_table SET password = :newPassword WHERE email = :email")
    void resetPassword(String email, String newPassword);

    // Find a user by their email
    @Query("SELECT * FROM user_table WHERE email = :email")
    User findByEmail(String email);

    @Query("SELECT * FROM user_table WHERE username = :username")
    User findByUsername(String username);
}
