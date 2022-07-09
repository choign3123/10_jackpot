package hackathon.jackpot.post;


import hackathon.jackpot.baserepose.BaseException;
import hackathon.jackpot.baserepose.BaseResponseStatus;
import hackathon.jackpot.post.model.GetMyPostRes;
import hackathon.jackpot.post.model.GetPostRes;
import hackathon.jackpot.post.model.PostDeleteEmojiReq;
import hackathon.jackpot.post.model.PostPostEmojiReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import static hackathon.jackpot.baserepose.BaseResponseStatus.*;

@Service
public class PostService {


    private final PostRepository postRepository;
    //이미지 저장 경로
    private String path = "";
    private String folder;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;

        //이미지 저장 경로 설정
        String[] paths;
        String tempPath = System.getProperty("user.dir"); //cwd 가져오기

        System.out.println(tempPath);

        String osName = System.getProperty("os.name").toLowerCase(); //현재 운영체제 가져오기

        if(osName.contains("win")){ //윈도우일 때
            paths = tempPath.split("\\\\");
            for(int i=0; i< paths.length-1; i++){
                path += paths[i];
                path += "\\";
            }
            folder = "imgHKT\\";
        }
        else { //그 외 우분투에서
            // var/www/해당폴더
            paths = tempPath.split("/");
            for(int i=0; i< paths.length-1; i++){
                path += paths[i];
                path += "/";
                if(paths[i].equals("www")){
                    break;
                }
            }

            folder = "imgHKT/";
        }

        System.out.println("최종 경로: " + this.path + this.folder);
    }


    //게시물 삭제
    public void deletePost(int userIdx, int postIdx) throws BaseException {
        try{
            postRepository.deletePost(userIdx,postIdx);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //게시물 전체조회
    public List<GetPostRes> getPostInfo(int userIdx, int page) throws BaseException{
        try{
            return postRepository.getPostInfo(userIdx,page);
        }catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }

    }


    //게시물 생성
    public void createPost(int userIdx, MultipartFile img, String content) throws BaseException{

        //이미지 저장
        String imgName;
        try{
            imgName = uploadImg(img);
        } catch (BaseException e){
            throw new BaseException(e.getStatus());
        }

        try{
            postRepository.createPost(userIdx, imgName, content);
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //이미지 저장
    public String uploadImg(MultipartFile img) throws BaseException{
        Date date = new Date();
        StringBuilder sb = new StringBuilder();

        System.out.println("이미지 저장 테스트");

        // file image 가 없을 경우
        if (img.isEmpty()) {
            throw new BaseException(NOT_FOUND_IMG);
        }

        //이미지 이름 설정
        sb.append(date.getTime());
        sb.append(img.getOriginalFilename());
        //이미지가 저장될 경로 설정
        File dest = new File(this.path + this.folder + sb.toString());

        try {
            img.transferTo(dest); //해당 경로로 이미지 옮기기
        } catch (IllegalStateException e) {
            e.printStackTrace();
            throw new BaseException(FAIL_TO_UPLOAD_IMG);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BaseException(FAIL_TO_UPLOAD_IMG);
        }

        //이미지 이름 리턴
        return sb.toString();
    }

    // 이미지 출력
    public ResponseEntity<Resource> displayImg(String imgName){
        //이미지 리소스
        Resource resource = new FileSystemResource(this.path + this.folder + imgName);

        //이미지가 존재하지 않으면
        if(!resource.exists()){
            System.out.println("이미지 출력 테스트3");
            //HttpStatus.NOT_FOUND = 404 에러
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        Path imgPath; //이미지 경로

        try{
            //이미지 경로
            imgPath = Paths.get(this.path + this.folder + imgName);
            //헤더에 이미지 확장자 넣기
            headers.add("Content-Type", Files.probeContentType(imgPath));
        } catch (IOException e){
            System.out.println("이미지 출력 테스트3");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    //게시글 검색
    public List<GetPostRes> searchPost(int userIdx, String q, int page) throws BaseException {
        try{
            return postRepository.searchPost(userIdx, q, page);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetMyPostRes getMyPostInfo(int userIdx, int page) throws BaseException {
        try{
            return postRepository.getMyPostInfo(userIdx, page);
        }catch(Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void createEmoji(PostPostEmojiReq postPostEmojiReq) throws BaseException {
        try{
            postRepository.createEmoji(postPostEmojiReq);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }

    public void deleteEmoji(PostDeleteEmojiReq postDeleteEmojiReq) throws BaseException{
        try{
            postRepository.deleteEmoji(postDeleteEmojiReq);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void notifyPost(int userIdx,int postIdx) throws BaseException{
        //post의 numOfNotify의 수를 1 올림
        try{
            postRepository.notifyPost(postIdx);
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
        //post테이블의 numOfNotify가 10이상이면 true반환 ->에러발생
        //deletePost로 연동시킴
        if(checkNotifyNum(postIdx)) {
            postRepository.deletePost(userIdx,postIdx);
            throw new BaseException(DELETE_BY_NUM_OF_NOTIFY);
        }

    }
    public boolean checkNotifyNum(int postIdx){
        int numONotify = postRepository.checkNotifyNum(postIdx);
        if(numONotify>=10)
            return true;
        else
            return false;

    }
}
