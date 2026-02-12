package com.example.secureauthapp.model;

public class User {

    private String username;   // Username
    private String password;   // Encoded password
    private String role;       // ⭐ เพิ่ม role

    // ⭐ Constructor ใหม่ (รับ 3 ค่า)
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // ===== Getters =====
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {        // ⭐ getter role
        return role;
    }

    // ===== Setters =====
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {   // ⭐ setter role
        this.role = role;
    }
}
