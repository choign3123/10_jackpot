package hackathon.jackpot.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "https://hana-umc.shop")
public class TestController {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @ResponseBody
    @GetMapping("/log")
    public String test(){
        String selectUserQuery = "select id from user where userIdx=1";


        return "테스트 성공 in jackpot";
    }
    @ResponseBody
    @GetMapping("/db")
    public String test12(){
        String selectUserQuery = "select id from user where userIdx=1";
        return this.jdbcTemplate.queryForObject(selectUserQuery,String.class);
    }

}
