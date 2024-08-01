package entity;

import constant.UserRole;

public class User {
    private static int AUTO_ID = 1;

    private int id;
    private String email;
    private String password;
    private UserRole role;
    private String fullName;
    private int age;
    private String motherTounge;

    public User(String email, String password, String fullname, int age, String motherTounge){
        this.id = AUTO_ID;
        AUTO_ID++;
    }

    public User(int id, String email, String password, UserRole role, String fullName, int age, String motherTounge) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.age = age;
        this.motherTounge = motherTounge;
    }

    public User(int id, String email, String password, String fullName, int age, String motherTounge) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.age = age;
        this.motherTounge = motherTounge;
    }


    public User(String adminEmail, String adminPassword, UserRole userRole) {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getFullname() {
        return fullName;
    }

    public void setFullname(String fullname) {
        this.fullName = fullname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMothertounge() {
        return motherTounge;
    }

    public void setMothertounge(String mothertounge) {
        this.motherTounge = mothertounge;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role+
                ", fullname='" + fullName + '\'' +
                ", age=" + age +
                ", mothertounge='" + motherTounge + '\'' +
                '}';
    }
}