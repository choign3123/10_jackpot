package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers(){
        String getUsersQuery = "select userIdx,name,nickName,email from User";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("name"),
                        rs.getString("nickName"),
                        rs.getString("email")
                ));
    }

    public GetUserRes getUsersByEmail(String email){
        String getUsersByEmailQuery = "select userIdx,name,nickName,email from User where email=?";
        String getUsersByEmailParams = email;
        return this.jdbcTemplate.queryForObject(getUsersByEmailQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("name"),
                        rs.getString("nickName"),
                        rs.getString("email")),
                getUsersByEmailParams);
    }



    public GetUserRes getUsersByIdx(int userIdx){
        String getUsersByIdxQuery = "select userIdx,name,nickName,email from User where userIdx=?";
        int getUsersByIdxParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUsersByIdxQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("name"),
                        rs.getString("nickName"),
                        rs.getString("email")),
                getUsersByIdxParams);
    }


    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into User (name, nickName, phone, email, password) VALUES (?,?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getName(), postUserReq.getNickName(),postUserReq.getPhone(), postUserReq.getEmail(), postUserReq.getPassword()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    public int modifyUserName(PatchUserReq patchUserReq){
        String modifyUserNameQuery = "update User set nickName = ? where userIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getNickName(), patchUserReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }

    public int modifyUserStatus(String status, DeleteUserReq deleteUserReq){
        String modifyUserStatusQuery = "update User set status = ? where userIdx = ?";
        Object[] modifyUserStatusParams = new Object[]{status, deleteUserReq.getUserIdx()};
//        int modifyUserStatusParams = deleteUserReq.getUserIdx();
        return this.jdbcTemplate.update(modifyUserStatusQuery, modifyUserStatusParams);
    }

    //유저 정보 조회
    public GetUserInfoRes selectUserInfo(int userIdx){
        String selectUserInfoQuery = "select u.userIdx as userIdx,\n" +
                "       u.nickName as nickName,\n" +
                "       u.name as name,\n" +
                "       u.profileImgUrl as profileImgUrl,\n" +
                "       u.website as website,\n" +
                "       u.introduce as introduction,\n" +
                "       if(postCount is null, 0, postCount) as postCount,\n" +
                "       if(followerCount is null, 0, followerCount) as followerCount,\n" +
                "       if(followingCount is null, 0, followingCount) as followingCount\n" +
                "from User as u\n" +
                "    left join (select userIdx, count(postIdx) as postCount from Post where status = 'ACTIVE' group by userIDx) p on p.userIdx = u.userIdx\n" +
                "    left join (select followerIdx, count(followIdx) as followerCount from Follow where status = 'ACTIVE' group by followerIdx) fc on fc.followerIdx = u.userIdx\n" +
                "    left join (select followeeIdx, count(followIdx) as followingCount from Follow where status = 'ACTIVE' group by followeeIdx) fec on fec.followeeIdx = u.userIdx\n" +
                "where u.userIdx = ?";
        int selectUserInfoParams = userIdx;
        return this.jdbcTemplate.queryForObject(selectUserInfoQuery,
                (rs, rowNum) -> new GetUserInfoRes(
                        rs.getString("nickName"),
                        rs.getString("name"),
                        rs.getString("profileImgUrl"),
                        rs.getString("website"),
                        rs.getString("introduction"),
                        rs.getInt("followerCount"),
                        rs.getInt("followingCount"),
                        rs.getInt("postCount")
                ),
                selectUserInfoParams);
    }

    //유저 게시물
    public List<GetUserPostsRes> selectUserPosts(int userIdx){
        String selectUserPostsQuery = "select p.postIdx as postIdx,\n" +
                "       pi.postImgUrl as postImgUrl\n" +
                "from Post as p\n" +
                "    left join PostImgUrl as pi on p.postIdx = pi.postIdx and pi.status = 'ACTIVE'\n" +
                "where p.userIdx = ? and p.status = 'ACTIVE'\n" +
                "group by p.postIdx";

        int selectUserPostsParams = userIdx;
        System.out.println("제대로 실행 됐나?");
        return this.jdbcTemplate.query(selectUserPostsQuery,
                (rs, rowNum) -> new GetUserPostsRes(
                        rs.getInt("postIdx"),
                        rs.getString("postImgUrl")
                ),
                selectUserPostsParams
        );
    }

    //유저 idx가 유효한지 확인
    public int checkUserExist(int userIdx){
        String checkUserExistQuery = "select exists(select userIdx from User where userIdx = ?)";
        int checkUserExistParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserExistQuery,
                int.class,
                checkUserExistParams);

    }

    public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select userIdx, name, nickName, email, pw from User where email=?";
        String getPwdParams = postLoginReq.getEmail();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new User(
                        rs.getInt("userIdx"),
                        rs.getString("name"),
                        rs.getString("nickName"),
                        rs.getString("email"),
                        rs.getString("pw")),
                getPwdParams);
    }
}
