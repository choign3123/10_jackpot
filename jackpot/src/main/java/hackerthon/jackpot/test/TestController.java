package hackerthon.jackpot.test;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @ResponseBody
    @GetMapping("/log")
    public String test(){

        return "테스트 성공 in jackpot";
    }


}
