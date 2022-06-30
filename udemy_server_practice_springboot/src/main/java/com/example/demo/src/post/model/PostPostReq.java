package com.example.demo.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostPostReq {
    private int userIdx; //누가 작성했는지
    private String content; //게시글 내용
    private List<PostImgUrlReq> postImgUrl; //게시글 이미지

    public PostPostReq(){

    }
}
