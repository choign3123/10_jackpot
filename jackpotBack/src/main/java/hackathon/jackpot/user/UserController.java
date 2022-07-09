package hackathon.jackpot.user;

import hackathon.jackpot.baserepose.BaseException;
import hackathon.jackpot.baserepose.BaseResponse;
import hackathon.jackpot.user.model.PostLoginReq;
import hackathon.jackpot.user.model.PostLoginRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<PostLoginRes> loginUser(@RequestBody PostLoginReq postLoginReq){

        try{
            PostLoginRes postLoginRes= userService.loginUser(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }


}
