package hackathon.jackpot.post.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetPostRes {
    private int postIdx;
    private String imgUrl;
    private String content;
    private int numOfLike;
    private boolean checkLike;
}
