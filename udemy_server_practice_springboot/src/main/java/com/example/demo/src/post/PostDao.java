package com.example.demo.src.post;


import com.example.demo.src.post.model.GetPostImgRes;
import com.example.demo.src.post.model.GetPostRes;
import com.example.demo.src.post.model.PatchPostReq;
import com.example.demo.src.post.model.PostImgUrlReq;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PostDao {

    private JdbcTemplate jdbcTemplate;
    private List<GetPostImgRes> getPostImgRes;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetPostRes> selectPosts(int userIdx){
        String selectPostsQuery = "select p.postIdx as postIdx,\n" +
                "       u.userIdx as userIdx,\n" +
                "       u.nickName as nickName,\n" +
                "       u.profileImgUrl as profileImgUrl,\n" +
                "       p.content as content,\n" +
                "       if(postLikeCount is null, 0, postLikeCount) as postLikeCount,\n" +
                "       if(commentCount is null, 0, commentCount) as commentCount,\n" +
                "       if(pl.status is null, 'N', 'Y') as likeOrNot\n" +
                "from Post as p\n" +
                "    join User as u on u.userIdx = p.userIdx\n" +
                "    left join (select postIdx, count(postLikeIdx) as postLikeCount from PostLike group by postIdx) as plc on plc.postIdx = p.postIdx\n" +
                "    left join (select postIdx, count(commentIdx) as commentCount from Comment group by postIdx) as c on c.postIdx = p.postIdx\n" +
                "    left join Follow as f on f.followeeIdx = p.userIdx\n" +
                "    left join PostLike as pl on pl.userIdx = f.followerIdx and pl.postIdx = p.postIdx\n" +
                "where f.followerIdx = ?";
        int selectPostsParams = userIdx;

        return this.jdbcTemplate.query(selectPostsQuery,
                (rs, rowNum) -> new GetPostRes(
                        rs.getInt("postIdx"),
                        rs.getInt("userIdx"),
                        rs.getString("nickName"),
                        rs.getString("profileImgUrl"),
                        rs.getString("content"),
                        rs.getInt("postLikeCount"),
                        rs.getInt("commentCount"),
                        rs.getString("likeOrNot"),
                        getPostImgRes = this.jdbcTemplate.query("select pi.postImgUrlIdx, pi.postImgUrl\n" +
                                "from PostImgUrl as pi\n" +
                                "    join Post as p on p.postIdx = pi.postIdx\n" +
                                "where pi.status = 'ACTIVE' and p.postIdx = ?;",
                                (rs2, rowNum2) -> new GetPostImgRes(
                                        rs2.getInt("postImgUrlIdx"),
                                        rs2.getString("postImgUrl")
                                ), rs.getInt("postIdx"))
                ), selectPostsParams);
    }

    //유저 idx가 유효한지 확인
    public int checkUserExist(int userIdx){
        String checkUserExistQuery = "select exists(select userIdx from User where userIdx = ?)";
        int checkUserExistParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserExistQuery,
                int.class,
                checkUserExistParams);

    }

    // 게시물 작성
    public int insertPosts(int userIdx, String content){
        String insertPostQuery = "insert into Post(userIdx, content) values (?,?)";
        Object[] insertPostParams = new Object[] {userIdx, content};
        this.jdbcTemplate.update(insertPostQuery, insertPostParams);

        String lastInsertIdxQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdxQuery, int.class);
    }

    // 게시글 이미지 추가
    public int insertPostImgs(int postIdx, PostImgUrlReq postImgUrlReq){
        String insertPostImgsQuery = "insert into PostImgUrl(postIdx, postImgUrl) values (?, ?)";
        Object[] insertPostImgsParams = new Object[] {postIdx, postImgUrlReq.getPostImgUrl()};
        this.jdbcTemplate.update(insertPostImgsQuery, insertPostImgsParams);

        String lastinsertPostImgsQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastinsertPostImgsQuery, int.class);
    }

    // 게시물 수정
    public int updatePosts(int postIdx, String content){
        String updatePostsQuery = "update Post set content = ? where postIdx = ?";
        Object[] updatePostParams = new Object[] {content, postIdx};
        return this.jdbcTemplate.update(updatePostsQuery, updatePostParams);
    }

    // 게시물이 존재하는지 확인
    public int checkPostExist(int postIdx){
        String checkPostExistQuery = "select exists(select postIdx from Post where postIdx = ?)";
        int checkPostExistParams = postIdx;
        return this.jdbcTemplate.queryForObject(checkPostExistQuery,
                int.class,
                checkPostExistParams);

    }

    // 게시물 삭제
    public int deletePost(int postIdx){
        String deletePostQuery = "update Post set status = 'DELETED' where postIdx = ?";
        int deletePostParams = postIdx;
        return this.jdbcTemplate.update(deletePostQuery, deletePostParams);
    }
}

