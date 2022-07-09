package hackathon.jackpot.post.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostPostLikeReq {
    private int userIdx;
    private int postIdx;
}
