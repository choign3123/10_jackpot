package hackathon.jackpot.post;


import hackathon.jackpot.post.model.GetMyPostRes;
import hackathon.jackpot.post.model.GetPostRes;
import hackathon.jackpot.post.model.PostDeleteEmojiReq;
import hackathon.jackpot.post.model.PostPostEmojiReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class PostRepository {

    private JdbcTemplate jdbcTemplate;

    private static final int MAX_NOTIFIY_NUM = 10;

    private static final int PAGE_SIZE = 10;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    //게시물 삭제
    public void deletePost(int userIdx, int postIdx) {
        String deletePostQuery = "delete from post where userIdx = ? and postIdx =?";
        Object[] deletePostParam = new Object[]{userIdx, postIdx};
        this.jdbcTemplate.update(deletePostQuery, deletePostParam);
    }

    //게시물 전체 조회
    public List<GetPostRes> getPostInfo(int userIdx, int page) {
        String getPostInfoQuery = "select post.postIdx, imgUrl, content,\n" +
                "       (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx and postLike.emogiIdx=0)) as checkEm0,\n" +
                "       (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx and postLike.emogiIdx=1)) as checkEm1,\n" +
                "       (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx and postLike.emogiIdx=2)) as checkEm2,\n" +
                "       (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx and postLike.emogiIdx=3)) as checkEm3\n" +
                "from post\n" +
                "order by createdAt desc, postIdx desc\n" +
                "limit ?, ?;";

        Object[] getPostinfoQuery = new Object[]{userIdx, userIdx, userIdx, userIdx, page, PAGE_SIZE};
        return this.jdbcTemplate.query(getPostInfoQuery,
                (rs, rowNum) -> new GetPostRes(
                        rs.getInt("post.postIdx"),
                        rs.getString("imgUrl"),
                        rs.getString("content"),
                        Arrays.asList(rs.getBoolean("checkEm0"), rs.getBoolean("checkEm1"),
                                rs.getBoolean("checkEm2"),rs.getBoolean("checkEm3"))
                ), getPostinfoQuery);

    }

    //게시물 생성
    public void createPost(int userIdx, String imgUrl, String content) {
        String createPostQuery = "insert into post(userIdx, imgUrl, content) values (?, ?, ?)";
        Object[] createPostParams = new Object[]{userIdx, imgUrl, content};

        this.jdbcTemplate.update(createPostQuery, createPostParams);
    }

    //게시물 검색
    public List<GetPostRes> searchPost(int userIdx, String q, int page) {
        String searchPostQuery = "select post.postIdx, imgUrl, content,\n" +
                "        (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx and postLike.emogiIdx=0)) as checkEm0,\n" +
                "        (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx and postLike.emogiIdx=1)) as checkEm1,\n" +
                "        (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx and postLike.emogiIdx=2)) as checkEm2,\n" +
                "        (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx and postLike.emogiIdx=3)) as checkEm3\n" +
                "from post\n" +
                "where post.content like '%나에게%'\n" +
                "order by createdAt desc, postIdx desc\n" +
                "limit ?,?";
        Object[] searchPostParam = new Object[]{userIdx,userIdx,userIdx,userIdx, page, PAGE_SIZE};
        return this.jdbcTemplate.query(searchPostQuery, (rs, rowNum) -> new GetPostRes(
                rs.getInt("post.postIdx"),
                rs.getString("imgUrl"),
                rs.getString("content"),
                Arrays.asList(rs.getBoolean("checkEm0"), rs.getBoolean("checkEm1"),
                        rs.getBoolean("checkEm2"), rs.getBoolean("checkEm3"))

        ), searchPostParam);
    }

    //게시글 my조회
    public GetMyPostRes getMyPostInfo(int userIdx,int page){


        String getPostQuery = "select postIdx, imgUrl, content,\n" +
                "        (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx and postLike.emogiIdx=0)) as checkEm0,\n" +
                "        (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx and postLike.emogiIdx=1)) as checkEm1,\n" +
                "        (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx and postLike.emogiIdx=2)) as checkEm2,\n" +
                "        (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx and postLike.emogiIdx=3)) as checkEm3\n" +
                "from post\n" +
                "where userIdx = ?\n" +
                "order by createdAt desc, postIdx desc\n" +
                "limit ?,?";
        Object[] getPostParams = new Object[]{userIdx, userIdx, userIdx, userIdx, userIdx, page, PAGE_SIZE};
        List<GetPostRes> posts = this.jdbcTemplate.query(getPostQuery,
                (rsT, rowNum) -> new GetPostRes(
                        rsT.getInt("post.postIdx"),
                        rsT.getString("imgUrl"),
                        rsT.getString("content"),
                        Arrays.asList(rsT.getBoolean("checkEm0"), rsT.getBoolean("checkEm1"),
                                rsT.getBoolean("checkEm2"), rsT.getBoolean("checkEm3"))

                ), getPostParams);

        String getMyPostInfoQuery1 = "select id, np.numOfPost, point\n" +
                "from user as user\n" +
                "    left join (select userIdx, count(*) as numOfPost from post group by userIdx) as np on user.userIdx = np.userIdx\n" +
                "where user.userIdx = ?";
        Object[] getMyPostInfoParam1 = new Object[]{userIdx};

        return this.jdbcTemplate.queryForObject(getMyPostInfoQuery1, (rs, rowNum) -> new GetMyPostRes(
                rs.getString("id"),
                rs.getInt("np.numOfPost"),
                rs.getInt("point"),
                posts
        ), getMyPostInfoParam1);
    }

    public void createEmoji(PostPostEmojiReq postPostEmojiReq) {
        String createEmojiQuery = "insert into ";
        Object[] createEmojiParam = new Object[]{postPostEmojiReq.getUserIdx(),postPostEmojiReq.getPostIdx(),postPostEmojiReq.getEmojiIdx()};

    }

    public void deleteEmoji(PostDeleteEmojiReq postDeleteEmojiReq) {
//        String deleteEmojiQuery = "";
//        Object[] deleteEmojiParam = new Object[]{postDeleteEmojiReq.getUserIdx(),postDeleteEmojiReq.getPostIdx(),postDeleteEmojiReq.};

    }
}