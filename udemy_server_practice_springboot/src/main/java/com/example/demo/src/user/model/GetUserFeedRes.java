package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetUserFeedRes {
    private boolean _isMyFeed; //내 피드인지, 다른사람의 피드인지 구분.
    private GetUserInfoRes getUserInfo; //유저 정보
    private List<GetUserPostsRes> getUserPosts; //유저 게시물들

}
