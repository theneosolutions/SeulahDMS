package com.example.User.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"})})
@JsonIgnoreProperties("hibernateLazyInitializer")
public class UserAccount extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;


    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;



    @Column(nullable = true)
    private String phoneNo;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @JsonProperty("isActive")
    private boolean isActive;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "user_account_id")
    private List<UserToken> userTokens;

    @Transient
    UserToken userToken;

    long userAccountTypeId;

    @JsonIgnore
    int invalidLoginCount;

    @JsonIgnore
    private boolean isEmailVerified;
//
//    @Enumerated(EnumType.STRING)
//    UserStatus currentUserStatus;



}
