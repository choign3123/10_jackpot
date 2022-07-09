package hackathon.jackpot.user;

import hackathon.jackpot.baserepose.BaseException;
import hackathon.jackpot.baserepose.BaseResponse;
import hackathon.jackpot.user.model.PostLoginReq;
import hackathon.jackpot.user.model.PostLoginRes;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "https://test-domains.shop")
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    //로그인
    //users/login
    @ResponseBody
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인")
    public BaseResponse<PostLoginRes> loginUser(@RequestBody PostLoginReq postLoginReq){

        try{
            PostLoginRes postLoginRes= userService.loginUser(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }


}
