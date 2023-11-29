package com.example.User.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import javax.persistence.Entity;

@Getter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserToken extends BaseEntity<Long> {

    private static final long serialVersionUID = 1173835683206584781L;

    /**
     * -- GETTER --
     *
     * @return the sessionToken
     */
    private String sessionToken;

    /**
     * -- GETTER --
     *
     * @return the sessionTokenExpiry
     */
    private long sessionTokenExpiry;


    private String refreshToken;

    private Long refreshTokenExpiry;


    private boolean active;


    private String error;

    private int refreshTokenCount;


}
