package hackathon.jackpot.post;


import hackathon.jackpot.baserepose.BaseException;
import hackathon.jackpot.baserepose.BaseResponse;
import hackathon.jackpot.post.model.GetMyPostRes;
import hackathon.jackpot.post.model.GetPostRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    private final PostRepository postRepository;

    @Autowired
    public PostController(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    //게시물 삭제
    ///posts/{userIdx}?postIdx=""
    @ResponseBody
    @DeleteMapping("/{userIdx}")
    public BaseResponse<String> deletePost(@PathVariable("userIdx") int userIdx, @RequestParam("postIdx") int postIdx){
        try{
            postService.deletePost(userIdx,postIdx);
            String result = "삭제되었습니다.";
            return new BaseResponse<>(result);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    //게시물 전체조회
    //posts/{userIdx}?page=""
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetPostRes>> getPostInfo(@PathVariable("userIdx") int userIdx,@RequestParam("page") int page){
        try{
            List<GetPostRes> getPostRes = postService.getPostInfo(userIdx,page);
            return new BaseResponse<>(getPostRes);
        }catch(BaseException exception){
            return new BaseResponse(exception.getStatus());
        }

    }


    //게시글 검색
    ///posts/search?q=""&page=""
    @ResponseBody
    @GetMapping("/search/{userIdx}")
    public BaseResponse<List<GetPostRes>> searchPost(@PathVariable("userIdx") int userIdx,@RequestParam("q") String q,@RequestParam("page") int page){
        try{
            List<GetPostRes> getPostRes = postService.searchPost(userIdx,q,page);
            return new BaseResponse<>(getPostRes);
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }

    //게시글 my조회
    //posts/my/{userIdx}?page=""
    @ResponseBody
    @GetMapping("/my/{userIdx}")
    public BaseResponse<GetMyPostRes> getMyPostInfo(@PathVariable("userIdx")int userIdx,@RequestParam("page") int page){
        try{
            GetMyPostRes getMyPostRes = postService.getMyPostInfo(userIdx,page);
            return new BaseResponse<>(getMyPostRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }


}
