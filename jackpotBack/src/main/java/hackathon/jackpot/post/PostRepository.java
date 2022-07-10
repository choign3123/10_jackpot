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
        String getPostInfoQuery = "select user.id, post.postIdx, imgUrl, content,\n" +
                "       (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx and postLike.emogiIdx=0)) as checkEm0,\n" +
                "       (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx and postLike.emogiIdx=1)) as checkEm1,\n" +
                "       (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx and postLike.emogiIdx=2)) as checkEm2,\n" +
                "       (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx and postLike.emogiIdx=3)) as checkEm3\n" +
                "from post join user on post.userIdx = user.userIdx\n" +
                "order by createdAt desc, postIdx desc\n" +
                "limit ?, ?;";

        Object[] getPostinfoQuery = new Object[]{userIdx, userIdx, userIdx, userIdx, page, PAGE_SIZE};
        return this.jdbcTemplate.query(getPostInfoQuery,
                (rs, rowNum) -> new GetPostRes(
                        rs.getInt("post.postIdx"),
                        rs.getString("imgUrl"),
                        rs.getString("content"),
                        rs.getString("user.id"),
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
        String searchPostQuery = "select user.id, post.postIdx, imgUrl, content,\n" +
                "        (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx and postLike.emogiIdx=0)) as checkEm0,\n" +
                "        (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx and postLike.emogiIdx=1)) as checkEm1,\n" +
                "        (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx and postLike.emogiIdx=2)) as checkEm2,\n" +
                "        (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx and postLike.emogiIdx=3)) as checkEm3\n" +
                "from post join user on post.userIdx = user.userIdx\n" +
                "where post.content like '%"+q+"%'\n" +
                "order by createdAt desc, postIdx desc\n" +
                "limit ?,?";
        System.out.println(searchPostQuery);
        Object[] searchPostParam = new Object[]{userIdx,userIdx,userIdx,userIdx, page, PAGE_SIZE};
        return this.jdbcTemplate.query(searchPostQuery, (rs, rowNum) -> new GetPostRes(
                rs.getInt("postIdx"),
                rs.getString("imgUrl"),
                rs.getString("content"),
                rs.getString("user.id"),
                Arrays.asList(rs.getBoolean("checkEm0"), rs.getBoolean("checkEm1"),
                        rs.getBoolean("checkEm2"), rs.getBoolean("checkEm3"))

        ), searchPostParam);
    }

    //게시글 my조회
    public GetMyPostRes getMyPostInfo(int userIdx,int page){


        String getPostQuery = "select user.id, post.postIdx, imgUrl, content,\n" +
                "        (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx and postLike.emogiIdx=0)) as checkEm0,\n" +
                "        (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx and postLike.emogiIdx=1)) as checkEm1,\n" +
                "        (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx and postLike.emogiIdx=2)) as checkEm2,\n" +
                "        (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx and postLike.emogiIdx=3)) as checkEm3\n" +
                "from post join user on post.userIdx = user.userIdx\n" +
                "where user.userIdx = ?\n" +
                "order by createdAt desc, postIdx desc\n" +
                "limit ?,?";
        Object[] getPostParams = new Object[]{userIdx, userIdx, userIdx, userIdx, userIdx, page, PAGE_SIZE};
        List<GetPostRes> posts = this.jdbcTemplate.query(getPostQuery,
                (rsT, rowNum) -> new GetPostRes(
                        rsT.getInt("post.postIdx"),
                        rsT.getString("imgUrl"),
                        rsT.getString("content"),
                        rsT.getString("user.id"),
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
        String createEmojiQuery = "insert into postLike(postIdx, userIdx, emogiIdx) values (?,?,?)";
        Object[] createEmojiParam = new Object[]{postPostEmojiReq.getPostIdx(),postPostEmojiReq.getUserIdx(),postPostEmojiReq.getEmojiIdx()};
        this.jdbcTemplate.update(createEmojiQuery,createEmojiParam);

    }

    public void deleteEmoji(PostDeleteEmojiReq postDeleteEmojiReq) {
        String deleteEmojiQuery = "delete from postLike where userIdx=? and postIdx=? and emogiIdx=?";
        Object[] deleteEmojiParam = new Object[]{postDeleteEmojiReq.getUserIdx(),postDeleteEmojiReq.getPostIdx(),postDeleteEmojiReq.getEmojiIdx()};
        this.jdbcTemplate.update(deleteEmojiQuery,deleteEmojiParam);
    }


    public void notifyPost(int postIdx) {
        String notifyPostQuery = "update post set numOfNotify = post.numOfNotify + 1 where postIdx=?";
        this.jdbcTemplate.update(notifyPostQuery,postIdx);
    }

    public int checkNotifyNum(int postIdx) {
        String checkNotifyNumQuery = "select numOfNotify\n" +
                "from post\n" +
                "where postIdx=?;";
        int notifyNum = this.jdbcTemplate.queryForObject(checkNotifyNumQuery,int.class,postIdx);
        return notifyNum;
    }
}