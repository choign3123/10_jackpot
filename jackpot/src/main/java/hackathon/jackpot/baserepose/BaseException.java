package hackathon.jackpot.baserepose;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@AllArgsConstructor
public class BaseException extends Exception {
    private BaseResponseStatus status;

    public BaseException(BaseResponseStatus status){
        this.status = status;
    }

    //public BaseException getStatus;
}