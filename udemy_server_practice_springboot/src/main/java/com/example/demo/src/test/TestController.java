package com.example.demo.src.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:9000")
@RestController
@RequestMapping("/test")
public class TestController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public TestController() {}

    /**
     * 로그 테스트 API
     * [GET] /test/log
     * @return String
     */

    @ResponseBody
    @GetMapping("gana")
    public String testGana(){
        return "sussess in jacpot";
    }
}
