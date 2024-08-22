package org.example.model;

public class User {

    private String username;
    private String passwordHash;
    private int roleId;

    public User(String username, String passwordHash, int roleId) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
