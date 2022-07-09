package hackathon.jackpot.post;


import hackathon.jackpot.baserepose.BaseException;
import hackathon.jackpot.baserepose.BaseResponse;
import hackathon.jackpot.post.model.GetPostRes;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //게시물 삭제
    //[DELETE] posts/{userIdx}?postIdx=""
    @ResponseBody
    @DeleteMapping("/{userIdx}")
    @Operation(summary = "게시물 삭제", description = "게시물 삭제")
    public BaseResponse<String> deletePost(@PathVariable("userIdx") int userIdx, @RequestParam("postIdx") int postIdx){
        try{
            postService.deletePost(userIdx,postIdx);
            String result = "삭제되었습니다.";
            return new BaseResponse<>(result);
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }



    //게시물 전체조회
    //[GET] posts/{userIdx}?page=""
    @ResponseBody
    @GetMapping("/{userIdx}")
    @Operation(summary = "게시물 전체 조회", description = "게시물 전체 조회")
    public BaseResponse<List<GetPostRes>> getPostInfo(@PathVariable("userIdx") int userIdx,@RequestParam("page") int page){
        try{
            List<GetPostRes> getPostRes = postService.getPostInfo(userIdx,page);
            return new BaseResponse<>(getPostRes);
        }catch(BaseException exception){
            return new BaseResponse((exception.getStatus()));
        }


    }


    //게시물 생성
    //[POST] posts/{userIdx}
    @ResponseBody
    @PostMapping("/{userIdx}")
    @Operation(summary = "게시물 생성", description = "게시물 생성")
    public BaseResponse<String> createPost(@PathVariable int userIdx, @RequestPart MultipartFile img, @RequestPart String content){
        try{
            postService.createPost(userIdx, img, content);

            String result = "게시글 생성에 성공했습니다";
            return new BaseResponse<String>(result);
        } catch (BaseException e){
            return new BaseResponse(e.getStatus());
        }
    }


    //게시물 이미지 출력
    //[GET] posts/img/display/{imgName}
    @ResponseBody
    @GetMapping("/img/display/{imgName}")
    @Operation(summary = "게시물 이미지 출력", description = "게시물 이미지 출력")
    public ResponseEntity<Resource> displayImg(@PathVariable String imgName){
        System.out.println("이미지 출력 테스트");

        return postService.displayImg(imgName);
    }
}
