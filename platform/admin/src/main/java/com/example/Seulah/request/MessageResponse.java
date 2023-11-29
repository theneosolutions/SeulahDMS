package com.example.Seulah.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageResponse {

    private String message;
    private Object data;
    private Boolean error;

}
