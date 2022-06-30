package com.example.demo.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PatchPostReq {
    private int userIdx; //누가 수정할건지
    private String content; //수정할 게시글 내용
}
