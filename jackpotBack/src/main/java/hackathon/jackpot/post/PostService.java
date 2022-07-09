package hackathon.jackpot.post;


import hackathon.jackpot.baserepose.BaseException;
import hackathon.jackpot.baserepose.BaseResponseStatus;
import hackathon.jackpot.post.model.GetPostRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static hackathon.jackpot.baserepose.BaseResponseStatus.*;

@Service
public class PostService {


    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    //게시물 삭제
    public void deletePost(int userIdx, int postIdx) throws BaseException {
        try{
            postRepository.deletePost(userIdx,postIdx);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //게시물전체조회
    public List<GetPostRes> getPostInfo(int userIdx, int page) throws BaseException{
        try{
            return postRepository.getPostInfo(userIdx,page);
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }

}
