package hackathon.jackpot.user;


import hackathon.jackpot.baserepose.BaseException;
import hackathon.jackpot.user.model.PostLoginReq;
import hackathon.jackpot.user.model.PostLoginRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import static hackathon.jackpot.baserepose.BaseResponseStatus.*;
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //로그인
    public PostLoginRes loginUser(PostLoginReq postLoginReq) throws BaseException {
        try{
            return userRepository.selectUser(postLoginReq);
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
