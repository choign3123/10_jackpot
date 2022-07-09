package hackathon.jackpot.user;


import hackathon.jackpot.user.model.PostLoginReq;
import hackathon.jackpot.user.model.PostLoginRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    //로그인
    public PostLoginRes selectUser(PostLoginReq postLoginReq) {
        String selectUserQuery = "select userIdx from user where id =? and password=?";
        Object[] selectUserParam = new Object[]{postLoginReq.getId(),postLoginReq.getPassword()};
        int userIdx = this.jdbcTemplate.queryForObject(selectUserQuery, int.class,selectUserParam);
        return new PostLoginRes(userIdx);

    }
}
