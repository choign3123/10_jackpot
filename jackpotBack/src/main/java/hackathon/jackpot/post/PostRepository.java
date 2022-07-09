package hackathon.jackpot.post;


import hackathon.jackpot.post.model.GetPostRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PostRepository {

    private JdbcTemplate jdbcTemplate;

    private static final int MAXNOTIFIYNUM =10;

    private static final int PAGESIZE =10;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    //게시물 삭제
    public void deletePost(int userIdx, int postIdx) {
        String deletePostQuery = "delete from post where userIdx = ? and postIdx =?";
        Object[] deletePostParam = new Object[]{userIdx,postIdx};
        this.jdbcTemplate.update(deletePostQuery,deletePostParam);
    }

    //게시물전체조회
    public List<GetPostRes> getPostInfo(int userIdx, int page) {
        String getPostInfoQuery = "select post.postIdx, imgUrl, content, if(pl.cnt is null, 0, pl.cnt) as numOfLike, (select exists(select postLikeIdx from postLike where userIdx = ? and postIdx = post.postIdx)) as checkLikd\n" +
                "from post as post\n" +
                "    left join (select postIdx, count(*) as cnt from postLike group by postIdx) as pl on pl.postIdx = post.postIdx\n" +
                "limit ?,?";
        Object[] getPostinfoQuery = new Object[]{userIdx,page,PAGESIZE};
        return this.jdbcTemplate.query(getPostInfoQuery,
                (rs, rowNum) -> new GetPostRes(
                        rs.getString("imgUrl"),
                        rs.getString("content"),
                        rs.getInt("post.postIdx"),
                        rs.getBoolean("numOfLike")
                ),getPostinfoQuery);

    }

}
