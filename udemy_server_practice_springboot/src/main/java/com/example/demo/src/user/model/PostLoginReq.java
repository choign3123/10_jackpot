package com.example.demo.src.user.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonAutoDetect
public class PostLoginReq {
    private String email;
    private String password;

//    public PostLoginReq(String email, String pw){
//        this.email = email;
//        this.password = pw;
//    }

//    public PostLoginReq(){
//
//    }
}
