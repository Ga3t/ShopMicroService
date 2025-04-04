package com.booking.auth_service.models;


import com.booking.core.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="USERS")
public class UserEntity {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="NAME", nullable = false)
    private String name;

    @Column(name="SURNAME", nullable = false)
    private String surname;

    @Column(name="ROLE",nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Column(name="EMAIL", nullable = false)
    private String email;

    @Column(name="PHONE_NUMBER", nullable = false)
    private String phoneNumber;

    @Column(name="PASSWORD")
    @JsonIgnore
    private String password;

    @Column(name="USERNAME")
    private String username;

    @Column(name="BIRTHDAY")
    private Date birthday;

    @Column(name="RATING ")
    private BigDecimal rating;

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
