package hackathon.jackpot.test;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @ResponseBody
    @GetMapping("/log/{userIdx1}/{userIdx2}")
    public String test(@PathVariable int userIdx1, @PathVariable int userIdx2){
        System.out.println("userIdx1 : "+userIdx1+"     userIdx2 : " + userIdx2);

        return "테스트 성공 in jackpot";
    }


}
