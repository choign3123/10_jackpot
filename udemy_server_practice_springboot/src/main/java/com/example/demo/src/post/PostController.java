package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.post.model.GetPostRes;
import com.example.demo.src.post.model.PatchPostReq;
import com.example.demo.src.post.model.PostPostReq;
import com.example.demo.src.post.model.PostPostRes;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/posts")
public class PostController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final PostProvider postProvider;
    @Autowired
    private final PostService postService;
    @Autowired
    private final JwtService jwtService;


    public PostController(PostProvider postProvider, PostService postService, JwtService jwtService){
        this.postProvider = postProvider;
        this.postService = postService;
        this.jwtService = jwtService;
    }


    /*
    * 게시물 조회 API
    * [GET] /posts/:userIdx
    */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetPostRes>> getPosts(@RequestParam int userIdx) {
        try{
            List<GetPostRes> getPostRes = postProvider.retrievePosts(userIdx);
            return new BaseResponse<>(getPostRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostPostRes> postPosts(@RequestBody PostPostReq postPostReq) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if(postPostReq.getUserIdx() != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }

            if(postPostReq.getContent().length() > 450){ //글자수가 기준을 넘을 때
                return new BaseResponse<>(BaseResponseStatus.POST_POST_INVALID_CONTENTS);
            }
            if(postPostReq.getPostImgUrl().size() < 1){
                return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_IMGURL);
            }

            PostPostRes postPostRes = postService.createPosts(postPostReq.getUserIdx(), postPostReq);
            return new BaseResponse<>(postPostRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/{postIdx}")
    public BaseResponse<String> modifyPosts(@PathVariable("postIdx") int postIdx, @RequestBody PatchPostReq patchPostReq){
        try{
            if(patchPostReq.getContent().length() > 450){
                return new BaseResponse<>(BaseResponseStatus.POST_POST_INVALID_CONTENTS);
            }

            postService.modifyPost(patchPostReq.getUserIdx(), postIdx, patchPostReq);

            String result = "게시물 수정을 완료했습니다.";
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/{postIdx}/status")
    public BaseResponse<String> deletePosts(@PathVariable("postIdx") int postIdx){
        try{

            postService.deletePost(postIdx);

            String result = "게시물 삭제를 완료했습니다.";
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

//    @ResponseBody
//    @GetMapping(
//            value = "/img.jpg",
//            produces = MediaType.IMAGE_JPEG_VALUE
//    )
//    public BaseResponse<String> testImg(){
//        System.out.println("되는건 만자?");
////        String result = "C:\\Users\\Choi ga na\\Desktop\\side-projects\\udemy_server_practice_springboot-main\\UMC_1주차_워크북.jpg";
////        return new BaseResponse<>(result);
//
//    }
}